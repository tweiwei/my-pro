package com.my.netty.client.codec;

import com.my.netty.common.Operation;
import com.my.netty.common.RequestMessage;
import com.my.netty.util.IdUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class OperationToRequestMessageEncoder extends MessageToMessageEncoder<Operation> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Operation operation, List<Object> out) throws Exception {
        RequestMessage requestMessage = new RequestMessage(IdUtil.nextId(), operation);

        out.add(requestMessage);

        System.out.println("OperationToRequestMessageEncoder");
    }
}
