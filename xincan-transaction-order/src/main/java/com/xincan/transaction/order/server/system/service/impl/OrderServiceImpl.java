package com.xincan.transaction.order.server.system.service.impl;

import cn.com.hatech.common.data.universal.AbstractService;
import com.xincan.transaction.order.server.system.entity.Order;
import com.xincan.transaction.order.server.system.entity.User;
import com.xincan.transaction.order.server.system.mapper.IOrderMapper;
import com.xincan.transaction.order.server.system.service.IOrderService;
import com.xincan.transaction.order.server.system.service.IUserService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.hint.HintManager;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Slf4j
@Service("orderService")
public class OrderServiceImpl extends AbstractService<Order> implements IOrderService {

    @Resource
    IOrderMapper orderMapper;

    /**
     * 测试feign分布式事务插入order
     * @return
     */
    @Override
    public Integer testFeignTransaction() {
        Integer res = 0;
        TransactionTypeHolder.set(TransactionType.BASE);
        log.info("----------全局事务id: "+ RootContext.getXID());
        try {
            HintManager.getInstance().setDatabaseShardingValue("tenant2");
            Order order = Order.builder().orderName("feign").build();
            res = orderMapper.insert(order);
        } finally {
            HintManager.clear();
        }
        return res;
    }

}
