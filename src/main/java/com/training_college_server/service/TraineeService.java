package com.training_college_server.service;

import com.training_college_server.entity.Trainee;
import utils.ResultBundle;

public interface TraineeService {

    /**
     *
     * 判断邮箱是否已被注册
     *
     * @param email
     * @return
     */
    public boolean hasRegistered(String email);

    /**
     *
     * 注册新用户方法
     *
     * @param trainee
     * @param verificationCode
     * @return
     */
    public ResultBundle addTrainee(Trainee trainee, String verificationCode);

    /**
     *
     * 学员登陆方法
     *
     * @param email
     * @param password
     * @return
     */
    public ResultBundle traineeLogin(String email, String password);

}