package com.quang.serv.collect;

import com.quang.serv.core.components.collector.Collector;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Lianquan Yang
 */
@Slf4j
public class MessageCollector implements Collector<String> {
    @Override
    public void collect(String s) {
        log.info(String.format("message received:[%s]", s));
    }
}
