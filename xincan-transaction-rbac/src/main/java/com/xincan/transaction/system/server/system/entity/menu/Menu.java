package com.xincan.transaction.system.server.system.entity.menu;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;
import java.util.List;

@ApiModel(value = "com.xincan.transaction.system.server.system.entity.menu.Menu",description = "菜单实体类")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("menu")
public class Menu implements TreeNode<Menu> {

    // 此处使用snowflake分布式主键算法
    @ApiModelProperty(value="菜单ID", dataType = "Integer", example = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value="父级菜单ID", dataType = "Integer", example = "UUID")
    @TableField("parent_id")
    private Integer parentId;

    @ApiModelProperty(value="菜单名称", dataType = "String")
    @TableField("menu_name")
    private String menuName;

    @ApiModelProperty(value="菜单图标", dataType = "String")
    @TableField("icon")
    private String icon;

    @ApiModelProperty(value="菜单对应前端文件路由", dataType = "String")
    @TableField("path")
    private String path;

    @ApiModelProperty(value="路由参数", dataType = "String")
    @TableField("params")
    private String params;

    @ApiModelProperty(value="菜单级别", dataType = "Integer")
    @TableField("level")
    private Integer level;

    @ApiModelProperty(value="菜单顺序", dataType = "Integer")
    @TableField("sequence")
    private Integer sequence;

    @ApiModelProperty(value="创建时间", dataType = "Date")
    @TableField("create_time")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value="菜单对应vue视图名称", dataType = "String")
    @TableField(exist = false)
    private String code;

    @ApiModelProperty(value="子菜单", dataType = "List")
    @TableField(exist = false)
    private List<Menu> children;

    /**
     * 获取树形结构
     * @return
     */
    public TreeMenu castTreeMenu() {
        return TreeMenu.builder()
                .id(id)
                .label(menuName)
                .parentId(parentId)
                .level(level)
                .build();
    }

}
