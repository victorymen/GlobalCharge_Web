package org.appserver.dao;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.appserver.entity.ProductsUser;
import org.appserver.entity.Userinfo;

/**
 * 订单记录(ProductsUser)表数据库访问层
 *
 * @author makejava
 * @since 2025-02-18 00:02:32
 */
public interface ProductsUserDao extends BaseMapper<ProductsUser> {



}

