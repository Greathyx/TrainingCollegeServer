package com.training_college_server.service.impl;

import com.training_college_server.dao.InstitutionApplyDao;
import com.training_college_server.dao.InstitutionDao;
import com.training_college_server.dao.SupervisorDao;
import com.training_college_server.entity.Institution;
import com.training_college_server.entity.InstitutionApply;
import com.training_college_server.entity.Supervisor;
import com.training_college_server.service.MailService;
import com.training_college_server.service.SupervisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utils.ResultBundle;
import utils.VerificationCode;

import java.util.ArrayList;
import java.util.List;


@Component
public class SupervisorServiceImpl implements SupervisorService {

    @Autowired
    private SupervisorDao supervisorDao;

    @Autowired
    private InstitutionApplyDao institutionApplyDao;

    @Autowired
    private InstitutionDao institutionDao;

    @Autowired
    private MailService mailService;

    // done表示机构申请已被处理
    private String doneTag = "done";

    @Override
    public boolean supervisorLogin(Supervisor supervisor) {
        boolean isValid = false;
        Supervisor supervisor1 = supervisorDao.findOne(supervisor.getSupervisor_id());

        if (supervisor1 != null && supervisor1.getPassword().equals(supervisor.getPassword())) {
            isValid = true;
        }
        return isValid;
    }

    @Override
    public ResultBundle getAllApplies(String tag) {
        List<InstitutionApply> institutionApplies = institutionApplyDao.findAllByTag(tag);
        ArrayList<InstitutionApply> arrayList = new ArrayList<>();
        // 过滤返回信息中的密码
        for (int i = 0; i < institutionApplies.size(); i++) {
            InstitutionApply institutionApply = new InstitutionApply(
                    institutionApplies.get(i).getInstitution_apply_id(),
                    institutionApplies.get(i).getEmail(),
                    institutionApplies.get(i).getName(),
                    "",
                    institutionApplies.get(i).getLocation(),
                    institutionApplies.get(i).getFaculty(),
                    institutionApplies.get(i).getIntroduction(),
                    institutionApplies.get(i).getTag()
            );
            arrayList.add(institutionApply);
        }
        return new ResultBundle<ArrayList>(true, "success", arrayList);
    }

    @Override
    public ResultBundle approveApply(int institution_apply_id) {

        InstitutionApply institutionApply = institutionApplyDao.findOne(institution_apply_id);

        // 若批准机构注册申请
        if (institutionApply.getTag().equals("register")) {
            // 随机生成7位机构登陆码
            String code = VerificationCode.getInstitutionCode();
            // 若生成的登陆码与数据库中某条记录的登陆码一致，则返回，使界面重新发送请求
            Institution test_institution = institutionDao.findByCode(code);
            if (test_institution != null) {
                return new ResultBundle<Institution>(false, "网络繁忙，请稍后再试。", null);
            }

            // 设置institution_apply表中对应行的tag为"done"，表示该条记录已经处理完成
            institutionApply.setTag(doneTag);
            InstitutionApply institutionApply1 = institutionApplyDao.save(institutionApply);

            Institution institution = new Institution(
                    code,
                    institutionApply1.getEmail(),
                    institutionApply1.getName(),
                    institutionApply1.getPassword(),
                    institutionApply1.getLocation(),
                    institutionApply1.getFaculty(),
                    institutionApply1.getIntroduction()
            );
            Institution institution1 = institutionDao.save(institution);

            return new ResultBundle<Institution>(true, "已批准机构注册申请！", institution1);
        }
        // 若批准机构修改信息申请
        else if (institutionApply.getTag().equals("modify")) {
            // 设置institution_apply表中对应行的tag为"done"，表示该条记录已经处理完成
            institutionApply.setTag(doneTag);
            InstitutionApply institutionApply1 = institutionApplyDao.save(institutionApply);

            Institution institution = institutionDao.findByEmail(institutionApply1.getEmail());

            institution.setName(institutionApply1.getName());
            institution.setPassword(institutionApply1.getPassword());
            institution.setLocation(institutionApply1.getLocation());
            institution.setFaculty(institutionApply1.getFaculty());
            institution.setIntroduction(institutionApply1.getIntroduction());
            // 修改数据库信息
            Institution institution1 = institutionDao.save(institution);

            return new ResultBundle<Institution>(true, "已批准机构修改信息申请！", institution1);

        }
        else {
            return new ResultBundle<Institution>(false, "处理失败！", null);
        }

    }

    @Override
    public ResultBundle rejectApply(int institution_apply_id) {
        InstitutionApply institutionApply = institutionApplyDao.findOne(institution_apply_id);

        // 若驳回机构注册申请
        if (institutionApply.getTag().equals("register")) {
            institutionApply.setTag(doneTag);
            institutionApplyDao.save(institutionApply);
            return new ResultBundle<Institution>(true, "已驳回机构注册申请！", null);
        }
        // 若驳回机构修改信息申请
        else if (institutionApply.getTag().equals("modify")) {
            institutionApply.setTag(doneTag);
            institutionApplyDao.save(institutionApply);
            return new ResultBundle<Institution>(true, "已驳回机构修改信息申请！", null);
        } else {
            return new ResultBundle<Institution>(false, "处理失败！", null);
        }
    }

}
