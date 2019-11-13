package com.xincan.transaction.order.server.system.mapper;

import cn.com.hatech.common.data.universal.IBaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xincan.transaction.order.server.system.entity.Order;
import com.xincan.transaction.order.server.system.entity.TenantDatasource;
import com.xincan.transaction.order.server.system.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ITenantDatasourceMapper extends IBaseMapper<TenantDatasource> {

    /**
     * 查询所有租户信息
     * @param map
     * @return
     */
    List<TenantDatasource> findAll(Page page, @Param("map") Map<String, Object> map);

}
