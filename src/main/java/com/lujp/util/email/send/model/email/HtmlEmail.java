package com.lujp.util.email.send.model.email;

import com.lujp.util.email.send.exception.EmailException;
import com.lujp.util.email.send.model.attachment.InlineImageAttachment;
import com.lujp.util.email.send.model.attachment.Attachment;
import lombok.Data;

import javax.activation.DataHandler;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.util.List;

/**
 * 文本邮件
 * @author lujp
 * @date 2021-03-03
 */
@Data
public class HtmlEmail extends AbstractEmail {

    /**
     * 文本
     */
    protected String html;

    /**
     * 内嵌图片附件
     */
    protected List<InlineImageAttachment> inlineImageAttachmentGroup;

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
                msg.setText(this.getHtml(), "utf-8", "html");
            }else{
                // 第一个bodyPart为内容，第一个之后为附件
                Multipart multipart = new MimeMultipart();
                BodyPart textPart = new MimeBodyPart();
                textPart.setContent(this.getHtml(), "text/html;charset=utf-8");
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

                // 内嵌图片附件
                if(this.inlineImageAttachmentGroup != null && !inlineImageAttachmentGroup.isEmpty()) {
                    for(InlineImageAttachment attach : this.inlineImageAttachmentGroup) {
                        BodyPart imagePart = new MimeBodyPart();
                        // 编码一下，不然会乱码
                        imagePart.setFileName(MimeUtility.encodeText(attach.getFileName()));
                        imagePart.setDataHandler(new DataHandler(new ByteArrayDataSource(attach.getInputStream(), new MimetypesFileTypeMap().getContentType(attach.getFileName()))));
                        imagePart.setHeader("Content-ID", "<" + attach.getCid() + ">");
                        multipart.addBodyPart(imagePart);
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
        if (this.html  == null || "".equals(this.html.trim())) {
            throw new EmailException("内容不能为空");
        }
    }
}
