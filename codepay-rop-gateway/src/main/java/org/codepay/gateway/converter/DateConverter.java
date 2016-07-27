package org.codepay.gateway.converter;

import java.util.Date;

import org.codepay.common.utils.DateUtil;

import com.rop.request.RopConverter;

/**
 * 日期转换器.
 */
public class DateConverter implements RopConverter<String, Date> {

    private final static String DATETIME_FORMAT = "yyyyMMddHHmmss";

    public Date convert(String s) {
        return DateUtil.strToDate(s, DATETIME_FORMAT);
    }


    public String unconvert(Date date) {
        return DateUtil.dateToStr(date, DATETIME_FORMAT);
    }


    public Class<String> getSourceClass() {
        return String.class;
    }


    public Class<Date> getTargetClass() {
        return Date.class;
    }
}
