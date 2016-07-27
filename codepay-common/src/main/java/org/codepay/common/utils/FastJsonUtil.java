package org.codepay.common.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.*;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class FastJsonUtil {

    private static SerializeConfig configMapping = new SerializeConfig();
    private static String dateFormat;

    static {
        dateFormat = "yyyy-MM-dd HH:mm:ss";
        configMapping.put(Date.class, new SimpleDateFormatSerializer(dateFormat));
    }

    /**
     * 根据string类型的json串的一个key获取value
     *
     * @param json
     * @param name
     * @return
     */
    public static String getStringValue(String json, String name) {
        try {
            if (StringUtils.isBlank(json)) {
                return "";
            }
            JSONObject jsonObj = JSON.parseObject(json);
            String value = jsonObj.getString(name);
            if (StringUtils.isBlank(value)) {
                return "";
            }
            return value;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据json对象的一个key获取value
     *
     * @param jsonObj
     * @param name
     * @return
     */
    public static String getStringValue(JSONObject jsonObj, String name) {
        try {
            if (null == jsonObj) {
                return "";
            }
            String value = jsonObj.getString(name);
            if (StringUtils.isBlank(value)) {
                return "";
            }
            return value;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据JSON对象的一个key获取object
     *
     * @param jsonObj
     * @param name
     * @return
     */
    public static String getObjectValueByJson(JSONObject jsonObj, String name) {
        try {
            if (null == jsonObj) {
                return null;
            }
            String value = jsonObj.getString(name);
            if (StringUtils.isBlank(value)) {
                return null;
            }
            return value;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据string类型的json串的一个key获取object
     *
     * @param json
     * @param name
     * @return
     */
    public static Object getObjectValueByJson(String json, String name) {
        try {
            if (StringUtils.isBlank(json)) {
                return null;
            }
            JSONObject jsonObj = JSON.parseObject(json);
            Object value = jsonObj.get(name);
            if (null == value) {
                return null;
            }
            return value;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 将一个 Object 或者List对象转化为JSONObject或者JSONArray
     *
     * @param ObjOrList Object 或者List对象
     * @return
     */
    public static Object toJSON(Object ObjOrList) {
        Object obj = null;
        try {
            obj = JSON.toJSON(ObjOrList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    /**
     * 将一个 Object 或者List对象转化为JSOObject或者JSONArray
     *
     * @param object Object 或者List对象 或者hashmap 但是如果是set  就会有问题
     * @return
     */
    public static String toJSONStr(Object object, SerializerFeature... features) {

        object = parseToObject(object);

        if (features == null) {
            return JSON.toJSONString(object, configMapping, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullListAsEmpty);
        }

        return JSON.toJSONString(object, configMapping, features);

    }

    /**
     * 将一个 Object 或者List对象转化为JSOObject或者JSONArray
     * filter做过滤
     *
     * @param object
     * @param filter
     * @param features
     * @return
     */
    public static final String toJSONString(Object object, SerializeFilter filter, SerializerFeature... features) {
        object = parseToObject(object);

        if (null != features && features.length > 0) {
            return JSON.toJSONString(object, filter, features);
        }
        return JSON.toJSONString(object, filter, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullListAsEmpty);
    }


    /**
     * 将一个 Object 或者List对象转化为JSOObject或者JSONArray
     * filters做过滤
     *
     * @param object
     * @param filters
     * @param features
     * @return
     */
    public static final String toJSONString(Object object, SerializeFilter[] filters, SerializerFeature... features) {
        object = parseToObject(object);

        if (null != features && features.length > 0) {
            return JSON.toJSONString(object, filters, features);
        }
        return JSON.toJSONString(object, filters, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullListAsEmpty);
    }

    /**
     * 将一个 Object 或者List对象转化为JSOObject或者JSONArray
     * 并且转换时间格式
     *
     * @param object
     * @param dateFormat
     * @param features
     * @return
     */
    public static String toJSONStringWithDateFormat(Object object, String dateFormat,
                                                    SerializerFeature... features) {

        object = parseToObject(object);

        if (null != features && features.length > 0) {
            return JSON.toJSONStringWithDateFormat(object, dateFormat, features);
        }

        return JSON.toJSONStringWithDateFormat(object, dateFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullListAsEmpty);

    }

    /**
     * 字符串转object对象
     *
     * @param jsonstr
     * @param clazz
     * @return
     */
    public static <T> T parseToObject(String jsonstr, Class<T> clazz) {
        try {
            return JSON.parseObject(jsonstr, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 字符串转list
     *
     * @param jsonstr
     * @param clazz
     * @return
     */
    public static <T> List<T> parseToList(String jsonstr, Class<T> clazz) {
        List<T> parseObj = null;
        try {
            parseObj = JSON.parseArray(jsonstr, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return parseObj;
    }

    /**
     * 字符串转jsonobj
     *
     * @param jsonstr
     * @return
     */
    public static JSONObject parseToJSONObejct(String jsonstr) {
        JSONObject parseObj = null;
        try {
            parseObj = JSON.parseObject(jsonstr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return parseObj;
    }

    /**
     * 字符串转list
     *
     * @param jsonstr
     * @return
     */
    public static JSONArray parseToJSONArray(String jsonstr) {
        JSONArray parseObj = null;
        try {
            parseObj = JSON.parseArray(jsonstr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return parseObj;
    }

    /**
     * json对象转bean
     *
     * @param jsonObj
     * @param obj
     * @return
     */
    public static Object parseToObject(JSONObject jsonObj, Class<?> obj) {
        Object parseObj = null;
        try {
            parseObj = JSON.parseObject(jsonObj + "", obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return parseObj;
    }

    /**
     * JSONArr 转list
     *
     * @param jsonArr
     * @param obj
     * @return
     */
    public static <T> List<T> parseToList(JSONArray jsonArr, Class<T> obj) {
        List<T> list = null;
        try {
            list = JSON.parseArray(jsonArr.toString(), obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /**
     * 从json HASH表达式中获取一个map，改map支持嵌套功能
     *
     * @param jsonString
     * @return
     */
    public static Map<?, ?> getMapByJsonString(String jsonString) {
        try {
            Map<?, ?> valueMap = JSON.parseObject(jsonString, Map.class);
            return valueMap;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T getObject4Map(Map<?, ?> map, Class<T> obj) {
        T t = null;
        try {
            String mapStr = JSON.toJSONString(map);
            t = (T) JSON.parseObject(mapStr, obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return t;
    }

    /**
     * 转成json对象，会去掉自动添加的斜杠
     *
     * @param obj
     * @return
     */
    public static Object parseToObject(Object obj) {
        return JSONObject.parse(JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));
    }

    /**
     * 转成json对象，会去掉自动添加的斜杠
     *
     * @param obj
     * @param filter
     * @return
     */
    public static Object parseToObjectWithFilter(Object obj, SimplePropertyPreFilter filter) {
        return JSONObject.parse(JSON.toJSONString(obj, filter, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));
    }

    /**
     * 转成String对象，会去掉自动添加的斜杠
     *
     * @param obj
     * @return
     */
    public static String parseToString(Object obj) {
        return JSON.toJSONString(parseToObject(obj));
    }

    /**
     * 转成List<String>
     *
     * @param json
     * @return
     */
    public static List<String> parseToList(String json) {
        List<String> list = null;
        try {
            JSONArray jsonArray = JSON.parseArray(json);
            list = JSON.parseArray(jsonArray + "", String.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /**
     * 简单校验是否是json串
     *
     * @param str
     * @return
     */
    public static Boolean isJson(String str) {
        try {
            JSON.parse(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
