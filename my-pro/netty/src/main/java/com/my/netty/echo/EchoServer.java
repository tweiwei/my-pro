package com.my.netty.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class EchoServer {

    public static void main(String[] args){

        EventLoopGroup group = new NioEventLoopGroup();

        final EchoServerHandler handler = new EchoServerHandler();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                            pipeline.addLast(handler);
                        }
                    });


            ChannelFuture f = b.bind(8080).sync();
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            group.shutdownGracefully();
        }
    }

}
