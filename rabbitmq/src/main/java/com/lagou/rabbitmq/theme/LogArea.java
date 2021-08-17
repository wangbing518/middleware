package com.lagou.rabbitmq.theme;

import lombok.Getter;

@Getter
public enum LogArea {

    BEIJING(0, "beijing"),

    SHANGHAI(1, "shanghai"),

    SHENZHEN(2, "shenzhen");

    private int type;

    private String desc;

    LogArea(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }


    public static LogArea getLogArea(int type) {
        for (LogArea temp : LogArea.values()) {
            if (temp.type == type) {
                return temp;
            }
        }
        return null;
    }
}
