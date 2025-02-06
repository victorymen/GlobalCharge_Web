package org.appserver.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.appserver.entity.Products;

/**
 * 产品(Products)表服务接口
 *
 * @author makejava
 * @since 2025-02-18 00:02:31
 */
public interface ProductsService extends IService<Products> {

    JSONObject findProducts(String id);

    JSONObject products(String countryId);

}

