package com.quang.serv.collect.collectors;

import com.quang.serv.common.chain.Chain;
import com.quang.serv.common.chain.ChainApplication;
import com.quang.serv.core.components.collector.Collector;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

/**
 * @author Lianquan Yang
 */
@Slf4j
public class NettyMessageCollector implements Collector<String> {

    // 初始化需要添加的收集器
    List<Chain> collectors = Arrays.asList(
            new HealthReportCollector(),
            new StatisticsReportCollector()
    );

    @Override
    public void collect(String s) {
        log.info(String.format("message received:[%s]", s));
        String runner = ChainApplication.run(collectors, s, () -> "Chains execute finished");
        log.debug(String.format("Chain execute finished, result:[%s]", runner));
    }
}
