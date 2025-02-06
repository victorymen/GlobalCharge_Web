package org.appserver.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.appserver.entity.Userinfo;

/**
 * (Userinfo)表服务接口
 *
 * @author makejava
 * @since 2025-02-16 16:20:52
 */
public interface UserinfoService extends IService<Userinfo> {
     JSONObject getObject(String path, JSONObject params);

     JSONObject globe_Api(String path, JSONObject params);

}

