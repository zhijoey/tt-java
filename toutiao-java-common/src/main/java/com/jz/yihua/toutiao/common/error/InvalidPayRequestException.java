package com.jz.yihua.toutiao.common.error;

public class InvalidPayRequestException extends Exception {

    private static final long serialVersionUID = -658275551101008651L;

    public InvalidPayRequestException(String message) {
        super(message, null);
    }

    public InvalidPayRequestException(String message, Throwable e) {
        super(message, e);
    }
}

