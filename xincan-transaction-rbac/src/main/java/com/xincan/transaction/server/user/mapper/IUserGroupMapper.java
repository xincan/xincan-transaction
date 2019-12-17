package com.xincan.transaction.server.user.mapper;

import cn.com.hatech.common.data.universal.IBaseMapper;
import com.xincan.transaction.server.user.entity.UserGroup;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Copyright (C), 2019,北京同创永益科技发展有限公司
 * @ProjectName: xincan-transaction
 * @Package: com.xincan.transaction.system.server.user.mapper
 * @ClassName: IUserMapper
 * @author: wangmingshuai
 * @Description: 用户组信息数据访问层
 * @Date: 2019/12/17 13:57
 * @Version: 1.0
 */
@Mapper
public interface IUserGroupMapper extends IBaseMapper<UserGroup> {
}
