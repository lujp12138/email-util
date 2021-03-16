package com.lujp.util.email.send;

import com.lujp.util.email.send.model.attachment.InlineImageAttachment;
import com.lujp.util.email.send.model.email.HtmlEmail;
import com.lujp.util.email.send.model.email.TextEmail;
import com.lujp.util.email.send.model.attachment.Attachment;
import com.lujp.util.email.send.model.email.AbstractEmail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author lujp
 * @date 2021-03-04
 */
public class EmailBuilder {

    /**
     * 主题
     */
    private String subject;

    /**
     * 发送人email
     */
    private String from;

    /**
     * 收件人email集合
     */
    private List<String> toGroup;

    /**
     * 附件
     */
    private List<Attachment> attachmentGroup;

    /**
     * 内嵌图片附件
     */
    private List<InlineImageAttachment> inlineImageAttachmentGroup;

    /**
     * 邮件类型：text或html，默认为text
     */
    private String emailType = "text";

    /**
     * 内容
     */
    private String content;

    /**
     * 邮件内容
     * @param content
     * @return
     */
    public EmailBuilder content(String content) {
        this.content = content;
        return this;
    }

    /**
     * 发送人email
     * @param from
     * @return
     */
    public EmailBuilder from(String from) {
        this.from = from;
        return this;
    }

    /**
     * 主题
     * @param subject
     * @return
     */
    public EmailBuilder subject(String subject) {
        this.subject = subject;
        return this;
    }

    /**
     * 接收人email
     * @param tos
     * @return
     */
    public EmailBuilder to(String... tos) {
        if (this.toGroup == null) {
            this.toGroup = new ArrayList<>();
        }
        Collections.addAll(this.toGroup, tos);
        return this;
    }

    /**
     * 添加附件
     * @param attachments
     * @return
     */
    public EmailBuilder addAttachment(Attachment... attachments) {
        if (this.attachmentGroup == null) {
            this.attachmentGroup = new ArrayList<>();
        }
        Collections.addAll(this.attachmentGroup, attachments);
        return this;
    }

    /**
     * 添加内联图片附件
     * @param attachments
     * @return
     */
    public EmailBuilder addInlineImageAttachment(InlineImageAttachment... attachments) {
        if (this.inlineImageAttachmentGroup == null) {
            this.inlineImageAttachmentGroup = new ArrayList<>();
        }
        Collections.addAll(this.inlineImageAttachmentGroup, attachments);
        return this;
    }

    /**
     * 文本邮件，文本邮件不能添加图片内容，所以addInlineImageAttachment方法会失效
     * @return
     */
    public EmailBuilder textEmail() {
        this.emailType = "text";
        return this;
    }

    /**
     * html邮件
     * @return
     */
    public EmailBuilder htmlEmail() {
        this.emailType = "html";
        return this;
    }

    /**
     * 构建邮件对象
     * @return
     */
    public AbstractEmail build() {
        AbstractEmail abstractEmail = null;
        if ("text".equals(this.emailType)) {
            abstractEmail = new TextEmail();
            TextEmail textEmail = (TextEmail) abstractEmail;
            textEmail.setText(this.content);
        } else if ("html".equals(this.emailType)) {
            abstractEmail = new HtmlEmail();
            HtmlEmail htmlEmail = (HtmlEmail) abstractEmail;
            htmlEmail.setHtml(this.content);
            htmlEmail.setInlineImageAttachmentGroup(this.inlineImageAttachmentGroup);
        }
        abstractEmail.setFrom(this.from);
        abstractEmail.setToGroup(this.toGroup);
        abstractEmail.setSubject(this.subject);
        abstractEmail.setAttachmentGroup(this.attachmentGroup);
        return abstractEmail;
    }
}
