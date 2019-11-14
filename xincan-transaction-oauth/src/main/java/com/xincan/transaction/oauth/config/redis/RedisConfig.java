package com.xincan.transaction.oauth.config.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.resource.ClientResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;


@Configuration
@ConditionalOnClass(RedisClient.class)
public class RedisConfig {

    @Autowired
    private RedisProperties redisProperties;

//    @Bean
//    @ConditionalOnMissingBean(RedisConnectionFactory.class)
//    public JedisConnectionFactory redisConnectionFactory() {
//        RedisStandaloneConfiguration standaloneConfiguration = getRedisStandaloneConfiguration();
//        return new JedisConnectionFactory(standaloneConfiguration);
//    }
//
//    private RedisStandaloneConfiguration getRedisStandaloneConfiguration() {
//        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
//        standaloneConfiguration.setHostName(redisProperties.getHost());
//        standaloneConfiguration.setPort(redisProperties.getPort());
//        standaloneConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
//        standaloneConfiguration.setDatabase(redisProperties.getDatabase());
//        return standaloneConfiguration;
//    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisProperties.getHost());
        config.setPort(redisProperties.getPort());
        config.setPassword(RedisPassword.of(redisProperties.getPassword()));
        return new JedisConnectionFactory(config);
    }

}
