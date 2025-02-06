package org.appserver.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 充值类型编码(RechargeTypes)表实体类
 *
 * @author makejava
 * @since 2025-02-18 00:02:33
 */
@SuppressWarnings("serial")
public class RechargeTypes extends Model<RechargeTypes> {
    //类型编号
    private Integer type;
    //类型名称
    private String name;

    private String id;


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

