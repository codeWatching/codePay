package org.codepay.common.utils.http;

/**
 * http客户端工厂.
 *
 * @author wangliping
 * @date 2016-04-15
 * @since JDK1.6
 */
public class HttpClientFactory {

    /**
     * 默认连接客户端.
     */
    private final static SafeHttpClient CLIENT = new SafeHttpClient();
    /**
     * 代理连接客户端.
     */
    private static SafeHttpClient proxyClient;

    /**
     * 获取客户端连接.
     *
     * @param isProxy
     * @return
     */
    public static SafeHttpClient getHttpClient(boolean isProxy) {
        if (isProxy && null != proxyClient) {
            return proxyClient;
        }
        return CLIENT;
    }

    /**
     * 设置代理.
     *
     * @param proxyType
     * @param proxyAddress
     */
    public static synchronized void setProxy(String proxyType, String proxyAddress) {
        proxyClient = new SafeHttpClient(ProxyType.valueOf(proxyType.toUpperCase()), proxyAddress);
    }

}
