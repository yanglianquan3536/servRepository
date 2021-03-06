package com.quang.serv.register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 注册中心应集群部署，避免单点
 * 注册中心通过该类启动，执行main方法即可
 * @author Lianquan Yang
 */
@SpringBootApplication
@ComponentScan(basePackages="com.quang.serv")
public class RegisterBootstrap {
    public static void main(String[] args) {

        SpringApplication.run(RegisterBootstrap.class, args);
        // 启动健康数据上报

    }
}
