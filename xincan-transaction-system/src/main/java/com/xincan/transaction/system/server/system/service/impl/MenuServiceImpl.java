package com.xincan.transaction.system.server.system.service.impl;


import cn.com.hatech.common.data.page.MybatisPage;
import cn.com.hatech.common.data.universal.AbstractService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xincan.transaction.system.server.system.entity.menu.Menu;
import com.xincan.transaction.system.server.system.entity.menu.TreeMenu;
import com.xincan.transaction.system.server.system.entity.menu.TreeNode;
import com.xincan.transaction.system.server.system.mapper.menu.IMenuMapper;
import com.xincan.transaction.system.server.system.service.IMenuService;
import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 菜单信息server
 */
@Service("menuService")
public class MenuServiceImpl extends AbstractService<Menu> implements IMenuService {

    /**
     * 注入菜单信息mapper
     */
    @Resource
    private IMenuMapper menuMapper;

    /**
     * 注入主数据源
     */
    @Value("${spring.datasource.name}")
    private String mainDataSource;

    /**
     * 获取所有菜单
     * @return
     */
    @Override
    public List<Menu> getMenu() {
        try {
            HintManager.getInstance().setDatabaseShardingValue(mainDataSource);
            return menuMapper.selectList(null);
        }
        finally {
            HintManager.clear();
        }
    }

    /**
     * 根据菜单ID查询菜单信息（路径形式）
     * @return
     */
    @Override
    public List<Menu> listMenu() {
        List<Menu> menuList = getMenu();
        if (menuList!=null && menuList.size()>0) {
            for (Menu menu : menuList) {
                if (!StringUtils.isEmpty(menu.getPath())) {
                    menu.setCode(menu.getPath().substring(menu.getPath().lastIndexOf("/") + 1));
                }
            }
            return getMenuTree(-1, 1, menuList);
        }
        return null;
    }

    /**
     * 根据菜单ID查询菜单信息（树形结构）
     * @return
     */
    @Override
    public List<TreeMenu> treeMenu() {
        List<Menu> menuList = getMenu();
        if (menuList!=null && menuList.size()>0) {
            List<TreeMenu> list = menuList.stream()
                    .map(Menu::castTreeMenu)
                    .collect(Collectors.toList());
            return getMenuTree(-1, 1, list);
        }
        return null;
    }

    /**
     * 根据分页获取菜单信息
     * @param map
     * @return
     */
    @Override
    public Page<Menu> getMenuPage(Map<String, Object> map) {
        try {
            HintManager.getInstance().setDatabaseShardingValue(mainDataSource);
            Page<Menu> page = MybatisPage.getPageSize(map);
            List<Menu> pageList = menuMapper.findPage(page, map);
            page.setRecords(pageList);
            return page;
        }
        finally {
            HintManager.clear();
        }
    }

    /**
     * 新建菜单
     * @param menu
     * @return
     */
    @Override
    public int save(Menu menu) {
        try {
            HintManager.getInstance().setDatabaseShardingValue(mainDataSource);
            return menuMapper.insert(menu);
        }
        finally {
            HintManager.clear();
        }
    }

    /**
     * 递归处理预案类型生成tree数据
     * @param id
     * @param list
     */
    @Override
    public <T extends TreeNode> List<T> getMenuTree(Integer id, Integer level, List<T> list) {
        List<T> items = new ArrayList<>();
        for (T obj : list) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (obj.getParentId().equals(id)) {
                if (obj.getLevel().equals(level)) {
                    items.add(obj);
                }
                if (items.size() != 0) {
                    List<T> tree = this.getMenuTree(obj.getId(), obj.getLevel()+1, list);
                    if (tree.size() > 0) {
                        obj.setChildren(tree);
                    }
                }
            }
        }
        return items;
    }

}
