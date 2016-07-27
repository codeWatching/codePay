package org.codepay.common.utils.http;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * <pre>
 * 简单String响应处理器 .
 * </pre>
 * 
 * @date 2016年4月15日
 * @author wangliping
 */
class SimpleResponseHandler implements ResponseHandler<String> {

    public static final String DEFAULT_ENCODE = "UTF-8";

    private String encode;

    public SimpleResponseHandler(){
        this(null);
    }

    public SimpleResponseHandler(String encode){
        if (StringUtils.isNotBlank(encode)) this.encode = encode;
        else this.encode = DEFAULT_ENCODE;

    }

    public String handleResponse(HttpResponse response) throws IOException {
        StatusLine statusLine = response.getStatusLine();
        if (statusLine.getStatusCode() >= 300) {
            throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
        }

        HttpEntity entity = response.getEntity();
        return entity == null ? null : EntityUtils.toString(entity, this.encode);
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }
}
