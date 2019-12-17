package com.xincan.transaction.system.server.system.service.impl;

import cn.com.hatech.common.data.page.MybatisPage;
import cn.com.hatech.common.data.result.ResultObject;
import cn.com.hatech.common.data.result.ResultResponse;
import cn.com.hatech.common.data.universal.AbstractService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xincan.transaction.system.server.system.entity.user.OAuthParam;
import com.xincan.transaction.system.server.system.entity.user.User;
import com.xincan.transaction.system.server.system.feign.IFUserOauthService;
import com.xincan.transaction.system.server.system.mapper.user.IUserMapper;
import com.xincan.transaction.system.server.system.service.IUserService;
import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends AbstractService<User> implements IUserService {

    @Resource
    private IUserMapper userMapper;

    /**
     * feign调用
     */
    @Autowired
    private IFUserOauthService userOauthService;

    /**
     * 主数据库名称
     */
    @Value("${spring.datasource.name}")
    private String mainDataSource;

    /**
     * 发送登录请求到认证中心, 获取登录token
     * @param oAuthParam
     * @return
     */
    @Override
    public OAuth2AccessToken userLogin(OAuthParam oAuthParam) {
        // 创建basic auth的header
        String userMsg = oAuthParam.getHeaderUserName() + ":" + oAuthParam.getHeaderPassword();
        String base64UserMsg = Base64.getEncoder().encodeToString(userMsg.getBytes());
        // 创建post请求body
        Map<String, String> postBody= new HashMap<>();
        postBody.put("grant_type",oAuthParam.getGrantType());
        postBody.put("scope",oAuthParam.getScope());
        postBody.put("username",oAuthParam.getUsername());
        postBody.put("password",oAuthParam.getPassword());
        return userOauthService.userLogin("Basic "+base64UserMsg, postBody);
    }

    /**
     * 发送退出请求到认证中心, 用户退出登录
     * @return
     */
    @Override
    public ResultObject userLogout() {
        return userOauthService.logout();
    }


    /**
     * 创建用户
     * @param username
     * @param password
     * @return
     */
    @Override
    public ResultObject createUser(String username, String password) {
        try {
            HintManager.getInstance().setDatabaseShardingValue(mainDataSource);
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name", username);
            int size = userMapper.selectCount(queryWrapper);
            if (size > 0) {
                return ResultResponse.error("用户名已存在", size);
            }
            String cryptPassword = (new BCryptPasswordEncoder()).encode(password);
            User user = new User(username, cryptPassword);
            int res = userMapper.insert(user);
            if (res > 0) {
                return ResultResponse.success("用户注册成功", res);
            }
        }
        finally {
            HintManager.clear();
        }
        return ResultResponse.error("用户注册失败", null);
    }

    /**
     * 按用户名删除用户数据
    */
    @Override
    public int deleteUser(String userName) {
        try {
            HintManager.getInstance().setDatabaseShardingValue(mainDataSource);
            QueryWrapper<User> queryUser = new QueryWrapper<>();
            queryUser.eq("name", userName);
            return userMapper.delete(queryUser);
        }
        finally {
            HintManager.clear();
        }
    }


    /**
     * 分页查询用户信息
     * @param map
     * @return
     */
    @Override
    public Page<User> getUserPage(Map<String, Object> map){
        try {
            HintManager.getInstance().setDatabaseShardingValue(mainDataSource);
            Page<User> page = MybatisPage.getPageSize(map);
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderBy(!StringUtils.isEmpty(map.get("sortName"))&&!StringUtils.isEmpty(map.get("sortType")),
                    "ASC".equals(map.get("sortType")), "create_time");
            return  (Page)userMapper.selectPage(page, queryWrapper);
        }
        finally {
            HintManager.clear();
        }
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @Override
    public int updateUser(User user) {
        if (StringUtils.isEmpty(user.getId())) {
            return 0;
        }
        try {
            HintManager.getInstance().setDatabaseShardingValue(mainDataSource);
            return userMapper.updateById(user);
        }
        finally {
            HintManager.clear();
        }
    }

    /**
     * 添加用户信息
     * @param user
     * @return
     */
    @Override
    public int saveUser(User user) {
        try {
            HintManager.getInstance().setDatabaseShardingValue(mainDataSource);
            return userMapper.insert(user);
        }
        finally {
            HintManager.clear();
        }
    }

    /**
     * 删除用户信息
     * @param id
     * @return
     */
    @Override
    public int deleteUserById(String id) {
        try {
            HintManager.getInstance().setDatabaseShardingValue(mainDataSource);
            return userMapper.deleteById(id);
        }
        finally {
            HintManager.clear();
        }
    }

}
