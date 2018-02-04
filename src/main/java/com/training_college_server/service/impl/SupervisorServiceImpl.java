package com.training_college_server.service.impl;

import com.training_college_server.dao.SupervisorDao;
import com.training_college_server.entity.Supervisor;
import com.training_college_server.service.SupervisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SupervisorServiceImpl implements SupervisorService {

    @Autowired
    private SupervisorDao supervisorDao;

    @Override
    public boolean isValidSupervisor(Supervisor supervisor) {
        boolean isValid = false;
        Supervisor supervisor1 = supervisorDao.findOne(supervisor.getSupervisor_id());

        if (supervisor1 != null && supervisor1.getPassword().equals(supervisor.getPassword())) {
            isValid = true;
        }
        return isValid;
    }

}
