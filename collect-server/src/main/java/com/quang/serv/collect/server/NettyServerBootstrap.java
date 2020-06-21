package com.quang.serv.collect.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author Lianquan Yang
 */
@Slf4j
@Component
public class NettyServerBootstrap implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 启动新线程维护netty
        log.info("Starting Netty Server...");
        new Thread(() -> {
            EventLoopGroup bossGroup = new NioEventLoopGroup(1);
            EventLoopGroup workerGroup = new NioEventLoopGroup();

            try {
                ServerBootstrap boot = new ServerBootstrap();
                boot.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ch.pipeline().addLast(new ChannelInboundHandler());
                            }
                        })
                        .option(ChannelOption.SO_BACKLOG, 128)
                        .childOption(ChannelOption.SO_KEEPALIVE, true);;

                // start
                ChannelFuture chanelFuture = boot.bind(ServerConfig.getInstance().getPort()).sync();
                chanelFuture.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                log.error("exception occurred when start netty server", e);
            } finally {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
        }).start();
        log.info("Netty Server has been started");
    }
}
