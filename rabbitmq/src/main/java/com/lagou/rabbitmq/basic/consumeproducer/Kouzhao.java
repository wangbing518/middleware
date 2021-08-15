package com.lagou.rabbitmq.basic.consumeproducer;

import lombok.Data;

@Data
public class Kouzhao {

    private String name;
    private Integer size;

    public Kouzhao(String name, Integer size) {
        this.name = name;
        this.size = size;
    }
}
