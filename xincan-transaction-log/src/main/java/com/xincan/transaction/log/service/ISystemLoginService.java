package com.xincan.transaction.log.service;

import cn.com.hatech.common.data.universal.IBaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xincan.transaction.log.entity.SystemLog;

/**
  * @program: xincan-transaction
  * @Description: 系统操作日志接口层
  * @Author: JiangXincan
  * @Date: 2019/12/16 16:16
  * @return:
  */

public interface ISystemLoginService extends IBaseService<SystemLog> {

    IPage<SystemLog> find(SystemLog systemLog);
}
