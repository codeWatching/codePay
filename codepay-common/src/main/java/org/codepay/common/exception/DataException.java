package org.codepay.common.exception;


/**
 * @Descriptions 数据校验异常
 */
public class DataException extends RuntimeException {
    private static final long serialVersionUID = -1855492522612991591L;

    public DataException(String message) {
        super(message);
    }
}

