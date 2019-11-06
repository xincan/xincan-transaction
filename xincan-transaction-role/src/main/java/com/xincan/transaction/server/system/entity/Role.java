package com.xincan.transaction.server.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description: TODO
 * @className: Role
 * @date: 2019/10/30 16:36
 * @author: Xincan Jiang
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("role")
@ApiModel(value = "com.xincan.transaction.server.system.entity.Role",description = "角色信息实体类")
public class Role {
    /**
     * 主键
     * @TableId中可以决定主键的类型,不写会采取默认值,默认值可以在yml中配置
     * AUTO: 数据库ID自增
     * INPUT: 用户输入ID
     * ID_WORKER: 全局唯一ID，Long类型的主键
     * ID_WORKER_STR: 字符串全局唯一ID
     * UUID: 全局唯一ID，UUID类型的主键
     * NONE: 该类型为未设置主键类型
     */
    @ApiModelProperty(value="角色ID", dataType = "String", example = "UUID")
    @TableId(type = IdType.UUID)
    @TableField("id")
    private String id;

    @ApiModelProperty(value = "角色名称", dataType = "String", example = "角色名称")
    @TableField("role")
    private String role;

    @ApiModelProperty(value = "角色状态", dataType = "Integer", example = "0")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "角色说明", dataType = "String", example = "角色作用")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "创建时间", dataType = "Date", example = "2020-11-06 00:00:00")
    @TableField("create_time")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
