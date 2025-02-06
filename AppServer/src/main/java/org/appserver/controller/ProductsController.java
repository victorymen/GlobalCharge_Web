package org.appserver.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.appserver.entity.Products;
import org.appserver.service.ProductsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 产品(Products)表控制层
 *
 * @author makejava
 * @since 2025-02-18 00:02:31
 */
@RestController
@RequestMapping("/api/products")
public class ProductsController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ProductsService productsService;


    @PostMapping()
    public R selectOne(@RequestBody Products products ) {
        return success(this.productsService.products(products.getCountryid()));
    }







}

