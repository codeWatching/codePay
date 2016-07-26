package org.codepay.common.exception;
/**
 * 合作商接口请求异常
 */
public class PartnerApiRequestException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String apiName;


	public PartnerApiRequestException(String apiName, Throwable cause) {
		super(apiName, cause);
		this.apiName = apiName;
	}

	public PartnerApiRequestException(String apiName) {
		this.apiName = apiName;
	}


	@Override
	public String getMessage() {
		return "第三方api请求失败！api name: " + apiName;
	}
	
}