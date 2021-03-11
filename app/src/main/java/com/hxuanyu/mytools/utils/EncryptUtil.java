package com.hxuanyu.mytools.utils;


import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

public class EncryptUtil {
    private static final char[] hexChar = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    private static final String ALGORITHM_DES ="DES/CBC/PKCS5Padding";
    private static final String ALGORITHM_3DES ="DESede/CBC/PKCS5Padding";
    private static final String ivString = "12345678";
    private static final String codeType = "UTF-8";



    /**
     * 对字符串DES加密
     *@param str 待加密字符串
     *@param key 秘钥字符串
     *@return 加密后的字符串（转换为Base64）
     */
    public static String EncryptorDES(String str , String key){

        try {
            // Cipher负责完成加密或解密工作
            Cipher c = null;
            c = Cipher.getInstance(ALGORITHM_DES);
            //该字节数组负责保存加密的结果
            byte[] cipherByte = new byte[0];
            AlgorithmParameterSpec parameterSpec = new IvParameterSpec(ivString.getBytes());
            // 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
            if (c != null) {
                c.init(Cipher.ENCRYPT_MODE, getKey(key, "DES"), parameterSpec);
            }
            // 加密，结果保存进cipherByte
            if (c != null) {
                cipherByte = c.doFinal(str.getBytes(codeType));
            }
            return new String(EncryptorBase64(cipherByte),codeType);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 对字符串DES解密
     *@param buff 待解密字符串（Base64）
     *@param key 秘钥字符串
     *@return 解密后的字符串
     */
    public static String DecryptorDES(String buff, String key){
        Cipher c = null;
        try {
            c = Cipher.getInstance(ALGORITHM_DES);
        //该字节数组负责保存解密的结果
        byte[] cipherByte = new byte[0];
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(ivString.getBytes());
        // 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式
        c.init(Cipher.DECRYPT_MODE, getKey(key,"DES"),paramSpec);
        return new String(c.doFinal(DecryptorBase64(buff.getBytes(codeType))),codeType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 对字符串3DES加密
     *@param str 待加密字符串
     *@param key 秘钥字符串
     *@return 加密后的字符串（转换为Base64）
     */
    public static String Encryptor3DES(String str , String key){

        try {
            // Cipher负责完成加密或解密工作
            Cipher c = null;
            c = Cipher.getInstance(ALGORITHM_3DES);
            //该字节数组负责保存加密的结果
            byte[] cipherByte = new byte[0];
            AlgorithmParameterSpec parameterSpec = new IvParameterSpec(ivString.getBytes());
            // 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
            if (c != null) {
                c.init(Cipher.ENCRYPT_MODE, getKey(key, "DESede"), parameterSpec);
            }
            // 加密，结果保存进cipherByte
            if (c != null) {
                cipherByte = c.doFinal(str.getBytes(codeType));
            }
            return new String(EncryptorBase64(cipherByte),codeType);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对字符串DES解密
     *@param buff 待解密字符串（Base64）
     *@param key 秘钥字符串
     *@return 解密后的字符串
     */
    public static String Decryptor3DES(String buff, String key){
        Cipher c = null;
        try {
            c = Cipher.getInstance(ALGORITHM_3DES);
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(ivString.getBytes());
            // 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式
            c.init(Cipher.DECRYPT_MODE, getKey(key,"DESede"),paramSpec);
            return new String(c.doFinal(DecryptorBase64(buff.getBytes(codeType))),codeType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static byte[] EncryptorBase64(byte[] src){
        return Base64.encodeBase64(src);
    }

    public static byte[] DecryptorBase64(byte[] src){
        return Base64.decodeBase64(src);
    }


    /**
     * 生成密钥
     * @return
     * @throws Exception
     */
    public static Key getKey(String strKey, String algorithm) throws Exception{

        switch (algorithm){
            case "DES":
                DESKeySpec dks = new DESKeySpec(strKey.getBytes());
                SecretKeyFactory keyFactoryDES = SecretKeyFactory.getInstance(algorithm);
                //System.out.println("生成密钥:"+toHexString(getKey.getEncoded ())+"----"+toHexString(getKey.getEncoded ()).length());
                return keyFactoryDES.generateSecret(dks);
            case "DESede":
                DESedeKeySpec d3ks = new DESedeKeySpec(strKey.getBytes());
                SecretKeyFactory keyFactory3DES = SecretKeyFactory.getInstance(algorithm);
                System.out.println("生成密钥:"+ Arrays.toString(keyFactory3DES.generateSecret(d3ks).getEncoded()));
                return keyFactory3DES.generateSecret(d3ks);
        }

        return null;
    }


    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (byte aB : b) {
            sb.append(hexChar[(aB & 0xf0) >>> 4]);
            sb.append(hexChar[aB & 0x0f]);
        }
        return sb.toString();
    }
}
