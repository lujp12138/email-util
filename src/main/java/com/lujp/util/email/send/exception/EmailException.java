package com.lujp.util.email.send.exception;

/**
 * @author lujp
 * @date 2021-03-04
 */
public class EmailException extends RuntimeException {

    public EmailException() {
    }

    public EmailException(String message) {
        super(message);
    }

    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailException(Throwable cause) {
        super(cause);
    }
}
