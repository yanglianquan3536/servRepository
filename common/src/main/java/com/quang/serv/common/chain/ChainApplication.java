package com.quang.serv.common.chain;

import java.util.List;

/**
 * @author Lianquan Yang
 */
public class ChainApplication {

    public static <T> T run(List<Chain> chains, Object val, Process<T> process){

        // 触发
        ChainDispatcher handler = new ChainDispatcher(chains);
        return handler.doChain(handler, val,0, process);

    }

}
