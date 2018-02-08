package com.training_college_server.service;

import org.springframework.stereotype.Component;


@Component
public interface MailService {

    /**
     * 发送邮件的方法
     *
     * @param to 接受邮件地址
     */
    public void sendVerificationCode(String to);

    /**
     * 获取邮件所发验证码
     *
     * @return
     */
    public String getVerificationCode();

    /**
     *
     * 发送管理员处理结果邮件
     *
     * @param to
     * @param title
     * @param content
     */
    public void sendSupervisorReply(String to, String title, String content);

}
