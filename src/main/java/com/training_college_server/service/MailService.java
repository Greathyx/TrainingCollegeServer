package com.training_college_server.service;

import org.springframework.stereotype.Component;


@Component
public interface MailService {

    /**
     * 发送邮件的方法
     *
     * @param to 接受邮件地址
     */
    void sendVerificationCode(String to);

    /**
     * 获取邮件所发验证码
     *
     * @return 6位验证码
     */
    String getVerificationCode();

    /**
     * 发送管理员处理结果邮件
     *
     * @param to      目标电子邮箱地址
     * @param title   邮件标题
     * @param content 邮件内容
     */
    void sendSupervisorReply(String to, String title, String content);

}
