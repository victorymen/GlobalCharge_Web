package org.appserver;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.appserver.service.UserinfoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.appserver.utils.DynamicJsonArrayToDatabase.createTableAndInsertData;

@SpringBootTest
class AppServerApplicationTests {
    @Resource
    private UserinfoService userinfoService;
    @Test
    void contextLoads() {
//        JSONObject path = userinfoService.getObject("/countries",new JSONObject());
//        JSONArray jsonArray1 = path.getJSONObject("result").getJSONArray("countries");
//        System.out.println(jsonArray1);

//       createTableAndInsertData(jsonArray1.toJSONString(), "products");

//        JSONObject jsonObject = userinfoService.getObject("/products",new JSONObject());
//        jsonObject.getJSONObject("result").getJSONArray("products");

        JSONObject jsonObject = userinfoService.getObject("/recharge_types",new JSONObject());
        JSONArray jsonArray = jsonObject.getJSONArray("types");
        System.out.println(jsonArray);
        createTableAndInsertData(jsonArray.toJSONString(), "recharge_types");

    }








}
