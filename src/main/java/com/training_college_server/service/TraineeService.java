package com.training_college_server.service;

import com.training_college_server.entity.Trainee;
import utils.ResultBundle;

public interface TraineeService {

    /**
     * 注册新用户方法
     * @param trainee
     * @param verificationCode
     * @return
     */
    public ResultBundle addTrainee(Trainee trainee, String verificationCode);

}
