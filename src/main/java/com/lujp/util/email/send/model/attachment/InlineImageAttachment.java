package com.lujp.util.email.send.model.attachment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

/**
 * 内联图片附件
 * @author lujp
 * @date 2021-03-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InlineImageAttachment extends Attachment {

    public InlineImageAttachment(String fileName, InputStream inputStream, String cid) {
        super(fileName, inputStream);
        this.cid = cid;
    }

    /**
     * 内联图片的cid
     */
    protected String cid;
}
