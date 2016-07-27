package org.codepay.common.utils;

/**
 * ====================================================================
 *
 * Copyright (C) 2002-2005 by MyVietnam.net
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * All copyright notices regarding MyVietnam and MyVietnam CoreLib
 * MUST remain intact in the scripts and source code.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * Correspondence and Marketing Questions can be sent to:
 * info@MyVietnam.net
 *
 * @author: Minh Nguyen  minhnn@MyVietnam.net
 * @author: Mai  Nguyen  mai.nh@MyVietnam.net
 */
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Encoder {

    /**
     * 生成3DES密钥.
     * 
     * @param key_byte
     *            seed key
     * @throws Exception
     * @return javax.crypto.SecretKey Generated DES key
     */
    public static javax.crypto.SecretKey genDESKey(byte[] key_byte)
            throws Exception {
        SecretKey k = null;
        k = new SecretKeySpec(key_byte, "DESede");
        return k;
    }

    /**
     * 3DES加密(byte[]).
     * 
     * @param src
     *            byte[]
     * @throws Exception
     * @return byte[]
     */
    public static byte[] desEncrypt(byte[] src) throws Exception {
        javax.crypto.SecretKey key = genDESKey("123456781234567812345678"
            .getBytes());
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("DESede");
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(src);
    }

    /**
     * 3DES 解密(byte[]).
     * 
     * @param crypt
     *            byte[]
     * @throws Exception
     * @return byte[]
     */
    public static byte[] desDecrypt(byte[] crypt) throws Exception {
        javax.crypto.SecretKey key = genDESKey("123456781234567812345678"
            .getBytes());
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("DESede");
        cipher.init(javax.crypto.Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(crypt);
    }

    public static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    public static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    public static String encrypt(String pwd) {
        String code = null;
        try {
            byte[] enc = desEncrypt(pwd.getBytes("UTF-8")); 
            code = byteArr2HexStr(enc);
        } catch (Exception e) {
            return null;
        }
        return code;
    }

    public static String decrypt(String code) {
        byte[] des = null;
        String dec = "";
        try {
            des = desDecrypt(hexStr2ByteArr(code));
            dec = new String(des,"UTF-8");
        } catch (Exception e) {
            return null;
        }
        return dec;
    }

    public static void main(String[] args) throws Exception {
        String result = encrypt("{pkg_id:1,iuser:'guolin'}");
        String code = decrypt("227bedbe9578a05e18eec89eb427baef48cccc92b222df8728a9797956a17b56edf967ea6f13b4bc");
        System.out.println(result);
        System.out.println(code);

    }


}

