package org.appserver.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 国家(Countries)表实体类
 *
 * @author makejava
 * @since 2025-02-18 00:02:19
 */
@SuppressWarnings("serial")
public class Countries extends Model<Countries> {
    //国家 id
    private Integer id;
    //国家
    private String cname;
    //国家英文名称
    private String ename;
    //货币单位
    private String currencycode;
    //国家编码
    private String countrycode;
    //国家号码区号
    private String areanumber;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getCurrencycode() {
        return currencycode;
    }

    public void setCurrencycode(String currencycode) {
        this.currencycode = currencycode;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    public String getAreanumber() {
        return areanumber;
    }

    public void setAreanumber(String areanumber) {
        this.areanumber = areanumber;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}

