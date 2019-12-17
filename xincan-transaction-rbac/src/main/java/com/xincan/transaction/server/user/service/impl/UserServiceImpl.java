package com.xincan.transaction.server.user.service.impl;

import cn.com.hatech.common.data.universal.AbstractService;
import com.xincan.transaction.server.user.entity.User;
import com.xincan.transaction.server.user.mapper.IUserMapper;
import com.xincan.transaction.server.user.service.IUserService;
import com.xincan.transaction.server.user.vo.UserLoginAccountTypeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Copyright (C), 2019,北京同创永益科技发展有限公司
 * @ProjectName: xincan-transaction
 * @Package: com.xincan.transaction.system.server.user.service.impl
 * @ClassName: UserServiceImpl
 * @author: wangmingshuai
 * @Description: 用户信息接口层实现
 * @Date: 2019/12/17 14:02
 * @Version: 1.0
 */
@Service("userService")
public class UserServiceImpl extends AbstractService<User> implements IUserService {

    private IUserMapper userMapper;

    @Autowired
    public UserServiceImpl (IUserMapper userMapper) {
         this.userMapper = userMapper;
    }

    @Override
    public User findUserByLoginAccountType(UserLoginAccountTypeVo userLoginAccountType) {
        return this.userMapper.findUserByLoginAccountType(userLoginAccountType);
    }
}
