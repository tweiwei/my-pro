package com.my.netty.server;

import com.my.netty.server.codec.OrderFrameDecoder;
import com.my.netty.server.codec.OrderFrameEncoder;
import com.my.netty.server.codec.OrderProtocolDecoder;
import com.my.netty.server.codec.OrderProtocolEncoder;
import com.my.netty.server.handler.OrderServerProcessHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * 无论是出栈handler，还是入栈handler，具体顺序怎么执行，是需要两个因素来决定，
 *  一个是当前handler所在的位置，
 *  二个就是当前handler的泛型，
 * 只有这两个条件同时满足，才会拦截到消息。
 *
 * 1、ctx.writeAndFlush只会从当前的handler位置开始，往前找ChannelOutboundHandler执行；
 * 2、ctx.pipeline().writeAndFlush与ctx.channel().writeAndFlush会从最开始的的位置，往前找ChannelOutboundHandler执
 */
public class ServerV0 {

    public static void main(String[] args){

        ServerBootstrap s = new ServerBootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            s.channel(NioServerSocketChannel.class)
                .group(group)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
                        pipeline.addLast(new OrderFrameDecoder());
                        pipeline.addLast(new OrderFrameEncoder());
                        pipeline.addLast(new OrderProtocolEncoder());
                        pipeline.addLast(new OrderProtocolDecoder());

                        pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));

                        pipeline.addLast(new OrderServerProcessHandler());
                    }
                });

            int port = 8080;
            ChannelFuture channelFuture = s.bind(port).sync();
            channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
                public void operationComplete(Future<? super Void> future) throws Exception {
                    //启动成功后的处理
                    if (future.isSuccess()) {
                        System.out.println("服务器启动成功，Started Successed:" + port);
                    } else {
                        System.out.println("服务器启动失败，Started Failed:" + port);
                    }
                }
            });

            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            group.shutdownGracefully();
        }

    }

}
