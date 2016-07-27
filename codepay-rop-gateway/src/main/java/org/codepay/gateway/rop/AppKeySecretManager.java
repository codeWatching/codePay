package org.codepay.gateway.rop;


import java.util.HashMap;
import java.util.Map;

import com.rop.security.AppSecretManager;

/**
 * appkey验证中心.
 */
public class AppKeySecretManager implements AppSecretManager {

    private static Map<String, String> appKeySecretMap = new HashMap<String, String>();

    static {
        appKeySecretMap.put("Gb2DjW0uCGSQ84r1vn7qOIcs", "abcde12345abcde12345abcde12345");
        appKeySecretMap.put("4a3f8e3049a57ba0", "youxizhongchou");
    }


    public String getSecret(String appKey) {
        return appKeySecretMap.get(appKey);
    }


    public boolean isValidAppKey(String appKey) {
        return getSecret(appKey) != null;
    }
}

