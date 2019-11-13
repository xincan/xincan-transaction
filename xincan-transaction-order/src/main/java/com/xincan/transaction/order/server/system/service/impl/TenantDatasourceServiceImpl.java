package com.xincan.transaction.order.server.system.service.impl;

import cn.com.hatech.common.data.page.MybatisPage;
import cn.com.hatech.common.data.universal.AbstractService;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xincan.transaction.order.config.DruidProperties;
import com.xincan.transaction.order.config.ShardingJdbcConfig;
import com.xincan.transaction.order.server.system.entity.TenantDatasource;
import com.xincan.transaction.order.server.system.entity.User;
import com.xincan.transaction.order.server.system.mapper.ITenantDatasourceMapper;
import com.xincan.transaction.order.server.system.service.ITenantDatasourceService;
import com.xincan.transaction.order.server.system.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.hint.HintManager;
import org.apache.shardingsphere.core.rule.ShardingRule;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.apache.shardingsphere.shardingjdbc.spring.boot.util.DataSourceUtil;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

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
    @Value("${spring.shardingsphere.datasource.names}")
    private String mainDataSource;

    @Autowired
    DruidProperties druidProperties;

    /**
     * 刷新租户数据源
     */
    public synchronized void renewDataSource() {
        ShardingDataSource shardingDataSource = (ShardingDataSource)dataSource;
        ShardingRule shardingRule = shardingDataSource.getRuntimeContext().getRule();
        Collection<String> dsNames = shardingRule.getShardingDataSourceNames().getDataSourceNames();
        // 设置查询数据源为配置的主数据源
        HintManager.getInstance().setDatabaseShardingValue(mainDataSource);
        try {
            // 获取主数据源中所有租户数据源数据
            List<TenantDatasource> list = tenantDatasourceMapper.selectList(new QueryWrapper<>());
            for (TenantDatasource tenantDatasource : list) {
                // 注入spring datasource
                //DataSource dataSource = DataSourceUtil.getDataSource(tenantDatasource.getDatasourceType(), tenantDatasource.toDatasourceProp());
                // 注入druid datasource
                DruidDataSource dataSource = druidProperties.getDruidDataSource();
                dataSource.setUrl(tenantDatasource.getDatasourceUrl());
                dataSource.setUsername(tenantDatasource.getDatasourceUsername());
                dataSource.setPassword(tenantDatasource.getDatasourcePassword());
                shardingDataSource.getDataSourceMap().put(tenantDatasource.getTenantName(), dataSource);
                dsNames.add(tenantDatasource.getTenantName());
            }
            log.info("添加租户数据源:" + StringUtils.collectionToDelimitedString(dsNames, ","));
        } catch (SQLException e) {
            log.error("添加租户数据源失败", e);
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
