package com.training_college_server.service.impl;

import com.training_college_server.bean.InstitutionEarningInfo;
import com.training_college_server.dao.*;
import com.training_college_server.entity.*;
import com.training_college_server.service.SupervisorService;
import com.training_college_server.utils.SupervisorHelper;
import org.springframework.stereotype.Component;
import com.training_college_server.utils.ResultBundle;
import com.training_college_server.utils.VerificationCode;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Component
public class SupervisorServiceImpl implements SupervisorService {

    @Resource
    private SupervisorDao supervisorDao;

    @Resource
    private InstitutionApplyDao institutionApplyDao;

    @Resource
    private InstitutionDao institutionDao;

    @Resource
    private BankAccountDao bankAccountDao;

    @Resource
    private CourseOrderDao courseOrderDao;

    // done表示机构申请已被处理
    private String doneTag = "done";

    @Override
    public boolean supervisorLogin(Supervisor supervisor) {
        boolean isValid = false;
        Supervisor supervisor1 = supervisorDao.findOne(supervisor.getSupervisor_id());

        if (supervisor1 != null && supervisor1.getPassword().equals(supervisor.getPassword())) {
            isValid = true;
        }
        return isValid;
    }

    @Override
    public ResultBundle getAllApplies(String tag) {
        List<InstitutionApply> institutionApplies = institutionApplyDao.findAllByTag(tag);
        ArrayList<InstitutionApply> arrayList = new ArrayList<>();
        // 过滤返回信息中的密码
        for (int i = 0; i < institutionApplies.size(); i++) {
            InstitutionApply institutionApply = new InstitutionApply(
                    institutionApplies.get(i).getInstitution_apply_id(),
                    institutionApplies.get(i).getEmail(),
                    institutionApplies.get(i).getName(),
                    "",
                    institutionApplies.get(i).getLocation(),
                    institutionApplies.get(i).getFaculty(),
                    institutionApplies.get(i).getIntroduction(),
                    institutionApplies.get(i).getTag()
            );
            arrayList.add(institutionApply);
        }
        return new ResultBundle<ArrayList>(true, "success", arrayList);
    }

    @Override
    public ResultBundle approveApply(int institution_apply_id) {

        InstitutionApply institutionApply = institutionApplyDao.findOne(institution_apply_id);

        // 若批准机构注册申请
        if (institutionApply.getTag().equals("register")) {
            // 随机生成7位机构登陆码
            String code = VerificationCode.getInstitutionCode();
            // 若生成的登陆码与数据库中某条记录的登陆码一致，则返回，使界面重新发送请求
            Institution test_institution = institutionDao.findByCode(code);
            if (test_institution != null) {
                return new ResultBundle<Institution>(false, "网络繁忙，请稍后再试。", null);
            }

            // 设置institution_apply表中对应行的tag为"done"，表示该条记录已经处理完成
            institutionApply.setTag(doneTag);
            InstitutionApply institutionApply1 = institutionApplyDao.save(institutionApply);

            Institution institution = new Institution(
                    code,
                    institutionApply1.getEmail(),
                    institutionApply1.getName(),
                    institutionApply1.getPassword(),
                    institutionApply1.getLocation(),
                    institutionApply1.getFaculty(),
                    institutionApply1.getIntroduction()
            );
            Institution institution1 = institutionDao.save(institution);

            return new ResultBundle<>(true, "已批准机构注册申请！", institution1);
        }
        // 若批准机构修改信息申请
        else if (institutionApply.getTag().equals("modify")) {
            // 设置institution_apply表中对应行的tag为"done"，表示该条记录已经处理完成
            institutionApply.setTag(doneTag);
            InstitutionApply institutionApply1 = institutionApplyDao.save(institutionApply);

            Institution institution = institutionDao.findByEmail(institutionApply1.getEmail());

            institution.setName(institutionApply1.getName());
            institution.setPassword(institutionApply1.getPassword());
            institution.setLocation(institutionApply1.getLocation());
            institution.setFaculty(institutionApply1.getFaculty());
            institution.setIntroduction(institutionApply1.getIntroduction());
            // 修改数据库信息
            Institution institution1 = institutionDao.save(institution);

            return new ResultBundle<>(true, "已批准机构修改信息申请！", institution1);

        } else {
            return new ResultBundle<Institution>(false, "处理失败！", null);
        }

    }

    @Override
    public ResultBundle rejectApply(int institution_apply_id) {
        InstitutionApply institutionApply = institutionApplyDao.findOne(institution_apply_id);

        // 若驳回机构注册申请
        if (institutionApply.getTag().equals("register")) {
            institutionApply.setTag(doneTag);
            institutionApplyDao.save(institutionApply);
            return new ResultBundle<Institution>(true, "已驳回机构注册申请！", null);
        }
        // 若驳回机构修改信息申请
        else if (institutionApply.getTag().equals("modify")) {
            institutionApply.setTag(doneTag);
            institutionApplyDao.save(institutionApply);
            return new ResultBundle<Institution>(true, "已驳回机构修改信息申请！", null);
        } else {
            return new ResultBundle<Institution>(false, "处理失败！", null);
        }
    }

    @Override
    public ResultBundle getToSettleList() {
        List<Institution> institutionList = institutionDao.findAll();
        if (institutionList == null || institutionList.size() == 0) {
            return new ResultBundle<>(false, "暂无已注册机构！", null);
        }

        ArrayList<InstitutionEarningInfo> earningInfos = new ArrayList<>();

        for (int i = 0; i < institutionList.size(); i++) {
            // 根据机构ID在course_order表中找出没有结算的订单项，并将这些项中的payment累加
            List<CourseOrder> orderList = courseOrderDao.findAllByInstitutionIDAndStatusAndSettled
                    (institutionList.get(i).getInstitution_id(), "paid", false);
            if (orderList != null && orderList.size() != 0) {
                double course_earning = 0;
                for (int j = 0; j < orderList.size(); j++) {
                    course_earning += orderList.get(j).getPayment();
                }
                InstitutionEarningInfo earningInfo = new InstitutionEarningInfo(
                        institutionList.get(i).getInstitution_id(),
                        institutionList.get(i).getName(),
                        institutionList.get(i).getEarning(),
                        course_earning,
                        0.8 * course_earning
                );
                earningInfos.add(earningInfo);
            }
        }

        return new ResultBundle<ArrayList>(true, "已获取待结算机构列表！", earningInfos);
    }

    @Override
    public ResultBundle settlePayment(int institutionID, double course_earning) {
        List<CourseOrder> orderList = courseOrderDao.findAllByInstitutionIDAndStatusAndSettled
                (institutionID, "paid", false);

        Institution institution = institutionDao.findOne(institutionID);

        // 将所得金额的的80%结算给机构，并将机构所得存入institution表中对应机构项中
        // 四舍五入保留2位小数
        BigDecimal bigDecimal = new BigDecimal(0.8 * course_earning);
        double institution_earning = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        institution.setEarning(institution.getEarning() + institution_earning);
        institutionDao.save(institution);

        BankAccount institution_account = bankAccountDao.findByHolderAndType(institutionID, "institution");
        institution_account.setBalance(institution_account.getBalance() + institution_earning);
        bankAccountDao.save(institution_account);

        // 20%结算给若水教育，并增加若水教育银行账户余额
        // 四舍五入保留2位小数
        BigDecimal bigDecimal2 = new BigDecimal(0.2 * course_earning);
        double supervisor_earning = bigDecimal2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        BankAccount supervisor_account = bankAccountDao.findByHolderAndType(
                SupervisorHelper.getSupervisorID(), "supervisor"
        );
        supervisor_account.setBalance(supervisor_account.getBalance() + supervisor_earning);
        bankAccountDao.save(supervisor_account);

        // 将course_order表中对应项的settled记为true
        for (int i = 0; i < orderList.size(); i++) {
            orderList.get(i).setSettled(true);
        }
        courseOrderDao.save(orderList);

        return new ResultBundle<>(true, "已将金额结算给该机构！", null);
    }

    /**
     * 获取本年订单数据
     *
     * @param list_all_year 所有年份订单数据
     * @return 本年订单数据
     */
    private ArrayList<CourseOrder> getThisYearStatics(List<CourseOrder> list_all_year) {
        ArrayList<CourseOrder> list_this_year = new ArrayList<>();

        Calendar cal_now = Calendar.getInstance(); // 获取当前时间
        int this_year = cal_now.get(Calendar.YEAR); //获取本年年份

        for (int i = 0; i < list_all_year.size(); i++) {
            Date date = new Date(list_all_year.get(i).getBook_time().getTime());
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            if (cal.get(Calendar.YEAR) == this_year) {
                list_this_year.add(list_all_year.get(i));
            }
        }
        return list_this_year;
    }

    @Override
    public ResultBundle getStatisticsForBarChart() {
        ArrayList<String[]> staticsList = new ArrayList<>();

        List<CourseOrder> all_year_unsubscribe_list = courseOrderDao.findAllByStatus("unsubscribe");
        List<CourseOrder> all_year_paid_list = courseOrderDao.findAllByStatus("paid");

        ArrayList<CourseOrder> this_year_unsubscribe_list = this.getThisYearStatics(all_year_unsubscribe_list);
        ArrayList<CourseOrder> this_year_paid_list = this.getThisYearStatics(all_year_paid_list);

        // 1～12表示1月～12月
        for (int i = 1; i <= 12; i++) {
            String[] statics_unit = new String[2];
            double earning_sum = 0;

            for (int j = 0; j < this_year_unsubscribe_list.size(); j++) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(this_year_unsubscribe_list.get(j).getBook_time());
                int month = cal.get(Calendar.MONTH) + 1;  // 获取月份
                if (month == i) {
                    // 退课差价全部结算给若水教育
                    BigDecimal bigDecimal = new BigDecimal(
                            this_year_unsubscribe_list.get(j).getPayment() - this_year_unsubscribe_list.get(j).getPayback()
                    );
                    double earning = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    earning_sum += earning;
                }
            }

            for (int j = 0; j < this_year_paid_list.size(); j++) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(this_year_paid_list.get(j).getBook_time());
                int month = cal.get(Calendar.MONTH) + 1;  // 获取月份
                if (month == i) {
                    // 20%结算给若水教育
                    BigDecimal bigDecimal = new BigDecimal(this_year_paid_list.get(j).getPayment() * 0.2);
                    double earning = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    earning_sum += earning;
                }
            }
            statics_unit[0] = String.valueOf(i) + "月";
            statics_unit[1] = String.valueOf(earning_sum);
            staticsList.add(statics_unit);
        }
        return new ResultBundle<ArrayList>(true, "已获取本年每月收入统计柱状图数据！", staticsList);
    }

    @Override
    public ResultBundle getStatisticsForPieChart() {
        ArrayList<String[]> staticsList = new ArrayList<>();

        // 获取本年订课分成的收入金额
        List<CourseOrder> all_year_paid_list = courseOrderDao.findAllByStatus("paid");
        ArrayList<CourseOrder> this_year_paid_list = this.getThisYearStatics(all_year_paid_list);

        String[] statics_unit_paid = new String[2];
        statics_unit_paid[0] = "订课分成";
        double paid_earning = 0;
        for (int i = 0; i < this_year_paid_list.size(); i++) {
            // 20%结算给若水教育
            BigDecimal bigDecimal = new BigDecimal(this_year_paid_list.get(i).getPayment() * 0.2);
            double earning = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            paid_earning += earning;
        }
        statics_unit_paid[1] = String.valueOf(paid_earning);
        staticsList.add(statics_unit_paid);

        // 获取本年退课差额的收入金额
        List<CourseOrder> all_year_unsubscribe_list = courseOrderDao.findAllByStatus("unsubscribe");
        ArrayList<CourseOrder> this_year_unsubscribe_list = this.getThisYearStatics(all_year_unsubscribe_list);

        String[] statics_unit_unsubscribe = new String[2];
        statics_unit_unsubscribe[0] = "退课差额";
        double unsubscribe_earning = 0;
        for (int i = 0; i < this_year_unsubscribe_list.size(); i++) {
            BigDecimal bigDecimal = new BigDecimal(
                    this_year_unsubscribe_list.get(i).getPayment() - this_year_unsubscribe_list.get(i).getPayback()
            );
            double earning = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            unsubscribe_earning += earning;
        }
        statics_unit_unsubscribe[1] = String.valueOf(unsubscribe_earning);
        staticsList.add(statics_unit_unsubscribe);

        return new ResultBundle<ArrayList>(true, "已获取本年收入来源占比饼图的数据！", staticsList);
    }

}
