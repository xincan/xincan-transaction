package com.xincan.transaction.oauth.server.service;

import com.xincan.transaction.oauth.server.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author Levin
 * @date 2017-08-15.
 */
@Service
public interface IUserRoleService {

    List<User> findRoleByUser(User user);

    Integer saveUser(User user);

}
