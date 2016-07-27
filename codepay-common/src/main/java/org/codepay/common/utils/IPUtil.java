package org.codepay.common.utils;

import java.util.regex.Pattern;

import org.codepay.common.SeparatorConstant;

public class IPUtil {

    private final static Pattern PATTERN =
            Pattern.compile("^(1|([1-9]{1,2}|[1-9]0)|(1[0-9]{2}|2[0-5]{2}))((.(0|([1-9]{1,2}|[1-9]0)|(1[0-9]{2}|2[0-5]{2}))){2}).(1|([1-9]{1,2}|[1-9]0)|(1[0-9]{2}|2[0-5]{2}))$");

    public static long bytesToLong(byte a, byte b, byte c, byte d) {
        return int2long((((a & 0xff) << 24) | ((b & 0xff) << 16) | ((c & 0xff) << 8) | (d & 0xff)));
    }

    public static int str2Ip(String ip) {
        String[] ss = ip.split(SeparatorConstant.FULL_POINT_REGEX);
        int a, b, c, d;
        a = Integer.parseInt(ss[0]);
        b = Integer.parseInt(ss[1]);
        c = Integer.parseInt(ss[2]);
        d = Integer.parseInt(ss[3]);
        return (a << 24) | (b << 16) | (c << 8) | d;
    }

    /**
     * ip匹配.
     * 
     * @param ip
     * @return
     */
    public static boolean match(String ip) {
        return PATTERN.matcher(ip).matches();
    }

    public static long ip2long(String ip) {
        return int2long(str2Ip(ip));
    }

    public static long int2long(int i) {
        long l = i & 0x7fffffffL;
        if (i < 0) {
            l |= 0x080000000L;
        }
        return l;
    }
}
