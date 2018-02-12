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


@RestController
@RequestMapping("/trainee")
public class TraineeController {

    @Resource
    private TraineeService traineeService;

    @Resource
    private MailService mailService;

    /**
     *
     * 获取会员状态，即是否已注销
     *
     * @param trainee_id
     * @return
     */
    @RequestMapping(path = "/isActive", method = RequestMethod.POST)
    @ResponseBody
    public boolean isActive (int trainee_id) {
        return traineeService.isActive(trainee_id);
    }

    /**
     *
     * 发送验证码
     *
     * @param email
     * @return
     */
    @RequestMapping(path = "/sendVerificationCode", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle sendVerificationCode(String email) {
        if (traineeService.hasRegistered(email)){
            return new ResultBundle<>(false, "该邮箱已被注册！", null);
        }
        else {
            mailService.sendVerificationCode(email);
            return new ResultBundle<>(true, "验证码已发送至您的邮箱，请即时查收！", null);
        }
    }

    /**
     *
     * 学员注册
     *
     * @param email
     * @param password
     * @param verificationCode
     * @return
     */
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle register(String email, String password, String verificationCode) {
        Trainee trainee = new Trainee(email, password);
        return traineeService.addTrainee(trainee, verificationCode);
    }

    /**
     *
     * 学员登陆
     *
     * @param email
     * @param password
     * @return
     */
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle login(String email, String password){
        return traineeService.traineeLogin(email, password);
    }

    /**
     *
     * 学员修改信息方法
     *
     * @param trainee_id
     * @param email
     * @param name
     * @param password_previous
     * @param password_new
     * @param expenditure
     * @param credit
     * @param is_active
     * @return
     */
    @RequestMapping(path = "/traineeEditInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle traineeEditInfo(int trainee_id, String email, String name, String password_previous,
                                        String password_new, double expenditure, int credit, boolean is_active) {

        Trainee trainee = new Trainee(trainee_id,email,name, password_new, expenditure,credit,is_active);
        return  traineeService.traineeEditInfo(trainee, password_previous);
    }

}
