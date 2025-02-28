package org.appserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * (Userinfo)表实体类
 *
 * @author makejava
 * @since 2025-02-19 19:23:34
 */
@SuppressWarnings("serial")
public class Userinfo extends Model<Userinfo> {
//id
@TableId(type = IdType.AUTO)
    private Integer id;
//微信唯一标识
    private String openid;
//微信昵称
    private String username;
//用户图像
    private String userioc;
//手机号
    private String phone;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserioc() {
        return userioc;
    }

    public void setUserioc(String userioc) {
        this.userioc = userioc;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

