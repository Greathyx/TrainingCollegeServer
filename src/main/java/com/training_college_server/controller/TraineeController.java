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
    public ResultBundle register(String email, String password, String verificationCode) {
        Trainee trainee = new Trainee(email, password);
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

}
