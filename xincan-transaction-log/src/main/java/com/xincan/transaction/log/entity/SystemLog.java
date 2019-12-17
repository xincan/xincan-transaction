package com.xincan.transaction.log.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xincan.transaction.log.enums.ESystemBusinessLogType;
import com.xincan.transaction.log.enums.ESystemOperationLogType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * Copyright (C), 2018, 北京同创永益科技发展有限公司
 * FileName: SystemLog
 * Author:   JiangXincan
 * Date:     2018-12-19 15:47:00
 * Description: 操作日志实体类
 */
@ApiModel(value = "SystemLog", description = "操作日志实体类")
@Data                       // 快捷生成Getter,Setter,equals,hashCode,toString函数
@Builder
@NoArgsConstructor          // 快捷生成无参构造函数
@AllArgsConstructor         // 快捷生成全参构造函数
@TableName(value = "system_log")   // 指定该类映射数据库表名

public class SystemLog {

    @ApiModelProperty(value="日志ID", dataType = "String", example = "UUID")
    @NotBlank(message = "日志ID不能为空")
    @Length(message = "日志ID长度应该在{min}~{max}之间", min = 1, max = 64)
    @TableId(type = IdType.UUID)
    @TableField("id")
    private String id;

    @ApiModelProperty(value="当前系统操作用户ID", dataType = "String", required = true, example = "UUID")
    @NotBlank(message = "当前系统操作用户ID不能为空")
    @Length(message = "当前系统操作用户ID长度应该在{min}~{max}之间", min = 1, max = 64)
    @TableField(value = "user_id")
    private String userId;

    @ApiModelProperty(value="当前系统操作用户名称", dataType = "String", required = true, example = "张三")
    @NotBlank(message = "当前系统操作用户名称不能为空")
    @Length(message = "当前系统操作用户名称长度应该在{min}~{max}之间", min = 1, max = 50)
    @TableField(value = "user_name")
    private String userName;

    @ApiModelProperty(value="当前系统操作租户ID", dataType = "String", example = "UUID")
    @NotBlank(message = "当前系统操作租户ID不能为空")
    @Length(message = "当前系统操作用户名称长度应该在{min}~{max}之间", min = 1, max = 64)
    @TableField(value = "tenant_id")
    private String tenantId;

    @ApiModelProperty(value="当前系统操作用户所属公司名称", dataType = "String", required = true, example = "同创永益")
    @NotBlank(message = "当前系统操作用户所属公司名称不能为空")
    @Length(message = "当前系统操作用户所属公司名称长度应该在{min}~{max}之间", min = 1, max = 50)
    @TableField(value = "company")
    private String company;

    @ApiModelProperty(value="当前系统操作用户所在部门名称", dataType = "String", example = "视觉交互部")
    @NotBlank(message = "当前系统操作用户所在部门名称不能为空")
    @Length(message = "当前系统操作用户所在部门名称长度应该在{min}~{max}之间", min = 1, max = 50)
    @TableField(value = "department")
    private String department;

    @ApiModelProperty(value="当前系统操作主机IP", dataType = "String", required = true, example = "192.168.1.1")
    @NotBlank(message = "当前系统操作主机IP不能为空")
    @Length(message = "当前系统操作主机IP长度应该在{min}~{max}之间", min = 1, max = 30)
    @TableField(value = "ip")
    private String ip;

    @ApiModelProperty(value="当前系统操作微服务端口", dataType = "Integer", required = true, example = "99999")
    @NotNull(message = "当前系统操作微服务端口不能为空")
    @DecimalMin(message = "当前系统操作微服务端口错误,应当大于等于于{value}", value = "1")
    @DecimalMax(message = "当前系统操作微服务端口错误,应当小于等于{value}", value = "99999")
    @TableField(value = "port")
    private Integer port;

    @ApiModelProperty(value="当前系统微服务名称", dataType = "String", required = true, example = "xincan-transaction-user")
    @NotBlank(message = "当前系统微服务名称不能为空")
    @Length(message = "当前系统微服务名称长度应该在{min}~{max}之间", min = 1, max = 50)
    @TableField(value = "micro_service")
    private String microService;

    @ApiModelProperty(value="当前系统请求微服务方法函数路径", dataType = "String", required = true, example = "http://192.168.1.1:8080/user/find")
    @NotBlank(message = "当前系统请求微服务方法函数路径不能为空")
    @Length(message = "当前系统请求微服务方法函数路径长度应该在{min}~{max}之间", min = 1, max = 100)
    @TableField(value = "class_url")
    private String classUrl;

    @ApiModelProperty(value="当前系统类请求微服务方法函数参数", dataType = "String", required = true, example = "[{\"name\":\"lisi\"}]")
    @TableField(value = "class_param")
    private String classParam;

    @ApiModelProperty(value="当前系统操作类型", dataType = "ESystemOperationLogType", required = true, example = "OPERATION_CREATE")
    @NotNull(message = "当前系统操作类型不能为空")
    @TableField(value = "type")
    private ESystemOperationLogType type;


    @ApiModelProperty(value="当前系统业务日志类型", dataType = "ESystemBusinessLogType", required = true, example = "BUSINESS_LOGIN")
    @NotNull(message = "当前系统业务日志类型不能为空")
    @TableField(value = "business_type")
    private ESystemBusinessLogType businessType;

    @ApiModelProperty(value="操作模块名称", dataType = "String", example = "用户管理、角色管理")
    @NotBlank(message = "操作模块名称不能为空")
    @Length(message = "操作模块名称不能为空长度应该在{min}~{max}之间", min = 1, max = 50)
    @TableField(value = "model")
    private String model;

    @ApiModelProperty(value="当前系统操作说明", dataType = "String", example = "调用接口做实现")
    @TableField(value = "description")
    @Length(message = "操作模块名称不能为空长度应该在{min}~{max}之间", min = 1, max = 500)
    private String description;

    @ApiModelProperty(value="操作时间，格式：yyyy-MM-dd HH:mm:ss", dataType="String", notes="格式：yyyy-MM-dd HH:mm:ss", example = "2019-09-09 09:09:09")
    @DateTimeFormat
    @TableField(value = "edit_time")
    private LocalDateTime editTime;

}
