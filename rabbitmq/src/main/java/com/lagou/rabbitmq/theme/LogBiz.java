package com.lagou.rabbitmq.theme;

import lombok.Getter;

@Getter
public enum LogBiz {

    EDU_ONLINE(0, "edu-online"),

    BIZ_ONLINE(1, "biz-online"),

    EMP_ONLINE(2, "emp-online");

    private int type;

    private String desc;

    LogBiz(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static LogBiz getLogBiz(int type) {
        for (LogBiz temp : LogBiz.values()) {
            if (temp.type == type) {
                return temp;
            }
        }
        return null;
    }
}
