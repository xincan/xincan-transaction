package com.xincan.transaction.server.system.service.impl;

import cn.com.hatech.common.data.universal.AbstractService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xincan.transaction.server.system.entity.Role;
import com.xincan.transaction.server.system.entity.UserRole;
import com.xincan.transaction.server.system.mapper.IRoleMapper;
import com.xincan.transaction.server.system.mapper.IUserRoleMapper;
import com.xincan.transaction.server.system.service.IRoleService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @description: TODO
 * @className: RoleServiceImpl
 * @date: 2019/10/30 16:34
 * @author: Xincan Jiang
 * @version: 1.0
 */
@Slf4j
@Service("roleService")
public class RoleServiceImpl extends AbstractService<Role> implements IRoleService {

    @Resource
    private IRoleMapper roleMapper;

    @Resource
    private IUserRoleMapper userRoleMapper;

    @Override
    public IPage<Role> findRoles() {
        IPage<Role> page = new Page<>(1, 10);
        return this.roleMapper.selectPage(page, null);
    }

    @Override
    public List<Role> findRoleByUserId(String userId, Integer second) {
        List<String> roleIds = findUserRoleByUserId(userId)
                .stream()
                .map(ur -> ur.getRoleId())
                .collect(Collectors.toList());

        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper();
        roleQueryWrapper.lambda().in(Role::getId, roleIds);

        List<Role> roles = this.roleMapper.selectList(roleQueryWrapper);
        return roles;
    }

    /**
     * @description: 根据用户信息查询用户角色
     * @method: findUserRoleByUserId
     * @author: Xincan Jiang
     * @date: 2019-11-07 09:40:39
     * @param: []
     * @return: java.util.List<com.xincan.transaction.server.system.entity.UserRole>
     * @exception:
     */
    private List<UserRole> findUserRoleByUserId(String userId) {
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper();
        userRoleQueryWrapper.lambda().eq(UserRole::getUserId, userId);
        return this.userRoleMapper.selectList(userRoleQueryWrapper);
    }

    /**
     * 测试feign分布式事务插入role
     * @return
     */
    @Override
    public Integer testFeignTransaction() {
        log.info("----------全局事务id: "+ RootContext.getXID());
        Role role = Role.builder()
                .id("testId")
                .role("feign")
                .description("测试feign角色")
                .createTime(new Date())
                .build();
        //throw new RuntimeException("模拟role抛出异常");
        return roleMapper.save(role);
    }
}
