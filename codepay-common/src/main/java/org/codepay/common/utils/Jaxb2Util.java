package org.codepay.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * Description: 使用Jaxb2进行XML和JavaBean转换<br>
 * 
 * Copyright: Copyright (c) 2014<br>
 * Company: www.zgzcw.com www.diyicai.com
 * 
 * @author 王李平
 * @create-time 2014年5月9日
 * @version 1.0
 * @E_mail wangliping@v1.cn
 */
public class Jaxb2Util {

    private static final Map<Class<?>, JAXBContext> JAXB_CONTEXT_CACHE = new ConcurrentHashMap<Class<?>, JAXBContext>();

    private static JAXBContext getJAXBContext(Class<?> clazz) {
        JAXBContext jaxbContext = JAXB_CONTEXT_CACHE.get(clazz);
        if (null == jaxbContext) {
            try {
                jaxbContext = JAXBContext.newInstance(clazz);
                JAXB_CONTEXT_CACHE.put(clazz, jaxbContext);
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
        }
        return jaxbContext;
    }

    /**
     * JavaBean转换成xml 默认编码UTF-8
     * 
     * @param object
     *            待转换的JavaBean对象
     * @return
     */
    public static String marshal(Object object) {
        return marshal(object, "UTF-8", false);
    }

    public static String marshal(Object object, boolean formatted) {
        return marshal(object, "UTF-8", formatted);
    }

    /**
     * 将JavaBean转换为XML文档
     * 
     * @param object
     *            待转换的JavaBean对象
     * @param encoding
     *            编码格式
     * @return 生成的XML文档内容
     */
    public static String marshal(Object object, String encoding, boolean formatted) {
        if (null == object) {
            return StringUtils.EMPTY;
        }
        String result = null;
        StringWriter writer = new StringWriter();
        try {
            JAXBContext jaxbContext = getJAXBContext(object.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatted);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            marshaller.marshal(object, writer);
            result = writer.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * 将JavaBean转换为XML文档，并保存到文件里
     * 
     * @param object
     *            待转换的JavaBean对象
     * @param clazz
     *            XML文档根节点Class
     * @param xmlFile
     *            保存xml文档的文件
     * @return 生成的XML文档内容
     */
    public void marshal(Object object, File xmlFile) {
        if (object == null) {
            return;
        }
        if (xmlFile == null) {
            return;
        }

        try {
            JAXBContext jaxbContext = getJAXBContext(object.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.marshal(object, xmlFile);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 将XML解析为JavaBean
     * 
     * @param xml
     *            XML文档内容
     * @param clazz
     *            XML文档根节点Class
     * @return 解析结果
     */
    @SuppressWarnings("unchecked")
    public static <T> T unmarshal(String xml, Class<T> clazz) {
        if (StringUtils.isBlank(xml)) {
            return null;
        }
        Object object = null;
        try {
            JAXBContext jaxbContext = getJAXBContext(clazz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            object = unmarshaller.unmarshal(new StringReader(xml));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        return (T) object;
    }

    /**
     * 将XML文件解析为JavaBean
     * 
     * @param xmlFile
     *            XML文件
     * @param clazz
     *            XML文档根节点Class
     * @return 解析结果
     */
    @SuppressWarnings("unchecked")
    public static <T> T unmarshal(File xmlFile, Class<T> clazz) {
        if (null == xmlFile) {
            return null;
        }
        Object object = null;

        try {
            JAXBContext jaxbContext = getJAXBContext(clazz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            object = unmarshaller.unmarshal(xmlFile);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        return (T) object;
    }

    /**
     * 将XML文件解析为JavaBean
     * 
     * @param xmlFile
     *            XML文件
     * @param clazz
     *            XML文档根节点Class
     * @return 解析结果
     */
    @SuppressWarnings("unchecked")
    public static <T> T unmarshal(InputStream xmlFile, Class<T> clazz) {
        if (null == xmlFile) {
            return null;
        }
        Object object = null;
        try {
            JAXBContext jaxbContext = getJAXBContext(clazz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            object = unmarshaller.unmarshal(xmlFile);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return (T) object;
    }

}
