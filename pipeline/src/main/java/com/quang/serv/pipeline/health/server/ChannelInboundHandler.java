package com.quang.serv.pipeline.health.server;

import com.quang.serv.core.components.collector.Collector;
import com.quang.serv.core.health.HealthReport;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * @author Lianquan Yang
 */
public class ChannelInboundHandler extends ChannelInboundHandlerAdapter{

    private Collector<HealthReport> collector;

    public void setCollector(Collector<HealthReport> collector){
        this.collector = collector;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf)msg;
        System.out.println(in.toString(StandardCharsets.UTF_8));
        collector.collect(new HealthReport());
        ctx.write(msg);
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
