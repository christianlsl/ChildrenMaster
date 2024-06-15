package com.cm.service.impl;

import com.cm.dto.Result;
import com.cm.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender jms;

    public Result sendText(String sender, String receiver, String subject, String text) {
        //建立邮件消息
        SimpleMailMessage mainMessage = new SimpleMailMessage();
        //发送方
        mainMessage.setFrom(sender);
        //接收方
        mainMessage.setTo(receiver);
        //发送的标题
        mainMessage.setSubject(subject);
        //发送的内容
        mainMessage.setText(text);
        //发送邮件
        jms.send(mainMessage);
        return Result.ok();
    }
}
