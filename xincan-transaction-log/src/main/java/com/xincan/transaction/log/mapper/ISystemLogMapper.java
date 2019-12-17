package com.xincan.transaction.log.mapper;

import cn.com.hatech.common.data.universal.IBaseMapper;
import com.xincan.transaction.log.entity.SystemLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: xincan-transaction
 * @description: 系统操作日志数据访问层
 * @author: JiangXincan
 * @create: 2019/12/16 16:19
 * @version: 1.0
 */
@Mapper
public interface ISystemLogMapper extends IBaseMapper<SystemLog> {
}
