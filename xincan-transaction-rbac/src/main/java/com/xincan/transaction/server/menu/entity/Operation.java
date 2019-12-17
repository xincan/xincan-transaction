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
import java.time.LocalDateTime;

/**
 * @Copyright (C), 2019,北京同创永益科技发展有限公司
 * @ProjectName: xincan-transaction
 * @Package: com.xincan.transaction.system.server.user.entity
 * @ClassName: User
 * @author: wangmingshuai
 * @Description: 操作实体类
 * @Date: 2019/12/17 13:06
 * @Version: 1.0
 */
@ApiModel(value = "Operation", description = "操作实体类")
@Data                       // 快捷生成Getter,Setter,equals,hashCode,toString函数
@Builder
@NoArgsConstructor          // 快捷生成无参构造函数
@AllArgsConstructor         // 快捷生成全参构造函数
@TableName(value = "operation")   // 指定该类映射数据库表名
public class Operation {

    @ApiModelProperty(value="操作ID", dataType = "String", example = "UUID")
    @NotBlank(message = "操作ID不能为空")
    @Length(message = "操作ID长度应该在{min}~{max}之间", min = 1, max = 64)
    @TableId(type = IdType.UUID)
    @TableField("id")
    private String id;

    @ApiModelProperty(value="操作名称", dataType = "String", required = true, example = "添加")
    @NotBlank(message = "操作名称不能为空")
    @Length(message = "操作名称长度应该在{min}~{max}之间", min = 1, max = 50)
    @TableField("operation_name")
    private String operationName;

    @ApiModelProperty(value="操作编码", dataType = "String", required = true, example = "insert-58")
    @NotBlank(message = "操作编码不能为空")
    @Length(message = "操作编码长度应该在{min}~{max}之间", min = 1, max = 50)
    @TableField("operation_id")
    private String operationId;

    @ApiModelProperty(value="拦截URL前缀", dataType = "String", example = "/select/list")
    @TableField("filter_url")
    private String filterUrl;

    @ApiModelProperty(value="父级操作ID", dataType = "String", required = true, example = "UUID")
    @NotBlank(message = "父级操作ID不能为空")
    @Length(message = "父级操作ID长度应该在{min}~{max}之间", min = 1, max = 64)
    @TableField("parent_id")
    private String parentId;

    @ApiModelProperty(value="编辑人员ID", dataType = "String", example = "UUID")
    @TableField("edit_user_id")
    private String editUserId;

    @ApiModelProperty(value="操作时间，格式：yyyy-MM-dd HH:mm:ss", dataType="String", notes="格式：yyyy-MM-dd HH:mm:ss", example = "2019-09-09 09:09:09")
    @DateTimeFormat
    @TableField(value = "edit_time")
    private LocalDateTime editTime;
}
