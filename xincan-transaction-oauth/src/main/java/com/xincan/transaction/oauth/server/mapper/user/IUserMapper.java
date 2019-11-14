package com.xincan.transaction.oauth.server.mapper.user;

import com.xincan.transaction.oauth.server.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import feign.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Copyright (C), 2018,北京同创永益科技发展有限公司
 * @Package: com.xincan.transaction.oauth.server.mapper
 * @ClassName: IUserMapper
 * @Author: Xincan
 * @Description: ${description}
 * @Date: 2019/4/16 16:59
 * @Version: 1.0
 */
@Mapper
public interface IUserMapper extends BaseMapper<User> {

    /**
     * 按用户名查询用户信息
     * @param username
     * @return
     */
    User findByUsername(@Param("username") String username);

    /**
     * 按手机号查询用户信息
     * @param phone
     * @return
     */
    Integer findByPhone(@Param("phone") String phone);

}