package com.quang.serv.collect.server;

import com.quang.serv.collect.collectors.NettyMessageCollector;
import com.quang.serv.core.components.collector.Collector;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @author Lianquan Yang
 */
@Slf4j
public class ChannelInboundHandler extends ChannelInboundHandlerAdapter{

    private Collector<String> collector = new NettyMessageCollector();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf)msg;
        String message = in.toString(StandardCharsets.UTF_8);
        collector.collect(message);
        ReferenceCountUtil.release(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
