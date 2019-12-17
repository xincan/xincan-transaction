package com.xincan.transaction.system.config.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: TODO
 * @className: MyBatisPageConfig
 * @date: 2019/10/31 13:18
 * @author: Xincan Jiang
 * @version: 1.0
 */
@Configuration
public class MyBatisPlusConfig {

    /**
     * @description: 分页配置
     * @method: paginationInterceptor
     * @author: Xincan Jiang
     * @date: 2019-11-01 10:11:35
     * @return: PaginationInterceptor
     * @exception:
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return  new PaginationInterceptor();
    }

}
