package com.xincan.transaction.order.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.xincan.transaction.order.server.system.entity.TenantDatasource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.HintShardingStrategyConfiguration;
import org.apache.shardingsphere.core.constant.properties.ShardingProperties;
import org.apache.shardingsphere.core.constant.properties.ShardingPropertiesConstant;
import org.apache.shardingsphere.core.database.DatabaseTypes;
import org.apache.shardingsphere.core.exception.ShardingException;
import org.apache.shardingsphere.core.execute.ShardingExecuteEngine;
import org.apache.shardingsphere.core.execute.metadata.TableMetaDataInitializer;
import org.apache.shardingsphere.core.metadata.ShardingMetaData;
import org.apache.shardingsphere.core.metadata.datasource.ShardingDataSourceMetaData;
import org.apache.shardingsphere.core.metadata.table.ShardingTableMetaData;
import org.apache.shardingsphere.core.rule.ShardingRule;
import org.apache.shardingsphere.core.spi.database.MySQLDatabaseType;
import org.apache.shardingsphere.core.util.InlineExpressionParser;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.metadata.CachedDatabaseMetaData;
import org.apache.shardingsphere.shardingjdbc.jdbc.metadata.JDBCTableMetaDataConnectionManager;
import org.apache.shardingsphere.shardingjdbc.spring.boot.util.DataSourceUtil;
import org.apache.shardingsphere.shardingjdbc.spring.boot.util.PropertyUtil;
import org.apache.shardingsphere.spi.database.DatabaseType;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * sharding-jdbc配置
 */
@Slf4j
@Configuration
public class ShardingJdbcConfig {

    @Resource(name="druidDataSource")
    DataSource druidDataSource;

    @Autowired
    PaginationInterceptor paginationInterceptor;

    /**
     * 获取自定义数据源
     * @return
     */
    public Map<String, DataSource> getDataSourceMap() {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        log.info("===========读取tenant_datasource中租户数据源信息=============");
        try (Connection conn = druidDataSource.getConnection();
             Statement statement = conn.createStatement();) {
            // 通过druid数据源查询自定义数据源信息
            ResultSet resultSet= statement.executeQuery("select tenant_name, datasource_url, datasource_username, " +
                    "datasource_password, datasource_driver, datasource_type from tenant_datasource");
            while(resultSet.next()) {
                TenantDatasource tenantDatasource = TenantDatasource.builder()
                        .tenantName(resultSet.getString("tenant_name"))
                        .datasourceUrl(resultSet.getString("datasource_url"))
                        .datasourceUsername(resultSet.getString("datasource_username"))
                        .datasourcePassword(resultSet.getString("datasource_password"))
                        .datasourceDriver(resultSet.getString("datasource_driver"))
                        .datasourceType(resultSet.getString("datasource_type"))
                        .build();
                DataSource dataSource = DataSourceUtil.getDataSource(tenantDatasource.getDatasourceType(), tenantDatasource.toDatasourceProp());
                dataSourceMap.put(tenantDatasource.getTenantName(), dataSource);
            }
        } catch (SQLException e) {
            log.error("查询租户数据源失败", e);
        } catch (ReflectiveOperationException e) {
            log.error("初始化自定义数据源集合失败", e);
        }
        return dataSourceMap;
    }

    /**
     * 自定义sharding-jdbc规则
     * @return
     */
    public ShardingRuleConfiguration getShardingRuleConfiguration() {
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        // 设置自定义分库逻辑
        HintShardingStrategyConfiguration shardingStrategyConfiguration
                = new HintShardingStrategyConfiguration(new DataSourceTypeHintShardingAlgorithm());
        shardingRuleConfiguration.setDefaultDatabaseShardingStrategyConfig(shardingStrategyConfiguration);
        return shardingRuleConfiguration;
    }

    /**
     * 设置主datasource为ShardingJdbc的datasource,
     * 需要指定该dataSource为主datasource,mybatis plus会查找该名称的bean
     * @return
     * @throws SQLException
     */
    @Bean
    @Primary
    public DataSource shardingDataSource() throws SQLException {
        Properties props = new Properties();
        props.setProperty("sql.show", "true");
        log.info("===========初始化shardingDataSource主数据源=============");
        return ShardingDataSourceFactory.createDataSource(getDataSourceMap(), getShardingRuleConfiguration(), props);
    }

    /**
     * 设置mybatis plus使用ShardingJdbc的datasource;
     * 注入mybatis plus的SqlSessionFactory
     * @return
     * @throws Exception
     */
    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*/*.xml"));
        // 设置Mybatis plus分页
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
