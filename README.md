# xincan-transaction

## 数据源动态切换
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
- 配置dataSourceMap数据源Map:
xincan-transaction-order微服务启动时会从租户数据源信息表tenant_datasource中读取
每个租户对应的数据源信息,并放入到类型是Map<String, DataSource>的dataSourceMap中.

- 配置DataSource:
利用上一步自定义的dataSourceMap和分库逻辑shardingRuleConfiguration, 通过ShardingDataSourceFactory
工厂生成类型为ShardingDataSource的DataSource并注入到Spring容器中.

- 配置SqlSessionFactory:
利用上一步定义的DataSource和Mybatis Plus的分页配置paginationInterceptor, 生成Mybatis Plus的配置SqlSessionFactory.

- 配置SqlSessionTemplate:
使用上一步定义的Mybatis Plus的sqlSessionFactory生成SqlSessionTemplate.

#### 3. 定义数据源选择逻辑类DataSourceTypeHintShardingAlgorithm
该类继承自HintShardingAlgorithm, 并实现了doSharding方法. 用户每次设置的shardingValue数据源名称, 在进行数据库查询时会从
初始化时生成的所有数据源信息(availableTargetNames)中选择出对应的数据源信息.



