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

}
