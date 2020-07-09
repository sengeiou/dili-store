package com.dili.alioss.sdk.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * 用于七牛URL参数Base64编码
 */
public class UrlUtil {
    public static String safeUrlBase64Encode(byte[] data){
        String encodeBase64 = new BASE64Encoder().encode(data);
        String safeBase64Str = encodeBase64.replace('+', '-');
        safeBase64Str = safeBase64Str.replace('/', '_');
//        safeBase64Str = safeBase64Str.replaceAll("=", "");
        return safeBase64Str;
    }


    public static byte[] safeUrlBase64Decode(final String safeBase64Str) throws IOException {
        String base64Str = safeBase64Str.replace('-', '+');
        base64Str = base64Str.replace('_', '/');
        int mod4 = base64Str.length()%4;
        if(mod4 > 0){
            base64Str = base64Str + "====".substring(mod4);
        }
        return new BASE64Decoder().decodeBuffer(base64Str);
    }

//    public static void main(String[] args) throws IOException {
//        System.out.println(UrlUtil.safeUrlBase64Encode("#057CB1".getBytes()));
//        System.out.println(new String(UrlUtil.safeUrlBase64Decode("IzA1N0NCMQ==")));
//    }

}
