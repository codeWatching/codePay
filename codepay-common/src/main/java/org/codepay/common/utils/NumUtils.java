package org.codepay.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class NumUtils {
    /**
     * 字符串转浮点型，如果为空則返回NULL
     * 
     * @param num
     * @return
     */
    public static Float createFloat(String num) {
        if (num == null || num.equals(""))
            return null;
        try {
            return new Float(num);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字符串转整型，如果为空則返回NULL
     * 
     * @param num
     * @return
     */
    public static Integer createInteger(String num) {
        if (num == null || num.equals(""))
            return null;
        try {
            return new Integer(num);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字符串转Double，如果为空則返回NULL
     * 
     * @param num
     * @return
     */
    public static Double createDouble(String num) {
        if (num == null || num.equals(""))
            return null;
        try {
            return new Double(num);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字符串转长整型，如果为空則返回NULL
     * 
     * @param num
     * @return
     */
    public static Long createLong(String num) {
        if (num == null || num.equals(""))
            return null;
        try {
            return new Long(num);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Integer[] toIntegerArr(String strArr[]) {
        Integer[] ia = new Integer[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            ia[i] = Integer.valueOf(strArr[i]);
        }
        return ia;
    }

    public static int[] toIntArr(String strArr[]) {
        int[] ia = new int[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            ia[i] = Integer.parseInt(strArr[i]);
        }
        return ia;
    }

    public static Integer[] toIntegerArr(List<String> strList) {
        Integer[] ia = new Integer[strList.size()];
        for (int i = 0; i < strList.size(); i++) {
            ia[i] = Integer.valueOf(strList.get(i));
        }
        return ia;
    }

    public static List<Long> toLongArr(String[] strArr) {
        List<Long> ia = new ArrayList<Long>(strArr.length);
        for (String line : strArr) {
            if (line.matches(RegexUtils.Number)) {
                ia.add(Long.valueOf(line));
            }
        }
        return ia;
    }

    public static List<Integer> toIntegerList(List<String> strList) {
        List<Integer> l = new ArrayList<Integer>();
        Iterator<String> it = strList.iterator();
        while (it.hasNext()) {
            l.add(Integer.valueOf(it.next()));
        }
        return l;
    }

    public static List<Integer> toIntegerList(String[] strArr) {
        List<Integer> l = new ArrayList<Integer>();
        for (String str : strArr) {
            l.add(Integer.valueOf(str));
        }
        return l;
    }

    public static List<Integer> toIntegerList(String bet, String splitStr) {
        List<Integer> l = new ArrayList<Integer>();
        if (bet.indexOf(splitStr) != -1) {
            String[] bets = bet.split(splitStr);
            for (String str : bets) {
                l.add(Integer.valueOf(str));
            }
        } else {
            l.add(Integer.valueOf(bet));
        }
        return l;
    }

    public static Integer[] listToIntegerArr(List<String> intList) {
        Integer[] arr = new Integer[intList.size()];
        for (int i = 0; i < intList.size(); i++) {
            arr[i] = Integer.valueOf(intList.get(i));
        }
        return arr;
    }

    public static Integer compare(Integer[] bet, Integer[] result, Boolean sort) {
        Integer hit = Integer.valueOf("0");
        if (sort) {
            Arrays.sort(bet);
            Arrays.sort(result);
        }
        for (int i = 0; i < bet.length; i++) {
            if (bet[i].equals(result[i])) {
                hit++;
            }
        }
        return hit;
    }

    /**
     * 判断20以内的数是否为质数
     * 
     * @param i
     * @return
     */
    public static boolean isPrimNum(int r) {
        if (r == 1 || r == 3 || r == 5 || r == 7 || r == 11 || r == 13 || r == 17 || r == 19) {
            return true;
        }
        return false;
    }

    // 数字转换字符串 不足长度补零 目标stringLenth字符串长度:2 转为002
    public static String getNumToString(int num, int stringLenth) {
        String str_m = String.valueOf(num);
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < stringLenth; i++) {
            str.append("0");
        }
        str_m = str.substring(0, stringLenth - str_m.length()) + str_m;
        return str_m;
    }
}
