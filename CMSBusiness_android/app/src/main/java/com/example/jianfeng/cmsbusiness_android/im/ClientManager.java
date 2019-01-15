package com.example.jianfeng.cmsbusiness_android.im;

import com.example.jianfeng.cmsbusiness_android.im.helper.ImResultHandler;
import com.pythonsh.common.ApplicationException;
import com.pythonsh.common.Constant;
import com.pythonsh.common.RawPacketDecoder;
import com.pythonsh.common.adaptor.base.AdaptorContext;
import com.pythonsh.common.adaptor.base.BaseAdaptor;
import com.pythonsh.common.adaptor.base.OutboundAdaptor;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;


/**
 * Created by oracle on 2017/10/11.
 * 使用说明:
 * 1. 创建client时,启动主进程等待服务器端返回
 * 2. 服务器返回时,根据返回的类型执行相应操作
 * 3. 程序获取当前客户端并创建对应的channel进行发送
 */
public class ClientManager {
    private static final Bootstrap bootstrap = new Bootstrap();

    private static ChannelFutureListener channelFutureListener = null;

    private static NioEventLoopGroup workGroup = new NioEventLoopGroup(4);

    private static ChannelFuture future = null;

    private static HeartbeatManager heartbeatManager = new HeartbeatManager();


    private SocketChannel socketChannel;

    public static void start(final String serverIp, final int port, final ImResultHandler handler) throws InterruptedException {
        bootstrap
                .group(workGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .handler(new ChannelInitializer<SocketChannel>() {

                    /** 结果处理调用设置 */
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline p = socketChannel.pipeline();
                        p.addLast(new IdleStateHandler(0, 0, HeartbeatManager.HEARTBEAT_MIN));
                        p.addLast(new LengthFieldBasedFrameDecoder(10 * 1024 * 1024, 0, 4, -4, 0));
                        p.addLast(new RawPacketDecoder());
                        p.addLast(new IMClientHandler(heartbeatManager)); //设置回调结果对象
                    }
                });

        channelFutureListener = new ChannelFutureListener() {
            public void operationComplete(ChannelFuture f) throws Exception {
                if (f.isSuccess()) {
                    //连接成功
                    handler.connect(true);
                } else {
                    handler.connect(false);
                    //连接失败，重新连接
                    f.channel().eventLoop().schedule(new Runnable() {
                        @Override
                        public void run() {
                            doConnect(serverIp, port);
                        }
                    }, 3, TimeUnit.SECONDS);
                }
            }
        };

        doConnect(serverIp, port);
    }

    /** 发起连接 */
    public static void doConnect(String serverIP, int port) {

        try {
            future = bootstrap.connect(new InetSocketAddress(
                    serverIP, port));
            future.addListener(channelFutureListener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Channel getChannel() throws InterruptedException {
        return future.sync().channel();
    }

    /** 消息write */
    public static void write(BaseAdaptor base) throws InterruptedException, ApplicationException {
        if (base.isInitialBeforeWrite())
            base.checkHeaderIfInitialized();

        ((OutboundAdaptor)base).preWrite(null);
        AdaptorContext context = new AdaptorContext(getChannel(),null, null, base);
            try {
                base.writeAndFlush(context);

                /** 心跳包 */
                if (base.getRawPacket().getOptrType() == Constant.TYPE_PING) {
                    heartbeatManager.increaseTotal();
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
