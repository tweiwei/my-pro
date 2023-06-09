package com.my.netty.server.handler;

import com.my.netty.common.Operation;
import com.my.netty.common.OperationResult;
import com.my.netty.common.RequestMessage;
import com.my.netty.common.ResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 业务逻辑处理
 */
@Slf4j
public class OrderServerProcessHandler extends SimpleChannelInboundHandler<RequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RequestMessage requestMessage) throws Exception {
        log.info("receive data: "+requestMessage);

        Operation operation = requestMessage.getMessageBody();
        OperationResult result = operation.execute();

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessageHeader(requestMessage.getMessageHeader());
        responseMessage.setMessageBody(result);

        channelHandlerContext.writeAndFlush(responseMessage);
    }
}
