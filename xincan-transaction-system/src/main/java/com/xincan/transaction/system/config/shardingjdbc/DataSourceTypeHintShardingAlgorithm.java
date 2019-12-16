package com.xincan.transaction.system.config.shardingjdbc;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 设置ShardingJdbc使用自定义的datasource选择逻辑
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
