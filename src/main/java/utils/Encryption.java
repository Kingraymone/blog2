package utils;

import org.apache.tomcat.util.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 加密工具类，提供rsa加密、base64编码
 * @package utils
 * @date 2020/11/23
 */
public class Encryption {
    public final static String PRIVATE_KEY="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJYWj7+iNvsNUuKbTVUQtGdDnTd9X5xQULi7NnTi5oONl501Ra704QbwQaxIcN5/dX4KJqnQWBLhzksqj4oLzHMZxDxq3992uJGAaRg/6qoAQWZC7ejVIao5M6kqIiNTcej1FGkMhBG4qOyEPogLOMxtPX95KLPaP7KPd5g5R3GfAgMBAAECgYEAiufKloMc+3CzW0J8adQSAthS8v43tMWoT7AEkF1HFk2/GiIpoejFMtS9EyPgQiwYo1PlwJgwSxltAagcPJj618YYNlMVA+XILdNe7DyI3J068LFilAb6+VWtSMap09a3DYbeP0Ssvee8wSuYn6s9ks+J0ZG8CKMoFRVEyuZIREECQQDe3lMKeoJTkNbh5HrYVoku0BeCw3I1qsKK8BENMGjOFIaZvkCJ0jtoqndMETrvZ6v1UB8V5IBng4AqrXaMDEoRAkEArGZyJVW1n9VNFiUyIEl6CuM/uUW7OA3Vo4yku6n1VnpYacn/eS5nP3re50A4p+mMH9kLjenk5Yrj277G+oPQrwJAOJY6f26bqJwd8z0P894E9t3fGlSFqxqwSpapva+M7MZJZr/noxa9/tVJdQbq795iyuaM/gIGmEHLSQrCDs0QAQJAQhyXMVII8F4vzYo+CruinYI/8aCYBwrnHmzUz1KBzsPkG81mtuXgmitYR34NO/cH0T6+e5FyQcRdVOt4CqF56QJAQGeJ7Gz+JJN4vLCOtDAh2Km0wJFLA4B7pYhPvRwJulAY82jDv9Bsi81sw2V0VSpm9Mc7TFkB4/JK67ZWlsEFTQ==";
    public final static String PUBLIC_KEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCWFo+/ojb7DVLim01VELRnQ503fV+cUFC4uzZ04uaDjZedNUWu9OEG8EGsSHDef3V+Ciap0FgS4c5LKo+KC8xzGcQ8at/fdriRgGkYP+qqAEFmQu3o1SGqOTOpKiIjU3Ho9RRpDIQRuKjshD6ICzjMbT1/eSiz2j+yj3eYOUdxnwIDAQAB";
    private static final String SALT = "king7";
    // md5加密
    public static String encode(String password) {
        password = password + SALT;
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        char[] charArray = password.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
    //生成秘钥对
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    //获取私钥(Base64编码)
    public static String getPrivateKey(KeyPair keyPair){
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] bytes = privateKey.getEncoded();
        return byte2Base64(bytes);
    }

    //将Base64编码后的公钥转换成PublicKey对象
    public static PublicKey string2PublicKey(String pubStr) throws Exception{
        byte[] keyBytes = base642Byte(pubStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    //将Base64编码后的私钥转换成PrivateKey对象
    public static PrivateKey string2PrivateKey(String priStr) throws Exception{
        byte[] keyBytes = base642Byte(priStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    //公钥加密
    public static byte[] publicEncrypt(byte[] content, PublicKey publicKey) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }

    //私钥解密
    public static byte[] privateDecrypt(byte[] content, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }
        //获取公钥(Base64编码)
    public static String getPublicKey(KeyPair keyPair){
        PublicKey publicKey = keyPair.getPublic();
        byte[] bytes = publicKey.getEncoded();
        return byte2Base64(bytes);
    }

    //字节数组转Base64编码
    public static String byte2Base64(byte[] bytes){
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bytes);
    }

    //Base64编码转字节数组
    public static byte[] base642Byte(String base64Key) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(base64Key);
    }


}
