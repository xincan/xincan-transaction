package com.xincan.transaction.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumSet;

/**
 * @Copyright (C), 2019,北京同创永益科技发展有限公司
 * @ProjectName: xincan-transaction
 * @Package: com.xincan.transaction.result
 * @ClassName: ResultMessage
 * @author: wangmingshuai
 * @Description: 响应结果描述枚举类
 * @Date: 2019/12/13 09:15
 * @Version: 1.0
 */
public enum ResultMessage {

    // 成功
    SUCCESS("response_result_message_success"),

    // 失败
    FAIL("response_result_message_error");

    public String message;

    //信息国际化接口类
    private MessageSource messageSource;

    public ResultMessage setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
        return this;
    }

    ResultMessage(String message) {
        this.message = message;
    }

    /**
     * 通过静态内部类的方式注入MessageSource，并赋值到枚举中
     */
    @Component
    public static class ReportTypeServiceInjector {

        //信息国际化接口类
        @Autowired
        private MessageSource messageSource;

        @PostConstruct
        public void postConstruct() {
            for (ResultMessage rt : EnumSet.allOf(ResultMessage.class))
                rt.setMessageSource(messageSource);
        }
    }

    /**
     * @return message，根据语言环境返回国际化字符串
     */
    public String getMessage() {
        //第三个参数是找不到信息时的默认值，如果不指定找不到信息时会报错：NoSuchMessageException
        return messageSource.getMessage(message,null,message, LocaleContextHolder.getLocale());
    }
}
