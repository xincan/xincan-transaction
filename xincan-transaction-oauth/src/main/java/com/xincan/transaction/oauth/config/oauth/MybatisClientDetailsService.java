package com.xincan.transaction.oauth.config.oauth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xincan.transaction.oauth.server.mapper.oauth.IClientDetailMapper;
import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 由于用sharding jdbc的datasource替换掉原spring的datasource, 查询方式需要改变为mybatis plus查询
 */
public class MybatisClientDetailsService extends JdbcClientDetailsService {

    private IClientDetailMapper clientDetailMapper;

    private String dataSourceName;

    public MybatisClientDetailsService(DataSource dataSource, IClientDetailMapper clientDetailsMapper, String dataSourceName) {
        super(dataSource);
        this.clientDetailMapper = clientDetailsMapper;
        this.dataSourceName = dataSourceName;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
        ClientDetails details;
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("client_id", clientId);
            try {
                HintManager.getInstance().setDatabaseShardingValue(dataSourceName);
                details = clientDetailMapper.selectOne(queryWrapper);
            }
            finally {
                HintManager.clear();
            }
        }
        catch (EmptyResultDataAccessException e) {
            throw new NoSuchClientException("No client with requested id: " + clientId);
        }
        return details;
    }

}
