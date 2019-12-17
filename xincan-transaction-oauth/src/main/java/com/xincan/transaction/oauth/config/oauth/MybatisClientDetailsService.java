package com.xincan.transaction.oauth.config.oauth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xincan.transaction.oauth.server.entity.MybatisClientDetails;
import com.xincan.transaction.oauth.server.mapper.oauth.IClientDetailMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import javax.sql.DataSource;

/**
 * 替换掉原spring的datasource, 查询方式需要改变为mybatis plus查询
 */
public class MybatisClientDetailsService extends JdbcClientDetailsService {

    private IClientDetailMapper clientDetailMapper;

    public MybatisClientDetailsService(DataSource dataSource, IClientDetailMapper clientDetailsMapper) {
        super(dataSource);
        this.clientDetailMapper = clientDetailsMapper;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
        ClientDetails details;
        try {
            QueryWrapper<MybatisClientDetails> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("client_id", clientId);
            details = clientDetailMapper.selectOne(queryWrapper);
        }
        catch (EmptyResultDataAccessException e) {
            throw new NoSuchClientException("No client with requested id: " + clientId);
        }
        return details;
    }

}
