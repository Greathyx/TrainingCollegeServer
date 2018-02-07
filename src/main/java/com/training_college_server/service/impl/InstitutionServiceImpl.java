package com.training_college_server.service.impl;

import com.training_college_server.dao.InstitutionApplyDao;
import com.training_college_server.dao.InstitutionDao;
import com.training_college_server.entity.Institution;
import com.training_college_server.entity.InstitutionApply;
import com.training_college_server.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utils.ResultBundle;


@Component
public class InstitutionServiceImpl implements InstitutionService {

    @Autowired
    InstitutionDao institutionDao;

    @Autowired
    InstitutionApplyDao institutionApplyDao;

    @Override
    public ResultBundle institutionApply(Institution institution, InstitutionApply institutionApply) {
        Institution institution1 = institutionDao.findByEmail(institution.getEmail());
        if (institution1 != null) {
            return new ResultBundle<Institution>(false, "该邮箱已被注册！", null);
        } else {
            institutionApplyDao.save(institutionApply);
            String message = "正在等待管理员验证，验证通过后，我们会第一时间发邮件通知您，请注意查收。";
            return new ResultBundle<Institution>(true, message, institution);
        }
    }


}
