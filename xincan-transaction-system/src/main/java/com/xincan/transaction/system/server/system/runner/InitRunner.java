package com.xincan.transaction.system.server.system.runner;

import com.xincan.transaction.system.server.system.service.ITenantDatasourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitRunner implements ApplicationRunner {

    @Autowired
    ITenantDatasourceService tenantDatasourceService;

    /**
     * 服务启动后执行
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        /**
         * 刷新租户数据源
         */
        tenantDatasourceService.refreshDataSource();
    }
}
