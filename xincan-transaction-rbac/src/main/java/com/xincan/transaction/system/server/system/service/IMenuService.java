package com.xincan.transaction.system.server.system.service;

import cn.com.hatech.common.data.universal.IBaseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xincan.transaction.system.server.system.entity.menu.Menu;
import com.xincan.transaction.system.server.system.entity.menu.TreeMenu;
import com.xincan.transaction.system.server.system.entity.menu.TreeNode;

import java.util.List;
import java.util.Map;

public interface IMenuService extends IBaseService<Menu> {

    List<Menu> getMenu();

    List<Menu> listMenu();

    List<TreeMenu> treeMenu();

    Page<Menu> getMenuPage(Map<String, Object> map);

    int save(Menu menu);

    <T extends TreeNode> List<T> getMenuTree(Integer id, Integer level, List<T> list);

}
