package com.quang.serv.core.enums;

import lombok.Getter;

/**
 * 消息类型Enum
 */
@Getter
public enum MsgReportTypeEnum {
    HEALTH(1, "健康上报"),
    REQUEST(2, "访问上报");

    private int code;
    private String name;

    MsgReportTypeEnum(int code, String name){
        this.code = code;
        this.name = name;
    }
}
