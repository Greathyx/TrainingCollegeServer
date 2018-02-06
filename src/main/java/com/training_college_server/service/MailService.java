package com.training_college_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import utils.VerificationCode;


@Component
public class MailService {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender sender;

    private static String verificationCode = VerificationCode.getVerificationCode();

    /**
     * 发送邮件的方法
     *
     * @param to 接受邮件地址
     */
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

    /**
     * 获取邮件所发验证码
     *
     * @return
     */
    public static String getVerificationCode() {
        return verificationCode;
    }

}
