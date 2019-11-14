# xincan-transaction

## 数据源动态更新和切换
### 一. 系统配置
#### 1. maven配置
pom文件中需要引用ShardingJdbc依赖

```text
<!-- 集成sharding-jdbc依赖 -->
<dependency>
    <groupId>org.apache.shardingsphere</groupId>
    <artifactId>sharding-jdbc-spring-boot-starter</artifactId>
</dependency>
<!-- 集成sharding-jdbc xa分布式事务 依赖 -->
<dependency>
    <groupId>org.apache.shardingsphere</groupId>
    <artifactId>sharding-transaction-xa-core</artifactId>
</dependency>
```

#### 2. ShardingJdbc配置
- 配置application.yml:
xincan-transaction-order微服务启动时会从配置的 *主数据源* 中生成ShardingDataSource类型的dataSource.

- 配置SqlSessionFactory:
利用上一步定义的DataSource和Mybatis Plus的分页配置paginationInterceptor, 生成Mybatis Plus的配置SqlSessionFactory.

- 配置SqlSessionTemplate:
使用上一步定义的Mybatis Plus的sqlSessionFactory生成SqlSessionTemplate.

#### 3. 定义数据源选择逻辑类DataSourceTypeHintShardingAlgorithm
该类继承自HintShardingAlgorithm, 并实现了doSharding方法. 用户每次设置的shardingValue数据源名称, 在进行数据库查询时会从
初始化时生成的所有数据源信息(availableTargetNames)中选择出对应的数据源信息.

#### 4. 动态读取主数据源中配置的租户信息:
系统启动后会调用InitRunner类中的run方法. 该方法查询了主数据源中的tenant_datasource表, 读取出所有租户的数据源信息并根据其对应的
数据源信息生成对应的DruidDataSource, 加入到ShardingDataSource的dataSourceMap中.

### 二. 示例
#### 1. 根据租户查询租户对应的用户表信息
启动xincan-transaction-eureka和xincan-transaction-order微服务. 

- 租户登录获取token. 发送POST请求到 http://localhost:8070/oauth/token, 
    
    header中配置basic auth类型的参数如下:
    
    |类型         |值           |说明          |
    |----------------|-----------|-----------|
    |`username`|`xincan-transaction-order`|微服务的clientId|
    |`password`|`123456`|微服务的clientSecret|
    
    其中body中传入以下参数:
    
    |key      |value       |说明      |
    |----------------|-----------|-----------|
    |`grant_type`|`password`|oauth2.0鉴权类型, 这里采用password类型|
    |`username`|`tenant`|租户名|
    |`password`|`123456`|密码|
    |`scope`|`server`|scope用来限制访问的范围. 即下面header中设置的xincan-transaction-order微服务所拥有的scope|
    
    经过POST发送请求后, 得到的token如下
    ```text
    {
        "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NzM3MjYyODksInVzZXJfbmFtZSI6InRlbmFudDAiLCJhdXRob3JpdGllcyI6WyJST0xFX0FETUlOIiwiUk9MRV9URU5BTlQiLCJST0xFX0RFVkxPUCJdLCJqdGkiOiJiMTYyMjY1Mi1mN2RmLTQyOWMtOTRkZC00ZDc3NjQzYzJlMDAiLCJjbGllbnRfaWQiOiJ4aW5jYW4tdHJhbnNhY3Rpb24tb3JkZXIiLCJzY29wZSI6WyJzZXJ2ZXIiXX0.GssQ2l5htCniXF7yfQDf8PuAqLQOq-7JMoMFkgxrlqE",
        "token_type": "bearer",
        "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ0ZW5hbnQwIiwic2NvcGUiOlsic2VydmVyIl0sImF0aSI6ImIxNjIyNjUyLWY3ZGYtNDI5Yy05NGRkLTRkNzc2NDNjMmUwMCIsImV4cCI6MTU3MzcyNjI4OSwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVEVOQU5UIiwiUk9MRV9ERVZMT1AiXSwianRpIjoiMGFhMzdmZTEtYjcwMS00MjM1LWI4MzMtMmZlOWM0Mzk1M2ZjIiwiY2xpZW50X2lkIjoieGluY2FuLXRyYW5zYWN0aW9uLW9yZGVyIn0.yplZRkHbfrbXKBKuF7-56MSROnNpWAVAEldX4WHR3BI",
        "expires_in": 5999,
        "scope": "server",
        "jti": "b1622652-f7df-429c-94dd-4d77643c2e00"
    }
    ```
    其中access_token即为获取到的token, token类型为bearer, token有效期为5999秒, token的scope范围为server. 

- 利用上一步得到token值, 设置到header中并发送GET请求到 /order/selectUser/obj,

    其中header中配置bearer token类型的参数如下:
    
    |key        |value                         |说明             |
    |----------------|------------------------|---------------------|
    |`Authorization`|`Bearer eyJhbGciOiJIUzI1Ni...`|字符Bearer+空格+第一步得到的token|
    
    请求列表如下:
    
    |参数名称         |参数值           |说明          |
    |----------------|-----------|-----------|
    |`limit`|`10`|每页行数|
    |`page`|`1`|当前页数|
    |`sortName`|`create_time`|排序字段名称|
    
    此时查询的是tenant0租户对应的数据库xincan-transaction中的user用户表

- 使用租户tenant2登录. 和第一步相同, 首先得到token, 再将token设置到header中并发送GET请求到 /order/selectUser/obj

    其中请求列表和上述请求列表相同, 此时查询的是tenant2租户对应的数据库xincan-transaction-order中的user用户表

#### 2. XA分布式事务
xincan-transaction-order服务内不同数据库之间分布式事务. 分别向数据库xincan-transaction中的user用户表和
数据库xincan-transaction-order中的user_order表中插入数据, 这两步插入操作在一个事务中.

发送POST请求到/order/test

UserServiceImpl类中的testInsert方法首先向数据库xincan-transaction中的user用户表成功插入了未提交的数据, 但向数据库
xincan-transaction-order中的user_order表插入数据时失败, 事务回滚. 此时数据库xincan-transaction中成功插入的
数据也不会被提交.
