package com.xincan.transaction.server.system.service.impl;

import cn.com.hatech.common.data.universal.AbstractService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.xincan.transaction.server.system.entity.TenantDatasource;
import com.xincan.transaction.server.system.entity.User;
import com.xincan.transaction.server.system.mapper.ITenantDatasourceMapper;
import com.xincan.transaction.server.system.service.ITenantDatasourceService;
import com.xincan.transaction.server.system.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("tenantDatasourceService")
public class TenantDatasourceServiceImpl extends AbstractService<TenantDatasource> implements ITenantDatasourceService {

    @Resource
    private ITenantDatasourceMapper tenantDatasourceMapper;


    /**
     * 获取所有租户连接信息
     * @return
     */
    @Override
    public Map<String, TenantDatasource> getTenantDataSourceMap() {
        Map<String, TenantDatasource> res = new HashMap<>();
        QueryWrapper<TenantDatasource> queryWrapper = new QueryWrapper<>();
        List<TenantDatasource> list = tenantDatasourceMapper.selectList(queryWrapper);
        for (TenantDatasource tenantDatasource : list) {
            res.put(tenantDatasource.getTenantName(), tenantDatasource);
        }
        return res;
    }
}
