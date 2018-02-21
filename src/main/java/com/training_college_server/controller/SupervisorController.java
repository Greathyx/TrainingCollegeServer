package com.training_college_server.controller;

import com.training_college_server.entity.Supervisor;
import com.training_college_server.service.MailService;
import com.training_college_server.service.SupervisorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.training_college_server.utils.ResultBundle;

import javax.annotation.Resource;


@RestController
@RequestMapping("/supervisor")
public class SupervisorController {

    @Resource
    private SupervisorService supervisorService;

    @Resource
    private MailService mailService;

    /**
     * 管理员登陆方法
     *
     * @param supervisor_id 管理员ID
     * @param password      密码
     * @return ResultBundle
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
     * 获取所有机构注册申请
     *
     * @return ResultBundle
     */
    @RequestMapping(path = "/getAllRegisterApply", method = RequestMethod.GET)
    @ResponseBody
    public ResultBundle getAllRegisterApply() {
        String tag = "register"; // 机构申请注册的标签
        return supervisorService.getAllApplies(tag);
    }

    /**
     * 获取所有机构修改信息申请
     *
     * @return ResultBundle
     */
    @RequestMapping(path = "/getAllModifyApply", method = RequestMethod.GET)
    @ResponseBody
    public ResultBundle getAllModifyApply() {
        String tag = "modify"; // 机构申请注册的标签
        return supervisorService.getAllApplies(tag);
    }

    /**
     * 批准机构注册或修改信息申请
     *
     * @param institution_apply_id 机构申请处理项的ID
     * @return ResultBundle
     */
    @RequestMapping(path = "/approveApply", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle approveApply(int institution_apply_id) {
        return supervisorService.approveApply(institution_apply_id);
    }

    /**
     * 驳回机构注册或修改信息申请
     *
     * @param institution_apply_id 机构申请处理项的ID
     * @return ResultBundle
     */
    @RequestMapping(path = "/rejectApply", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle rejectApply(int institution_apply_id) {
        return supervisorService.rejectApply(institution_apply_id);
    }

    /**
     * 发送批准或驳回提示邮件
     *
     * @param to      目标电子邮箱地址
     * @param title   邮件标题
     * @param content 邮件内容
     */
    @RequestMapping(path = "/sendReplyMail", method = RequestMethod.POST)
    @ResponseBody
    public void sendSupervisorReply(String to, String title, String content) {
        mailService.sendSupervisorReply(to, title, content);
    }

    /**
     * 获取所有要结算钱款的机构列表
     *
     * @return ResultBundle
     */
    @RequestMapping(path = "/getToSettleList", method = RequestMethod.GET)
    @ResponseBody
    public ResultBundle getToSettleList() {
        return supervisorService.getToSettleList();
    }

    /**
     * 结算各机构应得钱款
     *
     * @param institutionID 机构ID
     * @return ResultBundle
     */
    @RequestMapping(path = "/settlePayment", method = RequestMethod.POST)
    @ResponseBody
    public ResultBundle settlePayment(int institutionID, double course_earning) {
        return supervisorService.settlePayment(institutionID, course_earning);
    }

    /**
     * 获取若水教育每月收入数据
     *
     * @return ResultBundle
     */
    @RequestMapping(path = "/getStatisticsForBarChart", method = RequestMethod.GET)
    @ResponseBody
    public ResultBundle getStatisticsForBarChart() {
        return supervisorService.getStatisticsForBarChart();
    }

    /**
     * 获取本年收入来源占比饼图数据
     *
     * @return ResultBundle
     */
    @RequestMapping(path = "/getStatisticsForPieChart", method = RequestMethod.GET)
    @ResponseBody
    public ResultBundle getStatisticsForPieChart() {
        return supervisorService.getStatisticsForPieChart();
    }

}
