package com.training_college_server.controller;

import com.training_college_server.entity.Course;
import com.training_college_server.entity.Institution;
import com.training_college_server.entity.InstitutionApply;
import com.training_college_server.service.InstitutionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import utils.ResultBundle;
import javax.annotation.Resource;
import java.sql.Date;


@RestController
@RequestMapping("/institution")
public class InstitutionController {

    @Resource
    private InstitutionService institutionService;

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
    public ResultBundle releaseCourse(int publisher, String name, int trainee_amount,
                                      int periods_per_week, int total_weeks, String teacher, String type,
                                      double price, Date start_date, String introduction) {

        Course course = new Course(publisher, name, trainee_amount, periods_per_week,
                total_weeks, teacher, type, price, start_date, introduction);
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

}
