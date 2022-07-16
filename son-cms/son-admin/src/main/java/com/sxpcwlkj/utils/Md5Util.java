package com.sxpcwlkj.utils;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

    public static void main(String[] args) {
        System.out.println("MD5多次加密:" + getPassword("123456"));
        System.out.println("加密：" + getOne("集福购"));
        System.out.println("解密：" + getTwo("Z83E0yuK52FB3OdS/uoetw=="));
    }

    private static String sKey = "abcdef0123456789";
    private static String ivParameter = "0123456789abcdef";
    private static Md5Util instance = null;
    private Md5Util() {

    }

    /**
     * MD5再次加密
     *
     * @param password
     * @return
     */
    public static String getPassword(String password) {
        password = md5(password);
        password = md5(password);
        password = md5(password);
        password = md5(password);
        return password;
    }

    /**
     * MD5 加密主方法
     *
     * @param str
     * @return
     */
    public static String md5(String str) {
        try {
            //确定计算方法
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            //加密后的字符串
            String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
            return newstr;
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }
   /*========================================可逆加密/解密==========================================*/
    // 加密
    public static String getOne(String sSrc) {
        String result = "";
        try {
            Cipher cipher;
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] raw = sKey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
            result = new BASE64Encoder().encode(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 此处使用BASE64做转码。
        return result;

    }

    // 解密
    public static String getTwo(String sSrc) {
        try {
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);// 先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Md5Util getInstance() {
        if (instance == null)
            instance = new Md5Util();
        return instance;
    }

    //key


}
