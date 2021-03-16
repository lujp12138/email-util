package com.lujp.util.email.send.model.email;

import com.lujp.util.email.send.exception.EmailException;
import com.lujp.util.email.send.model.attachment.Attachment;
import lombok.Data;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;

/**
 * 文本邮件
 * @author lujp
 * @date 2021-03-03
 */
@Data
public class TextEmail extends AbstractEmail {

    /**
     * 文本
     */
    protected String text;

    @Override
    public MimeMessage buildMimeMessage(Session session) {
        check();

        MimeMessage msg = new MimeMessage(session);
        try {
            // 发件人
            msg.setFrom(new InternetAddress(this.getFrom()));
            // 收件人
            InternetAddress[] addrs = new InternetAddress[this.getToGroup().size()];
            for(int i = 0; i < this.getToGroup().size(); i++) {
                addrs[i] = new InternetAddress(this.getToGroup().get(i));
            }
            msg.setRecipients(Message.RecipientType.TO, addrs);
            // 主题
            msg.setSubject(this.getSubject());

            // 没有附件，就采用text方式，如果有附件，采用Multipart方式
            if (this.attachmentGroup == null || this.attachmentGroup.isEmpty()) {
                // 内容
                msg.setText(this.getText(), "utf-8");
            }else{
                // 第一个bodyPart为内容，第一个之后为附件
                Multipart multipart = new MimeMultipart();
                BodyPart textPart = new MimeBodyPart();
                textPart.setContent(this.getText(), "text/plain;charset=utf-8");
                multipart.addBodyPart(textPart);

                // 附件
                if (this.attachmentGroup != null && !attachmentGroup.isEmpty()) {
                    for(Attachment attach : this.attachmentGroup) {
                        BodyPart filePart = new MimeBodyPart();
                        // 编码一下，不然会乱码
                        filePart.setFileName(MimeUtility.encodeText(attach.getFileName()));
                        filePart.setDataHandler(new DataHandler(new ByteArrayDataSource(attach.getInputStream(), "application/octet-stream")));
                        multipart.addBodyPart(filePart);
                    }
                }

                msg.setContent(multipart);
            }
        } catch (Exception e) {
            throw new EmailException(e);
        }
        return msg;
    }

    @Override
    protected void check() {
        super.check();
        if (this.text  == null || "".equals(this.text.trim())) {
            throw new EmailException("内容不能为空");
        }
    }
}
