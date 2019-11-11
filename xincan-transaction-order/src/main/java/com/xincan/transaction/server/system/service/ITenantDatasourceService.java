package com.xincan.transaction.server.system.service;

import cn.com.hatech.common.data.universal.IBaseService;
import com.xincan.transaction.server.system.entity.TenantDatasource;
import com.xincan.transaction.server.system.entity.User;

import javax.sql.DataSource;
import java.util.Map;

public interface ITenantDatasourceService extends IBaseService<TenantDatasource> {

    Map<String, TenantDatasource> getTenantDataSourceMap();

}
