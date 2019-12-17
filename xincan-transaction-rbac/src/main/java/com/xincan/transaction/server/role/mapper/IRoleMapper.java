package com.xincan.transaction.server.role.mapper;

import cn.com.hatech.common.data.universal.IBaseMapper;
import com.xincan.transaction.server.role.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Copyright (C), 2019,北京同创永益科技发展有限公司
 * @ProjectName: xincan-transaction
 * @Package: com.xincan.transaction.system.server.user.mapper
 * @ClassName: IUserMapper
 * @author: wangmingshuai
 * @Description: 角色信息数据访问层
 * @Date: 2019/12/17 13:57
 * @Version: 1.0
 */
@Mapper
public interface IRoleMapper extends IBaseMapper<Role> {
}
