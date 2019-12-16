package com.xincan.transaction.oauth.server.mapper.user;

import cn.com.hatech.common.data.universal.IBaseMapper;
import com.xincan.transaction.oauth.server.entity.Tenant;
import com.xincan.transaction.oauth.server.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
public interface IUserMapper extends IBaseMapper<User> {

    /**
     * 按用户名查询用户信息
     * @param username
     * @return
     */
    User findByUsername(@Param("username") String username);

    /**
     * 按用户查询所属租户信息
     * @param userId
     * @return
     */
    List<Tenant> findTenantByUserId(@Param("userId") String userId);

    /**
     * 按手机号查询用户信息
     * @param phone
     * @return
     */
    Integer findByPhone(@Param("phone") String phone);

}