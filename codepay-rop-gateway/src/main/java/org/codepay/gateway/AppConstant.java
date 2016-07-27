package org.codepay.gateway;


/**
 * 系统常量.
 */
public class AppConstant {

    /**
     * 系统配置
     */
    public static final String SYS_PROPERTIES = "application.properties";

    /**
     * spring加载文件
     */
    public static final String SPRING_LOADFILES = "applicationContext.xml";

    /**
     * zk连接地址
     */
    public static final String ZK_ADDR = "zookeeper.addr";
    /**
     * zk超时时间
     */
    public static final String ZK_TIMEOUT = "zookeeper.timeout";
    /**
     * 号码生成器标识
     */
    public static final String ZK_CODEGEN = "zookeeper.codegen";

    /**
     * http代理开关
     */
    public static final String HTTP_PROXY_ENABLE = "http.proxy.enable";

    /**
     * http代理类型
     */
    public static final String HTTP_PROXY_TYPE = "http.proxy.type";

    /**
     * http代理地址
     */
    public static final String HTTP_PROXY_ADDRESS = "http.proxy.address";
}
