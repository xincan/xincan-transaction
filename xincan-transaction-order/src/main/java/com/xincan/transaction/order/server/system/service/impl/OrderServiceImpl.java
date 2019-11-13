package com.xincan.transaction.order.server.system.service.impl;

import cn.com.hatech.common.data.universal.AbstractService;
import com.xincan.transaction.order.server.system.entity.Order;
import com.xincan.transaction.order.server.system.entity.User;
import com.xincan.transaction.order.server.system.mapper.IOrderMapper;
import com.xincan.transaction.order.server.system.service.IOrderService;
import com.xincan.transaction.order.server.system.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Slf4j
@Service("orderService")
public class OrderServiceImpl extends AbstractService<Order> implements IOrderService {

    @Resource
    IOrderMapper orderMapper;

}
