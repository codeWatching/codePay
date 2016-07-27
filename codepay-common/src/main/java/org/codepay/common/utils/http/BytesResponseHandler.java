package org.codepay.common.utils.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * <pre>
 * byte数据响应器 .
 * </pre>
 * 
 * @date 2016年4月15日
 * @author wangliping
 */
class BytesResponseHandler implements ResponseHandler<byte[]> {

    @Override
    public byte[] handleResponse(HttpResponse httpResponse) throws IOException {

        StatusLine statusLine = httpResponse.getStatusLine();
        if (statusLine.getStatusCode() >= 300) {
            throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
        }

        HttpEntity entity = httpResponse.getEntity();
        return entity == null ? null : EntityUtils.toByteArray(entity);
    }
}
