package com.example.jianfeng.cmsbusiness_android.im.holder;


import android.content.Intent;

//import com.admin.cmsandroid.base.MyApplication;
//import com.admin.cmsandroid.im.HeartbeatManager;
//import com.admin.cmsandroid.im.PayloadtoMap;
//import com.admin.cmsandroid.im.helper.ImHelper;
//import com.admin.cmsandroid.ui.im.MainActivity;
//import com.google.common.util.concurrent.ListenableFuture;
//import com.google.common.util.concurrent.ListeningExecutorService;
//import com.google.common.util.concurrent.MoreExecutors;
//import com.orhanobut.logger.Logger;
//import com.pythonsh.common.KeyBean;
//import com.pythonsh.common.RawPacket;
//
//import java.io.Serializable;
//import java.util.concurrent.Callable;
//import java.util.concurrent.Executors;

/**
 * Created by oracle on 2017/10/11.
 */
public class MessageSendHolder extends MessageHolder {

    private static final String TAG = "MessageSendHolder";

//    public MessageSendHolder(RawPacket rawPacket, HeartbeatManager counter) {
//        super(rawPacket, counter);
//    }
//
//    @Override
//    public ListenableFuture<Boolean> process() {
//        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
//        return executorService.submit(new Callable<Boolean>() {
//            public Boolean call() throws Exception {
//                Intent intent = new Intent();
//                intent.setAction("send");
//                PayloadtoMap payloadtoMap = new PayloadtoMap(rawPacket);
//                KeyBean bean = new KeyBean();
//                bean.setSecureId(MainActivity.secureid);
//                bean.setRandomKey(MainActivity.randomkey);
//                payloadtoMap.setKeyMap(bean.toMap());
//                try {
//                    payloadtoMap.preRead(null);
//                } catch (Exception NullPointerException) {
//                    intent.putExtra("msg", "网络请求超时");
//                    intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
//                    MyApplication.getContext().sendBroadcast(intent);
//                }
//                Logger.i(TAG, payloadtoMap.getPayloadMap() + "");
//                intent.putExtra("send", "申请成功");
//                intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
//                MyApplication.getContext().sendBroadcast(intent);
//                //执行一次接收
//                return true;
//            }
//        });
//    }
}
