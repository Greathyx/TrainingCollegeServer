package com.training_college_server.service;

import com.training_college_server.entity.*;
import com.training_college_server.utils.ResultBundle;

import java.util.ArrayList;

public interface InstitutionService {

    /**
     * 教育机构申请注册方法
     *
     * @param institution 机构对象
     * @return ResultBundle
     */
    ResultBundle institutionApply(Institution institution, InstitutionApply institutionApply);

    /**
     * 机构登陆方法
     *
     * @param code     7位机构登陆码
     * @param password 密码
     * @return ResultBundle
     */
    ResultBundle institutionLogin(String code, String password);

    /**
     * 机构申请修改信息方法
     *
     * @param institution       机构对象
     * @param password_previous 原密码
     * @return ResultBundle
     */
    ResultBundle institutionEditInfo(Institution institution, String password_previous);

    /**
     * 机构发布课程方法
     *
     * @param course 课程对象
     * @return ResultBundle
     */
    ResultBundle releaseCourse(Course course);

    /**
     * 获取机构所有课程信息
     *
     * @param publisher 机构ID
     * @return ResultBundle
     */
    ResultBundle getCourseInfo(int publisher);

    /**
     * 获得订课信息或退课信息
     *
     * @param institutionID 机构ID
     * @param status        订单状态
     * @return ResultBundle
     */
    ResultBundle getAllOrdersByStatus(int institutionID, String status);

    /**
     * 根据会员名字获取会员优惠信息
     *
     * @param name 会员姓名
     * @return ResultBundle
     */
    ResultBundle getTraineeInfoByName(String name);

    /**
     * 获取所有会员优惠信息
     *
     * @param institutionID 机构ID
     * @param status        订单状态
     * @return ResultBundle
     */
    ResultBundle getAllTraineeInfo(int institutionID, String status);

    /**
     * 听课登记
     *
     * @param courseRegistration 听课登记对象
     * @return ResultBundle
     */
    ResultBundle courseRegistration(CourseRegistration courseRegistration);

    /**
     * 获取该机构所有听课登记信息
     *
     * @param institutionID 机构ID
     * @return ResultBundle
     */
    ResultBundle getAllRegistrationInfo(int institutionID);

    /**
     * 获取该机构所有没有登记成绩的学生
     *
     * @param institutionID 机构ID
     * @return ResultBundle
     */
    ResultBundle getAllNoScoreTrainees(int institutionID);

    /**
     * 登记成绩
     *
     * @param course_order_id    课程订单ID
     * @param scoresRegistration 成绩登记对象
     * @return ResultBundle
     */
    ResultBundle setScores(int course_order_id, ScoresRegistration scoresRegistration);

    /**
     * 获取该机构所有学生的登记成绩
     *
     * @param institutionID 机构ID
     * @return ResultBundle
     */
    ResultBundle getAllTraineesScores(int institutionID);

    /**
     * 获取本年订单数据
     *
     * @param institutionID 机构ID
     * @param institutionID 订单状态
     * @return 本年订单数据
     */
    ArrayList<CourseOrder> getThisYearStatics(int institutionID, String status);

    /**
     * 获取机构本年每月课程收入及订课人数数据
     *
     * @param institutionID 机构ID
     * @return ResultBundle
     */
    ResultBundle getStatisticsForBarChart(int institutionID);

    /**
     * 获取机构本年各类型课程收入占比饼图数据
     *
     * @param institutionID 机构ID
     * @return ResultBundle
     */
    ResultBundle getStatisticsForPieChart(int institutionID);

    /**
     * 获取机构待分班课程列表
     *
     * @param institutionID 机构ID
     * @return ResultBundle
     */
    ResultBundle getToDivideClassList(int institutionID);

    /**
     * 分配班级方法
     *
     * @param courseID     课程ID
     * @param class_amount 班级数目
     * @return ResultBundle
     */
    ResultBundle divideClasses(int courseID, int class_amount);

}
