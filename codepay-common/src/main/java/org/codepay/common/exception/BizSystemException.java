package org.codepay.common.exception;


/**
 * @Descriptions 业务系统异常，这类异常一般需要捕捉并记录log，比如数据库的主键冲突、sql语句错误等
 */
public class BizSystemException extends Exception{
    private static final long serialVersionUID = 1L;

    public BizSystemException(Exception e) {
      super(e);
    }

    public BizSystemException(String msg) {
      super(msg);
    }

    public BizSystemException(String msg, Exception e) {
      super(msg, e);
    }
}
