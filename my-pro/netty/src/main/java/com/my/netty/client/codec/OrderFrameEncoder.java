package com.my.netty.client.codec;

import io.netty.handler.codec.LengthFieldPrepender;

public class OrderFrameEncoder extends LengthFieldPrepender {
    public OrderFrameEncoder() {
        super(2);

        System.out.println("OrderFrameEncoder");
    }
}
