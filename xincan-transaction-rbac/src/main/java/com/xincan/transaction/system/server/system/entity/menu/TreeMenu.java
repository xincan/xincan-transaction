package com.xincan.transaction.system.server.system.entity.menu;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 菜单实体类(树状结构，构建vue-treeSelect下拉)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TreeMenu implements TreeNode<TreeMenu> {

    @ApiModelProperty(value="菜单ID", dataType = "Integer", example = "ID")
    private Integer id;

    @ApiModelProperty(value="菜单名称", dataType = "String", example = "NAME")
    private String label;

    @ApiModelProperty(value="父菜单ID", dataType = "Integer", example = "ID")
    private Integer parentId;

    @ApiModelProperty(value="菜单级别", dataType = "String")
    private Integer level;

    private List<TreeMenu> children;

}
