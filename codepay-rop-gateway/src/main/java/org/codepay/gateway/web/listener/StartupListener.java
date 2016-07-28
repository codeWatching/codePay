package org.codepay.gateway.web.listener;

import java.io.IOException;

import javax.servlet.ServletContextEvent;

import org.apache.commons.lang3.StringUtils;
import org.codepay.common.CommonConstant;
import org.codepay.common.config.Configuration;
import org.codepay.common.utils.ExceptionUtil;
import org.codepay.gateway.AppConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;

public class StartupListener extends ContextLoaderListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private final String serverName = "支付网管服务";

    public void contextInitialized(ServletContextEvent event) {
        
        CommonConstant.ROOTPATH = event.getServletContext().getRealPath("/");
        
        CommonConstant.BASEPATH = event.getServletContext().getContextPath();

        int step = 0;

        logger.info("[{}]启动第{}步:加载系统配置文件", serverName, ++step);
        Configuration conf = Configuration.getInstance();
        try {
            conf.initialize(AppConstant.SYS_PROPERTIES);
            if (conf.getPropMap().isEmpty()) {
                throw new RuntimeException();
            }
        } catch (IOException e) {
            logger.error("加载{}异常!classpath:{},error:{}", AppConstant.SYS_PROPERTIES,
                this.getClass().getClassLoader().getResource(StringUtils.EMPTY).getPath(),
                ExceptionUtil.getException(e));
            System.exit(-1);
        }
        super.contextInitialized(event);
        logger.info("Y(^_^)Y系统已正常启动啦!");
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

}
