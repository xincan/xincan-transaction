package com.xincan.transaction.server.system.mapper;

import cn.com.hatech.common.data.universal.IBaseMapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xincan.transaction.server.system.entity.Order;
import com.xincan.transaction.server.system.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IOrderMapper extends IBaseMapper<Order> {

}
