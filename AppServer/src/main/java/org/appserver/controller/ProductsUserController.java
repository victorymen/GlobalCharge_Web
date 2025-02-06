package org.appserver.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.appserver.entity.ProductsUser;
import org.appserver.service.ProductsUserService;
import org.appserver.service.UserinfoService;
import org.appserver.utils.IDUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 订单记录(ProductsUser)表控制层
 *
 * @author makejava
 * @since 2025-02-18 00:02:32
 */
@RestController
@RequestMapping("/api/productsUser")
public class ProductsUserController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ProductsUserService productsUserService;

    /**
     * 分页查询所有数据
     *
     * @param page         分页对象
     * @param productsUser 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<ProductsUser> page, ProductsUser productsUser) {
        return success(this.productsUserService.page(page, new QueryWrapper<>(productsUser)));
    }

    /**
     * 通过主键查询单条数据
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.productsUserService.getById(id));
    }


    @Resource
    private UserinfoService userinfoService;

    /**
     * 新增数据 创建支付订单
     *
     * @param productsUser 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody ProductsUser productsUser) {
        productsUser.setSernober(IDUtils.genItemId()+"");
//       JSONObject result = userinfoService.globe_Api("/topup",new JSONObject(){{
//            put("recharge_no",productsUser.getRechargeNo());
//            put("user_order_no",productsUser.getSernober());
//            put("product_code",productsUser.getProid());
//        }});
//        System.out.println(result.toString());
//        if(result.getString("code").equals("10000")){
//            productsUser.setOrderNo(result.getJSONObject("result").getString("order_no"));
//        }
        return success(this.productsUserService.save(productsUser));
    }

    /**
     * 修改数据
     * @param productsUser 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody ProductsUser productsUser) {
        return success(this.productsUserService.updateById(productsUser));
    }


}

