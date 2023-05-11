package com.my.netty.server.codec;

import com.my.netty.common.ResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class OrderProtocolEncoder extends MessageToMessageEncoder<ResponseMessage> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ResponseMessage responseMessage, List<Object> out) throws Exception {
        log.info("executed");

        ByteBuf byteBuf = channelHandlerContext.alloc().buffer();

        responseMessage.encode(byteBuf);

        out.add(byteBuf);
    }
}
