package com.xincan.transaction.oauth.server.service.impl;


import com.xincan.transaction.oauth.server.entity.Tenant;
import com.xincan.transaction.oauth.server.entity.User;
import com.xincan.transaction.oauth.server.mapper.user.IUserMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * 查询用户信息
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private IUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户及其角色信息
        User user = userMapper.findByUsername(username);
        if (user==null) {
            throw new UnauthorizedUserException("用户不存在");
        }
        // 查询用户所属租户信息
        List<Tenant> tenants = userMapper.findTenantByUserId(user.getId());
        user.setTenants(tenants);
        return user;
    }
}
