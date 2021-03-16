package com.lujp.util.email.send;

import com.lujp.util.email.send.config.EmailConfig;
import com.lujp.util.email.send.model.attachment.Attachment;
import com.lujp.util.email.send.model.attachment.InlineImageAttachment;
import com.lujp.util.email.send.model.email.AbstractEmail;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author lujp
 * @date 2021-03-03
 */
public class EmailSendTest {

    private static final String USERNAME = "*";
    private static final String PASSWORD = "*";
    private static final String FORM = USERNAME;
    private static final List<String> TO_GROUP = Arrays.asList(FORM);

    private EmailSend emailSend;

    @Before
    public void setUp() throws Exception {
        EmailConfig emailConfig = new EmailConfig.EmailConfigBuilder()
                .setDebugEnable(true)
                .setUsername(USERNAME)
                .setPassword(PASSWORD)
                .build();
        emailSend = new EmailSend(emailConfig);
    }

    @Test
    public void sendText() {
        AbstractEmail email = new EmailBuilder()
                .from(FORM)
                .to(TO_GROUP.toArray(new String[TO_GROUP.size()]))
                .subject("普通文本邮件")
                .content("内容")
                .textEmail()
                .build();
        emailSend.send(email);
    }

    @Test
    public void sendTextAddAttachment() {
        AbstractEmail email = new EmailBuilder()
                .from(FORM)
                .to(TO_GROUP.toArray(new String[TO_GROUP.size()]))
                .subject("普通文本邮件，带附件")
                .content("内容")
                .textEmail()
                .addAttachment(new Attachment("附件1.jpg", this.getClass().getClassLoader().getResourceAsStream("a.jpg")))
                .addAttachment(new Attachment("附件2.gif", this.getClass().getClassLoader().getResourceAsStream("d.gif")))
                .build();
        emailSend.send(email);
    }

    @Test
    public void sendHtml() {
        AbstractEmail email = new EmailBuilder()
                .from(FORM)
                .to(TO_GROUP.toArray(new String[TO_GROUP.size()]))
                .subject("Html邮件")
                .content("<h1>我是一个H1标题</h1>")
                .htmlEmail()
                .build();
        emailSend.send(email);
    }

    @Test
    public void sendHtmlAddAttachment() {
        AbstractEmail email = new EmailBuilder()
                .from(FORM)
                .to(TO_GROUP.toArray(new String[TO_GROUP.size()]))
                .subject("Html邮件，带附件")
                .content("<h1>我是一个H1标题</h1><p><img src=\"cid:image1\"></p><p><img src=\"cid:image2\"></p>")
                .htmlEmail()
                .addAttachment(new Attachment("附件1.jpg", this.getClass().getClassLoader().getResourceAsStream("a.jpg")))
                .addAttachment(new Attachment("附件2.gif", this.getClass().getClassLoader().getResourceAsStream("d.gif")))
                .addInlineImageAttachment(new InlineImageAttachment("附件3.jpg", this.getClass().getClassLoader().getResourceAsStream("b.jpg"), "image1"))
                .addInlineImageAttachment(new InlineImageAttachment("附件4.png", this.getClass().getClassLoader().getResourceAsStream("c.png"), "image2"))
                .build();
        emailSend.send(email);
    }
}