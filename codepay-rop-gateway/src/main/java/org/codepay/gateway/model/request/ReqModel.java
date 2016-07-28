package org.codepay.gateway.model.request;

import com.rop.annotation.IgnoreSign;
import com.rop.request.AbstractZyRequest;

/**
 * 分页请求信息.
 *
 * @author codeWatching
 * @date 2016-03-01
 * @since JDK1.6
 */
public abstract class ReqModel extends AbstractZyRequest{

    @IgnoreSign
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
}

