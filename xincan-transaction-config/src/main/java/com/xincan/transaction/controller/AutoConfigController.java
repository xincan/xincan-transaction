package com.xincan.transaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: xincan-transaction
 * @description: 动态配置测试类
 * @author: JiangXincan
 * @create: 2019/12/10 16:40
 * @version: 1.0
 */
@RestController
public class AutoConfigController {



    private EurekaClientConfigBean eurekaClientConfigBean;

    @Autowired
    public AutoConfigController(EurekaClientConfigBean eurekaClientConfigBean) {
        this.eurekaClientConfigBean = eurekaClientConfigBean;
    }

    @GetMapping("/info")
    public Object getEurekaServerInfo(){
        return eurekaClientConfigBean.getServiceUrl();
//        return "asdfasdf";
    }

}
