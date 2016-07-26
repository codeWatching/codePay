package org.codepay.common.exception;


/**
 * @Descriptions web请求通讯异常,网络异常等
 */
public class WebException extends Exception {
 
    private static final long serialVersionUID = 1L;
    
    public WebException(String msg) {
        super(msg);
    }
 
    public WebException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
