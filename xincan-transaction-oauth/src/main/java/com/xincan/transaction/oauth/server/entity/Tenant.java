package com.xincan.transaction.oauth.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@TableName("tenant")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tenant implements Serializable {

    // 此处使用snowflake分布式主键算法
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @TableField("tenant_name")
    private String tenantName;

}
