/*
 * Copyright 2015 ireader.com All right reserved. This software is the
 * confidential and proprietary information of ireader.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ireader.com.
 */
package org.codepay.common.utils;

import java.util.UUID;

/**
 * @author wangliping
 * @Descriptions 生成8位长度的UUID.
 * @date 2015年1月14日
 */
public final class GenerateShortUUID {

    /**
     * 单例
     */
    private GenerateShortUUID() {
    }

    /**
     * 60个数字字母 3C
     */
    private final static char[] CHARS = {'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
            't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
            'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
            'W', 'X', 'Y', 'Z'};

    /**
     * @return 8位长度字符
     */
    public static String next() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        char[] buf = new char[8];
        String radix16;
        int index;
        for (int i = 0; i < 8; i++) {
            radix16 = uuid.substring(i * 4, i * 4 + 4);
            index = Integer.parseInt(radix16, 16) % 0x3E;
            buf[i] = CHARS[index];
        }
        return String.valueOf(buf);
    }
}
