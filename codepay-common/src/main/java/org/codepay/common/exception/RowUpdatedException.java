package org.codepay.common.exception;

/**
 * @Descriptions 更新异常，如更新数据库时，version版本不对异常
 */
public class RowUpdatedException extends ServiceException {

    public RowUpdatedException(String message) {
        super(message);
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

}
