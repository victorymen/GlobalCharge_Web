package org.appserver.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class SignGenerator {

    /**
     * 生成接口签名
     * @param params 请求参数Map（需包含所有非空参数）
     * @param appKey 平台分配的APP_KEY
     * @return 32位MD5签名（小写形式）
     */
    public static String generateSign(Map<String, Object> params, String appKey) {
        // 1. 过滤空值参数并排除sign字段
        Map<String, Object> filteredParams = new HashMap<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if ("sign".equals(key)) continue;
            if (value != null && !value.toString().isEmpty()) {
                filteredParams.put(key, value);
            }
        }

        // 2. 按字段名ASCII码升序排序
        Map<String, Object> sortedParams = new TreeMap<>(filteredParams);

        // 3. 拼接键值对字符串
        StringBuilder buf = new StringBuilder();
        for (Map.Entry<String, Object> entry : sortedParams.entrySet()) {
            if (buf.length() > 0) {
                buf.append("&");
            }
            buf.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue());
        }

        // 4. 追加APP_KEY
        buf.append(appKey);

        // 5. 生成MD5签名
        return md5(buf.toString());
    }

    /**
     * MD5加密（32位小写）
     */
    private static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());
            return bytesToHex(digest).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }

    /**
     * 字节数组转十六进制字符串
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // 示例调用
    public static void main(String[] args) {
        Map<String, Object> params = new HashMap<>();
        params.put("uid", "Men_api001");
        params.put("sign", "0"); // sign字段将被排除
        String appKey = "c33bd86351174a049f4b65c1a9a51fc1";
        String sign = generateSign(params, appKey);
        System.out.println("生成的签名: " + sign);

    }
}