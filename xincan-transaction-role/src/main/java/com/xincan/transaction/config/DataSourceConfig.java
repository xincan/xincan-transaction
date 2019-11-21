package com.xincan.transaction.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import io.seata.rm.datasource.DataSourceProxy;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

/**
 * 数据源配置
 */
@Configuration
public class DataSourceConfig {

    /**
     * 注入mybatis plus分页插件
     */
    @Autowired
    private PaginationInterceptor paginationInterceptor;

    /**
     * 定义druid数据源
     * @return
     */
    @Bean("druidDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    /**
     * 定义全局dataSource
     * 利用seata的数据源代理DataSourceProxy封装druid数据源
     * @param druidDataSource
     * @return
     * @throws SQLException
     */
    @Primary
    @Bean("dataSource")
    public DataSource dataSource(DataSource druidDataSource) {
        return new DataSourceProxy(druidDataSource);
    }

    /**
     * 设置mybatis plus使用上述定义的dataSource数据源
     * @param dataSourceProxy
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSourceProxy)throws Exception{
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSourceProxy);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/**/*.xml"));
        // mybatis plus分页设置
        sqlSessionFactoryBean.setPlugins(paginationInterceptor);
        sqlSessionFactoryBean.setTransactionFactory(new SpringManagedTransactionFactory());
        return sqlSessionFactoryBean.getObject();
    }

}
