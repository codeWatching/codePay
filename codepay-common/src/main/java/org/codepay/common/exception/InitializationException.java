package org.codepay.common.exception;


/**
 * @Descriptions 系统启动初始化异常,此异常发生,系统需要重启或者升级,并且检查修复异常原因;如数据库连接\中间件等问题
 */
public class InitializationException extends Exception {
	private static final long serialVersionUID = -5365630128856068164L;
	
    public InitializationException() {
    	
	super();
    }
 
    public InitializationException( String message ) {
	super( message );
    }

    public InitializationException( String message, Throwable cause ) {
        super(message, cause);
    }
 
    public InitializationException(Throwable cause) {
        super(cause);
    }
    
}

