package com.xincan.transaction.oauth.config.shardingjdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.xincan.transaction.oauth.config.druid.DruidProperties;
import io.seata.rm.datasource.DataSourceProxy;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.HintShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * sharding-jdbc配置
 */
@Slf4j
@Configuration
public class ShardingJdbcConfig {

    @Autowired
    private DruidProperties druidProperties;

    /**
     * 绑定主数据源名称
     */
    @Value("${spring.datasource.name}")
    private String dataSourceName;

    @Bean("druidDataSource")
    public DruidDataSource druidDataSource() throws SQLException {
        return druidProperties.getDruidDataSource();
    }

    /**
     * 定义shardingDataSource
     * 此datasource中的dataSourceMap需要将druid数据源用seata的DataSourceProxy封装
     * @return
     * @throws SQLException
     */
    @Bean("shardingDataSource")
    public DataSource shardingDataSource(DruidDataSource druidDataSource) throws SQLException {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        DataSourceProxy dataSourceProxy = new DataSourceProxy(druidDataSource);
        dataSourceMap.put(dataSourceName, dataSourceProxy);
        // 定义分库策略
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        HintShardingStrategyConfiguration hintShardingStrategyConfiguration
                = new HintShardingStrategyConfiguration(new DataSourceTypeHintShardingAlgorithm());
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(hintShardingStrategyConfiguration);
        // 显示sql查询语句
        Properties properties = new Properties();
        properties.setProperty("sql.show", "true");
        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, properties);
    }

    /**
     * 定义全局的dataSource使用shardingDataSource
     * @param shardingDataSource
     * @return
     */
    @Primary
    @Bean("dataSource")
    public DataSource dataSource(@Qualifier("shardingDataSource") DataSource shardingDataSource) {
        return shardingDataSource;
    }

    /**
     * 自动注入sharding-jdbc的datasource
     * 设置mybatis plus使用ShardingJdbc的datasource;
     * 注入mybatis plus的SqlSessionFactory
     * @return
     * @throws Exception
     */
    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory(DataSource shardingDataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(shardingDataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/**/*.xml"));
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
