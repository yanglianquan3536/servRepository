package com.quang.serv.common.chain;

public interface Chain {

    int HIGHEST_ORDER = Integer.MIN_VALUE;
    int LOWEREST_ORDER = Integer.MAX_VALUE;

    /**
     * 处理逻辑
     *
     * @param chainDispatcher 责任链组装
     * @param pos          下一个触发下标
     * @param process      实际处理逻辑
     * @return T 返回结果
     */
    <T> T doChain(ChainDispatcher chainDispatcher, Object val, int pos, Process<T> process);

    /**
     * 配置等级 越高优先级(越小) 越优先触发
     */
    default Integer getOrder() {
        return HIGHEST_ORDER;
    }
}
