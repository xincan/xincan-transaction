package com.xincan.transaction.rbac.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Copyright (C), 2018, 北京同创永益科技发展有限公司
 * FileName: ResultObject
 * Author:   JiangXincan
 * Date:     2018-12-19 16:32:00
 * Description: 创建返回对象实体类
 */
@Data
@ApiModel(value = "ResultObject<T>",description = "状态返回信息")
public class ResultObject<T> {

    // 响应结果编码
    @ApiModelProperty(name="code", value="响应结果编码")
    public Integer code;

    // 响应结果信息
    @ApiModelProperty(name="msg", value="响应结果信息")
    private String msg;

    // 统计列表总数
    @ApiModelProperty(name="count",value="统计列表总数（如果列表存在）")
    private long count;

    // 响应结果数据
    @ApiModelProperty(name="data",value="响应结果数据")
    private T data;

    public ResultObject<T> setCode(ResultCode retCode) {
        this.code = retCode.code;
        return this;
    }

    public ResultObject<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public ResultObject<T> setMsg(ResultMessage retMsg) {
        this.msg = retMsg.getMessage();
        return this;
    }

    public ResultObject<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public ResultObject<T> setCount(long count) {
        this.count = count;
        return this;
    }

    public ResultObject<T> setData(T data) {
        this.data = data;
        return this;
    }

}
