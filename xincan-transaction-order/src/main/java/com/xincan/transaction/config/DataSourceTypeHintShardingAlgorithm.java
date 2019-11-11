package com.xincan.transaction.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.ShardingValue;
import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 设置ShardingJdbc使用自定义的datasource选择逻辑
 * 通过DynamicDataSourceAspect类将数据源的名称注入到shardingValue,从自定义的所有数据源中选择出拥有该名称的数据源
 */
@Slf4j
public class DataSourceTypeHintShardingAlgorithm implements HintShardingAlgorithm<String> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, HintShardingValue<String> shardingValue) {
        if (!CollectionUtils.isEmpty(shardingValue.getValues())) {
            log.info("-------当前所选择的数据源---------"+shardingValue.getValues());
            return availableTargetNames.stream().filter(availableTargetName ->
                    shardingValue.getValues().contains(availableTargetName)).collect(Collectors.toList());
        }
        return availableTargetNames;
    }
}
