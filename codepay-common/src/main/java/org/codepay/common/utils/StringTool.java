package org.codepay.common.utils;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;




public class StringTool {
    public static boolean isEmpty(String value) {
        return (value == null || value.length() == 0 || value.trim().equals("")) ? true : false;
    }

    public static boolean isNotEmpty(String value) {
        return (value != null && value.length() > 0 && !value.trim().equals("")) ? true : false;
    }

    public static String notNull(String value) {
        return value == null ? "" : value.trim();
    }

    public static String toString(String value) {
        return value == null ? "" : value.trim();
    }

    public static String toString(String value, String defaultValue) {
        return value == null ? defaultValue : value.trim();
    }

    public static String toString(int value) {
        return String.valueOf(value);
    }

    public static String toString(double value) {
        return String.valueOf(value);
    }

    public static String toString(Integer value) {
        return value == null ? "" : String.valueOf(value);
    }

    public static String toString(Double value) {
        return value == null ? "" : String.valueOf(value);
    }
    
    public static boolean isBlank(Object o) {
        // o = o.toString().trim();
        if (o == null) {
            return true;
        }
        if (StringUtils.isBlank(o.toString())) {
            return true;
        }
        return false;
    }

    public static boolean isNotBlank(Object o) {

        // o = o.toString().trim();
        if (o == null) {
            return false;
        }
        if (StringUtils.isBlank(o.toString())) {
            return false;
        }
        return true;
    }
    
    /**
     *null处理返回字符串
     * 
     * @param resource
     * @return
     */
    public static String nullToString(Object obj) {
        String resource = "";
        if (obj == null || obj.equals("null")) {
            return resource;
        } else {
            resource = obj.toString().trim();
        }
        return resource;
    }
    
    public static int toInt(String value) {
        return toInt(value, 0);
    }

    public static int toInt(String value, int defaultValue) {
        if (value == null) {
            return defaultValue;
        }

        int num = defaultValue;
        try {
            num = Integer.parseInt(value);
        } catch (Exception e) {
        }
        return num;
    }

    public static int toInt(Integer value) {
        return value == null ? 0 : value.intValue();
    }

    public static int toInt(Integer value, int defaultValue) {
        return value == null ? defaultValue : value.intValue();
    }

    public static Integer toInteger(String value) {
        return value == null ? null : toInt(value);
    }

    public static double toDouble(String value) {
        return toDouble(value, 0.0);
    }

    public static double toDouble(String value, double defaultValue) {
        if (value == null) {
            return defaultValue;
        }

        double num = defaultValue;
        try {
            num = Double.parseDouble(value);
        } catch (Exception e) {
        }
        return num;
    }

    public static Date toDate(String s) {
        if (s == null || s.equals(""))
            return null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String formatDate(Date date) {
        if (date == null)
            return "";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    public static String formatDateTime(Date date) {
        if (date == null)
            return "";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    public static String formatPercent(Double value) {
        return value == null ? "" : formatPercent(value.doubleValue());
    }

    public static String formatPercent(double value) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        return numberFormat.format(value * 100) + "%";
    }

    public static String formatMoney(Double value) {
        return value == null ? "" : formatMoney(value.doubleValue());
    }

    public static String formatMoney(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("###,##0.00");
        return decimalFormat.format(value);
    }

    public static String formatFourString(int value) {
        DecimalFormat decimalFormat = new DecimalFormat("0000");
        return decimalFormat.format(value);
    }

    public static String formatSixString(int value) {
        DecimalFormat decimalFormat = new DecimalFormat("000000");
        return decimalFormat.format(value);
    }

    public static String replaceCR(String value) {
        return value == null ? "" : value.replaceAll("\n", "<br/>");
    }

    public static String formatHtml(String value) {
        if (value == null)
            return "";
        if (value.indexOf("<") > 0) {
            value = value.replaceAll("<", "&lt;");
        }

        if (value.indexOf(">") > 0) {
            value = value.replaceAll(">", "&gt;");
        }

        if (value.indexOf("\"") > 0) {
            value = value.replaceAll("\"", "&quot;");
        }
        return value;
    }

    public static boolean isContain(String value, String values) {
        if (isEmpty(value) || isEmpty(values)) {
            return false;
        }
        String[] array = values.split(",");
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(value)) {
                return true;
            }
        }
        return false;
    }
    
    public static String iso2gb(String value) {
        if (value == null) {
            return "";
        } else {
            try {
                return new String(value.trim().getBytes("iso-8859-1"), "gb2312");
            } catch (UnsupportedEncodingException e) {
                return "";
            }
        }
    }

    public static String repeat(String value, int n) {
        StringBuffer sb = new StringBuffer();
        sb.append(value);
        for (int i = 1; i < n; i++) {
            sb.append(value);
        }

        return sb.toString();
    }

    public static String cut(String value, int length) {
        if (value == null)
            return "";
        if (value.length() > length)
            return value.substring(0, length - 1) + "...";
        else
            return value;
    }

    public static String join(String[] values, String seperator) {
        int length = values.length;
        if (length == 0)
            return "";
        StringBuffer buf = new StringBuffer(length * values[0].length()).append(values[0]);
        for (int i = 1; i < length; i++) {
            buf.append(seperator).append(values[i]);
        }
        return buf.toString();
    }

    public static boolean isValidVarName(String value) {
        char[] chars = value.toCharArray();
        if (chars[0] != '_' && !Character.isLetter(chars[0]))
            return false;

        for (int i = 1; i < chars.length; i++) {
            char c = chars[i];
            if (c != '_' && !Character.isLetterOrDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static String toAlphabet(int value) {
        String alphabet[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
        if (value < 1 || value > 26)
            return "";
        return alphabet[value - 1];
    }
}