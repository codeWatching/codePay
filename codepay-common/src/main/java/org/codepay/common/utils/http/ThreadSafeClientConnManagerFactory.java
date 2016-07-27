package org.codepay.common.utils.http;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ClientConnectionManagerFactory;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;

/**
 * 客户端连接管理工厂.
 *
 * @author wangliping
 * @date 2016-04-15
 * @since JDK1.6
 */
public class ThreadSafeClientConnManagerFactory implements ClientConnectionManagerFactory {

    @SuppressWarnings("deprecation")
    @Override
    public ClientConnectionManager newInstance(HttpParams httpParams, SchemeRegistry schemeRegistry) {
        return new ThreadSafeClientConnManager(httpParams, schemeRegistry);
    }
}
