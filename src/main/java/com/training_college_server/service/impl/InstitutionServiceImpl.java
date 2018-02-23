package com.training_college_server.service.impl;

import com.training_college_server.bean.TraineeInfoForInstitution;
import com.training_college_server.dao.*;
import com.training_college_server.entity.*;
import com.training_college_server.service.InstitutionService;
import com.training_college_server.utils.CourseType;
import com.training_college_server.utils.TraineeStrategy;
import org.springframework.stereotype.Component;
import com.training_college_server.utils.ResultBundle;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


@Component
public class InstitutionServiceImpl implements InstitutionService {

    @Resource
    private InstitutionDao institutionDao;

    @Resource
    private InstitutionApplyDao institutionApplyDao;

    @Resource
    private CourseDao courseDao;

    @Resource
    private CourseOrderDao courseOrderDao;

    @Resource
    private TraineeDao traineeDao;

    @Resource
    private CourseRegistrationDao courseRegistrationDao;

    @Resource
    private ScoresRegistrationDao scoresRegistrationDao;

    @Override
    public ResultBundle institutionApply(Institution institution, InstitutionApply institutionApply) {
        Institution institution1 = institutionDao.findByEmail(institution.getEmail());
        if (institution1 != null) {
            return new ResultBundle<Institution>(false, "该邮箱已被注册！", null);
        } else {
            institutionApplyDao.save(institutionApply);
            String message = "正在等待管理员验证，验证通过后，我们会第一时间发邮件通知您，请注意查收。";
            return new ResultBundle<>(true, message, institution);
        }
    }

    @Override
    public ResultBundle institutionLogin(String code, String password) {
        Institution institution = institutionDao.findByCode(code);
        if (institution != null && institution.getPassword().equals(password)) {
            return new ResultBundle<>(true, "登陆成功！", institution);
        } else {
            return new ResultBundle<Institution>(false, "登陆码或密码错误！", null);

        }
    }

    @Override
    public ResultBundle institutionEditInfo(Institution institution, String password_previous) {
        if (institution.getCode() == null) {
            return new ResultBundle<Institution>(false, "修改机构信息出错！", null);
        } else {
            // modify表示机构申请修改信息
            String modifyTag = "modify";

            Institution institution1 = institutionDao.findByCode(institution.getCode());
            // 如果无法根据机构注册码查询到该机构
            if (institution1 == null) {
                return new ResultBundle<Institution>(false, "该机构未注册，暂无信息！", null);
            }
            // 不修改密码的情况，即原密码和新密码输入框的值均为""
            else if (institution.getPassword().equals("") && password_previous.equals("")) {
                InstitutionApply institutionApply = new InstitutionApply(
                        institution.getEmail(),
                        institution.getName(),
                        institution1.getPassword(), // 写入原密码
                        institution.getLocation(),
                        institution.getFaculty(),
                        institution.getIntroduction(),
                        modifyTag
                );
                institutionApplyDao.save(institutionApply);
                return new ResultBundle<>(true, "已向管理员申请修改信息，我们会通过邮件告诉您申请结果。", institutionApply);
            }
            // 修改密码的情况
            // 如果输入的原密码与数据库中的原密码不一致
            else if (!institution1.getPassword().equals(password_previous)) {
                return new ResultBundle<Institution>(false, "原密码错误！", null);
            }
            // 修改机构信息
            else {
                InstitutionApply institutionApply = new InstitutionApply(
                        institution.getEmail(),
                        institution.getName(),
                        institution.getPassword(), // 写入新密码
                        institution.getLocation(),
                        institution.getFaculty(),
                        institution.getIntroduction(),
                        modifyTag
                );
                institutionApplyDao.save(institutionApply);
                return new ResultBundle<>(true, "已向管理员申请修改信息，我们会通过邮件告诉您申请结果。", institutionApply);
            }
        }
    }

    @Override
    public ResultBundle releaseCourse(Course course) {
        Institution institution = institutionDao.findOne(course.getPublisher());
        if (institution == null) {
            return new ResultBundle<Course>(false, "该机构未注册！", null);
        } else {
            Course course1 = courseDao.save(course);
            return new ResultBundle<>(true, "课程已成功发布！", course1);
        }
    }

    @Override
    public ResultBundle getCourseInfo(int publisher) {
        List<Course> courseList = courseDao.findAllByPublisher(publisher);
        return new ResultBundle<List>(true, "已获取机构课程信息！", courseList);
    }

    @Override
    public ResultBundle getAllOrdersByStatus(int institutionID, String status) {
        List<CourseOrder> orderList = courseOrderDao.findAllByInstitutionIDAndStatus(institutionID, status);
        if (orderList == null) {
            return new ResultBundle<>(false, "暂无订课信息！", null);
        } else {
            return new ResultBundle<List>(false, "已获取订课信息！", orderList);
        }
    }

    @Override
    public ResultBundle getTraineeInfoByName(String name) {
        List<Trainee> traineeList = traineeDao.findAllByName(name);
        if (traineeList == null || traineeList.size() == 0) {
            return new ResultBundle<>(false, "暂无该学员优惠信息！", null);
        } else {
            ArrayList<TraineeInfoForInstitution> traineeArr = new ArrayList<>();
            for (int i = 0; i < traineeList.size(); i++) {
                int level = TraineeStrategy.getLevel(traineeList.get(i).getExpenditure());
                double discount = TraineeStrategy.getDiscount(level);
                TraineeInfoForInstitution traineeInfo = new TraineeInfoForInstitution(
                        traineeList.get(i).getTrainee_id(),
                        traineeList.get(i).getName(),
                        traineeList.get(i).getEmail(),
                        level,
                        discount
                );
                traineeArr.add(traineeInfo);
            }
            return new ResultBundle<ArrayList>(true, "已获取学员优惠信息！", traineeArr);
        }
    }

    @Override
    public ResultBundle getAllTraineeInfo(int institutionID, String status) {
        List<CourseOrder> orderList = courseOrderDao.findAllByInstitutionIDAndStatus(institutionID, status);
        if (orderList == null || orderList.size() == 0) {
            return new ResultBundle<>(false, "暂无该学员的优惠信息！", null);
        }

        ArrayList<TraineeInfoForInstitution> traineeArr = new ArrayList<>();
        for (int i = 0; i < orderList.size(); i++) {
            boolean canAdd = true; // 是否是同一个学员订过两次或以上课程
            for (int j = 0; j < traineeArr.size(); j++) {
                if (orderList.get(i).getTraineeID() == traineeArr.get(j).getTraineeID()){
                    canAdd = false;
                    break;
                }
            }
            if (canAdd) {
                Trainee trainee = traineeDao.findOne(orderList.get(i).getTraineeID());
                int level = TraineeStrategy.getLevel(trainee.getExpenditure());
                double discount = TraineeStrategy.getDiscount(level);
                TraineeInfoForInstitution traineeInfo = new TraineeInfoForInstitution(
                        trainee.getTrainee_id(),
                        trainee.getName(),
                        trainee.getEmail(),
                        level,
                        discount
                );
                traineeArr.add(traineeInfo);
            }
        }
        return new ResultBundle<ArrayList>(true, "已获取所有学员优惠信息！", traineeArr);
    }

    @Override
    public ResultBundle courseRegistration(CourseRegistration courseRegistration) {
        CourseRegistration courseRegistration1 = courseRegistrationDao.save(courseRegistration);
        return new ResultBundle<>(true, "已录入听课登记记录！", courseRegistration1);
    }

    @Override
    public ResultBundle getAllRegistrationInfo(int institutionID) {
        List<CourseRegistration> registrationList = courseRegistrationDao.findAllByInstitutionID(institutionID);
        return new ResultBundle<>(true, "已成功获取该机构听课登记信息！", registrationList);
    }

    @Override
    public ResultBundle getAllNoScoreTrainees(int institutionID) {
        List<CourseOrder> orderList = courseOrderDao.findAllByInstitutionIDAndStatusAndScore(institutionID, "paid", false);
        if (orderList == null || orderList.size() == 0) {
            return new ResultBundle<>(false, "暂无未登记成绩的学员！", null);
        }
        return new ResultBundle<List>(true, "已获取未登记成绩的学员！", orderList);
    }

    @Override
    public ResultBundle setScores(int course_order_id, ScoresRegistration scoresRegistration) {
        CourseOrder courseOrder = courseOrderDao.findOne(course_order_id);
        if (courseOrder == null) {
            return new ResultBundle<>(false, "暂无学员订购该课程！", null);
        }
        courseOrder.setScore(true);
        courseOrderDao.save(courseOrder);

        scoresRegistrationDao.save(scoresRegistration);

        return new ResultBundle<>(true, "已成功登记该学员成绩！", null);
    }

    @Override
    public ResultBundle getAllTraineesScores(int institutionID) {
        List<ScoresRegistration> scoresList = scoresRegistrationDao.findAllByInstitutionID(institutionID);
        if (scoresList == null || scoresList.size() == 0) {
            return new ResultBundle<>(false, "暂无该机构学员成绩！", null);
        }
        return new ResultBundle<List>(true, "已获取该机构学员成绩！", scoresList);
    }

    @Override
    public ArrayList<CourseOrder> getThisYearStatics(int institutionID, String status) {
        List<CourseOrder> orderList_all_year = courseOrderDao.findAllByInstitutionIDAndStatus(institutionID, status);
        ArrayList<CourseOrder> list_this_year = new ArrayList<>();

        Calendar cal_now = Calendar.getInstance(); // 获取当前时间
        int this_year = cal_now.get(Calendar.YEAR); //获取本年年份

        if (status.equals("paid")) {
            for (int i = 0; i < orderList_all_year.size(); i++) {
                Date date = orderList_all_year.get(i).getBook_time();
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                if (cal.get(Calendar.YEAR) == this_year) {
                    list_this_year.add(orderList_all_year.get(i));
                }
            }
        }
        else if(status.equals("unsubscribe")) {
            for (int i = 0; i < orderList_all_year.size(); i++) {
                Date date = orderList_all_year.get(i).getUnsubscribe_time();
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                if (cal.get(Calendar.YEAR) == this_year) {
                    list_this_year.add(orderList_all_year.get(i));
                }
            }
        }

        return list_this_year;
    }

    @Override
    public ResultBundle getStatisticsForBarChart(int institutionID) {
        ArrayList<String[]> staticsList = new ArrayList<>();

        // 获取本年的数据
        ArrayList<CourseOrder> orderList = this.getThisYearStatics(institutionID, "paid");

        // 1～12表示1月～12月
        for (int i = 1; i <= 12; i++) {
            String[] statics_unit = new String[3];
            double earning_sum = 0;
            int booked_amount = 0;

            for (int j = 0; j < orderList.size(); j++) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(orderList.get(j).getBook_time());
                int month = cal.get(Calendar.MONTH) + 1;  // 获取月份
                if (month == i) {
                    // 80%结算给机构
                    BigDecimal bigDecimal = new BigDecimal(orderList.get(j).getPayment() * 0.8);
                    double earning = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    earning_sum += earning;
                    // 订课人数+1
                    booked_amount++;
                }
            }

            statics_unit[0] = String.valueOf(i) + "月";
            statics_unit[1] = String.valueOf(earning_sum);
            statics_unit[2] = String.valueOf(booked_amount);
            staticsList.add(statics_unit);
        }
        return new ResultBundle<ArrayList>(true, "已获取本年每月消费统计柱状图数据！", staticsList);
    }

    @Override
    public ResultBundle getStatisticsForPieChart(int institutionID) {
        ArrayList<String[]> staticsList = new ArrayList<>();

        // 获取本年的数据
        ArrayList<CourseOrder> orderList = this.getThisYearStatics(institutionID, "paid");

        // 找出属于订购的同一个类型的课程，并将其收入累加
        String[] typeList = CourseType.getTypeList();
        for (int i = 0; i < typeList.length; i++) {
            String[] statics_unit = new String[2];
            double payment_sum = 0;

            // 将同一类型的课程的收入累加
            for (int j = 0; j < orderList.size(); j++) {
                Course course = courseDao.findOne(orderList.get(j).getCourseID());
                if (course.getType().equals(typeList[i])) {
                    // 80%结算给机构
                    BigDecimal bigDecimal = new BigDecimal(orderList.get(j).getPayment() * 0.8);
                    double earning = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    payment_sum += earning;
                }
            }

            if (payment_sum != 0) {
                statics_unit[0] = typeList[i];
                statics_unit[1] = String.valueOf(payment_sum);
                staticsList.add(statics_unit);
            }
        }
        return new ResultBundle<ArrayList>(true, "已获取本年各类型课程收入占比饼图的数据！", staticsList);
    }

}
