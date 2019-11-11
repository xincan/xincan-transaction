package com.xincan.transaction.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.xincan.transaction.server.system.entity.TenantDatasource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.HintShardingStrategyConfiguration;
import org.apache.shardingsphere.core.exception.ShardingException;
import org.apache.shardingsphere.core.util.InlineExpressionParser;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.apache.shardingsphere.shardingjdbc.spring.boot.util.DataSourceUtil;
import org.apache.shardingsphere.shardingjdbc.spring.boot.util.PropertyUtil;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

@Slf4j
@Configuration
public class ShardingJdbcConfig {

    /**
     * 配置主数据源相关设置
     */
    // 数据源名称
    @Value("${spring.shardingsphere.datasource.name}")
    private String dsName;

    // 数据库连接池类型
    @Value("${spring.shardingsphere.datasource.main-datasource.type}")
    private String dsType;

    // 数据库驱动类
    @Value("${spring.shardingsphere.datasource.main-datasource.driver-class-name}")
    private String dsDriverClassName;

    // 数据库连接
    @Value("${spring.shardingsphere.datasource.main-datasource.url}")
    private String dsUrl;

    // 数据库用户名
    @Value("${spring.shardingsphere.datasource.main-datasource.username}")
    private String dsUsername;

    // 数据库密码
    @Value("${spring.shardingsphere.datasource.main-datasource.password}")
    private String dsPassword;

    /**
     * 获取自定义数据源
     * @return
     */
    public Map<String, DataSource> getDataSourceMap() {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        TenantDatasource tenantDatasource = TenantDatasource.builder()
                .tenantName(dsName)
                .datasourceUrl(dsUrl)
                .datasourceUsername(dsUsername)
                .datasourcePassword(dsPassword)
                .datasourceDriver(dsDriverClassName)
                .datasourceType(dsType)
                .build();
        try {
            DataSource dataSource = DataSourceUtil.getDataSource(tenantDatasource.getDatasourceType(), tenantDatasource.toDatasourceProp());
            dataSourceMap.put(tenantDatasource.getTenantName(), dataSource);
        } catch (ReflectiveOperationException e) {
            log.error("初始化主数据源失败", e);
        }
        return dataSourceMap;
    }

    /**
     * 设置主datasource为ShardingJdbc的datasource
     * @return
     * @throws SQLException
     */
    @Bean
    @Primary
    public DataSource shardingDataSource() throws SQLException {
        Properties props = new Properties();
        props.setProperty("sql.show", "true");
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        // 设置自定义分库逻辑
        HintShardingStrategyConfiguration shardingStrategyConfiguration
                = new HintShardingStrategyConfiguration(new DataSourceTypeHintShardingAlgorithm());
        shardingRuleConfiguration.setDefaultDatabaseShardingStrategyConfig(shardingStrategyConfiguration);
        log.info("===========初始化主数据源=============");
        return ShardingDataSourceFactory.createDataSource(getDataSourceMap(), shardingRuleConfiguration, props);
    }

    /**
     * 设置mybatis plus使用ShardingJdbc的datasource;
     * 注入mybatis plus的SqlSessionFactory
     * @param paginationInterceptor
     * @return
     * @throws Exception
     */
    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory(DataSource shardingDataSource, PaginationInterceptor paginationInterceptor) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(shardingDataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*/*.xml"));
        // 设置分页
        sqlSessionFactoryBean.setPlugins(paginationInterceptor);
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 使用mybatis plus的SqlSessionFactory;
     * 注入SqlSessionTemplate
     * @param sqlSessionFactory
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
