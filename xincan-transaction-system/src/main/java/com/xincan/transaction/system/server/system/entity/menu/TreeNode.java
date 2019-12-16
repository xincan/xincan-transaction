package com.xincan.transaction.system.server.system.entity.menu;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 构造泛型 树形节点
 */
public interface TreeNode<T extends TreeNode> {

    Integer getId();

    Integer getParentId();

    Integer getLevel();

    List<T> getChildren();

    void setChildren(List<T> children);
}
