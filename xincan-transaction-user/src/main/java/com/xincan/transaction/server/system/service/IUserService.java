package com.xincan.transaction.server.system.service;

import cn.com.hatech.common.data.universal.IBaseService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xincan.transaction.server.system.entity.User;

import java.util.Map;

public interface IUserService extends IBaseService<User> {

    Page<User> findAll(Map<String, Object> map);

    Page<Map<String, Object>> findAllMap(Map<String, Object> map);

    Integer insertBatchOne(Map<String, Object> map);

    JSONObject insertBatchTwo(Map<String, Object> map);

    void testFeignTransaction();
}
