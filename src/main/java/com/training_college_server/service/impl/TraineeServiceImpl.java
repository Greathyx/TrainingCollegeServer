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

    @Autowired
    MailService mailService;

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
    public boolean isActive(int trainee_id) {
        Trainee trainee = traineeDao.findOne(trainee_id);
        return trainee.getIs_active();
    }

    @Override
    public ResultBundle addTrainee(Trainee trainee, String verificationCode) {

        if (hasRegistered(trainee.getEmail())) {
            return new ResultBundle<Trainee>(false, "该邮箱已被注册！", null);
        } else {
            if (verificationCode.equals(mailService.getVerificationCode())) {
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

    @Override
    public ResultBundle traineeEditInfo(Trainee trainee, String password_previous) {
        Trainee trainee1 = traineeDao.findOne(trainee.getTrainee_id());
        // 如果无法根据学员id查询到学员
        if (trainee1 == null){
            return new ResultBundle<>(false, "该学员未注册，暂无信息！", null);
        }
        else {
            // 不修改密码的情况，即原密码和新密码输入框的值均为""
            if (trainee.getPassword().equals("") && password_previous.equals("")){
                Trainee trainee_new = new Trainee(
                        trainee.getTrainee_id(),
                        trainee.getEmail(),
                        trainee.getName(),
                        trainee1.getPassword(), // 写入原密码
                        trainee1.getExpenditure(),
                        trainee1.getCredit(),
                        trainee.getIs_active()
                );
                traineeDao.save(trainee_new);
                return new ResultBundle<Trainee>(true, "修改信息成功！", trainee_new);
            }
            // 修改密码的情况
            // 如果输入的原密码与数据库中的原密码不一致
            else if(!trainee1.getPassword().equals(password_previous)){
                return new ResultBundle<Trainee>(false, "原密码错误！", null);
            }
            else {
                Trainee trainee_new = new Trainee(
                        trainee.getTrainee_id(),
                        trainee.getEmail(),
                        trainee.getName(),
                        trainee.getPassword(), // 写入新密码
                        trainee1.getExpenditure(),
                        trainee1.getCredit(),
                        trainee.getIs_active()
                );
                traineeDao.save(trainee_new);
                return new ResultBundle<Trainee>(true, "修改信息成功！", trainee_new);
            }
        }
    }


}
