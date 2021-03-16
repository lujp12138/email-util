package com.lujp.util.email.send.model.attachment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

/**
 * 附件
 * @author lujp
 * @date 2021-03-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {

    /**
     * 文件名称
     */
    protected String fileName;

    /**
     * 文件的输入流
     */
    protected InputStream inputStream;
}
