package com.my.netty.client;

import com.my.netty.client.codec.*;
import com.my.netty.common.auth.AuthOperation;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.TimeUnit;

public class ClientV1 {

    public static void main(String[] args){
        Bootstrap b = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup(new DefaultThreadFactory("worker"));

        try {
            b.channel(NioSocketChannel.class)
                .group(group)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
                        pipeline.addLast("frameDecoder", new OrderFrameDecoder());
                        pipeline.addLast("frameEncoder", new OrderFrameEncoder());
                        pipeline.addLast("protocolEncoder", new OrderProtocolEncoder());
                        pipeline.addLast("protocolDecoder", new OrderProtocolDecoder());

                        pipeline.addLast("logHandler", new LoggingHandler(LogLevel.INFO));

                        pipeline.addLast("operationToRequestMessageEncoder",new OperationToRequestMessageEncoder());
                    }
                });

            ChannelFuture channelFuture = b.connect("127.0.0.1",8080).sync();

            AuthOperation operation = new AuthOperation("admin","123");
            channelFuture.channel().writeAndFlush(operation).sync();

            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    /**
     * 功能描述: 断线重连，客户端有断线重连机制，就更不能使用异步阻塞了
     * @param
     * @return void
     * @author zhouwenjie
     * @date 2021/3/19 14:53
     */
    public void reconnect(Bootstrap bootstrap) {
        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1",8080);
        //使用最新的ChannelFuture -> 开启最新的监听器
        channelFuture.addListener((ChannelFutureListener) future -> {
            if (future.cause() != null) {
                System.out.println("连接失败。。。");
                future.channel().eventLoop().schedule(() -> reconnect(bootstrap), 3, TimeUnit.SECONDS);
            } else {
                System.out.println("客户端连接成功。。。");
            }
        });
    }

}
