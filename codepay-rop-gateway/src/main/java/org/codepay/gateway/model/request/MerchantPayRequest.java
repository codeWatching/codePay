package org.codepay.gateway.model.request;
/**
 * 获取商户传入参数
 * @date 2016年7月27日
 * @author lisai
 */
public class MerchantPayRequest {
    private String payKey; // 企业支付KEY
    private String productName;  // 商品名称
    private String orderNo ; // 订单编号
    private String orderPrice; // 订单金额 , 单位:元
    private String payWayCode; // 支付方式编码 支付宝: ALIPAY  微信:WEIXIN
    private String orderIp ; // 下单IP
    private String orderDate ; // 订单日期
    private String orderPeriod;  // 订单有效期
    private String returnUrl ; // 页面通知返回url
    private String notifyUrl ; // 后台消息通知Url
    private String remark;// 支付备注
    private String sign; // 签名
    private String field1; // 扩展字段1
    private String field2; // 扩展字段2
    private String field3; // 扩展字段3
    private String field4; // 扩展字段4
    private String field5; // 扩展字段5
    
    public MerchantPayRequest(String payKey, String productName, String orderNo, String orderPrice, String payWayCode,
                           String orderIp, String orderDate, String orderPeriod, String returnUrl, String notifyUrl,
                           String remark, String sign, String field1, String field2, String field3, String field4,
                           String field5){
        super();
        this.payKey = payKey;
        this.productName = productName;
        this.orderNo = orderNo;
        this.orderPrice = orderPrice;
        this.payWayCode = payWayCode;
        this.orderIp = orderIp;
        this.orderDate = orderDate;
        this.orderPeriod = orderPeriod;
        this.returnUrl = returnUrl;
        this.notifyUrl = notifyUrl;
        this.remark = remark;
        this.sign = sign;
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = field4;
        this.field5 = field5;
    }

    
    public String getPayKey() {
        return payKey;
    }

    
    public void setPayKey(String payKey) {
        this.payKey = payKey;
    }

    
    public String getProductName() {
        return productName;
    }

    
    public void setProductName(String productName) {
        this.productName = productName;
    }

    
    public String getOrderNo() {
        return orderNo;
    }

    
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    
    public String getOrderPrice() {
        return orderPrice;
    }

    
    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    
    public String getPayWayCode() {
        return payWayCode;
    }

    
    public void setPayWayCode(String payWayCode) {
        this.payWayCode = payWayCode;
    }

    
    public String getOrderIp() {
        return orderIp;
    }

    
    public void setOrderIp(String orderIp) {
        this.orderIp = orderIp;
    }

    
    public String getOrderDate() {
        return orderDate;
    }

    
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    
    public String getOrderPeriod() {
        return orderPeriod;
    }

    
    public void setOrderPeriod(String orderPeriod) {
        this.orderPeriod = orderPeriod;
    }

    
    public String getReturnUrl() {
        return returnUrl;
    }

    
    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    
    public String getNotifyUrl() {
        return notifyUrl;
    }

    
    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    
    public String getRemark() {
        return remark;
    }

    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    
    public String getSign() {
        return sign;
    }

    
    public void setSign(String sign) {
        this.sign = sign;
    }

    
    public String getField1() {
        return field1;
    }

    
    public void setField1(String field1) {
        this.field1 = field1;
    }

    
    public String getField2() {
        return field2;
    }

    
    public void setField2(String field2) {
        this.field2 = field2;
    }

    
    public String getField3() {
        return field3;
    }

    
    public void setField3(String field3) {
        this.field3 = field3;
    }

    
    public String getField4() {
        return field4;
    }

    
    public void setField4(String field4) {
        this.field4 = field4;
    }

    
    public String getField5() {
        return field5;
    }

    
    public void setField5(String field5) {
        this.field5 = field5;
    }
}
