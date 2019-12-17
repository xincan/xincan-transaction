package com.xincan.transaction.log.enumer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * @program: xincan-transaction
 * @description: 系统操作日志类型枚举类
 * @author: JiangXincan
 * @create: 2019/12/16 16:29
 * @version: 1.0
 */
@ApiModel(value = "SystemLog", description = "系统操作日志类型枚举类")
@Getter
public enum ESystemBusinessLogType {

//    登录日志，操作日志，流程日志，脚本日志

    BUSINESS_LOGIN(1, "登录日志"),
    BUSINESS_OPERATION(2, "操作日志"),
    BUSINESS_FLOW(3, "流程日志"),
    BUSINESS_SCRIPT(4, "脚本日志")
    ;

    @ApiModelProperty(value="操作日志类型CODE码", dataType = "Integer", example = "1")
    private Integer code;

    @ApiModelProperty(value="操作日志类型CODE码说明", dataType = "String", example = "登录日志")
    private String message;

    ESystemBusinessLogType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
