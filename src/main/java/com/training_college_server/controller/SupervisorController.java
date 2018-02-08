package com.training_college_server.controller;

import com.training_college_server.entity.Supervisor;
import com.training_college_server.service.MailService;
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

    @Resource
    private MailService mailService;

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

    /**
     *
     * 批准机构注册或修改信息申请
     *
     * @param institution_apply_id
     * @return
     */
    @RequestMapping(path = "/approveApply", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle approveApply(int institution_apply_id) {
        return supervisorService.approveApply(institution_apply_id);
    }

    /**
     *
     * 驳回机构注册或修改信息申请
     *
     * @param institution_apply_id
     * @return
     */
    @RequestMapping(path = "/rejectApply", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle rejectApply(int institution_apply_id) {
        return supervisorService.rejectApply(institution_apply_id);
    }

    /**
     *
     * 发送批准或驳回提示邮件
     *
     * @param to
     * @param title
     * @param content
     */
    @RequestMapping(path = "/sendReplyMail", method = RequestMethod.POST)
    @ResponseBody
    public void sendSupervisorReply(String to, String title, String content) {
        mailService.sendSupervisorReply(to, title, content);
    }

}
