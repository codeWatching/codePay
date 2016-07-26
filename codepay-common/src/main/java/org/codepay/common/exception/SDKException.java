package org.codepay.common.exception;


/**
 * @Descriptions 处理的基础类，可以在里面封装一些异常处理的基本函数
 */
public class SDKException extends Exception{
    private static final long serialVersionUID = 1L;

    public SDKException(Exception e) {
      super(e);
    }

    public SDKException(String msg) {
      super(msg);
    }

    public SDKException(String msg, Exception e) {
      super(msg, e);
    }
}
