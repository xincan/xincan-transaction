package com.xincan.transaction.server.menu.controller;

import com.xincan.transaction.server.menu.service.IMenuService;
import com.xincan.transaction.server.role.service.IRoleService;
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
 * @Description: 菜单信息控制层
 * @Date: 2019/12/17 14:41
 * @Version: 1.0
 */
@Slf4j
@Api(tags = {"菜单信息接口"}, description = "MenuController")
@RestController
@RequestMapping("/menu")
public class MenuController {

    private IMenuService menuService;

    @Autowired
    public MenuController(IMenuService menuService) {
        this.menuService = menuService;
    }


}
