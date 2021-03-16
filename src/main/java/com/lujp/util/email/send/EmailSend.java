package com.lujp.util.email.send;

import com.lujp.util.email.send.config.EmailConfig;
import com.lujp.util.email.send.model.email.AbstractEmail;
import lombok.Data;

import javax.mail.*;

/**
 * 邮件发送工具
 * @author lujp
 * @date 2021-03-03
 */
@Data
public class EmailSend {

    private EmailConfig emailConfig;

    public EmailSend() {
        this(null);
    }

    public EmailSend(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }

    /**
     * 创建一个session
     * @return
     */
    private Session openSession() {
        Authenticator auth = null;
        if (emailConfig.getAuth()) {
            auth = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailConfig.getUsername(), emailConfig.getPassword());
                }
            };
        }
        Session session = Session.getInstance(emailConfig.properties(), auth);
        if (session != null) {
             session.setDebug(emailConfig.getDebugEnable());
        }
        return session;
    }

    /**
     * 发送邮件
     * @param abstractEmail
     */
    public void send(AbstractEmail abstractEmail) {
        Session session = openSession();
        try {
            Transport.send(abstractEmail.buildMimeMessage(session));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
