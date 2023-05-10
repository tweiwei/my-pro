package com.my.netty.client;

import com.my.netty.client.codec.*;
import com.my.netty.client.handler.dispatcher.OperationResultFuture;
import com.my.netty.client.handler.dispatcher.RequestPendingCenter;
import com.my.netty.client.handler.dispatcher.ResponseDispatcherHandler;
import com.my.netty.common.OperationResult;
import com.my.netty.common.RequestMessage;
import com.my.netty.common.order.OrderOperation;
import com.my.netty.util.IdUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class ClientV2 {

    public static void main(String[] args){
        Bootstrap b = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        RequestPendingCenter requestPendingCenter = new RequestPendingCenter();
        try {
            b.channel(NioSocketChannel.class)
                .group(group)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
                        pipeline.addLast(new OrderFrameDecoder());
                        pipeline.addLast(new OrderFrameEncoder());
                        pipeline.addLast(new OrderProtocolEncoder());
                        pipeline.addLast(new OrderProtocolDecoder());

                        pipeline.addLast(new ResponseDispatcherHandler(requestPendingCenter));

                        pipeline.addLast(new OperationToRequestMessageEncoder());

                        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                    }
                });


            ChannelFuture channelFuture = b.connect("127.0.0.1",8080).sync();

            long streamId = IdUtil.nextId();
            RequestMessage requestMessage = new RequestMessage(streamId, new OrderOperation(100,"123"));

            OperationResultFuture operationResultFuture = new OperationResultFuture();
            requestPendingCenter.add(streamId, operationResultFuture);

            channelFuture.channel().writeAndFlush(requestMessage);

            OperationResult operationResult = operationResultFuture.get();
            System.out.println(operationResult);

            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            group.shutdownGracefully();
        }
    }

}
