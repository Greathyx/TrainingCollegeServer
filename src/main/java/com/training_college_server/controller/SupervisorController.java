package com.training_college_server.controller;


import com.training_college_server.entity.Supervisor;
import com.training_college_server.service.SupervisorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/supervisor")
public class SupervisorController {

    @Resource
    private SupervisorService supervisorService;

    @RequestMapping("/login")
    public String login() {
        Supervisor supervisor = new Supervisor(970718, "123456");
        return String.valueOf(supervisorService.isValidSupervisor(supervisor));
    }

}
