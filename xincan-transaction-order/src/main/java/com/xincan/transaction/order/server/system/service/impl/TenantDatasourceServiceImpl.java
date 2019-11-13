package com.xincan.transaction.order.server.system.service.impl;

import cn.com.hatech.common.data.page.MybatisPage;
import cn.com.hatech.common.data.universal.AbstractService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

@Slf4j
@Service("tenantDatasourceService")
public class TenantDatasourceServiceImpl extends AbstractService<TenantDatasource> implements ITenantDatasourceService {

    @Resource
    private ITenantDatasourceMapper tenantDatasourceMapper;

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
