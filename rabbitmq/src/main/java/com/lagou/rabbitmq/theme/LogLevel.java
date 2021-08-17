package com.lagou.rabbitmq.theme;

import lombok.Getter;

@Getter
public enum LogLevel {

    INFO(0, "info"),

    ERROR(1, "error"),

    WARN(2, "warn");

    private int type;

    private String desc;

    LogLevel(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static LogLevel getLogLevel(int type){
        for (LogLevel temp:LogLevel.values()){
            if (temp.type==type){
                return temp;
            }
        }
        return null;
    }
}
