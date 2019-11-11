package com.xincan.transaction.server.system.service.impl;

import cn.com.hatech.common.data.page.MybatisPage;
import cn.com.hatech.common.data.universal.AbstractService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xincan.transaction.server.system.entity.User;
import com.xincan.transaction.server.system.mapper.IUserMapper;
import com.xincan.transaction.server.system.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @description: TODO
 * @className: UserServiceImpl
 * @date: 2019/10/30 16:34
 * @author: Xincan Jiang
 * @version: 1.0
 */
@Slf4j
@Service("userService")
public class UserServiceImpl extends AbstractService<User> implements IUserService {

    @Resource
    private IUserMapper userMapper;

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public Page<User> findAll(Map<String, Object> map) {
        System.out.println(map);
        Page<User> page = MybatisPage.getPageSize(map);
        List<User> userList = this.userMapper.findAll(page, map);
        page.setRecords(userList);
        return page;

    }


}
