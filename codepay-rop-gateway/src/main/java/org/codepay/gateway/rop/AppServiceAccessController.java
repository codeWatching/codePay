package org.codepay.gateway.rop;

import com.rop.security.ServiceAccessController;
import com.rop.session.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 无校验服务配置.
 *
 */
public class AppServiceAccessController implements ServiceAccessController {

    private static final Map<String, List<String>> aclMap = new HashMap<String, List<String>>();

    static {
        ArrayList<String> serviceMethods = new ArrayList<String>();
        serviceMethods.add("user.logon");
        serviceMethods.add("user.logout");
        serviceMethods.add("user.getSession");
        aclMap.put("00003", serviceMethods);
    }


    public boolean isAppGranted(String appKey, String method, String version) {
        if (aclMap.containsKey(appKey)) {
            List<String> serviceMethods = aclMap.get(appKey);
            return serviceMethods.contains(method);
        }
        return true;
    }


    public boolean isUserGranted(Session session, String method, String version) {
        return true;
    }
}

