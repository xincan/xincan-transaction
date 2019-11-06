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
    List<User> findAll(Page page, @Param("map") Map<String, Object> map);

    /**
     * 查询mysql用户信息
     * @param map
     * @return
     */
    List<Map<String, Object>> findAllMap(Page page, @Param("map") Map<String, Object> map);


    /**
     * @description: 批量增加
     * @method:
     * @author: Xincan Jiang
     * @date: 2019-11-06 10:12:28
     * @param: []null
     * @return:
     * @exception:
     */
    Integer insertBatchTwo(List<User> list);
}
