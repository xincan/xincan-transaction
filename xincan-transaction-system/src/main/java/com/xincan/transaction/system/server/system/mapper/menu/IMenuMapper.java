package com.xincan.transaction.system.server.system.mapper.menu;


import cn.com.hatech.common.data.universal.IBaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xincan.transaction.system.server.system.entity.menu.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface IMenuMapper extends IBaseMapper<Menu> {

    List<Menu> findPage(Page page, @Param("map") Map<String, Object> map);

}
