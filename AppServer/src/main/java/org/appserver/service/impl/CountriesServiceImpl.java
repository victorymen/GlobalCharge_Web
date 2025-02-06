package org.appserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.appserver.dao.CountriesDao;
import org.appserver.entity.Countries;
import org.appserver.service.CountriesService;
import org.springframework.stereotype.Service;

/**
 * 国家(Countries)表服务实现类
 *
 * @author makejava
 * @since 2025-02-18 00:02:31
 */
@Service("countriesService")
public class CountriesServiceImpl extends ServiceImpl<CountriesDao, Countries> implements CountriesService {

}

