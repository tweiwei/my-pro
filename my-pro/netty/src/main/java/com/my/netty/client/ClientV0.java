package com.my.netty.client;

import com.my.netty.client.codec.OrderFrameDecoder;
import com.my.netty.client.codec.OrderFrameEncoder;
import com.my.netty.client.codec.OrderProtocolDecoder;
import com.my.netty.client.codec.OrderProtocolEncoder;
import com.my.netty.common.RequestMessage;
import com.my.netty.common.auth.AuthOperation;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class ClientV0 {

    public static void main(String[] args){
        Bootstrap b = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            b.channel(NioSocketChannel.class)
                .group(group)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
                        pipeline.addLast(new OrderFrameDecoder());
                        pipeline.addLast(new OrderProtocolDecoder());

                        pipeline.addLast(new OrderFrameEncoder());
                        pipeline.addLast(new OrderProtocolEncoder());

                        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                    }
                });


            ChannelFuture channelFuture = b.connect("127.0.0.1",8080).sync();

            RequestMessage requestMessage = new RequestMessage(1000, new AuthOperation("admin","123"));
            channelFuture.channel().writeAndFlush(requestMessage);

            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            group.shutdownGracefully();
        }
    }

}
