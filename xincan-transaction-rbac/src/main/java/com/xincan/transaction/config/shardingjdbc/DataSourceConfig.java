package com.xincan.transaction.config.shardingjdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.xincan.transaction.config.druid.DruidProperties;
import io.seata.rm.datasource.DataSourceProxy;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.HintShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class DataSourceConfig {

    @Autowired
    private PaginationInterceptor paginationInterceptor;

    @Autowired
    private DruidProperties druidProperties;

    /**
     * 绑定主数据源名称
     */
    @Value("${spring.datasource.name}")
    private String dataSourceName;

    @Bean
    public DruidDataSource druidDataSource() throws SQLException {
        return druidProperties.getDruidDataSource();
    }

    /**
     * 定义shardingDataSource
     * 此datasource中的dataSourceMap需要将druid数据源用seata的DataSourceProxy封装
     * @param druidDataSource
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

        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new Properties());
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
     * 读取mybatis plus配置
     * @return
     */
    @Bean
    @ConfigurationProperties("mybatis-plus.configuration")
    public MybatisConfiguration mybatisConfiguration() {
        return new MybatisConfiguration();
    }

    /**
     * 设置mybatis plus使用上述定义的dataSource数据源
     * @param dataSource
     * @param mybatisConfiguration
     * @return
     * @throws Exception
     */
    @Bean
    @ConditionalOnMissingClass
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource,
                                               MybatisConfiguration mybatisConfiguration)throws Exception{
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/**/*.xml"));
        // mybatis plus分页设置
        sqlSessionFactoryBean.setPlugins(paginationInterceptor);
        sqlSessionFactoryBean.setConfiguration(mybatisConfiguration);
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 使用mybatis plus的SqlSessionFactory;
     * 注入SqlSessionTemplate
     * @param sqlSessionFactory
     * @return
     */
    @Bean
    @ConditionalOnMissingClass
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
