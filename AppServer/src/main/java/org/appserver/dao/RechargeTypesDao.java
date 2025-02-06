package org.appserver.dao;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.appserver.entity.Products;
import org.appserver.entity.RechargeTypes;

/**
 * 充值类型编码(RechargeTypes)表数据库访问层
 *
 * @author makejava
 * @since 2025-02-18 00:02:33
 */
public interface RechargeTypesDao extends BaseMapper<RechargeTypes> {
    @Select("${sqlStr}")
    ArrayList<RechargeTypes> findArrayMap(@Param(value = "sqlStr") String sqlStr );

}

