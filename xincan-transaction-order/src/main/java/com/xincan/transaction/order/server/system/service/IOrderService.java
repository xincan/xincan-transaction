package com.xincan.transaction.order.server.system.service;

import cn.com.hatech.common.data.universal.IBaseService;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xincan.transaction.order.server.system.entity.Order;

public interface IOrderService extends IBaseService<Order> {

    Integer testFeignTransaction();

}
