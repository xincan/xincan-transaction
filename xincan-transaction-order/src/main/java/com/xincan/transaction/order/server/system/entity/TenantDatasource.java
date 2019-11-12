package com.xincan.transaction.order.server.system.entity;


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

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("tenant_datasource")
@ApiModel(value = "com.xincan.transaction.server.system.entity.TenantDatasource",description = "租户数据源实体类")
public class TenantDatasource {

    @ApiModelProperty(value="租户数据源ID", dataType = "Long", example = "123456")
    @TableId(type = IdType.ID_WORKER)
    @TableField("id")
    private Long id;

    @ApiModelProperty(value = "租户名称", dataType = "String", example = "product")
    @TableField("tenant_name")
    private String tenantName;

    @ApiModelProperty(value = "数据源连接地址", dataType = "String", example = "jdbc:mysql://localhost:3306/xincan-transaction")
    @TableField("datasource_url")
    private String datasourceUrl;

    @ApiModelProperty(value = "数据源连接用户名", dataType = "String", example = "root")
    @TableField("datasource_username")
    private String datasourceUsername;

    @ApiModelProperty(value = "数据源连接密码", dataType = "String", example = "root")
    @TableField("datasource_password")
    private String datasourcePassword;

    @ApiModelProperty(value = "数据源连接驱动", dataType = "String", example = "com.mysql.jdbc.Driver")
    @TableField("datasource_driver")
    private String datasourceDriver;

    @ApiModelProperty(value = "数据源连接池类型", dataType = "String", example = "com.alibaba.druid.pool.DruidDataSource")
    @TableField("datasource_type")
    private String datasourceType;

    /**
     * 提取TenantDatasource实体属性, 转变为datasource属性
     * @return
     */
    public Map<String, Object> toDatasourceProp() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", datasourceType);
        map.put("driver-class-name", datasourceDriver);
        map.put("url", datasourceUrl);
        map.put("username", datasourceUsername);
        map.put("password", datasourcePassword);
        return map;
    }

}
