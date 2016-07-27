package org.codepay.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 序列化工具<br>
 * 对象若实现序列化接口，则可用该工具实现字节流与对象间的转换
 * 
 * @author luodaoquan
 * @date 2016-03-16
 *
 */
public class SerializerUtil {
    /**
     * 反序列化
     * 
     * @param bytes
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        T obj = null;
        ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
        ObjectInputStream oi = new ObjectInputStream(bi);

        obj = (T) oi.readObject();
        bi.close();
        oi.close();
        return obj;
    }

    /**
     * 序列化
     * 
     * @param obj
     * @return
     * @throws IOException
     */
    public static byte[] serialize(Serializable obj) throws IOException {
        byte[] bytes = null;
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(obj);

        bytes = bo.toByteArray();

        bo.close();
        oo.close();
        return bytes;
    }
}
