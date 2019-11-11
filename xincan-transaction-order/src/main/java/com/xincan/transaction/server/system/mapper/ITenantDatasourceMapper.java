package com.xincan.transaction.server.system.mapper;

import cn.com.hatech.common.data.universal.IBaseMapper;
import com.xincan.transaction.server.system.entity.Order;
import com.xincan.transaction.server.system.entity.TenantDatasource;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ITenantDatasourceMapper extends IBaseMapper<TenantDatasource> {

}
