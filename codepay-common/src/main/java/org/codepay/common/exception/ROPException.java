package org.codepay.common.exception;

/**
 * rop层异常
 */
public class ROPException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1296728900806688114L;

	private int code;

	public ROPException(int code, String message) {
		super(message);
		this.code = code;
	}

	public ROPException() {
		super();
	}

	public ROPException(String message, Throwable cause) {
		super(message, cause);
	}

	public ROPException(Throwable cause) {
		super(cause);
	}

	public int getCode() {
		return code;
	}
	
}
