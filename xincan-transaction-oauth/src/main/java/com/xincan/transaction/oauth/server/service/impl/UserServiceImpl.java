package com.xincan.transaction.oauth.server.service.impl;

import com.xincan.transaction.oauth.server.entity.User;
import com.xincan.transaction.oauth.server.mapper.user.IUserMapper;
import com.xincan.transaction.oauth.server.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<IUserMapper, User> implements IUserService {
}
