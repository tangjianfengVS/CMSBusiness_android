package com.example.jianfeng.cmsbusiness_android.im.holder;

import com.example.jianfeng.cmsbusiness_android.im.HeartbeatManager;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.pythonsh.common.RawPacket;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * Created by oracle on 2017/10/20.
 */
public class MessagePongHolder extends MessageHolder {
    public MessagePongHolder(RawPacket rawPacket, HeartbeatManager counter) {
        super(rawPacket, counter);
    }

    @Override
    public ListenableFuture<Boolean> process() {
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
        return executorService.submit(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                getHeartbeatManager().increaseSuccess();
                return true;
            }
        });
    }
}
