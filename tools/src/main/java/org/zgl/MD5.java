package org.zgl;


import java.security.MessageDigest;

/**
 * @author 猪哥亮
 */
public final class MD5 {
    private static char hexChars[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


    private MD5(){}

    public static String convert(String s){
        return convert(s,hexChars);
    }
    /**
     * @Description:加密-16位小写
     * @author:liuyc
     * @time:2016年5月23日 上午11:15:33
     */
    public static String convert16(String encryptStr) {
        return convert(encryptStr).substring(8, 24);
    }
    private static String convert(String s,char[] hex){
        try {
            byte[] bytes = s.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            bytes = md.digest();
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                chars[k++] = hex[b >>> 4 & 0xf];
                chars[k++] = hex[b & 0xf];
            }
            return new String(chars);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static String wxMd5(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString().toUpperCase();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}