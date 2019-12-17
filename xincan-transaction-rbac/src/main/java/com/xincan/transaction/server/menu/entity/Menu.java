package com.xincan.transaction.server.menu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @Copyright (C), 2019,北京同创永益科技发展有限公司
 * @ProjectName: xincan-transaction
 * @Package: com.xincan.transaction.system.server.user.entity
 * @ClassName: User
 * @author: wangmingshuai
 * @Description: 菜单实体类
 * @Date: 2019/12/17 13:06
 * @Version: 1.0
 */
@ApiModel(value = "Menu", description = "菜单实体类")
@Data                       // 快捷生成Getter,Setter,equals,hashCode,toString函数
@Builder
@NoArgsConstructor          // 快捷生成无参构造函数
@AllArgsConstructor         // 快捷生成全参构造函数
@TableName(value = "menu")   // 指定该类映射数据库表名
public class Menu {

    @ApiModelProperty(value="菜单ID", dataType = "String", example = "UUID")
    @NotBlank(message = "菜单ID不能为空")
    @Length(message = "菜单ID长度应该在{min}~{max}之间", min = 1, max = 64)
    @TableId(type = IdType.UUID)
    @TableField("id")
    private String id;

    @ApiModelProperty(value="菜单名称", dataType = "String", required = true, example = "用户列表查询")
    @NotBlank(message = "菜单名称不能为空")
    @Length(message = "菜单名称长度应该在{min}~{max}之间", min = 1, max = 30)
    @TableField("menu_name")
    private String menuName;

    @ApiModelProperty(value="菜单路由", dataType = "String", required = true, example = "/user/list")
    @NotBlank(message = "菜单路由不能为空")
    @Length(message = "菜单路由长度应该在{min}~{max}之间", min = 1, max = 100)
    @TableField("path")
    private String path;

    @ApiModelProperty(value="父级菜单ID", dataType = "String", required = true, example = "UUID")
    @NotBlank(message = "父级菜单ID不能为空")
    @Length(message = "父级菜单ID长度应该在{min}~{max}之间", min = 1, max = 64)
    @TableField("parent_id")
    private String parentId;

    @ApiModelProperty(value="菜单图标", dataType = "String", example = "home")
    @TableField("icon")
    @NotBlank(message = "菜单图标不能为空")
    @Length(message = "菜单图标长度应该在{min}~{max}之间", min = 1, max = 50)
    private String icon;

    @ApiModelProperty(value="路由参数", dataType = "String", example = "{\"id\":\"UUID\"}")
    @TableField("params")
    private String params;

    @ApiModelProperty(value="菜单级别", dataType = "String", example = "1")
    @NotNull(message = "菜单级别不能为空")
    @TableField("level")
    private Integer level;

    @ApiModelProperty(value="菜单顺序", dataType = "String", example = "1")
    @NotNull(message = "菜单顺序不能为空")
    @TableField("sequence")
    private Integer sequence;

    @ApiModelProperty(value="编辑人员ID", dataType = "String", example = "UUID")
    @TableField("edit_user_id")
    private String editUserId;

    @ApiModelProperty(value="操作时间，格式：yyyy-MM-dd HH:mm:ss", dataType="String", notes="格式：yyyy-MM-dd HH:mm:ss", example = "2019-09-09 09:09:09")
    @DateTimeFormat
    @TableField(value = "edit_time")
    private LocalDateTime editTime;
}
