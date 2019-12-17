package com.xincan.transaction.system.server.system.service.impl;

import cn.com.hatech.common.data.result.ResultObject;
import cn.com.hatech.common.data.result.ResultResponse;
import cn.com.hatech.common.data.universal.AbstractService;
import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xincan.transaction.system.server.system.entity.tenant.Tenant;
import com.xincan.transaction.system.server.system.entity.tenant.TenantDatasource;
import com.xincan.transaction.system.server.system.entity.user.User;
import com.xincan.transaction.system.server.system.mapper.tenant.ITenantDatasourceMapper;
import com.xincan.transaction.system.server.system.mapper.tenant.ITenantMapper;
import com.xincan.transaction.system.server.system.mapper.user.IUserMapper;
import com.xincan.transaction.system.server.system.service.ITenantDatasourceService;
import com.xincan.transaction.system.server.system.service.ITenantService;
import com.xincan.transaction.system.server.system.service.IUserService;
import com.xincan.transaction.system.server.system.utils.ConstanceUtils;
import com.xincan.transaction.system.server.system.utils.FileUtils;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.hint.HintManager;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@Slf4j
@Service("tenantService")
public class TenantServiceImpl extends AbstractService<Tenant> implements ITenantService {

    /**
     * 注入租户mapper
     */
    @Resource
    private ITenantMapper tenantMapper;

    /**
     * 注入用户service
     */
    @Resource
    private IUserService userService;

    /**
     * 注入租户数据源service
     */
    @Autowired
    private ITenantDatasourceService tenantDatasourceService;

    /**
     * 主数据源用户名
     */
    @Value("${spring.datasource.druid.username}")
    private String dataSourceUsername;

    /**
     * 主数据源密码
     */
    @Value("${spring.datasource.druid.password}")
    private String dataSourcePassword;

    /**
     * 主数据库名称
     */
    @Value("${spring.datasource.name}")
    private String mainDataSource;

    /**
     * 主数据源连接地址
     */
    @Value("${spring.datasource.druid.url}")
    private String dataSourceUrl;

    /**
     * 注入sharding datasource
     */
    @Autowired
    private DataSource dataSource;

    /**
     * 注入druid datasource
     */
    @Autowired
    private DruidDataSource druidDataSource;

    /**
     * enum类型
     * 创建和删除数据库
     */
    private enum ExecuteType {
        CREATE,
        DROP
    }

    /**
     * 创建或删除租户数据库, sharding jdbc不支持建表语句
     * 需要使用datasource创建
     * @param databaseName
     * @throws Exception
     */
    private void executeDatabase(String databaseName, ExecuteType executeType) throws Exception {
        try (Connection connection = druidDataSource.getConnection();
                Statement statement = connection.createStatement();){
            String dbSql = "";
            if (ExecuteType.CREATE.equals(executeType)) {
                dbSql = "CREATE DATABASE IF NOT EXISTS "+databaseName+" DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci;";
                log.info("创建数据库{}", databaseName);
            }
            else if (ExecuteType.DROP.equals(executeType)) {
                dbSql = "DROP DATABASE "+databaseName;
                log.info("删除数据库{}", databaseName);
            }
            statement.executeUpdate(dbSql);
        }
    }

    /**
     * 添加租户
     * @param databaseName
     * @return
     */
    @Override
    public int createTenant(String databaseName) {
        try {
            HintManager.getInstance().setDatabaseShardingValue(mainDataSource);
            // 添加租户信息到租户数据表
            Tenant tenant = Tenant.builder()
                    .tenantName(databaseName)
                    .build();
            return tenantMapper.insert(tenant);
        }
        finally {
            HintManager.clear();
        }
    }

    /**
     * 删除租户
     * @param databaseName
     * @return
     */
    @Override
    public int deleteTenant(String databaseName) {
        try {
            HintManager.getInstance().setDatabaseShardingValue(mainDataSource);
            // 删除租户数据表中的数据
            QueryWrapper<Tenant> queryTenant = new QueryWrapper<>();
            queryTenant.eq("tenant_name", databaseName);
            return tenantMapper.delete(queryTenant);
        }
        finally {
            HintManager.clear();
        }
    }

    /**
     * 按租户名查询租户数据库个数
     * @param databaseName
     * @return
     */
    @Override
    public int queryCountDataBase(String databaseName) {
        try {
            HintManager.getInstance().setDatabaseShardingValue(mainDataSource);
            // 查询租户数据库是否同名
            QueryWrapper<Tenant> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("tenant_name", databaseName);
            return tenantMapper.selectCount(queryWrapper);
        }
        finally {
            HintManager.clear();
        }
    }

    /**
     * 执行sql语句创建表到新建租户数据库
     * @param databaseName
     * @throws IOException
     * @throws SQLException
     */
    @Override
    public void execSqlFile(String databaseName) throws IOException, SQLException {
        // 执行sql语句创建表到新建租户数据库 (需要先设置数据源)
        HintManager.getInstance().setDatabaseShardingValue(databaseName);

        try (Connection connection =
                     ((ShardingDataSource) dataSource).getDataSourceMap().get(databaseName).getConnection();
             Statement statement =
                     connection.createStatement();){
            Map<String, File> fileMap = FileUtils.getFileByDirectory(ResourceUtils.getURL("classpath:db/").getPath());
            for (File f : fileMap.values()) {
                String sqlText = FileUtils.readFile(f);
                statement.executeUpdate(sqlText);
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            HintManager.clear();
        }
    }

    /**
     * 创建租户数据库
     * @param databaseName
     */
    @Override
    @GlobalTransactional
    public ResultObject createTenantDatabase(String databaseName, String tenantPassword) throws Exception {
        try {
            // 设置事务为base的seata事务
            TransactionTypeHolder.set(TransactionType.BASE);
            // 查询租户数据库是否同名
            int size = queryCountDataBase(databaseName);
            // 不能注册同名租户和与mysql数据库中表名相同的租户
            if (size > 0 || Arrays.asList(ConstanceUtils.MYSQL_DATABASE_NAMES).contains(databaseName)) {
                return ResultResponse.error("存在同名的租户");
            }
            // 创建租户数据库, sharding jdbc不支持建表语句
            executeDatabase(databaseName, ExecuteType.CREATE);

            // 添加租户信息到租户数据表
            createTenant(databaseName);

            // 添加租户名, 租户密码到用户表
            userService.createUser(databaseName, tenantPassword);

            // 租户数据库url连接
            String[] urlPart = dataSourceUrl.split("\\?");
            String dbUrl =
                    // 地址, 端口
                    urlPart[0].substring(0, urlPart[0].lastIndexOf("/")+1)
                    // 数据库名称
                    + databaseName
                    // 数据库配置
                    + "?" + urlPart[1];
            // 添加租户数据源信息到租户数据源表
            tenantDatasourceService.createTenantDatasource(databaseName, dbUrl, dataSourceUsername, dataSourcePassword);

            // 刷新数据源
            tenantDatasourceService.refreshDataSource();

            // 创建seata事务的undo_log表到新建租户数据库
            execSqlFile(databaseName);
            return ResultResponse.success("创建租户成功", "");
        }
        catch (Exception e) {
            // 错误时删除该租户库
            executeDatabase(databaseName, ExecuteType.DROP);
            throw e;
        }
    }

    /**
     * 注销租户, 删除租户数据库
     * @param databaseName
     * @return
     * @throws Exception
     */
    @Override
    @GlobalTransactional
    public ResultObject deleteTenantDatabase(String databaseName) throws Exception {
        TransactionTypeHolder.set(TransactionType.BASE);
        // 删除租户数据表中的数据
        int size = deleteTenant(databaseName);
        if (size < 1 ) {
            return ResultResponse.error("租户不存在");
        }
        // 删除用户数据表中的租户数据
        userService.deleteUser(databaseName);

        // 删除租户数据源表中的租户数据
        tenantDatasourceService.deleteTenantDatasource(databaseName);

        // 刷新数据源
        tenantDatasourceService.refreshDataSource();

        // 删除租户数据库
        executeDatabase(databaseName, ExecuteType.DROP);
        return ResultResponse.success("租户注销成功", "");
    }

}
