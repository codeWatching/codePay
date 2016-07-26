package org.codepay.common.exception;
/**
 * 第三方合作商接口返回数据转换为模型时异常
 */
public class PartnerReponseCastException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String apiName;
	
	public PartnerReponseCastException(String apiName, Throwable cause) {
		super(apiName, cause);
		this.apiName = apiName;
	}

	public PartnerReponseCastException(String apiName) {
		this.apiName = apiName;
	}

	@Override
	public String getMessage() {
		return "第三方api响应数据转换异常！api name: "+apiName;
	}
	
}