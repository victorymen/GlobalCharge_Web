package org.appserver.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 订单记录(ProductsUser)表实体类
 *
 * @author makejava
 * @since 2025-02-18 01:21:16
 */
@SuppressWarnings("serial")
public class ProductsUser extends Model<ProductsUser> {
    //订单idid
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String amount;
    //国家
    private String cname;

    private String typename;
    //产品描述
    private String description;
    //产品名
    private String title;

    private Integer type;
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
    //用户id
    private String openid;
    //订单编号
    private String sernober;
    //充值号码
    private String rechargeNo;
    //实际支付金额
    private String userpayamount;
    //支付状态
    private String userpaystate;
    //订单状态
    private String sernoberstate;
    //下单时间
    private Date ordertime;
    //支付时间
    private Date paymenttime;
    //产品id
    private Integer proid;
    //平台订单号
    private String orderNo;


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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
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

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSernober() {
        return sernober;
    }

    public void setSernober(String sernober) {
        this.sernober = sernober;
    }

    public String getRechargeNo() {
        return rechargeNo;
    }

    public void setRechargeNo(String rechargeNo) {
        this.rechargeNo = rechargeNo;
    }

    public String getUserpayamount() {
        return userpayamount;
    }

    public void setUserpayamount(String userpayamount) {
        this.userpayamount = userpayamount;
    }

    public String getUserpaystate() {
        return userpaystate;
    }

    public void setUserpaystate(String userpaystate) {
        this.userpaystate = userpaystate;
    }

    public String getSernoberstate() {
        return sernoberstate;
    }

    public void setSernoberstate(String sernoberstate) {
        this.sernoberstate = sernoberstate;
    }

    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public Date getPaymenttime() {
        return paymenttime;
    }

    public void setPaymenttime(Date paymenttime) {
        this.paymenttime = paymenttime;
    }

    public Integer getProid() {
        return proid;
    }

    public void setProid(Integer proid) {
        this.proid = proid;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

