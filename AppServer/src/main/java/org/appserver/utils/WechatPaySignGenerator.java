package org.appserver.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;

// 微信支付签名生成器类
public class WechatPaySignGenerator {

    // 生成指定长度的随机字符串
    private static String generateNonceStr(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder nonceStr = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            nonceStr.append(characters.charAt(index));
        }
        return nonceStr.toString();
    }

    // 读取私钥文件
    private static String readPrivateKey(String filePath) throws IOException {
        StringBuilder privateKey = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("-----BEGIN PRIVATE KEY-----") && !line.startsWith("-----END PRIVATE KEY-----")) {
                    privateKey.append(line);
                }
            }
        }
        return privateKey.toString();
    }

    // 对签名串进行签名
    private static String sign(String signStr, String privateKeyPem) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException, InvalidKeySpecException {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyPem);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(signStr.getBytes(StandardCharsets.UTF_8));
        byte[] signBytes = signature.sign();
        return Base64.getEncoder().encodeToString(signBytes);
    }

    // 生成支付签名和相关参数
    public static Map<String, String> createPaySign(String prepayId, String appId, String pemFilePath) throws Exception {
        // 生成时间戳
        long timeStamp = new Date().getTime() / 1000;
        String timeStampStr = String.valueOf(timeStamp);

        // 生成随机字符串
        String nonceStr = generateNonceStr(32);

        // 构建签名串
        String signStr = String.format("%s\n%s\n%s\nprepay_id=%s\n", appId, timeStampStr, nonceStr, prepayId);

        // 读取私钥文件
        String privateKeyPem = readPrivateKey(pemFilePath);

        // 进行签名
        String paySign = sign(signStr, privateKeyPem);

        // 构建返回的支付参数
        Map<String, String> payParams = new HashMap<>();
        payParams.put("paySign", paySign);
        payParams.put("timestamp", timeStampStr);
        payParams.put("nonce_str", nonceStr);
        payParams.put("signType", "RSA");
        payParams.put("package", "prepay_id=" + prepayId);

        return payParams;
    }

    public static void main(String[] args) {
        try {
            String prepayId = "your_prepay_id";
            String appId = "your_app_id";
            String pemFilePath = "./pems/files/your_pem_file.pem";
            Map<String, String> payParams = createPaySign(prepayId, appId, pemFilePath);
            System.out.println(payParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
