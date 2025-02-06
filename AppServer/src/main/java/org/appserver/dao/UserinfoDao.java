package org.appserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.appserver.entity.Userinfo;

import java.util.ArrayList;

/**
 * (Userinfo)表数据库访问层
 *
 * @author makejava
 * @since 2025-02-16 16:20:52
 */
public interface UserinfoDao extends BaseMapper<Userinfo> {
    @Select("${sqlStr}")
    ArrayList<Userinfo> findArrayMap(@Param(value = "sqlStr") String sqlStr );
}

