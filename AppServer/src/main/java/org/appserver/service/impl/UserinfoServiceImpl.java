package org.appserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.log4j.Logger;
import org.appserver.dao.UserinfoDao;
import org.appserver.entity.Userinfo;
import org.appserver.service.UserinfoService;
import org.appserver.utils.SignGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * (Userinfo)表服务实现类
 *
 * @author makejava
 * @since 2025-02-16 16:20:52
 */
@Service("userinfoService")
public class UserinfoServiceImpl extends ServiceImpl<UserinfoDao, Userinfo> implements UserinfoService {

    @Value("${yll.APP_KEY}")
    private String APP_KEY;
    @Value("${yll.uid}")
    private String uid;
    @Value("${yll.url}")
    private String URL;
    @Resource
    private RestTemplate restTemplate;


    public JSONObject getObject(String path, JSONObject params) {
        return doGet("/dl" + path, JSONObject.class, params).getJSONObject("result");
    }

    public JSONObject globe_Api(String path, JSONObject params) {
        return doGet(path, JSONObject.class, params);
    }

    private <T> T doGet(String path, Class<T> responseType, JSONObject param) {
        param.put("uid", uid);
        String sign = SignGenerator.generateSign(param, APP_KEY);
        param.put("sign", sign);
        // 将 JSONObject 转换为查询字符串
        StringBuilder queryString = new StringBuilder();
        boolean first = true;
        for (String key : param.keySet()) {
            if (!first) {
                queryString.append("&");
            }
            queryString.append(key).append("=").append(param.getString(key));
            first = false;
        }
        // 拼接 URL
        String url = URL + path + "?" + queryString.toString();
        System.out.println("url: " + url);
        return restTemplate.getForObject(url, responseType);
    }

}

