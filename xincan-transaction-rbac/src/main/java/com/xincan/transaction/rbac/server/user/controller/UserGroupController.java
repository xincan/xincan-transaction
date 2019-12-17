package com.xincan.transaction.rbac.server.user.controller;

import com.xincan.transaction.rbac.server.user.service.IUserGroupService;
import groovy.util.logging.Slf4j;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Copyright (C), 2019,北京同创永益科技发展有限公司
 * @ProjectName: xincan-transaction
 * @Package: com.xincan.transaction.system.server.user.controller
 * @ClassName: UserController
 * @author: wangmingshuai
 * @Description: 用户组信息控制层
 * @Date: 2019/12/17 14:41
 * @Version: 1.0
 */
@Slf4j
@Api(tags = {"用户组信息接口"}, description = "UserGroupController")
@RestController
@RequestMapping("/user/group")
public class UserGroupController {

    private IUserGroupService userGroupService;

    @Autowired
    public UserGroupController(IUserGroupService userGroupService) {
        this.userGroupService = userGroupService;
    }


}
