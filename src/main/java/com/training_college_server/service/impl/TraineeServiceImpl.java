package com.training_college_server.service.impl;

import com.training_college_server.bean.TraineeVipInfo;
import com.training_college_server.dao.BankAccountDao;
import com.training_college_server.dao.CourseDao;
import com.training_college_server.dao.CourseOrderDao;
import com.training_college_server.dao.TraineeDao;
import com.training_college_server.entity.BankAccount;
import com.training_college_server.entity.Course;
import com.training_college_server.entity.CourseOrder;
import com.training_college_server.entity.Trainee;
import com.training_college_server.service.MailService;
import com.training_college_server.service.TraineeService;
//import org.springframework.data.domain.Sort;
import com.training_college_server.service.UpdateOrderStatusService;
import org.springframework.stereotype.Component;
import com.training_college_server.utils.ResultBundle;
import com.training_college_server.utils.TraineeStrategy;

import javax.annotation.Resource;
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

            CourseOrder courseOrder1 = new CourseOrder(
                    courseOrder.getTraineeID(),
                    courseOrder.getCourseID(),
                    courseOrder.getPayment(),
                    courseOrder.getAmount(),
                    courseOrder.getDescription(),
                    trainee_name,
                    course_name,
                    institution_name,
                    add_credits
            );
            // 在数据库中生成订单
            CourseOrder courseOrder2 = courseOrderDao.save(courseOrder1);

            // 该课程已订购人数加一
            // 锁定席位！若15min后用户未支付，则订购人数减一
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

}
