package org.appserver.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 产品(Products)表实体类
 *
 * @author makejava
 * @since 2025-02-18 00:02:31
 */
@SuppressWarnings("serial")
public class Products extends Model<Products> {
    //产品 id
    private Integer id;

    private String amount;
    //国家
    private String cname;

    private String typename;
    //产品描述
    private String description;
    //产品名
    private String title;

    private String type;
    //国家 id
    private String countryid;
    //优惠价
    private String yh;
    //原价
    private String yj;
    //运营商
    private String yys;
    //有效期
    private String yxq;

    private String currency;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCountryid() {
        return countryid;
    }

    public void setCountryid(String countryid) {
        this.countryid = countryid;
    }

    public String getYh() {
        return yh;
    }

    public void setYh(String yh) {
        this.yh = yh;
    }

    public String getYj() {
        return yj;
    }

    public void setYj(String yj) {
        this.yj = yj;
    }

    public String getYys() {
        return yys;
    }

    public void setYys(String yys) {
        this.yys = yys;
    }

    public String getYxq() {
        return yxq;
    }

    public void setYxq(String yxq) {
        this.yxq = yxq;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

