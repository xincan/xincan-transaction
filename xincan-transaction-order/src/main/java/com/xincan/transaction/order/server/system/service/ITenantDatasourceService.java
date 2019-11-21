package com.xincan.transaction.order.server.system.service;

import cn.com.hatech.common.data.universal.IBaseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xincan.transaction.order.server.system.entity.TenantDatasource;
import com.xincan.transaction.order.server.system.entity.User;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

public interface ITenantDatasourceService extends IBaseService<TenantDatasource> {

    void refreshDataSource();

    Page<TenantDatasource> findAll(String tenantName, Map<String, Object> map);

}
