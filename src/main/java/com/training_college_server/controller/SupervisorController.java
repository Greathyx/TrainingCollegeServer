package com.training_college_server.controller;

import com.training_college_server.entity.Supervisor;
import com.training_college_server.service.SupervisorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import utils.ResultBundle;
import javax.annotation.Resource;


@RestController
@RequestMapping("/supervisor")
public class SupervisorController {

    @Resource
    private SupervisorService supervisorService;

    /**
     *
     * 管理员登陆方法
     *
     * @param supervisor_id
     * @param password
     * @return
     */
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle login(Integer supervisor_id, String password) {
        Supervisor supervisor = new Supervisor(supervisor_id, password);
        if (supervisorService.supervisorLogin(supervisor)) {
            return new ResultBundle<Supervisor>(true, "登陆成功！", supervisor);
        } else {
            return new ResultBundle<Supervisor>(false, "用户名或密码错误！", null);
        }
    }

    /**
     *
     * 获取所有机构注册申请
     *
     * @return
     */
    @RequestMapping(path = "/getAllRegisterApply", method = RequestMethod.GET)
    @ResponseBody
    public ResultBundle getAllRegisterApply() {
        return supervisorService.getAllRegisterApply();
    }

}
