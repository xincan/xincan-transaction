package com.xincan.transaction.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xincan.transaction.server.system.entity.TenantDatasource;
import com.xincan.transaction.server.system.mapper.ITenantDatasourceMapper;
import com.xincan.transaction.server.system.service.ITenantDatasourceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.hint.HintManager;
import org.apache.shardingsphere.core.exception.ShardingException;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.apache.shardingsphere.shardingjdbc.spring.boot.util.DataSourceUtil;
import org.apache.shardingsphere.shardingjdbc.spring.boot.util.PropertyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设置order服务启动时读取数据源
 */
@Slf4j
@Component
public class InitRunner implements ApplicationRunner {

    @Autowired
    private ITenantDatasourceService tenantDatasourceService;

    @Resource
    ShardingDataSource shardingDataSource;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("===========读取tenant_datasource中租户数据源信息=============");
        Map<String, TenantDatasource> dsMap = tenantDatasourceService.getTenantDataSourceMap();
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        for (Map.Entry<String, TenantDatasource> each : dsMap.entrySet()) {
            try {
                DataSource dataSource = DataSourceUtil
                        .getDataSource(each.getValue().getDatasourceType(), each.getValue().toDatasourceProp());
                dataSourceMap.put(each.getKey(), dataSource);
                log.info("==================添加数据源:"+each.getKey()+"==================");
            } catch (final ReflectiveOperationException ex) {
                throw new ShardingException("Can't find datasource type!", ex);
            }
        }
        //TO-DO 将查询的数据源设置在datasource中
    }



}
