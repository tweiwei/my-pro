package com.my.netty.server.handler;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import io.netty.buffer.ByteBufAllocatorMetric;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocatorMetric;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


/**
 * ChannelDuplexHandler同时实现了ChannelInboundHandler和ChannelOutboundHandler接口，即既可以处理入栈事件和出栈事件
 */
@ChannelHandler.Sharable
public class MetricsHandler extends ChannelDuplexHandler {

    private AtomicLong totalConnectionNumber = new AtomicLong();

    private AtomicLong receiveDataNumber = new AtomicLong();

    {
        MetricRegistry metricRegistry = new MetricRegistry();
        metricRegistry.register("totalConnectionNumber", new Gauge<Long>() {

            @Override
            public Long getValue() {
                return totalConnectionNumber.longValue();
            }
        });

        metricRegistry.register("receiveDataNumber", new Gauge<Long>() {
            @Override
            public Long getValue() {
                return receiveDataNumber.longValue();
            }
        });

        metricRegistry.register("unPooledUsedDirectMemory", new Gauge<Long>() {
            @Override
            public Long getValue() {
                ByteBufAllocatorMetric byteBufAllocatorMetric = UnpooledByteBufAllocator.DEFAULT.metric();
                return byteBufAllocatorMetric.usedDirectMemory();
            }
        });

        metricRegistry.register("unPooledUsedHeapMemory", new Gauge<Long>() {
            @Override
            public Long getValue() {
                ByteBufAllocatorMetric byteBufAllocatorMetric = UnpooledByteBufAllocator.DEFAULT.metric();
                return byteBufAllocatorMetric.usedHeapMemory();
            }
        });

        metricRegistry.register("pooledUsedHeapMemory", new Gauge<Long>() {
            @Override
            public Long getValue() {
                PooledByteBufAllocatorMetric byteBufAllocatorMetric = PooledByteBufAllocator.DEFAULT.metric();
                return byteBufAllocatorMetric.usedHeapMemory();
            }
        });

        metricRegistry.register("pooledUsedDirectMemory", new Gauge<Long>() {
            @Override
            public Long getValue() {
                PooledByteBufAllocatorMetric byteBufAllocatorMetric = PooledByteBufAllocator.DEFAULT.metric();
                return byteBufAllocatorMetric.usedDirectMemory();
            }
        });

        ConsoleReporter consoleReporter = ConsoleReporter.forRegistry(metricRegistry).build();
        consoleReporter.start(1000, TimeUnit.SECONDS);

//        Slf4jReporter slf4jReporter = Slf4jReporter.forRegistry(metricRegistry).build();
//        slf4jReporter.start(15, TimeUnit.SECONDS);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        totalConnectionNumber.incrementAndGet();
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        totalConnectionNumber.decrementAndGet();
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        receiveDataNumber.incrementAndGet();
        super.channelRead(ctx, msg);
    }
}
