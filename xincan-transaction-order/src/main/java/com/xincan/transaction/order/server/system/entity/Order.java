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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("user_order")
@ApiModel(value = "com.xincan.transaction.server.system.entity.Order",description = "订单信息实体类")
public class Order {

    @ApiModelProperty(value="订单ID", dataType = "Long", example = "123456")
    @TableId(type = IdType.ID_WORKER)
    @TableField("id")
    private Long id;

    @ApiModelProperty(value = "订单名称", dataType = "String", example = "product")
    @TableField("order_name")
    private String orderName;
}
