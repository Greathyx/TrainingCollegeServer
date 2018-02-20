package com.training_college_server.controller;

import com.training_college_server.dao.CourseOrderDao;
import com.training_college_server.entity.*;
import com.training_college_server.service.InstitutionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.training_college_server.utils.ResultBundle;

import javax.annotation.Resource;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;


@RestController
@RequestMapping("/institution")
public class InstitutionController {

    @Resource
    private InstitutionService institutionService;

    @Resource
    private CourseOrderDao courseOrderDao;

    /**
     * 机构注册申请
     *
     * @param email        邮箱
     * @param name         机构名称
     * @param password     密码
     * @param location     地址
     * @param faculty      师资介绍
     * @param introduction 机构简介
     * @return ResultBundle
     */
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle institutionApply(String email, String name, String password,
                                         String location, String faculty, String introduction) {

        Institution institution = new Institution(email, name, password, location, faculty, introduction);
        InstitutionApply institutionApply = new InstitutionApply(email, name, password, location, faculty,
                introduction, "register");
        return institutionService.institutionApply(institution, institutionApply);

    }

    /**
     * 机构登陆方法
     *
     * @param code     7位机构登陆码
     * @param password 密码
     * @return ResultBundle
     */
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle institutionLogin(String code, String password) {
        return institutionService.institutionLogin(code, password);
    }

    /**
     * 机构申请修改信息方法
     *
     * @param code              7位机构登陆码
     * @param email             邮箱
     * @param name              机构名称
     * @param password_previous 原密码
     * @param password_new      新密码
     * @param location          地址
     * @param faculty           师资介绍
     * @param introduction      机构简介
     * @return ResultBundle
     */
    @RequestMapping(path = "/editInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle institutionEditInfo(String code, String email, String name,
                                            String password_previous, String password_new,
                                            String location, String faculty,
                                            String introduction) {

        Institution institution = new Institution(code, email, name,
                password_new, location, faculty, introduction);
        return institutionService.institutionEditInfo(institution, password_previous);
    }

    /**
     * 机构发布课程方法
     *
     * @param publisher        机构ID
     * @param name             课程名称
     * @param trainee_amount   学员数量
     * @param periods_per_week 每周课时数
     * @param total_weeks      总周数
     * @param teacher          教师介绍
     * @param type             课程类型
     * @param price            总价格
     * @param start_date       开始日期
     * @param introduction     课程简介
     * @return ResultBundle
     */
    @RequestMapping(path = "/releaseCourse", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle releaseCourse(int publisher, String publisher_name, String name, int trainee_amount,
                                      int periods_per_week, int total_weeks, String teacher, String type,
                                      double price, Date start_date, String introduction, boolean has_classes,
                                      Date book_due_date) {

        Course course = new Course(publisher, publisher_name, name, trainee_amount, periods_per_week,
                total_weeks, teacher, type, price, start_date, introduction, has_classes, book_due_date);
        return institutionService.releaseCourse(course);
    }

    /**
     * 获取机构课程信息
     *
     * @param publisher 机构ID
     * @return ResultBundle
     */
    @RequestMapping(path = "/getCourseInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle getCourseInfo(int publisher) {
        return institutionService.getCourseInfo(publisher);
    }

    /**
     * 获得订课信息或退课信息
     *
     * @param institutionID 机构ID
     * @param status        订单状态
     * @return ResultBundle
     */
    @RequestMapping(path = "/getAllOrdersByStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle getAllOrdersByStatus(int institutionID, String status) {
        return institutionService.getAllOrdersByStatus(institutionID, status);
    }

    /**
     * 根据会员名字获取会员优惠信息
     *
     * @param name 会员姓名
     * @return ResultBundle
     */
    @RequestMapping(path = "/getTraineeInfoByName", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle getTraineeInfoByName(String name) {
        return institutionService.getTraineeInfoByName(name);
    }

    /**
     * 获取所有会员优惠信息
     *
     * @param institutionID 机构ID
     * @param status        订单状态
     * @return ResultBundle
     */
    @RequestMapping(path = "/getAllTraineeInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle getAllTraineeInfo(int institutionID, String status) {
        return institutionService.getAllTraineeInfo(institutionID, status);
    }

    /**
     * 听课登记
     *
     * @param traineeID         学员ID
     * @param courseID          课程ID
     * @param traineeName       学员姓名
     * @param courseName        课程名称
     * @param registration_date 登记日期
     * @return ResultBundle
     */
    @RequestMapping(path = "/courseRegistration", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle courseRegistration(int traineeID, int courseID, int institutionID, String traineeName,
                                           String courseName, String institutionName, Timestamp registration_date) {
        CourseRegistration courseRegistration = new CourseRegistration(traineeID, courseID,
                institutionID, traineeName, courseName, institutionName, registration_date);
        return institutionService.courseRegistration(courseRegistration);
    }

    /**
     * 获取该机构所有听课登记信息
     *
     * @param institutionID 机构ID
     * @return ResultBundle
     */
    @RequestMapping(path = "/getAllRegistrationInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle getAllRegistrationInfo(int institutionID) {
        return institutionService.getAllRegistrationInfo(institutionID);
    }

    /**
     * 获取该机构所有没有登记成绩的学生
     *
     * @param institutionID 机构ID
     * @return ResultBundle
     */
    @RequestMapping(path = "/getAllNoScoreTrainees", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle getAllNoScoreTrainees(int institutionID) {
        return institutionService.getAllNoScoreTrainees(institutionID);
    }

    /**
     * 登记成绩
     *
     * @param course_order_id 订课订单ID
     * @param traineeID       学员ID
     * @param courseID        课程ID
     * @param institutionID   机构ID
     * @param trainee_name    学员姓名
     * @param course_name     课程名称
     * @param scores          成绩
     * @return ResultBundle
     */
    @RequestMapping(path = "/setScores", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle setScores(int course_order_id, int traineeID, int courseID, int institutionID,
                                  String trainee_name, String course_name, String institution_name, int scores) {
        ScoresRegistration scoresRegistration = new ScoresRegistration(traineeID, courseID,
                institutionID, trainee_name, course_name, institution_name, scores);
        return institutionService.setScores(course_order_id, scoresRegistration);
    }

    /**
     * 获取该机构所有学生的登记成绩
     *
     * @param institutionID 机构ID
     * @return ResultBundle
     */
    @RequestMapping(path = "/getAllTraineesScores", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle getAllTraineesScores(int institutionID) {
        return institutionService.getAllTraineesScores(institutionID);
    }

}
