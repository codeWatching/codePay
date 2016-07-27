package org.codepay.common.utils.http;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.codepay.common.utils.BeanMapUtils;
import org.codepay.common.utils.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * http客户端请求工具类 .
 * </pre>
 *
 * @author codeWatching
 * @date 2016年4月15日
 */
public class HttpClientUtil {

    protected static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 向指定URL发送GET方法的数据请求,默认开启代理.
     *
     * @param url 发送请求的URL
     * @return
     */
    public static String sendGet(String url) {
        return sendGet(url, null, true);
    }

    /**
     * 向指定URL发送GET方法的数据请求.
     *
     * @param url 发送请求的URL
     * @return
     */
    public static String sendGet(String url, boolean isProxy) {
        return sendGet(url, null, isProxy);
    }

    /**
     * 向指定URL发送GET方法的数据请求.
     *
     * @param url     发送请求的URL
     * @param encode  编码格式
     * @param isProxy 是否使用代理
     * @return
     */
    public static String sendGet(String url, String encode, boolean isProxy) {
        SafeHttpClient httpClient = HttpClientFactory.getHttpClient(isProxy);
        HttpUriRequest httpReq = new HttpGet(url);
        try {
            HttpContext context = new BasicHttpContext();
            ResponseHandler<String> responseHandler = new SimpleResponseHandler(encode);
            return httpClient.execute(httpReq, responseHandler, context);
        } catch (Exception e) {
            LOGGER.error("GET请求远程地址失败，url:{},encode:{},isProxy:{},Exception:{}", url, encode, isProxy,
                    ExceptionUtil.getException(e));
            httpReq.abort();
            httpClient.closeExpiredConnections();
        }
        return null;
    }

    /**
     * 向指定URL发送POST方法的数据请求, 默认开启代理.
     *
     * @param url 发送请求的URL
     * @return
     */
    public static String sendPost(String url) {
        return sendPost(url, null, null, true);
    }

    /**
     * 向指定URL发送POST方法的数据请求.
     *
     * @param url    发送请求的URL
     * @param params 请求参数k1=v1&k2=v2
     * @return
     */
    public static String sendPost(String url, String params) {
        return sendPost(url, params, null, true);
    }

    /**
     * 向指定URL发送POST方法的数据请求.
     *
     * @param url     发送请求的URL
     * @param isProxy 是否使用代理
     * @return
     */
    public static String sendPost(String url, boolean isProxy) {
        return sendPost(url, null, null, isProxy);
    }

    /**
     * 向指定URL发送POST方法的数据请求.
     *
     * @param url     发送请求的URL
     * @param isProxy 是否使用代理
     * @param encode  编码格式
     * @return
     */
    public static String sendPost(String url, boolean isProxy, String encode) {
        return sendPost(url, null, encode, isProxy);
    }

    /**
     * 向指定URL发送POST方法的数据请求.
     *
     * @param url     发送请求的URL
     * @param params  请求参数k1=v1&k2=v2
     * @param isProxy 是否使用代理
     * @return
     */
    public static String sendPost(String url, String params, boolean isProxy) {
        return sendPost(url, params, null, isProxy);
    }

    /**
     * 向指定URL发送POST方法的数据请求.
     *
     * @param url     发送请求的URL
     * @param params  请求参数k1=v1&k2=v2
     * @param encode  编码格式
     * @param isProxy 是否使用代理
     * @return
     */
    public static String sendPost(String url, String params, String encode, boolean isProxy) {
        SafeHttpClient httpClient = HttpClientFactory.getHttpClient(isProxy);
        HttpPost httpReq = new HttpPost(url);
        try {
            if (StringUtils.isNotBlank(params)) {
                // 构造最简单的字符串数据
                StringEntity reqEntity = new StringEntity(params);
                reqEntity.setContentType("application/x-www-form-urlencoded");
                // 设置请求的数据
                httpReq.setEntity(reqEntity);
            }
            ResponseHandler<String> responseHandler = new SimpleResponseHandler(encode);
            return httpClient.execute(httpReq, responseHandler, new BasicHttpContext());
        } catch (Exception e) {
            LOGGER.error("POST请求远程地址失败，url:{},params:{},encode:{},isProxy:{},Exception:{}", url, params, encode,
                    isProxy, ExceptionUtil.getException(e));
            httpReq.abort();
            httpClient.closeExpiredConnections();
        }
        return null;
    }

    /**
     * 获取远程数据bytes
     *
     * @param url     发送请求的URL
     * @param isProxy 是否使用代理
     * @return
     */
    public static byte[] sendGetBytes(String url, boolean isProxy) {
        SafeHttpClient httpClient = HttpClientFactory.getHttpClient(isProxy);
        HttpUriRequest httpReq = new HttpGet(url);
        try {
            HttpContext context = new BasicHttpContext();
            ResponseHandler<byte[]> responseHandler = new BytesResponseHandler();
            return httpClient.execute(httpReq, responseHandler, context);
        } catch (Exception e) {
            LOGGER.error("sendGetBytes请求远程地址失败，url:{},encode:{},Exception:{}", url, isProxy,
                    ExceptionUtil.getException(e));
            httpReq.abort();
            httpClient.closeExpiredConnections();
        }
        return null;
    }

    /**
     * 向指定URL发送POST方法的数据流请求
     *
     * @param url     发送请求的URL
     * @param message 请求数据
     * @param isProxy 是否使用代理
     * @return URL所代表远程资源的响应
     */
    public static String sendPostStream(String url, String message, String encode, boolean isProxy) {
        SafeHttpClient httpClient = HttpClientFactory.getHttpClient(isProxy);
        HttpPost httpReq = new HttpPost(url);
        try {
            if (StringUtils.isNotBlank(message)) {
                // 构造最简单的字符串数据
                byte[] content = message.getBytes();
                InputStream inputStream = new ByteArrayInputStream(content);
                InputStreamEntity reqEntity = new InputStreamEntity(inputStream, content.length);
                reqEntity.setContentType("application/x-www-form-urlencoded");
                // 设置请求的数据
                httpReq.setEntity(reqEntity);
            }
            ResponseHandler<String> responseHandler = new SimpleResponseHandler(encode);
            return httpClient.execute(httpReq, responseHandler, new BasicHttpContext());
        } catch (Exception e) {
            LOGGER.error("sendPostStream请求远程地址失败，url:{},message:{},encode:{},isProxy:{},Exception:{}", url, message,
                    encode, isProxy, ExceptionUtil.getException(e));
            httpReq.abort();
            httpClient.closeExpiredConnections();
        }
        return null;
    }

    /**
     * 拼接请求参数 k1=v1&k2=v2, 值为null的字段会被删除
     *
     * @param params
     * @param ignores 忽略字段
     * @return
     */
    public static String buildParams(Object params, String... ignores) {
        Map<String, Object> paramsmap = null;
        StringBuilder sb = new StringBuilder();
        try {
            paramsmap = BeanMapUtils.toMap(params);
            // remove ignore field
            if (null != ignores && ignores.length > 0) {
                for (int i = 0; i < ignores.length; i++) {
                    paramsmap.remove(ignores[i]);
                }
            }
            for (Map.Entry<String, Object> entry : paramsmap.entrySet()) {
                Object value = entry.getValue();
                String key = entry.getKey();
                sb.append("&").append(key).append("=").append(value);
            }
        } catch (Exception e) {
            LOGGER.error("拼接请求参数错误!", e);
        }
        return sb.substring(1);
    }
}
