package com.my.netty.client.codec;

import com.my.netty.common.RequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class OrderProtocolEncoder extends MessageToMessageEncoder<RequestMessage> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RequestMessage requestMessage, List<Object> out) throws Exception {
        ByteBuf byteBuf = channelHandlerContext.alloc().buffer();

        requestMessage.encode(byteBuf);

        out.add(byteBuf);

        System.out.println("OrderProtocolEncoder："+requestMessage);
    }
}
