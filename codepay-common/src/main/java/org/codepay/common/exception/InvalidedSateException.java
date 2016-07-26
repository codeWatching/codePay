package org.codepay.common.exception;

/**
 * @Descriptions  更新记录异常，当更新记录时，记录的状态和数据库状态不一致时，需要抛出此异常
 */
public class InvalidedSateException extends ServiceException {

    public InvalidedSateException(String message) {
        super(message);
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

}
