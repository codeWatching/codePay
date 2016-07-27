package org.codepay.common.spring;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

/**
 * 以静态变量保存Spring ApplicationContext,可在任意代码中取出ApplicaitonContext.
 * 
 * @date 2016年7月27日
 * @author lisai
 */
public class SpringContextHolder implements ApplicationContextAware, InitializingBean {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    };
    /**
     * 获取静态成员变量applicationContext
     */
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }
    /**
     * 通过BeanName获取Bean
     * @param beanName
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName){
        return (T) applicationContext.getBean(beanName);
    }
    /**
     * 通过字节码获取Bean
     * @param clazz
     */
    public static  <T> T getBean(Class<T> clazz){
        Map<?, T> map = applicationContext.getBeansOfType(clazz);
        if(map == null){
            return null;
        }
        return (T) map.values().iterator().next();
    }
    /**
     * 将每次获取资源前检查环境 改为属性注入后自动检查
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        checkApplicationContext();
    }

    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextUtil");
        }
    }
    /**
     * 二次封装系统发布事件接口
     * @param applicationEvent
     */
    public static void publishEvent(ApplicationEvent applicationEvent){
        applicationContext.publishEvent(applicationEvent);
    }
}
