package com.quang.serv.collect.server;

import lombok.Data;

/**
 * Server配置
 * @author Lianquan Yang
 */
@Data
public class ServerConfig {
    private int port = 3300;
    private ServerConfig(){}
    public static final ServerConfig _CONFIG = new ServerConfig();
    public static ServerConfig getInstance(){
        return _CONFIG;
    }
}
