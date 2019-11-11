package com.xincan.transaction.server.system.mapper;

import cn.com.hatech.common.data.universal.IBaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xincan.transaction.server.system.entity.User;
import javafx.scene.control.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @description: TODO
 * @className: IUserMapper
 * @date: 2019/10/30 16:36
 * @author: Xincan Jiang
 * @version: 1.0
 */
@Mapper
public interface IUserMapper extends IBaseMapper<User> {

    /**
     * 查询mysql用户信息
     * @param map
     * @return
     */
//    @TargetDataSource(value = DataSourceType.DATASOURCE_USER)
    List<User> findAll(Page page, @Param("map") Map<String, Object> map);

}
