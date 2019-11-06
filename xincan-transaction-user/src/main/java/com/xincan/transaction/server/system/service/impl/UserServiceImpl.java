package com.xincan.transaction.server.system.service.impl;

import cn.com.hatech.common.data.page.MybatisPage;
import cn.com.hatech.common.data.universal.AbstractService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xincan.transaction.server.system.entity.User;
import com.xincan.transaction.server.system.mapper.IUserMapper;
import com.xincan.transaction.server.system.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @description: TODO
 * @className: UserServiceImpl
 * @date: 2019/10/30 16:34
 * @author: Xincan Jiang
 * @version: 1.0
 */
@Slf4j
@Service("userService")
public class UserServiceImpl extends AbstractService<User> implements IUserService {

    @Resource
    private IUserMapper userMapper;

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public Page<User> findAll(Map<String, Object> map) {
        System.out.println(map);
        Page<User> page = MybatisPage.getPageSize(map);
        List<User> userList = this.userMapper.findAll(page, map);
        page.setRecords(userList);
        return page;

    }

    @Override
    public Page<Map<String, Object>> findAllMap(Map<String, Object> map) {
        System.out.println(map);
        Page<Map<String, Object>> page = MybatisPage.getPageSize(map);
        List<Map<String, Object>> userList = this.userMapper.findAllMap(page, map);
        page.setRecords(userList);
        return page;

    }


    @Override
    public Integer insertBatchOne(Map<String, Object> map){

        SqlSession session = this.sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        List<User> list = data(Integer.parseInt(map.get("num").toString()));
        try {
            Integer size = list.size();

            for(int i = 0; i < size; i++){
                this.userMapper.insert(list.get(i));
                if(i % 1000 == 999 || i == size - 1) {

                    session.commit();
                    session.clearCache();
                }
            }
            return size;
        }catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        }finally {
            session.close();
        }
        return 0;
    }

    /**
     * @description: TODO
     * @method: 特别注意：mysql默认接受sql的大小是1048576(1M)（可通过调整MySQL安装目录下的my.ini文件中[mysqld]段的＂max_allowed_packet = 1M＂）
     * @author: Xincan Jiang
     * @date: 2019-11-06 10:07:46
     * @param: []null
     * @return:
     * @exception:
     */
    @Override
    public JSONObject insertBatchTwo(Map<String, Object> map) {

        JSONObject json = new JSONObject();
        SqlSession session = this.sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        List<User> list = this.data(Integer.parseInt(map.get("num").toString()));
        try {
            long start  = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
            Integer num = this.userMapper.insertBatchTwo(list);
            long end  = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();

            json.put("num", num);
            json.put("time", end - start);
            return json;

        }catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        }finally {
            session.close();
        }

        return json;
    }





    private List<User> data(Integer num){
        List<User> list = new ArrayList<>();

        for (int i = 0; i<num; i++) {
            User user = User.builder().name("批量-" + i).age(11).sex(1).build();
            list.add(user);
        }
        System.out.println(list.size());
        return list;
    }
}
