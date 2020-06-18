package com.quang.serv.core.enums;

import lombok.Getter;

@Getter
public enum Environment {
    DEV("dev"),
    TEST("test"),
    SANDBOX("sandbox"),
    PRODUCT("product");

    private String env;

    Environment(String env){
        this.env = env;
    }
}
