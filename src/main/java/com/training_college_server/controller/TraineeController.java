package com.training_college_server.controller;

import com.training_college_server.entity.Trainee;
import com.training_college_server.service.MailService;
import com.training_college_server.service.TraineeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import utils.ResultBundle;

import javax.annotation.Resource;
import java.util.UUID;


@RestController
@RequestMapping("/trainee")
public class TraineeController {

    @Resource
    private TraineeService traineeService;

    @Resource
    private MailService mailService;


    @RequestMapping(path = "/sendVerificationCode", method = RequestMethod.POST)
    public String sendVerificationCode(String email) {
        mailService.sendVerificationCode(email);
        return "success";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle register(String email, String password, String verificationCode) {
//        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        Trainee trainee = new Trainee(email, password);
        return traineeService.addTrainee(trainee, verificationCode);
    }

}
