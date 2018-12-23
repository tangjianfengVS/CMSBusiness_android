package com.example.jianfeng.cmsbusiness_android.im.holder;


import android.content.Intent;

import com.example.jianfeng.cmsbusiness_android.im.HeartbeatManager;
import com.example.jianfeng.cmsbusiness_android.im.PayloadtoMap;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.pythonsh.common.KeyBean;
import com.pythonsh.common.RawPacket;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * Created by oracle on 2017/10/11.
 */
public class MessageSyncHolder extends MessageHolder {

    private static final String TAG = "MessageSyncHolder";

    public MessageSyncHolder(RawPacket rawPacket, HeartbeatManager counter) {
        super(rawPacket, counter);
    }

    @Override
    public ListenableFuture<Boolean> process() {
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
        return executorService.submit(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                Intent intent = new Intent();
                intent.setAction("sync");
                PayloadtoMap payloadtoMap = new PayloadtoMap(rawPacket);
                KeyBean bean = new KeyBean();

                //-----------我注释-------
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
//                Map map = payloadtoMap.getPayloadMap();
////                Map map1 = new HashMap();
////                Map map2 = new HashMap();
////                map1.put("a","b");
////                map2.put("option","batch");
////                map2.put("data",map1);
////                ImHelper.ImInvokeThrift("20171208112330","factoryDetailManage",map2);
////                Logger.i(TAG, map.toString());
//                if (map.size() > 1) {
//                    if (map.get("Msg").toString().length() > 2) {
//                        MainActivity.keys = (List) map.get("SyncKey");
//                        intent.putExtra("Imsync", map.get("Msg").toString());
//                        intent.putExtra("Imsyncmap", (Serializable) payloadtoMap.getPayloadMap());
//                        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
//                        MyApplication.getContext().sendBroadcast(intent);
//                    }
//                }

                //---------------
                return true;

            }
        });
    }
}
