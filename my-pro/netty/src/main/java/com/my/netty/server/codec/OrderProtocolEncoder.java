package com.my.netty.server.codec;

import com.my.netty.common.ResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class OrderProtocolEncoder extends MessageToMessageEncoder<ResponseMessage> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ResponseMessage responseMessage, List<Object> out) throws Exception {
        System.out.println("OrderProtocolEncoder");

        ByteBuf byteBuf = channelHandlerContext.alloc().buffer();

        responseMessage.encode(byteBuf);

        out.add(byteBuf);
    }
}
