package org.codepay.model.merchant;

import org.codepay.common.orm.domain.BaseDomain;
import org.codepay.model.merchant.enums.PublicStatusEnum;

public class MerchantInfo extends BaseDomain {

    private static final long serialVersionUID = 986079795551378651L;

    private String userNo;

    private String userName;

    private String accountNo;
    
    private String status;

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    public String getStatusDesc() {
        return PublicStatusEnum.getEnum(this.getStatus()).getDesc();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
