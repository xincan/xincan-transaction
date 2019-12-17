package com.xincan.transaction.server.user.entity;

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

import javax.validation.constraints.NotBlank;

/**
 * @Copyright (C), 2019,北京同创永益科技发展有限公司
 * @ProjectName: xincan-transaction
 * @Package: com.xincan.transaction.system.server.user.entity
 * @ClassName: UserRole
 * @author: wangmingshuai
 * @Description: 用户角色实体类
 * @Date: 2019/12/17 13:43
 * @Version: 1.0
 */
@ApiModel(value = "UserRole", description = "用户角色实体类")
@Data                       // 快捷生成Getter,Setter,equals,hashCode,toString函数
@Builder
@NoArgsConstructor          // 快捷生成无参构造函数
@AllArgsConstructor         // 快捷生成全参构造函数
@TableName(value = "user_role")   // 指定该类映射数据库表名
public class UserRole {

    @ApiModelProperty(value="用户角色ID", dataType = "String", example = "UUID")
    @NotBlank(message = "用户角色ID不能为空")
    @Length(message = "用户角色ID长度应该在{min}~{max}之间", min = 1, max = 64)
    @TableId(type = IdType.UUID)
    @TableField("id")
    private String id;

    @ApiModelProperty(value="用户ID", dataType = "String", example = "UUID")
    @NotBlank(message = "用户ID不能为空")
    @Length(message = "用户ID长度应该在{min}~{max}之间", min = 1, max = 64)
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value="角色ID", dataType = "String", example = "UUID")
    @NotBlank(message = "角色ID不能为空")
    @Length(message = "角色ID长度应该在{min}~{max}之间", min = 1, max = 64)
    @TableField("role_id")
    private String roleId;

}
