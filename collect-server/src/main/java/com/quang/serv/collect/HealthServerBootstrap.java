package com.quang.serv.collect;

import com.quang.serv.core.components.collector.Collector;
import com.quang.serv.core.health.HealthReport;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Lianquan Yang
 */
@Slf4j
public class HealthServerBootstrap{

    private static final ChannelInboundHandler CHANNEL_INBOUND_HANDLER = new ChannelInboundHandler();

    public static void start(Collector<HealthReport> collector){
        CHANNEL_INBOUND_HANDLER.setCollector(collector);
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap boot = new ServerBootstrap();
            boot.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(ServerConfig.getInstance().getPort())
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(CHANNEL_INBOUND_HANDLER);
                        }
                    });

            // start
            ChannelFuture future = boot.bind().sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
