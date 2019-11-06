package com.xincan.transaction.server.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xincan.transaction.server.system.entity.Role;
import com.xincan.transaction.server.system.mapper.IRoleMapper;
import com.xincan.transaction.server.system.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: TODO
 * @className: RoleServiceImpl
 * @date: 2019/10/30 16:34
 * @author: Xincan Jiang
 * @version: 1.0
 */
@Slf4j
@Service("roleService")
public class RoleServiceImpl implements IRoleService {

    @Resource
    private IRoleMapper userMapper;

    @Override
    public IPage<Role> findRoles() {
        IPage<Role> page = new Page<>(1, 10);
        IPage<Role> pageList = this.userMapper.selectPage(page, null);
        List<Role> list = pageList.getRecords();
        log.debug("查询角色信息");
        return pageList;
    }
}
