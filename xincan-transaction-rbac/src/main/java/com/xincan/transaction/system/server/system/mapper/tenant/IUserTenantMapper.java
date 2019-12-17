package com.xincan.transaction.system.server.system.mapper.tenant;

import cn.com.hatech.common.data.universal.IBaseMapper;
import com.xincan.transaction.system.server.system.entity.tenant.UserTenant;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户和租户对应关系mapper
 */
@Mapper
public interface IUserTenantMapper extends IBaseMapper<UserTenant> {

}
