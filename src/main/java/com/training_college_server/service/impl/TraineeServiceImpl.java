package com.training_college_server.service.impl;

import com.training_college_server.dao.TraineeDao;
import com.training_college_server.entity.Trainee;
import com.training_college_server.service.MailService;
import com.training_college_server.service.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utils.ResultBundle;


@Component
public class TraineeServiceImpl implements TraineeService {

    @Autowired
    TraineeDao traineeDao;

    @Override
    public ResultBundle addTrainee(Trainee trainee, String verificationCode) {

        Trainee trainee1 = traineeDao.findOne(trainee.getTrainee_id());
        if (trainee1 != null) {
            return new ResultBundle<Trainee>(false, "邮箱已被注册！", null);
        } else {
            if (verificationCode.equals(MailService.getVerificationCode())) {
                Trainee trainee2 = traineeDao.save(trainee);
                return new ResultBundle<Trainee>(true, "注册成功！", trainee2);
            }
            return new ResultBundle<Trainee>(false, "验证码错误！", null);
        }
    }

}
