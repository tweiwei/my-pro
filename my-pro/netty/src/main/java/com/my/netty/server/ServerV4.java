package com.my.netty.server;

import com.my.netty.server.codec.OrderFrameDecoder;
import com.my.netty.server.codec.OrderFrameEncoder;
import com.my.netty.server.codec.OrderProtocolDecoder;
import com.my.netty.server.codec.OrderProtocolEncoder;
import com.my.netty.server.handler.MetricsHandler;
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
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.UnorderedThreadPoolEventExecutor;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;

/**
 * 无论是出栈handler，还是入栈handler，具体顺序怎么执行，是需要两个因素来决定，
 *  一个是当前handler所在的位置，
 *  二个就是当前handler的泛型，
 * 只有这两个条件同时满足，才会拦截到消息。
 *
 * 1、ctx.writeAndFlush只会从当前的handler位置开始，往前找ChannelOutboundHandler执行；
 * 2、ctx.pipeline().writeAndFlush与ctx.channel().writeAndFlush会从最开始的的位置，往前找ChannelOutboundHandler执
 */
@Slf4j
public class ServerV4 {

    public static void main(String[] args) throws CertificateException, SSLException {

        ServerBootstrap s = new ServerBootstrap();
        NioEventLoopGroup boss = new NioEventLoopGroup(1, new DefaultThreadFactory("boss"));
        NioEventLoopGroup worker = new NioEventLoopGroup(new DefaultThreadFactory("worker"));

        UnorderedThreadPoolEventExecutor businessGroup = new UnorderedThreadPoolEventExecutor(10, new DefaultThreadFactory("business"));

        LoggingHandler infoLogHandler = new LoggingHandler(LogLevel.INFO);

        MetricsHandler metricsHandler = new MetricsHandler();

        SelfSignedCertificate selfSignedCertificate = new SelfSignedCertificate();
        log.info("certificate position:"+selfSignedCertificate.certificate().toString());
        SslContext sslContext = SslContextBuilder.forServer(selfSignedCertificate.certificate(), selfSignedCertificate.privateKey()).build();

        try {
            s.channel(NioServerSocketChannel.class)
                .group(boss, worker)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();

                        pipeline.addLast("metricHandler", metricsHandler);

//                        pipeline.addLast("ssl", sslContext.newHandler(nioSocketChannel.alloc()));

                        pipeline.addLast("frameDecoder", new OrderFrameDecoder());
                        pipeline.addLast("protocolDecoder", new OrderProtocolDecoder());
                        pipeline.addLast("frameEncoder", new OrderFrameEncoder());
                        pipeline.addLast("protocolEncoder", new OrderProtocolEncoder());


                        pipeline.addLast("logHandler", infoLogHandler);

                        //业务逻辑需另外定义业务线程池处理
                        pipeline.addLast(businessGroup,"processHandler", new OrderServerProcessHandler());
                    }
                });

            int port = 8080;
            ChannelFuture channelFuture = s.bind(port).sync();
            channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
                public void operationComplete(Future<? super Void> future) throws Exception {
                    //启动成功后的处理
                    if (future.isSuccess()) {
                        log.info("服务器启动成功，Started Successed:" + port);
                    } else {
                        log.info("服务器启动失败，Started Failed:" + port);
                    }
                }
            });

            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }

}
