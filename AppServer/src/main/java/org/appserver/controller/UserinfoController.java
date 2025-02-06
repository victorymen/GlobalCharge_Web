package org.appserver.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.appserver.entity.Userinfo;
import org.appserver.service.UserinfoService;
import org.appserver.utils.IDUtils;
import org.appserver.utils.SftpUtils;
import org.appserver.utils.utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * (Userinfo)表控制层
 *
 * @author makejava
 * @since 2025-02-16 16:20:52
 */
@RestController
@RequestMapping("/api/userInfo")
public class UserinfoController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private UserinfoService userinfoService;
    @Resource
    private RestTemplate restTemplate;

    /**
     * 分页查询所有数据
     *
     * @param page     分页对象
     * @param userinfo 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<Userinfo> page, Userinfo userinfo) {
        return success(this.userinfoService.page(page, new QueryWrapper<>(userinfo)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.userinfoService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param userinfo 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody Userinfo userinfo) {
        return success(this.userinfoService.save(userinfo));
    }

    /**
     * 修改数据
     *
     * @param userinfo 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody Userinfo userinfo) {
        return success(this.userinfoService.updateById(userinfo));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.userinfoService.removeByIds(idList));
    }

    @Value("${app.appid}")
    private String appid;
    @Value("${app.secret}")
    private String secret;
    @Value("${app.imageURL}")
    private String imageURL;

    /**
     * 登录操作  加载小程序登录信息
     * @param code 微信登录返回的code
     * @return 返回用户信息
     */
    @PostMapping("loginInfo")
    public R loginInfo(@RequestParam("code") String code) {
        if (code == null || code.trim().isEmpty()) {
            return failed("传入的code不能为空");
        }
        String url = String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code", appid, secret, code);
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            JSONObject jsonObject = JSONObject.parseObject(response.getBody());

            String openid = jsonObject.getString("openid");
            Userinfo userinfo = new Userinfo();
            userinfo.setOpenid(openid);
            int count = userinfoService.count(new QueryWrapper<>(userinfo));
            if (count == 0) {
                userinfoService.save(userinfo);
            } else {
                userinfo = userinfoService.getOne(new QueryWrapper<>(userinfo));
            }
            jsonObject.putAll(utils.removeEmpty(userinfo));
            jsonObject.put("userId", userinfo.getId());
            jsonObject.remove("id");
//            jsonObject.put("userinfo", userinfo);
            return success(jsonObject);
        } catch (Exception e) {
            return failed("调用接口失败: " + e.getMessage());
        }
    }

    /**
     * 上传操作
     *
     * @param file 上传文件
     * @return 返回上传文件修改的文件名称
     * @throws IOException
     */
    @PostMapping("/uploadFile")
    public R uploadFile(@RequestBody MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        String imageName = IDUtils.genImageName() + filename.substring(filename.lastIndexOf("."));
        // 获取当前日期时间并格式化
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");
        String dateTimePath = dateFormat.format(new Date());
        String path_userid = "/images/"+dateTimePath+"/";
        SftpUtils.upload(path_userid, file.getInputStream(), imageName);
        return success(imageURL+path_userid + imageName);
    }


    /**
     * 直接调用api接口数据
     * @return
     */
    @PostMapping("/countries")
    public R countries() {
        return success(userinfoService.getObject("/countries",new JSONObject()));
    }
    /**
     * 直接调用api接口数据
     * @return
     */
    @PostMapping("/products")
    public R products(@RequestBody JSONObject params ) {
        return success(userinfoService.getObject("/products",params));
    }
    /**
     * 直接调用api接口数据
     * @return
     */
    @PostMapping("/recharge_types")
    public R recharge_types(@RequestBody JSONObject params ) {
        return success(userinfoService.getObject("/recharge_types",params));
    }





}

