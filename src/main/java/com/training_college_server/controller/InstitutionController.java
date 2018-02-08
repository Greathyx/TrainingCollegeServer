package com.training_college_server.controller;

import com.training_college_server.entity.Institution;
import com.training_college_server.entity.InstitutionApply;
import com.training_college_server.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import utils.ResultBundle;


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

}
