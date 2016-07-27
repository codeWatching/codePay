package org.codepay.common.utils;
import java.util.Random;

/**
 * @Descriptions 随机数、随即字符串工具 
 * @date 2014-12-11
 * @author zhuhui
 */
public class RandomUtils { 
        private static final String ALL_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
        private static final String LETTER_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
        private static final String NUMBER_CHAR = "0123456789"; 
        private static final String LUCK_NUMBER_CHAR = "0268";
        /** 
         * 返回一个定长的随机字符串(只包含大小写字母、数字) 
         * 
         * @param length 随机字符串长度 
         * @return 随机字符串 
         */ 
        public static String generateString(int length) { 
                StringBuffer sb = new StringBuffer(); 
                Random random = new Random(); 
                for (int i = 0; i < length; i++) { 
                        sb.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length()))); 
                } 
                return sb.toString(); 
        } 


        /** 
         * 返回一个定长的随机字符串(只包含数字) 
         * 
         * @param length 随机字符串长度 
         * @return 随机字符串 
         */ 
        public static String generateNumString(int length) { 
                StringBuffer sb = new StringBuffer(); 
                Random random = new Random(); 
                for (int i = 0; i < length; i++) { 
                        sb.append(NUMBER_CHAR.charAt(random.nextInt(NUMBER_CHAR.length()))); 
                } 
                return sb.toString(); 
        } 
        
        /** 
         * 返回一个定长的随机纯字母字符串(只包含大小写字母) 
         * 
         * @param length 随机字符串长度 
         * @return 随机字符串 
         */ 
        public static String generateMixString(int length) { 
                StringBuffer sb = new StringBuffer(); 
                Random random = new Random(); 
                for (int i = 0; i < length; i++) { 
                        sb.append(ALL_CHAR.charAt(random.nextInt(LETTER_CHAR.length()))); 
                } 
                return sb.toString(); 
        } 

        /** 
         * 返回一个定长的随机纯大写字母字符串(只包含大小写字母) 
         * 
         * @param length 随机字符串长度 
         * @return 随机字符串 
         */ 
        public static String generateLowerString(int length) { 
                return generateMixString(length).toLowerCase(); 
        } 

        /** 
         * 返回一个定长的随机纯小写字母字符串(只包含大小写字母) 
         * 
         * @param length 随机字符串长度 
         * @return 随机字符串 
         */ 
        public static String generateUpperString(int length) { 
                return generateMixString(length).toUpperCase(); 
        } 

        /** 
         * 生成一个定长的纯0字符串 
         * 
         * @param length 字符串长度 
         * @return 纯0字符串 
         */ 
        public static String generateZeroString(int length) { 
                StringBuffer sb = new StringBuffer(); 
                for (int i = 0; i < length; i++) { 
                        sb.append('0'); 
                }  
                return sb.toString(); 
        } 

        /** 
         * 根据数字生成一个定长的字符串，长度不够前面补0 
         * 
         * @param num             数字 
         * @param fixdlenth 字符串长度 
         * @return 定长的字符串 
         */ 
        public static String toFixdLengthString(long num, int fixdlenth) { 
                StringBuffer sb = new StringBuffer(); 
                String strNum = String.valueOf(num); 
                if (fixdlenth - strNum.length() >= 0) { 
                        sb.append(generateZeroString(fixdlenth - strNum.length())); 
                } else { 
                        throw new RuntimeException("将数字" + num + "转化为长度为"+ fixdlenth + "的字符串发生异常！"); 
                } 
                sb.append(strNum); 
                return sb.toString(); 
        } 

        /** 
         * 根据数字生成一个定长的字符串，长度不够前面补0 
         * 
         * @param num             数字 
         * @param fixdlenth 字符串长度 
         * @return 定长的字符串 
         */ 
        public static String toFixdLengthString(int num, int fixdlenth) { 
                StringBuffer sb = new StringBuffer(); 
                String strNum = String.valueOf(num); 
                if (fixdlenth - strNum.length() >= 0) { 
                        sb.append(generateZeroString(fixdlenth - strNum.length())); 
                } else { 
                        throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常！"); 
                } 
                sb.append(strNum); 
                return sb.toString(); 
        } 
        
        /** 
         * 返回一个定长的随机字符串(只包含数字) 
         * 
         * @param length 随机字符串长度 
         * @return 随机字符串 
         */ 
        public static String generateLuckNumString(int length) { 
                StringBuffer sb = new StringBuffer(); 
                Random random = new Random(); 
                for (int i = 0; i < length; i++) { 
                        sb.append(LUCK_NUMBER_CHAR.charAt(random.nextInt(LUCK_NUMBER_CHAR.length()))); 
                } 
                return sb.toString(); 
        } 
}