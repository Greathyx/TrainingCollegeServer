package com.training_college_server.service;

import com.training_college_server.entity.Supervisor;

public interface SupervisorService {

    /**
     * 判断管理员用户名密码是否正确
     * @param supervisor
     * @return
     */
    public boolean supervisorLogin(Supervisor supervisor);

}
