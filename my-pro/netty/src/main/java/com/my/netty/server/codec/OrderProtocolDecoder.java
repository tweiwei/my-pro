package com.my.netty.server.codec;

import com.my.netty.common.RequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class OrderProtocolDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> out) {
        System.out.println("OrderProtocolDecoder");

        RequestMessage requestMessage = new RequestMessage();
        requestMessage.decode(byteBuf);

        out.add(requestMessage);


    }
}
