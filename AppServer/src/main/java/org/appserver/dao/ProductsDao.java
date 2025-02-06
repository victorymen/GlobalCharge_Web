package org.appserver.dao;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.appserver.entity.Products;
import org.appserver.entity.Userinfo;

/**
 * 产品(Products)表数据库访问层
 *
 * @author makejava
 * @since 2025-02-18 00:02:31
 */
public interface ProductsDao extends BaseMapper<Products> {

    @Select("${sqlStr}")
    ArrayList<Products> findArrayMap(@Param(value = "sqlStr") String sqlStr );

}

