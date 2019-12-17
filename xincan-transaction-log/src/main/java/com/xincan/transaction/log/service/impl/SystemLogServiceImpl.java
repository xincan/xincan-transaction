package com.xincan.transaction.log.service.impl;

import cn.com.hatech.common.data.page.MybatisPage;
import cn.com.hatech.common.data.universal.AbstractService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xincan.transaction.log.entity.SystemLog;
import com.xincan.transaction.log.service.ISystemLoginService;
import org.springframework.stereotype.Service;

/**
 * @program: xincan-transaction
 * @description: 系统操作日志接口实现层
 * @author: JiangXincan
 * @create: 2019/12/16 16:17
 * @version: 1.0
 */
@Service("systemLoginService")
public class SystemLogServiceImpl extends AbstractService<SystemLog> implements ISystemLoginService {

    @Override
    public IPage<SystemLog> find(SystemLog systemLog) {

//        IPage<SystemLog> page = MybatisPage.getPageSize(map);
//        QueryWrapper<SystemLog> queryWrapper = new QueryWrapper<>();

        return null;

    }
}
