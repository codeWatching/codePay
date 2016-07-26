package org.codepay.common.exception;


/**
 * @Descriptions 
 * 如果一个异常是致命的，不可恢复的。或者调用者去捕获它没有任何益处，使用unChecked异常。如果一个异常是可以恢复的，可以被调用者正确处理的，
 * 使用checked异常 。在使用unChecked异常时，必须在在方法声明中详细的说明该方法可能会抛出的unChekced异常。
 * 由调用者自己去决定是否捕获unChecked异常
 */
public class DaoException extends RuntimeException {
    public DaoException() {
        super();
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }

    private static final long serialVersionUID = -5365630128856068164L;
}
