package org.appserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.appserver.dao.ProductsUserDao;
import org.appserver.entity.ProductsUser;
import org.appserver.service.ProductsUserService;
import org.springframework.stereotype.Service;

/**
 * 订单记录(ProductsUser)表服务实现类
 *
 * @author makejava
 * @since 2025-02-18 00:02:32
 */
@Service("productsUserService")
public class ProductsUserServiceImpl extends ServiceImpl<ProductsUserDao, ProductsUser> implements ProductsUserService {

}

