package org.codepay.common.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringFind {

    /**
     * 获取substr代表的字符串在str中第frequency次出现的位置 例如：str="abcdaef";
     * substr="a";frequency=2;结果就是：4 如果没有找到substr则返回str的总长度
     * 如果frequency小于等于0则返回-1
     * 
     * @param str 源字符串
     * @param subStr 要查找的目标字符串
     * @param frequency 子字符串出现的次数
     * @return 返回子字符串第frequency次出现的位置
     */
    public static int getPos(String str, String subStr, int frequency) {
        // 如果出现的次数小于等于0，直接返回-1
        if (frequency <= 0) {
            return -1;
        }
        int start = -1;
        Matcher matcher = Pattern.compile(subStr).matcher(str);
        int mIdx = 0;
        while (matcher.find()) {
            mIdx++;
            // 当subStr出现的次数等于frequency停止循环
            if (mIdx == frequency) {
                break;
            }
        }
        try {
            start = matcher.start();
        } catch (Exception e) {
            start = str.length();
        }
        // 如果出现的次数小于等于指定的次数，返回整个字符串的长度
        if (mIdx < frequency && mIdx > 0) {
            start = str.length();
        }
        return start;
    }
}
