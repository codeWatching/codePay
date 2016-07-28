package org.codepay.gateway.model.response;

import java.util.ArrayList;
import java.util.List;

import com.rop.security.MainError;
import com.rop.security.SubError;

/**
 * @Descriptions 交易响应码.<br>
 *               系统级状态从0开始<br>
 *               帐号业务状态码以10开头<br>
 *               商品业务状态码以11开头<br>
 *               订单业务状态码以12开头<br>
 * @date 2015年3月30日
 * @author codeWatching
 */
public enum StateCode implements MainError {

    SERVICE_ERROR(100, "系统内部异常"),

    LOGIN_FAIL(101, "登录失败，用户信息有误"),

    USER_NOT_AVAILABLE(102, "该用户状态异常,不可用"),

    SESSION_OVERTIME(103, "session已超时，请重新登陆"),

    RAISE_NOT_EXIST(2001, "不存在该信息"),

    RAISE_SKU_LIMIT(2002, "该众筹商品达到数量限制"),

    RAISE_END(2003, "众筹已经结束"),

    RAISE_PRICE_LIMIT(2004, "众筹达到金额限制"),
    
    RAISE_LIST_ERROR(2005, "众筹列表查询失败"),
    
    RAISE_SKU_LIST_ERROR(2010, "众筹SKU列表查询失败"),
    
    RAISE_HOME_LIST_EMPTY(2006, "主页查询信息为空"),
    
    MY_FOCUS_QUERY_ERROR(2007, "我的关注众筹列表查询失败"),
    
    MY_FOCUS_QUERY_EMPTY(2008, "我的关注众筹列表查询为空"),
    
    MY_SUPPORT_QUERY_FAILED(2009, "我支持的众筹列表查询为空"),

    RAISE_NEW_ORDER_ERROR(3001, "众筹下单失败"),
    
    RAISE_NEW_ORDER_TRY(3002, "系统繁忙,请重试."),
    
    RAISE_ORDER_INFO_QUERY_FAILED(4002, "众筹订单查询失败"),
    
    RAISE_ORDER_PAY_INFO_QUERY_FAILED(4003, "众筹订单支付信息查询失败"),
    
    QUERY_USER_LIST_FAILED(4001, "查询用户订单失败");


    /** 响应代号 */
    private final int code;

    /** 提示详情 */
    private final String message;

    private String solution;

    private List<SubError> subErrors;

    private StateCode(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public static StateCode valueOfCode(String code) {
        for (StateCode rsc : StateCode.values()) {
            if (rsc.getCode().equals(code))
                return rsc;
        }
        return null;
    }

    @Override
    public String getCode() {
        return String.valueOf(code);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getSolution() {
        return solution;
    }

    @Override
    public List<SubError> getSubErrors() {
        return subErrors;
    }

    @Override
    public MainError addSubError(SubError subError) {
        if (null == subErrors) {
            subErrors = new ArrayList<SubError>();
        }
        subErrors.add(subError);

        return this;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public void setSubErrors(List<SubError> subErrors) {
        this.subErrors = subErrors;
    }
}