package com.example.jianfeng.cmsbusiness_android.im.holder;


import android.content.Intent;

//import com.admin.cmsandroid.ui.im.MainActivity;
//import com.admin.cmsandroid.base.MyApplication;
//import com.admin.cmsandroid.im.HeartbeatManager;
//import com.admin.cmsandroid.im.PayloadtoMap;
//import com.admin.cmsandroid.im.helper.ImHelper;
//import com.google.common.util.concurrent.ListenableFuture;
//import com.google.common.util.concurrent.ListeningExecutorService;
//import com.google.common.util.concurrent.MoreExecutors;
//import com.orhanobut.logger.Logger;
//import com.pythonsh.common.KeyBean;
//import com.pythonsh.common.RawPacket;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * Created by oracle on 2017/11/15.
 */
public class MessageInitHolder extends MessageHolder {
    private static final String TAG = "MessageInitHolder";

//    public MessageInitHolder(RawPacket rawPacket, HeartbeatManager heartbeatManager) {
//        super(rawPacket, heartbeatManager);
//    }
//
//    @Override
//    public ListenableFuture<Boolean> process() {
//        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
//        return executorService.submit(new Callable<Boolean>() {
//            public Boolean call() throws Exception {
//                PayloadtoMap payloadtoMap = new PayloadtoMap(rawPacket);
//                KeyBean bean = new KeyBean();
//                bean.setSecureId(MainActivity.secureid);
//                bean.setRandomKey(MainActivity.randomkey);
//                payloadtoMap.setKeyMap(bean.toMap());
//                Intent intent = new Intent();
//                intent.setAction("com.admin.cmsandroid.init");
//                try {
//                    payloadtoMap.preRead(null);
//                    Logger.i(TAG, payloadtoMap.getPayloadMap().toString());
//                    intent.putExtra("init", (Serializable) payloadtoMap.getPayloadMap());
//
//                } catch (Exception NullPointerException) {
//                    ImHelper.Iminit(MyApplication.getContext());
//                    intent.putExtra("msg", "网络请求超时");
//                }
//                intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
//                MyApplication.getContext().sendBroadcast(intent);
//
////                if (payloadtoMap.getPayloadMap().containsKey("SupAlias")) {
////                    intent.putExtra("init", (Serializable) payloadtoMap.getPayloadMap().get("SupAlias"));
////                } else {
////                    intent.putExtra("msg", "1");
////                }
////                intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
////
////                MyApplication.getContext().sendBroadcast(intent);
////                Logger.i(TAG, payloadtoMap.getPayloadMap().toString());
////                if (payloadtoMap.getPayloadMap().containsKey("User")) {
////
//////                    HashMap map = (HashMap) CodeUtils.transStringToMap(payloadtoMap.getPayloadMap().get("User").toString().substring(1, payloadtoMap.getPayloadMap().get("User").toString().length() - 1));
////                    MainActivity.keys = (List<Map>) payloadtoMap.getPayloadMap().get("SyncKey");
////                    Map user = (Map) (payloadtoMap.getPayloadMap().get("User"));
////                    MainActivity.username = user.get("Username").toString();
////                    MainActivity.userid_base64 = user.get("UserID").toString();
////                    MainActivity.mobile = user.get("Mobile").toString();
////                    MainActivity.NickName = user.get("NickName").toString();
////                    MainActivity.UserHeader = user.get("UserHeader").toString();
////                    MainActivity.Identification = user.get("Identification").toString();
////                    MainActivity.Email = user.get("Email").toString();
////                    MainActivity.Address = user.get("Address").toString();
////
////                    if (payloadtoMap.getPayloadMap().get("ContactList").toString().length() > 2) {
////                        MainActivity.ContactList = (List) payloadtoMap.getPayloadMap().get("ContactList");
////                    }
////                    if (MainActivity.isStart == false) {
////                        MainActivity.isStart = true;
////                        ImHelper.ImSync();
////                    }
////                }
//                //执行一次接收
//                return true;
//            }
//        });
//    }
}
