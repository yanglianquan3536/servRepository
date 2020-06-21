package com.quang.serv.collect.collectors;

import com.quang.serv.common.chain.Chain;
import com.quang.serv.common.chain.ChainDispatcher;
import com.quang.serv.common.chain.Process;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Lianquan Yang
 */
@Slf4j
public class HealthReportCollector implements Chain, Cloneable {

    @Override
    public <T> T doChain(ChainDispatcher chainDispatcher, Object val, int pos, Process<T> process) {
        log.debug("chain HealthReportCollector is now doChaining...");
        return chainDispatcher.doChain(chainDispatcher, val, pos, process);
    }

    @Override
    public Integer getOrder() {
        return LOWEREST_ORDER - 1;
    }
}
