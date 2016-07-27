package org.codepay.common.utils;

import java.util.Map;

import org.springframework.util.StringUtils;

public class RegexUtils {
    /**
     * 由汉字组成的字符串
     */
    public static final String Chinese = "^[\u4e00-\u9fa5]+$";

    /**
     * 由数字、26个英文字母或中文组成的字符串
     */
    public static final String LetterAndNumberAndChinese = "^[A-Za-z0-9\u4e00-\u9fa5]+$";

    /**
     * 由数字、26个英文字母或者下划线组成的字符串
     */
    public static final String LetterAndNumberAndUnderline = "^\\w+$";


    public static final String SIMPLE_JSON = "^\\{.+\\}|\\[\\{.+\\}\\]$";

    /**
     * 由数字和26个英文字母组成的字符串
     */
    public static final String LetterAndNumber = "^[A-Za-z0-9]+$";

    /**
     * 由数字和26个小写英文字母组成的字符串
     */
    public static final String LowerLetterAndNumber = "^[a-z0-9]+$";

    /**
     * 由数字和26个大写英文字母组成的字符串
     */
    public static final String UpperLetterAndNumber = "^[A-Z0-9]+$";

    /**
     * 由26个英文字母组成的字符串
     */
    public static final String Letter = "^[A-Za-z]+$";

    /**
     * 由26个英文字母的小写组成的字符串
     */
    public static final String LowerLetter = "^[a-z]+$";

    /**
     * 由26个英文字母的大写组成的字符串
     */
    public static final String UpperLetter = "^[A-Z]+$";

    /**
     * 由数字的字符串
     */
    public static final String Number = "^[0-9]+\\s*$";

    /**
     * email地址
     */
    public static final String Email = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";

    /**
     * url
     */
    public static final String Url = "^[a-zA-z]+://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(\\?\\S*)?$";

    /**
     * 正整数
     */
    public static final String Gt0Integer = "^[0-9]*[1-9][0-9]*$";

    /**
     * 非负数（正数 + 0）
     */
    public static final String Ge0Double = "^\\d+(\\.\\d+)?$";

    /**
     * 非数字
     */
    public static final String UnNumber = "[^0-9]+";

    /**
     * 身份证
     */
    public static final String IdCard = "^(([0-9]{14}[xX0-9]{1})|([0-9]{17}[xX0-9]{1}))$";

    /**
     * HH:mm:ss
     */
    public static final String HHmmss = "^([01][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";

    public static final String pathSeparator = "/";

    private static boolean matchStrings(String pattern, String str, Map<String, String> uriTemplateVariables) {
        AntPathStringMatcher matcher = new AntPathStringMatcher(pattern, str, uriTemplateVariables);
        return matcher.matchStrings();

    }

    /**
     * Actually match the given <code>path</code> against the given
     * <code>pattern</code>.
     *
     * @param pattern   the pattern to match against
     * @param path      the path String to test
     * @param fullMatch whether a full pattern match is required (else a pattern match
     *                  as far as the given base path goes is sufficient)
     * @return <code>true</code> if the supplied <code>path</code> matched,
     * <code>false</code> if it didn't
     */
    public static boolean pathMatchesUrl(String pattern, String path, boolean fullMatch,
                                         Map<String, String> uriTemplateVariables) {
        if (StringTool.isEmpty(pattern) || StringTool.isEmpty(path)) {
            return false;
        }
        if (path.startsWith(pathSeparator) != pattern.startsWith(pathSeparator)) {
            return false;
        }

        String[] pattDirs = StringUtils.tokenizeToStringArray(pattern, pathSeparator);
        String[] pathDirs = StringUtils.tokenizeToStringArray(path, pathSeparator);

        int pattIdxStart = 0;
        int pattIdxEnd = pattDirs.length - 1;
        int pathIdxStart = 0;
        int pathIdxEnd = pathDirs.length - 1;

        // Match all elements up to the first **
        while (pattIdxStart <= pattIdxEnd && pathIdxStart <= pathIdxEnd) {
            String patDir = pattDirs[pattIdxStart];
            if ("**".equals(patDir)) {
                break;
            }
            if (!matchStrings(patDir, pathDirs[pathIdxStart], uriTemplateVariables)) {
                return false;
            }
            pattIdxStart++;
            pathIdxStart++;
        }

        if (pathIdxStart > pathIdxEnd) {
            // Path is exhausted, only match if rest of pattern is * or **'s
            if (pattIdxStart > pattIdxEnd) {
                return (pattern.endsWith(pathSeparator) ? path.endsWith(pathSeparator) : !path.endsWith(pathSeparator));
            }
            if (!fullMatch) {
                return true;
            }
            if (pattIdxStart == pattIdxEnd && pattDirs[pattIdxStart].equals("*") && path.endsWith(pathSeparator)) {
                return true;
            }
            for (int i = pattIdxStart; i <= pattIdxEnd; i++) {
                if (!pattDirs[i].equals("**")) {
                    return false;
                }
            }
            return true;
        } else if (pattIdxStart > pattIdxEnd) {
            // String not exhausted, but pattern is. Failure.
            return false;
        } else if (!fullMatch && "**".equals(pattDirs[pattIdxStart])) {
            // Path start definitely matches due to "**" part in pattern.
            return true;
        }

        // up to last '**'
        while (pattIdxStart <= pattIdxEnd && pathIdxStart <= pathIdxEnd) {
            String patDir = pattDirs[pattIdxEnd];
            if (patDir.equals("**")) {
                break;
            }
            if (!matchStrings(patDir, pathDirs[pathIdxEnd], uriTemplateVariables)) {
                return false;
            }
            pattIdxEnd--;
            pathIdxEnd--;
        }
        if (pathIdxStart > pathIdxEnd) {
            // String is exhausted
            for (int i = pattIdxStart; i <= pattIdxEnd; i++) {
                if (!pattDirs[i].equals("**")) {
                    return false;
                }
            }
            return true;
        }

        while (pattIdxStart != pattIdxEnd && pathIdxStart <= pathIdxEnd) {
            int patIdxTmp = -1;
            for (int i = pattIdxStart + 1; i <= pattIdxEnd; i++) {
                if (pattDirs[i].equals("**")) {
                    patIdxTmp = i;
                    break;
                }
            }
            if (patIdxTmp == pattIdxStart + 1) {
                // '**/**' situation, so skip one
                pattIdxStart++;
                continue;
            }
            // Find the pattern between padIdxStart & padIdxTmp in str between
            // strIdxStart & strIdxEnd
            int patLength = (patIdxTmp - pattIdxStart - 1);
            int strLength = (pathIdxEnd - pathIdxStart + 1);
            int foundIdx = -1;

            strLoop:
            for (int i = 0; i <= strLength - patLength; i++) {
                for (int j = 0; j < patLength; j++) {
                    String subPat = pattDirs[pattIdxStart + j + 1];
                    String subStr = pathDirs[pathIdxStart + i + j];
                    if (!matchStrings(subPat, subStr, uriTemplateVariables)) {
                        continue strLoop;
                    }
                }
                foundIdx = pathIdxStart + i;
                break;
            }

            if (foundIdx == -1) {
                return false;
            }

            pattIdxStart = patIdxTmp;
            pathIdxStart = foundIdx + patLength;
        }

        for (int i = pattIdxStart; i <= pattIdxEnd; i++) {
            if (!pattDirs[i].equals("**")) {
                return false;
            }
        }

        return true;
    }

}
