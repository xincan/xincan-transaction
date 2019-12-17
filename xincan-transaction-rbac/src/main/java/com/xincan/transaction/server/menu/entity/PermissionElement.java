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

import javax.validation.constraints.NotBlank;

/**
 * @Copyright (C), 2019,北京同创永益科技发展有限公司
 * @ProjectName: xincan-transaction
 * @Package: com.xincan.transaction.system.server.user.entity
 * @ClassName: User
 * @author: wangmingshuai
 * @Description: 权限页面元素实体类
 * @Date: 2019/12/17 13:06
 * @Version: 1.0
 */
@ApiModel(value = "PermissionElement", description = "权限页面元素实体类")
@Data                       // 快捷生成Getter,Setter,equals,hashCode,toString函数
@Builder
@NoArgsConstructor          // 快捷生成无参构造函数
@AllArgsConstructor         // 快捷生成全参构造函数
@TableName(value = "permission_element")   // 指定该类映射数据库表名
public class PermissionElement {

    @ApiModelProperty(value="权限页面元素ID", dataType = "String", example = "UUID")
    @NotBlank(message = "权限页面元素ID不能为空")
    @Length(message = "权限页面元素ID长度应该在{min}~{max}之间", min = 1, max = 64)
    @TableId(type = IdType.UUID)
    @TableField("id")
    private String id;

    @ApiModelProperty(value="权限ID", dataType = "String", example = "UUID")
    @NotBlank(message = "权限ID不能为空")
    @Length(message = "权限ID长度应该在{min}~{max}之间", min = 1, max = 64)
    @TableField("permission_id")
    private String permissionId;

    @ApiModelProperty(value="页面元素ID", dataType = "String", example = "UUID")
    @NotBlank(message = "页面元素ID不能为空")
    @Length(message = "页面元素ID长度应该在{min}~{max}之间", min = 1, max = 64)
    @TableField("element_id")
    private String elementId;
}
