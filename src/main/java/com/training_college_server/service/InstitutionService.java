package com.training_college_server.service;

import com.training_college_server.entity.Institution;
import com.training_college_server.entity.InstitutionApply;
import utils.ResultBundle;

public interface InstitutionService {

    /**
     *
     * 教育机构申请注册方法
     *
     * @param institution
     * @return
     */
    public ResultBundle institutionApply(Institution institution, InstitutionApply institutionApply);

    /**
     *
     * 机构登陆方法
     *
     * @param code
     * @param password
     * @return
     */
    public ResultBundle institutionLogin(String code, String password);

    /**
     *
     * 机构申请修改信息方法
     *
     * @param institution
     * @param password_previous
     * @return
     */
    public ResultBundle institutionEditInfo(Institution institution, String password_previous);

}
