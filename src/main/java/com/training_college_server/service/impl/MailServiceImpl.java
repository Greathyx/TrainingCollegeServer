package com.training_college_server.service.impl;

import com.training_college_server.service.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import com.training_college_server.utils.VerificationCode;

import javax.annotation.Resource;


@Component
public class MailServiceImpl implements MailService {

    @Value("${spring.mail.username}")
    private String from;

    @Resource
    private JavaMailSender sender;

    private String verificationCode = VerificationCode.getVerificationCode();

    public void sendVerificationCode(String to) {
        SimpleMailMessage mail = new SimpleMailMessage();
        String title = "欢迎注册\"若水\"教育";
        mail.setFrom(from); // 发送者
        mail.setTo(to); // 接受者
        mail.setSubject(title); // 发送标题
        String content = "您的注册验证码是：" + verificationCode + "。请尽快对您的账号进行验证。";
        mail.setText(content);  // 发送内容
        sender.send(mail);
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    @Override
    public void sendSupervisorReply(String to, String title, String content) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(from); // 发送者
        mail.setTo(to); // 接受者
        mail.setSubject(title); // 发送标题
        mail.setText(content);  // 发送内容
        sender.send(mail);
    }

}
