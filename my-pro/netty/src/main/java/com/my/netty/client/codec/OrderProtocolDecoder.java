package com.my.netty.client.codec;

import com.my.netty.common.ResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class OrderProtocolDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> out) throws Exception {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.decode(byteBuf);

        out.add(responseMessage);

        System.out.println("OrderProtocolDecoder");
    }
}
