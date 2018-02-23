package com.training_college_server.service.impl;

import com.training_college_server.bean.TraineeVipInfo;
import com.training_college_server.dao.*;
import com.training_college_server.entity.*;
import com.training_college_server.service.MailService;
import com.training_college_server.service.TraineeService;
//import org.springframework.data.domain.Sort;
import com.training_college_server.service.UpdateOrderStatusService;
import com.training_college_server.utils.CourseType;
import com.training_college_server.utils.SupervisorHelper;
import org.springframework.stereotype.Component;
import com.training_college_server.utils.ResultBundle;
import com.training_college_server.utils.TraineeStrategy;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


@Component
public class TraineeServiceImpl implements TraineeService {

    @Resource
    private TraineeDao traineeDao;

    @Resource
    private MailService mailService;

    @Resource
    private CourseDao courseDao;

    @Resource
    private CourseOrderDao courseOrderDao;

    @Resource
    private UpdateOrderStatusService updateOrderStatusService;

    @Resource
    private BankAccountDao bankAccountDao;

    @Resource
    private ScoresRegistrationDao scoresRegistrationDao;

    @Resource
    private CourseRegistrationDao courseRegistrationDao;

    @Override
    public boolean hasRegistered(String email) {
        boolean result = false;
        Trainee trainee = traineeDao.findByEmail(email);
        if (trainee != null) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean isActive(int trainee_id) {
        Trainee trainee = traineeDao.findOne(trainee_id);
        return trainee.getIs_active();
    }

    @Override
    public ResultBundle addTrainee(Trainee trainee, String verificationCode) {

        if (hasRegistered(trainee.getEmail())) {
            return new ResultBundle<Trainee>(false, "该邮箱已被注册！", null);
        } else {
            if (verificationCode.equals(mailService.getVerificationCode())) {
                Trainee trainee2 = traineeDao.save(trainee);
                return new ResultBundle<>(true, "注册成功！", trainee2);
            }
            return new ResultBundle<Trainee>(false, "验证码错误！", null);
        }

    }

    @Override
    public ResultBundle traineeLogin(String email, String password) {

        Trainee trainee = traineeDao.findByEmail(email);
        if (trainee != null && trainee.getIs_active() && trainee.getPassword().equals(password)) {
            return new ResultBundle<>(true, "登陆成功！", trainee);
        } else if (trainee != null && !trainee.getIs_active()) {
            return new ResultBundle<Trainee>(false, "该账号已注销！", null);
        } else {
            return new ResultBundle<Trainee>(false, "邮箱或密码错误！", null);
        }

    }

    @Override
    public ResultBundle traineeEditInfo(Trainee trainee, String password_previous) {
        Trainee trainee1 = traineeDao.findOne(trainee.getTrainee_id());
        // 如果无法根据学员id查询到学员
        if (trainee1 == null) {
            return new ResultBundle<>(false, "该学员未注册，暂无信息！", null);
        } else {
            // 不修改密码的情况，即原密码和新密码输入框的值均为""
            if (trainee.getPassword().equals("") && password_previous.equals("")) {
                Trainee trainee_new = new Trainee(
                        trainee.getTrainee_id(),
                        trainee.getEmail(),
                        trainee.getName(),
                        trainee1.getPassword(), // 写入原密码
                        trainee1.getExpenditure(),
                        trainee1.getCredit(),
                        trainee.getIs_active()
                );
                traineeDao.save(trainee_new);
                return new ResultBundle<>(true, "修改信息成功！", trainee_new);
            }
            // 修改密码的情况
            // 如果输入的原密码与数据库中的原密码不一致
            else if (!trainee1.getPassword().equals(password_previous)) {
                return new ResultBundle<Trainee>(false, "原密码错误！", null);
            } else {
                Trainee trainee_new = new Trainee(
                        trainee.getTrainee_id(),
                        trainee.getEmail(),
                        trainee.getName(),
                        trainee.getPassword(), // 写入新密码
                        trainee1.getExpenditure(),
                        trainee1.getCredit(),
                        trainee.getIs_active()
                );
                traineeDao.save(trainee_new);
                return new ResultBundle<>(true, "修改信息成功！", trainee_new);
            }
        }
    }

    @Override
    public ResultBundle getTraineeVipInfo(int trainee_id) {
        Trainee trainee = traineeDao.findOne(trainee_id);
        if (trainee == null) {
            return new ResultBundle<TraineeVipInfo>(false, "该会员不存在！", null);
        } else {
            int level = TraineeStrategy.getLevel(trainee.getExpenditure());
            double discount = TraineeStrategy.getDiscount(level);
            TraineeVipInfo vipInfo = new TraineeVipInfo(
                    trainee.getExpenditure(),
                    level,
                    discount,
                    trainee.getCredit()
            );
            return new ResultBundle<>(true, "已成功获取会员信息！", vipInfo);
        }
    }

    @Override
    public ResultBundle getAllCourses() {
//        List<Course> courseList = courseDao.findAll(new Sort(Sort.Direction.DESC, "publisher"));
        List<Course> courseList = courseDao.findAllByHasClasses(false);
        return new ResultBundle<List>(true, "已成功获取数据！", courseList);
    }

    @Override
    public ResultBundle getAllCoursesWithClasses() {
        List<Course> courseList = courseDao.findAllByHasClasses(true);
        return new ResultBundle<List>(true, "已成功获取数据！", courseList);
    }

    @Override
    public ResultBundle generateOrder(CourseOrder courseOrder) {

        Trainee trainee = traineeDao.findOne(courseOrder.getTraineeID());
        Course course = courseDao.findOne(courseOrder.getCourseID());

        if (trainee == null || course == null) {
            return new ResultBundle<>(false, "生成订单出错！", null);
        } else {
            // 获取学员姓名，机构和课程名称
            String trainee_name = trainee.getName();
            String institution_name = course.getPublisher_name();
            String course_name = course.getName();
            int add_credits = TraineeStrategy.getCredit(courseOrder.getPayment());
            java.util.Date current_date = new java.util.Date();
            Date book_date = new Date(current_date.getTime());

            CourseOrder courseOrder1 = new CourseOrder(
                    courseOrder.getTraineeID(),
                    courseOrder.getCourseID(),
                    courseOrder.getInstitutionID(),
                    courseOrder.getPayment(),
                    courseOrder.getAmount(),
                    courseOrder.getDescription(),
                    trainee_name,
                    course_name,
                    institution_name,
                    add_credits,
                    book_date
            );
            // 在数据库中生成订单
            CourseOrder courseOrder2 = courseOrderDao.save(courseOrder1);

            // 该课程已订购人数+1
            // 锁定席位！若15min后用户未支付，则订购人数-1
            // 若用户在15mi内自己取消了订单，则订购人数也会-1
            course.setBooked_amount(course.getBooked_amount() + 1);
            courseDao.save(course);

            // 开启改变状态的定时器
            updateOrderStatusService.invalidateOrderStatus(courseOrder2.getCourse_order_id());

            return new ResultBundle<>(true, "已生成订单，请在15分钟内完成支付！", courseOrder2);
        }
    }

    @Override
    public ResultBundle getAllOrdersByStatus(int traineeID, String status) {
        List<CourseOrder> courseOrderList = courseOrderDao.findAllByTraineeIDAndStatus(traineeID, status);
        return new ResultBundle<List>(true, "已成功获取用户订单！", courseOrderList);
    }

    @Override
    public ResultBundle pay(int course_order_id, String identity, String password) {
        CourseOrder courseOrder = courseOrderDao.findOne(course_order_id);
        if (courseOrder == null) {
            return new ResultBundle<>(false, "订单不存在！", null);
        }
        // 订单状态为invalid
        else if (courseOrder.getStatus().equals("invalid")) {
            return new ResultBundle<>(false, "该订单已失效！", null);
        }

        BankAccount bankAccount = bankAccountDao.findByIdentity(identity);
        // 银行账户不存在
        if (bankAccount == null) {
            return new ResultBundle<>(false, "银行账户不存在！", null);
        }
        // 密码不正确
        else if (!bankAccount.getPassword().equals(password)) {
            return new ResultBundle<>(false, "银行密码错误！", null);
        }
        // 在预定后15min内正确付款
        else {
            // 扣除银行账户钱款
            double payment = courseOrder.getPayment();
            bankAccount.setBalance(bankAccount.getBalance() - payment);
            bankAccountDao.save(bankAccount); // 写入数据库

            // 将用户所付钱款打入若水教育账户
            BankAccount supervisor_account = bankAccountDao.findByHolderAndType(
                    SupervisorHelper.getSupervisorID(), "supervisor"
            );
            supervisor_account.setBalance(supervisor_account.getBalance() + payment);
            bankAccountDao.save(supervisor_account); // 写入数据库

            Trainee trainee = traineeDao.findOne(courseOrder.getTraineeID());
            // 增加会员累计消费
            trainee.setExpenditure(trainee.getExpenditure() + payment);

            // 增加会员积分
            int add_credits = courseOrder.getAdd_credits();
            trainee.setCredit(trainee.getCredit() + add_credits);
            traineeDao.save(trainee); // 写入数据库

            // 修改订单状态
            courseOrder.setStatus("paid");
            courseOrderDao.save(courseOrder); // 写入数据库

            return new ResultBundle<>(true, "已成功付款！", null);
        }
    }

    @Override
    public ResultBundle cancelPay(int course_order_id) {
        CourseOrder courseOrder = courseOrderDao.findOne(course_order_id);
        if (courseOrder == null) {
            return new ResultBundle<>(false, "订单不存在！", null);
        }
        // 订单状态为invalid
        else if (courseOrder.getStatus().equals("invalid")) {
            return new ResultBundle<>(false, "该订单已失效！", null);
        }
        // 取消订单
        else {
            // 该课程已订购人数减一
            Course course = courseDao.findOne(courseOrder.getCourseID());
            course.setBooked_amount(course.getBooked_amount() - 1);
            courseDao.save(course);

            // 设置订单状态为invalid
            courseOrder.setStatus("invalid");
            courseOrderDao.save(courseOrder);

            return new ResultBundle<>(true, "已成功取消订单！", null);
        }
    }

    @Override
    public ResultBundle unsubscribe(int course_order_id) {
        CourseOrder courseOrder = courseOrderDao.findOne(course_order_id);
        if (courseOrder == null) {
            return new ResultBundle<>(false, "订单不存在！", null);
        }
        // 订单状态为invalid
        else if (courseOrder.getStatus().equals("invalid")) {
            return new ResultBundle<>(false, "该订单已失效！", null);
        }
        // 退课
        else {
            Course course = courseDao.findOne(courseOrder.getCourseID());
            Date start_date = course.getStart_date();
            Calendar start_cal = Calendar.getInstance(); // 课程开始时间的Calendar对象
            start_cal.setTime(start_date);

            java.util.Date current_date = new java.util.Date();
            Calendar current_cal = Calendar.getInstance(); // 当前时间的Calendar对象
            current_cal.setTime(current_date);

            // 该课程已订购人数减一
            course.setBooked_amount(course.getBooked_amount() - 1);
            courseDao.save(course);

            // 订单状态改为unsubscribe
            courseOrder.setStatus("unsubscribe");
            // 写入退课日期
            java.util.Date date = new java.util.Date();
            Date unsubscribe_date = new Date(date.getTime());
            courseOrder.setUnsubscribe_time(unsubscribe_date);
            CourseOrder courseOrder1 = courseOrderDao.save(courseOrder);

            // 如果在开课日期前退课，则根据距离开课日期的时间的长短，退还一定比例的钱款，
            // 但扣除当时所有获得的会员积分, 且减少相应数目的累计消费金额
            if (current_cal.before(start_cal)) {
                Trainee trainee = traineeDao.findOne(courseOrder1.getTraineeID());
                int add_credits = courseOrder1.getAdd_credits();

                // 扣除当时所有获得的会员积分
                trainee.setCredit(trainee.getCredit() - add_credits);
                Trainee trainee1 = traineeDao.save(trainee); // 写入数据库

                double payment = courseOrder1.getPayment();
                // 获取银行账户
                BankAccount bankAccount = bankAccountDao.findByHolderAndType(courseOrder1.getTraineeID(), "trainee");

                // 获取若水教育银行账户余额
                int supervisor_id = SupervisorHelper.getSupervisorID();
                BankAccount supervisor_account = bankAccountDao.findByHolderAndType(supervisor_id, "supervisor");

                // 存入扣除的积分
                courseOrder1.setMinus_credits(add_credits);

                // 若开课前1周内退课，则退还1/4的钱款
                current_cal.add(Calendar.DATE, 7);
                if (current_cal.after(start_cal)) {
                    // 计算退回的钱，四舍五入保留两位小数
                    BigDecimal bigDecimal = new BigDecimal(0.25 * payment);
                    double money_back = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

                    bankAccount.setBalance(bankAccount.getBalance() + money_back);
                    bankAccountDao.save(bankAccount);

                    // 若水银行账户退款
                    supervisor_account.setBalance(supervisor_account.getBalance() - money_back);
                    bankAccountDao.save(supervisor_account);

                    // 减少相应数目的累计消费金额
                    trainee1.setExpenditure(trainee1.getExpenditure() - money_back);
                    traineeDao.save(trainee1);

                    // 保存退款金额
                    courseOrder1.setPayback(money_back);
                    courseOrderDao.save(courseOrder1);

                    return new ResultBundle<>(true, "已成功退课！且成功退款" + String.valueOf(money_back) + "元！", null);
                }

                // 若开课前2周内退课，则退还1/2的钱款
                current_cal.add(Calendar.DATE, 7);
                if (current_cal.after(start_cal)) {
                    // 计算退回的钱，四舍五入保留两位小数
                    BigDecimal bigDecimal = new BigDecimal(0.5 * payment);
                    double money_back = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

                    bankAccount.setBalance(bankAccount.getBalance() + money_back);
                    bankAccountDao.save(bankAccount);

                    // 若水银行账户退款
                    supervisor_account.setBalance(supervisor_account.getBalance() - money_back);
                    bankAccountDao.save(supervisor_account);

                    // 减少相应数目的累计消费金额
                    trainee1.setExpenditure(trainee1.getExpenditure() - money_back);
                    traineeDao.save(trainee1);

                    // 保存退款金额
                    courseOrder1.setPayback(money_back);
                    courseOrderDao.save(courseOrder1);

                    return new ResultBundle<>(true, "已成功退课！且成功退款" + String.valueOf(money_back) + "元！", null);
                }

                // 若开课前2周以外退课，则退还3/4的钱款
                // 计算退回的钱，四舍五入保留两位小数
                BigDecimal bigDecimal = new BigDecimal(0.75 * payment);
                double money_back = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

                bankAccount.setBalance(bankAccount.getBalance() + money_back);
                bankAccountDao.save(bankAccount);

                // 若水银行账户退款
                supervisor_account.setBalance(supervisor_account.getBalance() - money_back);
                bankAccountDao.save(supervisor_account);

                // 减少相应数目的累计消费金额
                trainee1.setExpenditure(trainee1.getExpenditure() - money_back);
                traineeDao.save(trainee1);

                // 保存退款金额
                courseOrder1.setPayback(money_back);
                courseOrderDao.save(courseOrder1);

                return new ResultBundle<>(true, "已成功退课！且成功退款" + String.valueOf(money_back) + "元！", null);

            } else {
                // 如果在开课日期后退课，则不退钱，但会员积分不会扣除
                // 保存退款金额
                courseOrder1.setMinus_credits(0);
                courseOrder1.setPayback(0);
                courseOrderDao.save(courseOrder1);
                return new ResultBundle<>(true, "已成功退课！", null);
            }
        }
    }

    @Override
    public ResultBundle creditsExchange(int trainee_id, int credits, String identity) {
        BankAccount bankAccount = bankAccountDao.findByIdentity(identity);
        if (bankAccount == null) {
            return new ResultBundle<>(false, "银行账户不存在！", null);
        }

        Trainee trainee = traineeDao.findOne(trainee_id);
        // 如果要求兑换的积分数额大于用户已获积分数额
        if (trainee.getCredit() < credits) {
            return new ResultBundle<>(false, "您暂无要求兑换数额的积分，请您再次核对积分信息！", null);
        }
        // 将积分兑换为卡余额
        else {
            bankAccount.setBalance(bankAccount.getBalance() + credits);
            bankAccountDao.save(bankAccount);

            // 扣除会员兑换数额的积分
            trainee.setCredit(trainee.getCredit() - credits);
            traineeDao.save(trainee);

            return new ResultBundle<>(true, "已成功将" + String.valueOf(credits) + "积分兑换为卡余额！", null);
        }
    }

    @Override
    public ResultBundle getAllScores(int traineeID) {
        List<ScoresRegistration> scoresList = scoresRegistrationDao.findAllByTraineeID(traineeID);
        if (scoresList == null || scoresList.size() == 0) {
            return new ResultBundle<>(false, "暂无该学员成绩！", null);
        }
        return new ResultBundle<List>(true, "已获取该学员成绩！", scoresList);
    }

    @Override
    public ResultBundle getAllCoursesRegistration(int traineeID) {
        List<CourseRegistration> registrationList = courseRegistrationDao.findAllByTraineeID(traineeID);
        if (registrationList == null || registrationList.size() == 0) {
            return new ResultBundle<>(false, "暂无该学员听课记录！", null);
        }
        return new ResultBundle<List>(true, "已获取该学员听课记录！", registrationList);
    }

    @Override
    public ArrayList<CourseOrder> getThisYearStatics(int traineeID) {
        List<CourseOrder> orderList_all_year = courseOrderDao.findAllByTraineeIDAndStatus(traineeID, "paid");
        ArrayList<CourseOrder> list_this_year = new ArrayList<>();

        Calendar cal_now = Calendar.getInstance(); // 获取当前时间
        int this_year = cal_now.get(Calendar.YEAR); //获取本年年份

        for (int i = 0; i < orderList_all_year.size(); i++) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(orderList_all_year.get(i).getBook_time());
            if (cal.get(Calendar.YEAR) == this_year) {
                list_this_year.add(orderList_all_year.get(i));
            }
        }
        return list_this_year;
    }

    @Override
    public ResultBundle getStatisticsForBarChart(int traineeID) {
        ArrayList<String[]> staticsList = new ArrayList<>();

        // 获取本年的数据
        ArrayList<CourseOrder> orderList = this.getThisYearStatics(traineeID);

        // 1～12表示1月～12月
        for (int i = 1; i <= 12; i++) {
            String[] statics_unit = new String[2];
            double payment_sum = 0;

            for (int j = 0; j < orderList.size(); j++) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(orderList.get(j).getBook_time());
                int month = cal.get(Calendar.MONTH) + 1;  // 获取月份
                if (month == i) {
                    payment_sum += orderList.get(j).getPayment();
                }
            }

            statics_unit[0] = String.valueOf(i) + "月";
            statics_unit[1] = String.valueOf(payment_sum);
            staticsList.add(statics_unit);
        }
        return new ResultBundle<ArrayList>(true, "已获取本年每月消费统计柱状图数据！", staticsList);
    }

    @Override
    public ResultBundle getStatisticsForPieChart(int traineeID) {
        ArrayList<String[]> staticsList = new ArrayList<>();

        // 获取本年的数据
        ArrayList<CourseOrder> orderList = this.getThisYearStatics(traineeID);

        // 找出属于订购的同一个类型的课程，并将其支出累加
        String[] typeList = CourseType.getTypeList();
        for (int i = 0; i < typeList.length; i++) {
            String[] statics_unit = new String[2];
            double payment_sum = 0;

            // 将同一类型的课程的支出累加
            for (int j = 0; j < orderList.size(); j++) {
                Course course = courseDao.findOne(orderList.get(j).getCourseID());
                if (course.getType().equals(typeList[i])) {
                    payment_sum += orderList.get(j).getPayment();
                }
            }

            if (payment_sum != 0) {
                statics_unit[0] = typeList[i];
                statics_unit[1] = String.valueOf(payment_sum);
                staticsList.add(statics_unit);
            }
        }
        return new ResultBundle<ArrayList>(true, "已获取本年各类型课程支出占比饼图的数据！", staticsList);
    }

}
