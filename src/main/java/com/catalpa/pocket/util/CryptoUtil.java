package com.catalpa.pocket.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by bruce on 2018/1/22.
 */
@Slf4j
public class CryptoUtil {

    public static String encryptMD5(String encryptStr) {

        // 生成一个MD5加密计算摘要
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            log.error("encrypt error:", e);
            return null;
        }
        // 计算md5函数
        md.update(encryptStr.getBytes());
        // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
        byte[] digest = md.digest();
        StringBuilder hexValue = new StringBuilder();
        for (byte b : digest) {
            int val = b & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }

    public static String encrypt16MD5(String encryptStr) {
        String md5 = encryptMD5(encryptStr);
        return md5 != null ? md5.substring(8, 24) : null;
    }

    public static String getHMAC256Str(String secretKey, String encryptStr) {
        String encdeStr = "";
        try {
            Mac hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
            hmac.init(secret_key);

            encdeStr = Base64.encodeBase64String(hmac.doFinal(encryptStr.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return encdeStr;
    }

    public static String getHashSHA1Str(String encryptStr, String charsetName) {
        String encdeStr = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(encryptStr.getBytes(charsetName));
            byte[] hash = messageDigest.digest();
            encdeStr = Hex.encodeHexString(hash);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encdeStr;
    }

    public static String aesCbcDecrypt(String encryptedData, String sessionKey, String iv) {
        String decryptStr = "";
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] _encryptedData = base64Decoder.decodeBuffer(encryptedData);
            byte[] _sessionKey = base64Decoder.decodeBuffer(sessionKey);
            byte[] _iv = base64Decoder.decodeBuffer(iv);
            SecretKeySpec secretKeySpec = new SecretKeySpec(_sessionKey, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(_iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] original = cipher.doFinal(_encryptedData);
            byte[] bytes = PKCS7Encoder.decode(original);
            decryptStr = new String(bytes, "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return decryptStr;
    }
}
