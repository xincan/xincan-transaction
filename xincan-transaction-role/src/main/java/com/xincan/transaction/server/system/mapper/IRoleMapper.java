package com.xincan.transaction.server.system.mapper;

import cn.com.hatech.common.data.universal.IBaseMapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xincan.transaction.server.system.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: TODO
 * @className: IRoleMapper
 * @date: 2019/10/30 16:36
 * @author: Xincan Jiang
 * @version: 1.0
 */
@Mapper
public interface IRoleMapper extends IBaseMapper<Role> {

    Integer save(Role role);

}
