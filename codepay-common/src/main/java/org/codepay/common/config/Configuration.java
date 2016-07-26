package org.codepay.common.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Descriptions 系统参数配置：通过指定路径 加载Properties初始化
 */
public class Configuration {

    private static Logger log = LoggerFactory.getLogger(Configuration.class);

    /** 系统参数统参数 */
    private Map<String, String> sysconf = new ConcurrentHashMap<String, String>();

    /** 系统参数分类 key:文件名 value:Properties */
    private Map<String, Properties> propMap = new ConcurrentHashMap<String, Properties>();

    public Map<String, Properties> getPropMap() {
        return Collections.unmodifiableMap(propMap);
    }

    public Properties getPropMapByFileName(String fileName) {
        return propMap.get(fileName);
    }

    public Map<String, String> getSysconf() {
        return Collections.unmodifiableMap(sysconf);
    }

    public String get(String key) {
        return sysconf.get(key);
    }

    public String get(String key, String defaultValue) {
        String value = sysconf.get(key);
        if (null != value && !"".equals(value.trim())) {
            return value;
        }
        return defaultValue;
    }

    public int getInt(String name, int defaultValue) {
        String valueString = get(name);
        if (valueString == null) return defaultValue;
        try {
            return Integer.parseInt(valueString);
        } catch (NumberFormatException e) {
            log.warn(e.getMessage());
        }
        return defaultValue;
    }
    
    public long getLong(String name, long defaultValue) {
        String valueString = get(name);
        if (valueString == null) return defaultValue;
        try {
            return Long.valueOf(valueString);
        } catch (NumberFormatException e) {
            log.warn(e.getMessage());
        }
        return defaultValue;
    }

    public boolean getBoolean(String name, boolean defaultValue) {
        String valueString = get(name);
        if (null == valueString || "".equals(valueString)) {
            return defaultValue;
        }
        return Boolean.valueOf(valueString);
    }

    public void set(String key, String value) {
        sysconf.put(key, value);
    }

    public String[] getStringArr(String paramName) {
        String value = get(paramName);
        if (null == value || "".equals(value)) {
            return null;
        }
        return value.split(",");
    }

    /**
     * 配置初始化
     * 
     * @param propFiles
     * @throws IOException
     */
    public void initialize(String... propFiles) throws IOException {
        if (propFiles == null) {
            return;
        }
        for (String propFile : propFiles) {
            loadProp(propFile);
        }
    }

    private void loadProp(String propFile) throws IOException {
        Properties prop = new Properties();
        InputStream is = null;
        try {
            is = this.getClass().getClassLoader().getResourceAsStream(propFile);
            prop.load(is);
            propMap.put(propFile, prop);
            Set<Entry<Object, Object>> set = prop.entrySet();
            for (Entry<Object, Object> en : set) {
                this.set(en.getKey().toString(), en.getValue().toString());
            }

        } catch (IOException e) {
            throw e;
        } finally {
            if (null != is) {
                is.close();
            }
        }

    }

    private static ReentrantLock lock = new ReentrantLock();

    private static Configuration schedConfiguration = null;

    private Configuration(){
    }

    public static Configuration getInstance() {
        if (null == schedConfiguration) {
            try {
                lock.lock();
                if (null == schedConfiguration) {
                    schedConfiguration = new Configuration();
                }
            } finally {
                lock.unlock();
            }
        }
        return schedConfiguration;
    }

}
