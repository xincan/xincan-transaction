package com.xincan.transaction.system.server.system.service;

import cn.com.hatech.common.data.universal.IBaseService;
import com.xincan.transaction.system.server.system.entity.tenant.TenantDatasource;

public interface ITenantDatasourceService extends IBaseService<TenantDatasource> {

    /**
     * 刷新数据源
     */
    void refreshDataSource();

    /**
     * 添加租户数据源信息到租户数据源表
     */
    int createTenantDatasource(String databaseName, String dbUrl, String dataSourceUsername, String dataSourcePassword);

    /**
     * 删除租户数据源
     * @param databaseName
     */
    void deleteTenantDatasource(String databaseName);
}
