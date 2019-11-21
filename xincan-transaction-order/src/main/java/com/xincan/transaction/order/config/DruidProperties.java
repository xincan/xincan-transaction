package com.xincan.transaction.order.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;
import java.util.Properties;

@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.druid")
public class DruidProperties {

    private String url;
    private String username;
    private String password;

    private int initialSize;
    private int minIdle;
    private int maxActive;
    // 配置获取连接等待超时的时间
    private int maxWait;
    //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
    private int timeBetweenEvictionRunsMillis;
    //配置一个连接在池中最小生存的时间，单位是毫秒
    private int minEvictableIdleTimeMillis;
    private String validationQuery;
    private boolean testWhileIdle;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    // 打开PSCache，并且指定每个连接上PSCache的大小
    private boolean poolPreparedStatements;
    private int maxPoolPreparedStatementPerConnectionSize;
    // 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
    private String filters;
    //通过connectProperties属性来打开mergeSql功能；慢SQL记录
    private String connectionProperties;
    // 合并多个DruidDataSource的监控数据
    private boolean useGlobalDataSourceStat;

    public DruidDataSource getDruidDataSource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);

        druidDataSource.setInitialSize(initialSize);
        druidDataSource.setMinIdle(minIdle);
        druidDataSource.setMaxActive(maxActive);
        druidDataSource.setMaxWait(maxWait);
        druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        druidDataSource.setValidationQuery(validationQuery);
        druidDataSource.setTestWhileIdle(testWhileIdle);
        druidDataSource.setTestOnBorrow(testOnBorrow);
        druidDataSource.setTestOnReturn(testOnReturn);
        druidDataSource.setPoolPreparedStatements(poolPreparedStatements);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        druidDataSource.setFilters(filters);
        druidDataSource.setConnectProperties(getConnectionProperties(connectionProperties));
        druidDataSource.setUseGlobalDataSourceStat(useGlobalDataSourceStat);
        return druidDataSource;
    }

    /**
     * 解析ConnectionProperties字符串
     * @param connectionProperties
     * @return
     */
    private Properties getConnectionProperties(String connectionProperties) {
        String[] entries = connectionProperties.split(";");
        Properties properties = new Properties();
        for (int i = 0; i < entries.length; i++) {
            String entry = entries[i];
            if (entry.length() > 0) {
                int index = entry.indexOf('=');
                if (index > 0) {
                    String name = entry.substring(0, index);
                    String value = entry.substring(index + 1);
                    properties.setProperty(name, value);
                } else {
                    properties.setProperty(entry, "");
                }
            }
        }

        return properties;
    }

}
