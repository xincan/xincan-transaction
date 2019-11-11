package com.xincan.transaction.server.system.service.impl;

import cn.com.hatech.common.data.universal.AbstractService;
import com.xincan.transaction.server.system.entity.Order;
import com.xincan.transaction.server.system.entity.User;
import com.xincan.transaction.server.system.mapper.IOrderMapper;
import com.xincan.transaction.server.system.service.IOrderService;
import com.xincan.transaction.server.system.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Slf4j
@Service("orderService")
public class OrderServiceImpl extends AbstractService<Order> implements IOrderService {

    @Resource
    IOrderMapper orderMapper;

}
