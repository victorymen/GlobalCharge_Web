package org.appserver.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.appserver.dao.ProductsDao;
import org.appserver.dao.RechargeTypesDao;
import org.appserver.entity.Products;
import org.appserver.entity.RechargeTypes;
import org.appserver.service.ProductsService;
import org.appserver.service.UserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

import static org.appserver.utils.utils.removeEmptyFields;

/**
 * 产品(Products)表服务实现类
 *
 * @author makejava
 * @since 2025-02-18 00:02:31
 */
@Service("productsService")
public class ProductsServiceImpl extends ServiceImpl<ProductsDao, Products> implements ProductsService {

    @Autowired
    RechargeTypesDao rechargeTypesDao;
    @Resource
    private UserinfoService userinfoService;

    /**
     * 根据产品类型查询产品列表 数据库版本
     * @param countryId
     * @return
     */
    @Override
    public JSONObject findProducts(String countryId) {
        String sqlStr = "SELECT DISTINCT rt.*  FROM recharge_types rt WHERE EXISTS ( SELECT 1 FROM products s  WHERE s.countryId = '" + countryId + "'  AND s.`type` = rt.`type` ) ORDER BY rt.`type`;";
        ArrayList<RechargeTypes> arrayMap = rechargeTypesDao.findArrayMap(sqlStr);
        JSONArray array = removeEmptyFields(arrayMap);
        JSONObject result = new JSONObject();
        for (Object item : array) {
            if (!result.containsKey("reType")) {
                result.put("reType", 0);
            }
            JSONObject object = (JSONObject) item;
            object.put("content", findProducts(countryId, object.getString("type"), result));
        }
        result.put("content", array);
        return result;
    }

    private JSONArray findProducts(String countryId, String type, JSONObject items) {

        String sqlStr = " select yys from  products s where s.countryId ='" + countryId + "' and  s.`type`= '" + type + "' group  by yys";
        ArrayList<Products> arrayMap = baseMapper.findArrayMap(sqlStr);
        JSONArray array = removeEmptyFields(arrayMap);
        for (Object item : array) {
            if (!items.containsKey("yysType")) {
                items.put("yysType", 0);
            }
            JSONObject object = (JSONObject) item;
            object.put("content", products(countryId, type, object.getString("yys"), items));
        }
        return array;
    }

    private JSONArray products(String countryId, String type, String yys, JSONObject items) {
        String sqlStr = " select * from  products s where s.countryId ='" + countryId + "' and  s.`type`= '" + type + "' and  s.yys= '" + yys + "' ";
        ArrayList<Products> arrayMap = baseMapper.findArrayMap(sqlStr);
        JSONArray array = removeEmptyFields(arrayMap);
        if (array.size() > 0)
            items.put("proType", 0);
        return array;
    }



    /**
     * 根据产品类型查询产品列表 接口版本
     * @param countryId
     * @return
     */
    public JSONObject products(String countryId) {
        JSONObject jsonObject = userinfoService.getObject("/recharge_types", new JSONObject() {{
            put("country", countryId);
        }});
        JSONObject result = new JSONObject();
        JSONArray jsonArray = jsonObject.getJSONArray("types");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            if (!result.containsKey("reType")) {
                result.put("reType", 0);
            }
            object.put("content", jsonObject(countryId, object.getString("type"),result));
        }
        result.put("content", jsonArray);
        return result;
    }

    private JSONArray jsonObject(String countryId, String type, JSONObject result) {
        JSONObject jsonObject = userinfoService.getObject("/products", new JSONObject() {{
            put("country", countryId);
            put("type", type);
        }});
        JSONArray jsonArray = jsonObject.getJSONArray("products");
        return groupByYys(jsonArray,result);
    }

    private JSONArray groupByYys(JSONArray jsonArray, JSONObject result1) {
        JSONObject groupedResult = new JSONObject();
        // 遍历 jsonArray
        for (int i = 0; i < jsonArray.size(); i++) {
            if (!result1.containsKey("yysType")) {
                result1.put("yysType", 0);
            }
            JSONObject item = jsonArray.getJSONObject(i);
            String yysValue = item.getString("yys");
            // 创建一个内容数组，如果 yysValue 不存在则初始化
            if (!groupedResult.containsKey(yysValue)) {
                groupedResult.put(yysValue, new JSONArray());
            }
            // 将当前项添加到对应的 yysValue 数组中
            groupedResult.getJSONArray(yysValue).add(item);
        }
        JSONArray result = new JSONArray();
        for (String key : groupedResult.keySet()) {
            result.add(new JSONObject() {{
                put("type", key);
                put("content", groupedResult.getJSONArray(key));
            }});
        }
        return result;
    }


}

