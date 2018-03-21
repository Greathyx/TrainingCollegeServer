package com.training_college_server.controller;

import com.training_college_server.entity.CourseOrder;
import com.training_college_server.entity.Trainee;
import com.training_college_server.service.MailService;
import com.training_college_server.service.TraineeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.training_college_server.utils.ResultBundle;

import javax.annotation.Resource;


@RestController
@RequestMapping("/trainee")
public class TraineeController {

    @Resource
    private TraineeService traineeService;

    @Resource
    private MailService mailService;

    /**
     * 获取会员状态，即是否已注销
     *
     * @param trainee_id 会员ID
     * @return ResultBundle
     */
    @RequestMapping(path = "/isActive", method = RequestMethod.POST)
    @ResponseBody
    public boolean isActive(int trainee_id) {
        return traineeService.isActive(trainee_id);
    }

    /**
     * 发送验证码
     *
     * @param email 邮箱
     * @return ResultBundle
     */
    @RequestMapping(path = "/sendVerificationCode", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle sendVerificationCode(String email) {
        if (traineeService.hasRegistered(email)) {
            return new ResultBundle<>(false, "该邮箱已被注册！", null);
        } else {
            mailService.sendVerificationCode(email);
            return new ResultBundle<>(true, "验证码已发送至您的邮箱，请即时查收！", null);
        }
    }

    /**
     * 学员注册
     *
     * @param email            邮箱
     * @param password         密码
     * @param verificationCode 验证码
     * @return ResultBundle
     */
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle register(String email, String password, String name, String verificationCode) {
        Trainee trainee = new Trainee(email, password, name);
        return traineeService.addTrainee(trainee, verificationCode);
    }

    /**
     * 学员登陆
     *
     * @param email    邮箱
     * @param password 密码
     * @return ResultBundle
     */
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle login(String email, String password) {
        return traineeService.traineeLogin(email, password);
    }

    /**
     * 学员修改信息方法
     *
     * @param trainee_id        会员ID
     * @param email             会员邮箱
     * @param name              会员真实姓名
     * @param password_previous 原密码
     * @param password_new      新密码
     * @param expenditure       余额
     * @param credit            积分
     * @param is_active         会员资格状态
     * @return ResultBundle
     */
    @RequestMapping(path = "/traineeEditInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle traineeEditInfo(int trainee_id, String email, String name, String password_previous,
                                        String password_new, double expenditure, int credit, boolean is_active) {

        Trainee trainee = new Trainee(trainee_id, email, name, password_new, expenditure, credit, is_active);
        return traineeService.traineeEditInfo(trainee, password_previous);
    }

    /**
     * 获取会员累计消费，等级，优惠折扣和积分
     *
     * @param trainee_id 会员ID
     * @return ResultBundle
     */
    @RequestMapping(path = "/getTraineeVipInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle getTraineeVipInfo(int trainee_id) {
        return traineeService.getTraineeVipInfo(trainee_id);
    }

    /**
     * 获取所有机构发布的所有不分班课程
     *
     * @return ResultBundle
     */
    @RequestMapping(path = "/getAllCourses", method = RequestMethod.GET)
    @ResponseBody
    public ResultBundle getAllCourses() {
        return traineeService.getAllCourses();
    }

    /**
     * 获取所有机构发布的所有分班课程
     *
     * @return ResultBundle
     */
    @RequestMapping(path = "/getAllCoursesWithClasses", method = RequestMethod.GET)
    @ResponseBody
    public ResultBundle getAllCoursesWithClasses() {
        return traineeService.getAllCoursesWithClasses();
    }

    /**
     * 生成课程订单
     *
     * @param traineeID   学员ID
     * @param courseID    课程ID
     * @param payment     实付课程费用
     * @param amount      订课人数
     * @param description 附加信息
     * @return ResultBundle
     */
    @RequestMapping(path = "/generateOrder", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle generateOrder(int traineeID, int courseID, int institutionID, double payment, int amount, String description, int use_credit) {
        CourseOrder courseOrder = new CourseOrder(traineeID, courseID, institutionID, payment, amount, description, use_credit);
        return traineeService.generateOrder(courseOrder);
    }

    /**
     * 根据订单状态获取学员所有订单
     *
     * @param traineeID 学员ID
     * @return ResultBundle
     */
    @RequestMapping(path = "/getAllOrdersByStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle getAllOrdersByStatus(int traineeID, String status) {
        return traineeService.getAllOrdersByStatus(traineeID, status);
    }

    /**
     * 线上支付课程费用
     *
     * @param course_order_id 课程订单ID
     * @param identity        银行账号
     * @param password        密码
     * @return ResultBundle
     */
    @RequestMapping(path = "/pay", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle pay(int course_order_id, String identity, String password) {
        return traineeService.pay(course_order_id, identity, password);
    }

    /**
     * 取消课程订单
     *
     * @param course_order_id 课程订单ID
     * @return ResultBundle
     */
    @RequestMapping(path = "/cancelPay", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle cancelPay(int course_order_id) {
        return traineeService.cancelPay(course_order_id);
    }

    /**
     * 用户退课
     *
     * @param course_order_id 课程订单ID
     * @return ResultBundle
     */
    @RequestMapping(path = "/unsubscribe", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle unsubscribe(int course_order_id) {
        return traineeService.unsubscribe(course_order_id);
    }

    /**
     * 积分兑换卡余额方法
     *
     * @param trainee_id 会员ID
     * @param credits    要兑换的积分数额
     * @param identity   银行卡号
     * @return ResultBundle
     */
    @RequestMapping(path = "/creditsExchange", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle creditsExchange(int trainee_id, int credits, String identity) {
        return traineeService.creditsExchange(trainee_id, credits, identity);
    }

    /**
     * 获取学员所有课程成绩
     *
     * @param traineeID 学员ID
     * @return ResultBundle
     */
    @RequestMapping(path = "/getAllScores", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle getAllScores(int traineeID) {
        return traineeService.getAllScores(traineeID);
    }

    /**
     * 获取学员所有听课登记记录
     *
     * @param traineeID 学员ID
     * @return ResultBundle
     */
    @RequestMapping(path = "/getAllCoursesRegistration", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle getAllCoursesRegistration(int traineeID) {
        return traineeService.getAllCoursesRegistration(traineeID);
    }

    /**
     * 获取学员本年每月消费统计柱状图数据
     *
     * @param traineeID 学员ID
     * @return ResultBundle
     */
    @RequestMapping(path = "/getStatisticsForBarChart", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle getStatisticsForBarChart(int traineeID) {
        return traineeService.getStatisticsForBarChart(traineeID);
    }

    /**
     * 获取学员本年各类型课程支出占比饼图数据
     *
     * @param traineeID 学员ID
     * @return ResultBundle
     */
    @RequestMapping(path = "/getStatisticsForPieChart", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle getStatisticsForPieChart(int traineeID) {
        return traineeService.getStatisticsForPieChart(traineeID);
    }

}
