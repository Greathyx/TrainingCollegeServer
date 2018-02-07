package com.training_college_server.service;

import com.training_college_server.entity.Supervisor;
import utils.ResultBundle;

public interface SupervisorService {

    /**
     *
     * 判断管理员用户名密码是否正确
     *
     * @param supervisor
     * @return
     */
    public boolean supervisorLogin(Supervisor supervisor);

    /**
     *
     * 获取所有机构申请信息
     *
     * @return
     */
    public ResultBundle getAllRegisterApply();

}
