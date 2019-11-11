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
 * @className: User
 * @date: 2019/10/30 16:36
 * @author: Xincan Jiang
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("user")
@ApiModel(value = "com.xincan.transaction.server.system.entity.User",description = "用户信息实体类")
public class User {
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
    @ApiModelProperty(value="用户ID", dataType = "String", example = "UUID")
    @TableId(type = IdType.UUID)
    @TableField("id")
    private String id;

    @ApiModelProperty(value = "用户名称", dataType = "String", example = "User")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "用户年龄", dataType = "Integer", example = "0")
    @TableField("age")
    private Integer age;

    @ApiModelProperty(value = "用户性别", dataType = "Integer", example = "1")
    @TableField("sex")
    private Integer sex;

    @ApiModelProperty(value = "创建时间", dataType = "Date", example = "2020-11-06 00:00:00")
    @TableField("create_time")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
