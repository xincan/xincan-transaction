package com.xincan.transaction.log.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.io.Serializable;

/**
 * @program: xincan-transaction
 * @description: 系统日志类型枚举类
 * @author: JiangXincan
 * @create: 2019/12/16 16:29
 * @version: 1.0
 */
@ApiModel(value = "ESystemBusinessLogType", description = "系统操作日志类型枚举类")
@Getter
public enum ESystemOperationLogType {

    OPERATION_CREATE(1, "增加"),
    OPERATION_READ(2, "读取"),
    OPERATION_UPDATE(3, "修改"),
    OPERATION_DELETE(4, "导出"),

    OPERATION_SEARCH(5, "查询"),
    OPERATION_IMPORT(6, "导入"),
    OPERATION_EXPORT(7, "导出"),
    OPERATION_UPLOAD(8, "上传"),
    OPERATION_DOWNLOAD(9, "下载"),
    OPERATION_DETAIL(10, "详情")
    ;

    @EnumValue
    @ApiModelProperty(value="系统操作日志CODE码", dataType = "Integer", example = "1")
    private Integer code;

    @ApiModelProperty(value="系统操作CODE码说明", dataType = "String", example = "增加")
    private String message;

    ESystemOperationLogType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
