package com.xincan.transaction.system.server.system.service;

import cn.com.hatech.common.data.result.ResultObject;
import cn.com.hatech.common.data.universal.IBaseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xincan.transaction.system.server.system.entity.user.OAuthParam;
import com.xincan.transaction.system.server.system.entity.user.User;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.Map;

public interface IUserService extends IBaseService<User> {

    OAuth2AccessToken userLogin(OAuthParam oAuthParam);

    ResultObject userLogout();

    ResultObject createUser(String username, String password);

    int deleteUser(String userName);

    Page<User> getUserPage(Map<String, Object> map);

    int updateUser(User user);

    int saveUser(User user);

    int deleteUserById(String id);
}
