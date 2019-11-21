package com.xincan.transaction.order.server.system.service.impl;

import cn.com.hatech.common.data.page.MybatisPage;
import cn.com.hatech.common.data.universal.AbstractService;
import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xincan.transaction.order.config.DruidProperties;
import com.xincan.transaction.order.server.system.entity.TenantDatasource;
import com.xincan.transaction.order.server.system.mapper.ITenantDatasourceMapper;
import com.xincan.transaction.order.server.system.service.ITenantDatasourceService;
import io.seata.rm.datasource.DataSourceProxy;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.hint.HintManager;
import org.apache.shardingsphere.core.database.DatabaseTypes;
import org.apache.shardingsphere.core.rule.ShardingRule;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang.StringUtils.*;

/**
 * 租户信息实现类
 */
@Slf4j
@Service("tenantDatasourceService")
public class TenantDatasourceServiceImpl extends AbstractService<TenantDatasource> implements ITenantDatasourceService {

    @Resource
    private ITenantDatasourceMapper tenantDatasourceMapper;

    /**
     * 注入sharding jdbc的datasource
     */
    @Autowired
    private DataSource dataSource;

    /**
     * 定义的主数据源
     */
    @Value("${spring.datasource.name}")
    private String mainDataSource;

    @Autowired
    private DruidProperties druidProperties;

    /**
     * 设置tenant租户数据源
     * @param shardingDataSource
     * @param dsNames
     * @param tenantDatasource
     */
    private void setTenantDatasource(ShardingDataSource shardingDataSource, Collection<String> dsNames, TenantDatasource tenantDatasource) {
        DruidDataSource dataSource = null;
        try {
            dataSource = druidProperties.getDruidDataSource();
        } catch (SQLException e) {
            log.error("添加租户数据源失败", e);
            return;
        }
        dataSource.setUrl(tenantDatasource.getDatasourceUrl());
        dataSource.setUsername(tenantDatasource.getDatasourceUsername());
        dataSource.setPassword(tenantDatasource.getDatasourcePassword());
        DataSourceProxy dataSourceProxy = new DataSourceProxy(dataSource);
        shardingDataSource.getDataSourceMap().put(tenantDatasource.getTenantName(), dataSourceProxy);
        dsNames.add(tenantDatasource.getTenantName());
    }

    /**
     * 刷新租户数据源
     */
    @Override
    public synchronized void refreshDataSource() {
        ShardingDataSource shardingDataSource = (ShardingDataSource)dataSource;
        ShardingRule shardingRule = shardingDataSource.getRuntimeContext().getRule();
        Collection<String> dsNames = shardingRule.getShardingDataSourceNames().getDataSourceNames();
        // 设置查询数据源为配置的主数据源
        HintManager.getInstance().setDatabaseShardingValue(mainDataSource);
        try {
            // 获取主数据源中所有租户数据源数据
            List<TenantDatasource> list = tenantDatasourceMapper.selectList(null);
            // 添加所有的租户数据源
            list.forEach(tenantDatasource->setTenantDatasource(shardingDataSource, dsNames, tenantDatasource));
            // 重新设置事务数据源
            shardingDataSource.getRuntimeContext().getShardingTransactionManagerEngine()
                    .init(DatabaseTypes.getActualDatabaseType("MySQL"), shardingDataSource.getDataSourceMap());
            log.info("添加租户数据源:" + dsNames.stream().collect(Collectors.joining(",")));
        } finally {
            HintManager.clear();
        }
    }

    /**
     * 获取所有租户连接信息
     * @return
     */
    @Override
    public Page<TenantDatasource> findAll(String tenantName, Map<String, Object> map) {
        Page<TenantDatasource> page = MybatisPage.getPageSize(map);
        try {
            // 根据租户名称设置指定的数据源名称
            HintManager.getInstance().setDatabaseShardingValue(tenantName);
            List<TenantDatasource> list = tenantDatasourceMapper.findAll(page, map);
            page.setRecords(list);
        } finally {
            HintManager.clear();
        }
        return page;
    }

}
