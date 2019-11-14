package com.xincan.transaction.oauth.server.service.impl;


import com.xincan.transaction.oauth.server.entity.User;
import com.xincan.transaction.oauth.server.mapper.user.IUserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author Levin
 * @date 2017-08-15.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private IUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("进入-----------------------");
        return this.userMapper.findByUsername(username);
//        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ADMIN"));
//        return new User("root", new BCryptPasswordEncoder().encode("root"), authorities);
    }
}
