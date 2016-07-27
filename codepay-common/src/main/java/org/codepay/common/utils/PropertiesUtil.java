package org.codepay.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 读取Properties文件
 * 
 * 
 */
public class PropertiesUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);

	public static HashMap<String, String> readProperties(String fileName) {
		Properties pro = new Properties();
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName.trim());
		try {
			pro.load(new InputStreamReader(in, "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
			    if(null!=in){
				in.close();
			    }
			} catch (IOException e) {
				LOGGER.error("加载配置文件[{}]异常!",fileName);
			}
		}
		return conver2Map(pro);
	}

	public static HashMap<String, String> conver2Map(Properties pro) {
		HashMap<String, String> map = new HashMap<String, String>();
		Iterator<?> iter = pro.keySet().iterator();
		for (; iter.hasNext();) {
			String key = (String) iter.next();
			String value = pro.getProperty(key);
			map.put(key, value);
		}
		return map;
	}

}
