package com.xincan.transaction.log.result;

/**
 * @Copyright (C), 2019,北京同创永益科技发展有限公司
 * @ProjectName: xincan-transaction
 * @Package: com.xincan.transaction.log.result
 * @ClassName: ResponseResult
 * @author: wangmingshuai
 * @Description: 统一响应结果实体类
 * @Date: 2019/12/12 17:28
 * @Version: 1.0
 */
public class ResponseResult {

    public ResponseResult() {
    }

    public static <T> ResultObject<T> success() {
        return (new ResultObject()).setCode(ResultCode.SUCCESS).setMsg(ResultMessage.SUCCESS);
    }

    public static <T> ResultObject<T> success(T data) {
        return (new ResultObject()).setCode(ResultCode.SUCCESS).setMsg(ResultMessage.SUCCESS).setCount(1L).setData(data);
    }

    public static <T> ResultObject<T> success(String message, T data) {
        return (new ResultObject()).setCode(ResultCode.SUCCESS).setMsg(message).setCount(1L).setData(data);
    }

    public static <T> ResultObject<T> success(Integer total, T data) {
        return (new ResultObject()).setCode(ResultCode.SUCCESS).setMsg(ResultMessage.SUCCESS).setCount(total).setData(data);
    }

    public static <T> ResultObject<T> success(String msg, Integer total, T data) {
        return (new ResultObject()).setCode(ResultCode.SUCCESS).setMsg(msg).setCount(total).setData(data);
    }

    public static <T> ResultObject<T> error() {
        return (new ResultObject()).setCode(ResultCode.FAIL).setMsg(ResultMessage.FAIL).setCount(0L).setData((Object)null);
    }

    public static <T> ResultObject<T> error(String message) {
        return (new ResultObject()).setCode(ResultCode.FAIL).setMsg(message).setCount(0L).setData((Object)null);
    }

    public static <T> ResultObject<T> error(String message, T data) {
        return (new ResultObject()).setCode(ResultCode.FAIL).setMsg(message).setCount(0L).setData(data);
    }

    public static <T> ResultObject<T> error(int code, String message, T data) {
        return (new ResultObject()).setCode(code).setMsg(message).setCount(0L).setData(data);
    }

    public static <T> ResultObject<T> success(int code, String msg, T data) {
        return (new ResultObject()).setCode(code).setMsg(msg).setCount(0L).setData(data);
    }
}
