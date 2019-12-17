package com.xincan.transaction.rbac.server.user.entity;

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
import java.time.LocalDateTime;

/**
 * @Copyright (C), 2019,北京同创永益科技发展有限公司
 * @ProjectName: xincan-transaction
 * @Package: com.xincan.transaction.system.server.user.entity
 * @ClassName: User
 * @author: wangmingshuai
 * @Description: 用户信息实体类
 * @Date: 2019/12/17 13:06
 * @Version: 1.0
 */
@ApiModel(value = "User", description = "用户信息实体类")
@Data                       // 快捷生成Getter,Setter,equals,hashCode,toString函数
@Builder
@NoArgsConstructor          // 快捷生成无参构造函数
@AllArgsConstructor         // 快捷生成全参构造函数
@TableName(value = "user")   // 指定该类映射数据库表名
public class User {

    @ApiModelProperty(value="用户ID", dataType = "String", example = "UUID")
    @NotBlank(message = "用户ID不能为空")
    @Length(message = "用户ID长度应该在{min}~{max}之间", min = 1, max = 64)
    @TableId(type = IdType.UUID)
    @TableField("id")
    private String id;

    @ApiModelProperty(value="用户登录名称", dataType = "String", required = true, example = "admin")
    @NotBlank(message = "用户登录名称不能为空")
    @Length(message = "用户登录名称长度应该在{min}~{max}之间", min = 1, max = 20)
    @TableField("name")
    private String name;

    @ApiModelProperty(value="用户登录密码", dataType = "String", required = true, example = "123456")
    @NotBlank(message = "用户登录密码不能为空")
    @Length(message = "用户登录名称长度应该在{min}~{max}之间", min = 1, max = 100)
    @TableField("password")
    private String password;

    @ApiModelProperty(value="用户名", dataType = "String", example = "张三")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(value="用户手机号", dataType = "String", example = "17088888888")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value="用户邮箱", dataType = "String", example = "11@qq.com")
    @TableField("email")
    private String email;

    @ApiModelProperty(value="是否是管理员", dataType = "boolean", example = "0")
    @TableField("is_admin")
    private Boolean isAdmin;

    @ApiModelProperty(value="用户年龄", dataType = "Integer", example = "20")
    @TableField("age")
    private Integer age;

    @ApiModelProperty(value="用户性别", dataType = "Integer", example = "1")
    @TableField("sex")
    private Integer sex;

    @ApiModelProperty(value="关联地区ID", dataType = "String", example = "UUID")
    @TableField("area_id")
    private String areaId;

    @ApiModelProperty(value="关联地区ID", dataType = "String", example = "UUID")
    @TableField("organization_id")
    private String organizationId;

    @ApiModelProperty(value="关联机构ID", dataType = "String", example = "UUID")
    @TableField("edit_user_id")
    private String editUserId;

    @ApiModelProperty(value="操作时间，格式：yyyy-MM-dd HH:mm:ss", dataType="String", notes="格式：yyyy-MM-dd HH:mm:ss", example = "2019-09-09 09:09:09")
    @DateTimeFormat
    @TableField(value = "edit_time")
    private LocalDateTime editTime;
}
