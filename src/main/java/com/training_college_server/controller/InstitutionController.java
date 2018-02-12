package com.training_college_server.controller;

import com.training_college_server.entity.Course;
import com.training_college_server.entity.Institution;
import com.training_college_server.entity.InstitutionApply;
import com.training_college_server.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import utils.ResultBundle;

import java.sql.Date;


@RestController
@RequestMapping("/institution")
public class InstitutionController {

    @Autowired
    InstitutionService institutionService;

    /**
     *
     * 机构注册申请
     *
     * @param email
     * @param name
     * @param password
     * @param location
     * @param faculty
     * @param introduction
     * @return
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
     *
     * 机构登陆方法
     *
     * @param code
     * @param password
     * @return
     */
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle institutionLogin(String code, String password) {
        return institutionService.institutionLogin(code, password);
    }

    /**
     *
     * 机构申请修改信息方法
     *
     * @param code
     * @param email
     * @param name
     * @param password_previous
     * @param password_new
     * @param location
     * @param faculty
     * @param introduction
     * @return
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
     *
     * 机构发布课程方法
     *
     * @param publisher
     * @param name
     * @param trainee_amount
     * @param periods_per_week
     * @param total_weeks
     * @param teacher
     * @param type
     * @param price
     * @param start_date
     * @param introduction
     * @return
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
     *
     * 获取机构课程信息
     *
     * @param publisher
     * @return
     */
    @RequestMapping(path = "/getCourseInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle getCourseInfo(int publisher) {
        return institutionService.getCourseInfo(publisher);
    }

}
