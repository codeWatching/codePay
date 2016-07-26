package org.codepay.common.exception;


/**
 * @Descriptions 系统版本异常,如接口版本\服务版本等
 */
public class VersionException extends RuntimeException {
	private static final long serialVersionUID = -5365630128856068164L;
	
    public VersionException() {
    	
	super();
    }

    public VersionException( String message ) {
	super( message );
    }

    public VersionException( String message, Throwable cause ) {
        super(message, cause);
    }
 
    public VersionException(Throwable cause) {
        super(cause);
    }
    
}

