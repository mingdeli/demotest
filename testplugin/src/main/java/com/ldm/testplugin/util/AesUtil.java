package com.ldm.testplugin.util;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.URLDecoder;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;

/**
 * AES的加密和解密
 * @ClassName: AesUtil
 * @author sunt
 * @date 2017年11月30日
 * @version V1.0
 */
@Slf4j
public class AesUtil {

    public static final String PDAES = "AES";
    public static final String PKEY = "4B65D4D348332222EA2ECFAFA0E876A3";
    public static String PWD_KEY = "passwd123456";
    public static final String PDWKEY2 = "0123456789ABCDEF";
    public static final String SKEY = "ABCDEF0123456789";
    public static final String PDAES_ALGORITHMSTR_ECB = "AES/ECB/PKCS5Padding";
    public static final String PDAES_ALGORITHMSTR_ECB7 = "AES/ECB/PKCS7Padding";
    public static final String PDAES_ALGORITHMSTR_CBC = "AES/CBC/PKCS5Padding";

    //算法
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    /**
     * aes解密
     * @param encrypt   内容
     * @return
     * @throws Exception
     */
    public static String aesDecrypt(String encrypt) {
        try {
            return aesDecrypt(encrypt, AesUtil.PKEY);
        } catch (Exception e) {
            log.info("aesDecrypt fail ... ");
            return "";
        }
    }

    /**
     * aes加密
     * @param content
     * @return
     * @throws Exception
     */
    public static String aesEncrypt(String content) {
        try {
            return aesEncrypt(content, AesUtil.PKEY);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 将byte[]转为各种进制的字符串
     * @param bytes byte[]
     * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
     * @return 转换后的字符串
     */
    public static String binary(byte[] bytes, int radix){
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
    }

    /**
     * base 64 encode
     * @param bytes 待编码的byte[]
     * @return 编码后的base 64 code
     */
    public static String base64Encode(byte[] bytes){
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * base 64 decode
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     * @throws Exception
     */
    public static byte[] base64Decode(String base64Code) throws Exception{
        return StrUtil.isEmpty(base64Code) ? null : Base64.getDecoder().decode(base64Code);
    }


    /**
     * AES加密
     * @param content 待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的byte[]
     * @throws Exception
     */
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance(AesUtil.PDAES);
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), AesUtil.PDAES));

        return cipher.doFinal(content.getBytes("utf-8"));
    }


    /**
     * AES加密为base 64 code
     * @param content 待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的base 64 code
     * @throws Exception
     */
    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }

    /**
     * AES解密
     * @param encryptBytes 待解密的byte[]
     * @param decryptKey 解密密钥
     * @return 解密后的String
     * @throws Exception
     */
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance(AesUtil.PDAES);
        kgen.init(128);

        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), AesUtil.PDAES));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }


    /**
     * 将base 64 code AES解密
     * @param encryptStr 待解密的base 64 code
     * @param decryptKey 解密密钥
     * @return 解密后的string
     * @throws Exception
     */
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        return StrUtil.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }

    public static String getKey(){
        String byte2Str="";
        try {
            String kye = AesUtil.PWD_KEY;
            byte[] kyeByte = MD5STo16Byte.encrypt2MD5toByte16(kye);
            byte2Str = byteArrayToHexStr(kyeByte);
        } catch (Exception e) {
            System.out.println("AES.getKey()出现异常");
            e.printStackTrace();
        }
        return byte2Str;
    }
    public static String getKeyByPass(String password){
        String byte2Str="";
        try {
            byte[] kyeByte = MD5STo16Byte.encrypt2MD5toByte16(password);
            byte2Str = byteArrayToHexStr(kyeByte);
        } catch (Exception e) {
            System.out.println("AES.getKeyByPass()出现异常");
            e.printStackTrace();
        }
        return byte2Str;
    }

    public static String byteArrayToHexStr(byte[] byteArray) {
        if (byteArray == null){
            return null;
        }
        char[] hexArray = AesUtil.PDWKEY2.toCharArray();
        char[] hexChars = new char[byteArray.length * 2];
        for (int j = 0; j < byteArray.length; j++) {
            int v = byteArray[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static void main(String[] args) {
        try {
            String str="wo shi good 人好人";
            String s = AesUtil.aesEncrypt(str,AesUtil.getKey());
            System.out.println(s );
            String value = AesUtil.aesDecrypt(s, AesUtil.getKey());
            System.out.println(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
