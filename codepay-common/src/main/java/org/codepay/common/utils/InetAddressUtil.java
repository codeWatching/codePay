package org.codepay.common.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

/**
 * 
 * Description: <br>
 * 
 * Copyright: Copyright (c) 2013 <br>
 * Company: www.caipiao168.com
 * 
 * @author hui.zhu
 * @create-time 2013-7-22
 * @version 1.0
 * @E_mail huashuizhuhui@126.com
 */
public class InetAddressUtil {
    public static String getLocalHost() {
        try {
            Collection<InetAddress> colInetAddress = getAllHostAddress();
            for (InetAddress address : colInetAddress) {
                if (!address.isSiteLocalAddress() && !address.isLoopbackAddress()
                        && address.getHostAddress().indexOf(":") == -1) {
                    return address.getHostAddress();
                }
            }
        } catch (Exception e) {
        }
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Collection<InetAddress> getAllHostAddress() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            Collection<InetAddress> addresses = new ArrayList<InetAddress>();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    addresses.add(inetAddress);
                }
            }
            return addresses;
        } catch (SocketException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static void main(String args[]) {
        System.out.println(InetAddressUtil.getLocalHost());
    }
}
