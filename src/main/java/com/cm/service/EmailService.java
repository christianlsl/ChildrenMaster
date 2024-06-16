package com.cm.service;

import com.cm.dto.Result;

public interface EmailService {
    /**
     * 普通邮件发送
     *
     * @param sender    发送人
     * @param receiver  发送对象
     * @param subject 主题
     * @param text 内容
     */
    Result sendText(String sender, String receiver, String subject, String text);
}
