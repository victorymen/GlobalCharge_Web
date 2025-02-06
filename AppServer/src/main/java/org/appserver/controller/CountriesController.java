package org.appserver.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.appserver.entity.Countries;
import org.appserver.service.CountriesService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 国家(Countries)表控制层
 *
 * @author makejava
 * @since 2025-02-18 00:02:19
 */
@RestController
@RequestMapping("/api/countries")
public class CountriesController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private CountriesService countriesService;

    @PostMapping
    public R selectAll(@RequestBody Countries countries) {
        return success( countriesService.listMaps());
    }

}

