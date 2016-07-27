package org.codepay.common.utils.http;

import java.util.LinkedList;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.codepay.common.SeparatorConstant;

/**
 * http连接客户端.
 *
 * @author codeWatching
 * @date 2016-04-15
 * @since JDK1.6
 */
class SafeHttpClient extends DefaultHttpClient {
    /**
     * 最大连接数
     */
    private final int maxTotalConnections = 800;

    /**
     * 获取连接的最大等待时间
     */
    private final int waitTimeout = 60000;
    /**
     * 每个路由最大连接数
     */
    private final int maxRouteConnections = 400;
    /**
     * 连接超时时间
     */
    private final int connectTimeout = 10000;
    /**
     * 读取超时时间
     */
    private final int readTimeout = 10000;

    /**
     * 连接创建factory
     */
    private final String factoryClassName = ThreadSafeClientConnManagerFactory.class.getName();

    /**
     * 存放代理IP.
     */
    protected LinkedList<HttpHost> httpHosts = new LinkedList<HttpHost>();

    public SafeHttpClient(ProxyType proxyType, String proxyAddress) {
        this();

        if (StringUtils.isBlank(proxyAddress)) {
            throw new RuntimeException("代理地址不能为空!");
        }

        proxyType = null == proxyType ? ProxyType.HTTP : proxyType;

        String[] addrArr = proxyAddress.split(SeparatorConstant.COMMA_REGEX);
        for (String addr : addrArr) {
            String[] arr = addr.split(SeparatorConstant.COLON_REGEX);
            HttpHost httpHost = new HttpHost(arr[0], Integer.parseInt(arr[1]), proxyType.name().toLowerCase());
            httpHosts.add(httpHost);
        }

        this.setProxy();
    }

    /**
     * 设置代理.
     */
    private void setProxy() {
        HttpHost httpHost = httpHosts.get(0);
        String proxyUserName = "test";
        String proxyPassWord = "123456";
        AuthScope authScope = new AuthScope(httpHost.getHostName(), httpHost.getPort());
        this.getCredentialsProvider().setCredentials(authScope,
                new UsernamePasswordCredentials(proxyUserName, proxyPassWord));
        this.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, httpHost);
    }


    @SuppressWarnings("deprecation")
    public SafeHttpClient() {

        HttpParams httpParams = new BasicHttpParams();
        httpParams.setParameter(ClientPNames.CONNECTION_MANAGER_FACTORY_CLASS_NAME, factoryClassName);
        // 设置最大连接数
        ConnManagerParams.setMaxTotalConnections(httpParams, maxTotalConnections);
        // 设置获取连接的最大等待时间
        ConnManagerParams.setTimeout(httpParams, waitTimeout);
        // 设置每个路由最大连接数
        ConnPerRouteBean connPerRoute = new ConnPerRouteBean(maxRouteConnections);
        ConnManagerParams.setMaxConnectionsPerRoute(httpParams, connPerRoute);
        // 设置连接超时时间
        HttpConnectionParams.setConnectionTimeout(httpParams, connectTimeout);
        // 设置读取超时时间
        HttpConnectionParams.setSoTimeout(httpParams, readTimeout);
        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
        this.setParams(httpParams);
        this.setKeepAliveStrategy(new ConnectionKeepAliveStrategy() {
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                HeaderElementIterator it =
                        new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        try {
                            return Long.parseLong(value) * 1000;
                        } catch (NumberFormatException ignore) {
                        }
                    }
                }
                return 5 * 1000;
            }
        });
        this.getConnectionManager();
    }


    /**
     * 关闭异常连接
     */
    public synchronized void closeExpiredConnections() {
        getConnectionManager().closeExpiredConnections();

        if (httpHosts.size() <= 1) {
            return;
        }
        httpHosts.addLast(httpHosts.getFirst());
        httpHosts.removeFirst();
        this.setProxy();
    }
}