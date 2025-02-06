package org.appserver.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;
import java.util.Random;

/**
 * 各种id生成策略
 * <p>Title: IDUtils</p>
 * <p>Description: </p>
 *
 * @version 1.0
 */
public class IDUtils {

    /**
     * 图片名生成
     */
    public static String genImageName() {
        //取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        //long millis = System.nanoTime();
        //加上三位随机数
        Random random = new Random();
        int end3 = random.nextInt(999);
        //如果不足三位前面补0
        String str = millis + String.format("%03d", end3);
        return str;
    }

    /**
     * 商品id生成
     */
    public static long genItemId() {
        //取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        //long millis = System.nanoTime();
        //加上两位随机数
        Random random = new Random();
        int end2 = random.nextInt(99);
        //如果不足两位前面补0
        String str = millis + String.format("%02d", end2);
        long id = new Long(str);
        return id;
    }


    public static String md5(String password) {
        try {//采用MD5处理
            MessageDigest md =
                    MessageDigest.getInstance("MD5");
            byte[] output = md.digest(
                    password.getBytes());//加密处理
            //将加密结果output利用Base64转成字符串输出
            String ret =
                    Base64.encodeBase64String(output);
            return ret;
        } catch (Exception e) {
            throw new RuntimeException(
                    "密码加密失败", e);
        }

    }


}
