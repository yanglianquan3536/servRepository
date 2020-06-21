package com.quang.serv.collect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Lianquan Yang
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.quang.serv")
public class CollectBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(CollectBootstrap.class, args);
    }

}
