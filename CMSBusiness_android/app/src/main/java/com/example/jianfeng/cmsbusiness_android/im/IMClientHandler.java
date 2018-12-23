package com.example.jianfeng.cmsbusiness_android.im;

import com.example.jianfeng.cmsbusiness_android.im.helper.ImHelper;
import com.example.jianfeng.cmsbusiness_android.im.holder.MessageHolder;
import com.example.jianfeng.cmsbusiness_android.im.holder.MessageHolderFactory;
import com.pythonsh.common.ApplicationException;
import com.pythonsh.common.RawPacket;
import com.pythonsh.common.adaptor.PingAdaptor;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;


/**
 * Created by oracle on 2017/10/8.
 */
public class IMClientHandler extends SimpleChannelInboundHandler<RawPacket> {

    private static final String TAG = "IMClientHandler";

    private final HeartbeatManager heartbeatManager;


    public IMClientHandler(HeartbeatManager heartbeatManager) {
        this.heartbeatManager = heartbeatManager;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RawPacket rawPacket) throws Exception {
        if (rawPacket == null) {

            return;
        }
        if (rawPacket.getOptrType() == 129) {
            ImHelper.ImSync();
        }
        MessageHolder holder = MessageHolderFactory.create(rawPacket, heartbeatManager);

        if (holder == null) return;

        processHolder(holder);

    }

    private void processHolder(final MessageHolder holder) {
        Futures.addCallback(holder.process(),
                new FutureCallback<Boolean>() {
                    public void onSuccess(Boolean aBoolean) {


                    }

                    public void onFailure(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // IdleStateHandler 所产生的 IdleStateEvent 的处理逻辑.
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case ALL_IDLE:
                    handleAllIdle(ctx);
                    break;
                default:
                    break;
            }
        }
    }


    protected void handleAllIdle(ChannelHandlerContext ctx) {

        if (heartbeatManager.shouldHeartbeat()) {
            sendHeartbeat(ctx);
        }

    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelInactive();
        //这里重连,但是需要设置一个调度任务,固定时间(避免网络不好时频繁重连)
        String ip = "222.66.158.238";
//        String ip = "10.33.5.223";


        int port = 61613;
        try {
            Thread.currentThread().sleep(2000);//阻断2秒
            ClientManager.doConnect(ip, port);
            //Logger.i(TAG, "重连");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.fireExceptionCaught(cause);
    }

    private void sendHeartbeat(ChannelHandlerContext ctx) {
        PingAdaptor ping = new PingAdaptor(null);
        try {
            ClientManager.write(ping);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

    }
}
