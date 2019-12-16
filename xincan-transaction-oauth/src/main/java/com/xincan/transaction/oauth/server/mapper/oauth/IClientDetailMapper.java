package com.xincan.transaction.oauth.server.mapper.oauth;

import cn.com.hatech.common.data.universal.IBaseMapper;
import com.xincan.transaction.oauth.server.entity.MybatisClientDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IClientDetailMapper extends IBaseMapper<MybatisClientDetails> {
}
