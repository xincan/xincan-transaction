package com.xincan.transaction.config;

import com.xincan.transaction.result.ResponseResult;
import com.xincan.transaction.result.ResultObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Copyright (C), 2019,北京同创永益科技发展有限公司
 * @ProjectName: xincan-transaction
 * @Package: com.xincan.transaction.config
 * @ClassName: GlobalExceptionHandler
 * @author: wangmingshuai
 * @Description: 全局异常配置类
 * @Date: 2019/12/12 14:37
 * @Version: 1.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 全局参数校验异常(参数未添加@RequestBody)
     */
    @ExceptionHandler(BindException.class)
    public ResultObject<Object> handleBindException(BindException e) {
        log.error(e.getMessage(), e);
        return ResponseResult.error(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * 全局参数校验异常(参数添加了@RequestBody)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultObject<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return ResponseResult.error(e.getBindingResult().getFieldError().getDefaultMessage());
    }


}
