package com.quang.serv.common.chain;

import java.util.Comparator;
import java.util.List;

/**
 * @author Lianquan Yang
 */
public class ChainDispatcher extends AbstractChain {

    private List<Chain> chains;

    public ChainDispatcher(List<Chain> chains) {
        this.chains = chains;
        chains.sort(Comparator.comparingInt(Chain::getOrder));
    }

    private boolean hasNext(int pos) {
        return chains.size() - 1 >= pos;
    }

    public List<Chain> getChains() {
        return chains;
    }

    public void setChains(List<Chain> chains) {
        this.chains = chains;
    }

    @Override
    public <T> T doChain(ChainDispatcher chainDispatcher, Object val, int pos, Process<T> process) {
        if (hasNext(pos)) return chainDispatcher.getChains().get(pos).doChain(chainDispatcher, val, ++pos, process);
        return process.doProcess();
    }
}
