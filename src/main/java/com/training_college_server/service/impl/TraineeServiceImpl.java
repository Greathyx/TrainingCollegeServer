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
    public boolean hasRegistered(String email) {
        boolean result = false;
        Trainee trainee = traineeDao.findByEmail(email);
        if (trainee != null) {
            result = true;
        }
        return result;
    }

    @Override
    public ResultBundle addTrainee(Trainee trainee, String verificationCode) {

        if (hasRegistered(trainee.getEmail())) {
            return new ResultBundle<Trainee>(false, "该邮箱已被注册！", null);
        } else {
            if (verificationCode.equals(MailService.getVerificationCode())) {
                Trainee trainee2 = traineeDao.save(trainee);
                return new ResultBundle<Trainee>(true, "注册成功！", trainee2);
            }
            return new ResultBundle<Trainee>(false, "验证码错误！", null);
        }

    }

    @Override
    public ResultBundle traineeLogin(String email, String password) {

        Trainee trainee = traineeDao.findByEmail(email);
        if (trainee != null && trainee.getIs_active() && trainee.getPassword().equals(password)) {
            return new ResultBundle<Trainee>(true, "登陆成功！", trainee);
        }
        else if (trainee != null && !trainee.getIs_active()){
            return new ResultBundle<Trainee>(false, "该账号已注销！", null);
        }
        else {
            return new ResultBundle<Trainee>(false, "邮箱或密码错误！", null);
        }

    }


}
