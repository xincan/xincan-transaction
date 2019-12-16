package com.xincan.transaction.oauth.server.service;

import cn.com.hatech.common.data.universal.IBaseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xincan.transaction.oauth.server.entity.TenantDatasource;

import java.util.List;
import java.util.Map;

public interface ITenantDatasourceService extends IBaseService<TenantDatasource> {

    void refreshDataSource();

}
