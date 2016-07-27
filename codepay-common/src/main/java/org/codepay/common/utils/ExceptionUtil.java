package org.codepay.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Description: 异常信息处理.<br>
 * Copyright: Copyright (c) 2013 <br>
 * Company: www.caipiao168.com
 *
 * @author caiqs
 * @version 1.0
 * @create-time 2013年11月4日
 */
public class ExceptionUtil {
    /**
     * 返回异常的堆栈信息.
     *
     * @param t
     * @return
     */
    public static String getException(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return sw.getBuffer().toString();
    }
}
