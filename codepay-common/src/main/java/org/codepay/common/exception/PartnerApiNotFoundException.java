package org.codepay.common.exception;
/**
 * 没有发现第三方合作商接口异常
 * 一般是因为配置文件中没有配置Api名称
 */
public class PartnerApiNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	private String apiName;
	

	public PartnerApiNotFoundException(String apiName, Throwable cause) {
		super(apiName, cause);
		this.apiName = apiName;
	}

	public PartnerApiNotFoundException(String apiName) {
		this.apiName = apiName;
	}

	@Override
	public String getMessage() {
		return "没有发现第三方api！api name: "+apiName;
	}
	
}