package com.xincan.transaction.rbac.result;

/**
 * Copyright (C), 2018, 北京同创永益科技发展有限公司
 * FileName: ResultCode
 * Author:   JiangXincan
 * Date:     2018-12-19 16:30:00
 * Description: 状态响应码工具类
 */
public enum  ResultCode {

    // 成功
    SUCCESS(200),

    // 失败
    FAIL(500),

    // 未认证（签名错误）
    UNAUTHORIZED(401),

    // 接口不存在
    NOT_FOUND(404),

    // 服务器内部错误
    INTERNAL_SERVER_ERROR(501),

    //token快要过期
    TOKEN_ALMOST_EXPIRED(7001),
    //token时间过期
    TOKEN_EXPIRED(7002);

    public int code;

    ResultCode(int code) {
        this.code = code;
    }
}
