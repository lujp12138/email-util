package com.lujp.util.email.send.model.email;

import com.lujp.util.email.send.exception.EmailException;
import com.lujp.util.email.send.model.attachment.Attachment;
import lombok.Data;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.List;

/**
 * email抽象类
 * @author lujp
 * @date 2021-03-03
 */
@Data
public abstract class AbstractEmail {

    /**
     * 主题
     */
    protected String subject;

    /**
     * 发送人email
     */
    protected String from;

    /**
     * 收件人email集合
     */
    protected List<String> toGroup;

    /**
     * 附件
     */
    protected List<Attachment> attachmentGroup;

     /**
     * 构建MimeMessage
     * @return
     */
    public abstract MimeMessage buildMimeMessage(Session session);

    /**
     * 检查属性，检查不通过，抛出 EmailException 异常
     */
    protected void check() {
        if (this.subject == null || "".equals(this.subject.trim())) {
            throw new EmailException("主题不能为空");
        }
        if (this.from == null || "".equals(this.from.trim())) {
            throw new EmailException("发送人不能为空");
        }
        if (this.toGroup == null || this.toGroup.isEmpty()) {
            throw new EmailException("收件人不能为空");
        }
    }
}
