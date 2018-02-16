package com.training_college_server.service;

import com.training_college_server.entity.CourseOrder;
import com.training_college_server.entity.Trainee;
import utils.ResultBundle;

public interface TraineeService {

    /**
     * 判断邮箱是否已被注册
     *
     * @param email 邮箱
     * @return 邮箱是否已经被注册的布尔值
     */
    boolean hasRegistered(String email);

    /**
     * 获取会员状态，即是否已注销
     *
     * @param trainee_id 会员ID
     * @return 会员资格状态的布尔值
     */
    boolean isActive(int trainee_id);

    /**
     * 注册新用户方法
     *
     * @param trainee          会员对象
     * @param verificationCode 验证码
     * @return ResultBundle
     */
    ResultBundle addTrainee(Trainee trainee, String verificationCode);

    /**
     * 学员登陆方法
     *
     * @param email    邮箱
     * @param password 密码
     * @return ResultBundle
     */
    ResultBundle traineeLogin(String email, String password);

    /**
     * 修改学员信息
     *
     * @param trainee           会员对象
     * @param password_previous 原密码
     * @return ResultBundle
     */
    ResultBundle traineeEditInfo(Trainee trainee, String password_previous);

    /**
     * 获取会员累计消费，等级优惠折扣和积分
     *
     * @param trainee_id 会员ID
     * @return ResultBundle
     */
    ResultBundle getTraineeVipInfo(int trainee_id);

    /**
     * 获取所有机构发布的所有课程
     *
     * @return ResultBundle
     */
    ResultBundle getAllCourses();

    /**
     * 获取所有机构发布的所有课程
     * 不包括班级信息，只有班级数目
     *
     * @return ResultBundle
     */
    ResultBundle getAllCoursesWithClasses();

    /**
     * 生成课程订单
     *
     * @param courseOrder 课程订单对象
     * @return ResultBundle
     */
    ResultBundle generateOrder(CourseOrder courseOrder);

    /**
     * 根据订单状态获取学员所有订单
     *
     * @param traineeID 学员ID
     * @param status 订单状态
     * @return ResultBundle
     */
    ResultBundle getAllOrdersByStatus(int traineeID, String status);

}
