package com.example.jianfeng.cmsbusiness_android.im.holder;

import android.content.Intent;

//import com.admin.cmsandroid.base.MyApplication;
//import com.admin.cmsandroid.im.HeartbeatManager;
//import com.admin.cmsandroid.im.PayloadtoMap;
//import com.admin.cmsandroid.im.helper.ImHelper;
//import com.admin.cmsandroid.ui.im.MainActivity;
//import com.admin.cmsandroid.utils.UnicodeUtils;
//import com.google.common.util.concurrent.ListenableFuture;
//import com.google.common.util.concurrent.ListeningExecutorService;
//import com.google.common.util.concurrent.MoreExecutors;
//import com.pythonsh.common.KeyBean;
//import com.pythonsh.common.RawPacket;
//import com.orhanobut.logger.Logger;
//
//import org.json.JSONObject;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * Created by duantao
 *
 * @date on 2017/12/6 20:27
 */
public class MessageInvokeHolder extends MessageHolder {
    private static final String TAG = "MessageInvokeHolder";

//    public MessageInvokeHolder(RawPacket rawPacket, HeartbeatManager heartbeatManager) {
//        super(rawPacket, heartbeatManager);
//    }
//
//
//    @Override
//    public ListenableFuture<Boolean> process() {
//        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
//        return executorService.submit(new Callable<Boolean>() {
//            public Boolean call() throws Exception {
//                Intent intent = new Intent();
//                intent.setAction("invoke");
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
//                Logger.i(TAG, payloadtoMap.getPayloadMap().toString());
//                if (((Map) payloadtoMap.getPayloadMap().get("BaseResponse")).get("Ret").toString().equals("0")) {
//                    if (payloadtoMap.getPayloadMap().get("InvokeList") != null || !payloadtoMap.getPayloadMap().get("InvokeList").equals("")) {
//                        intent.putExtra("Invoke", (Serializable) payloadtoMap.getPayloadMap());
//                        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
//                        MyApplication.getContext().sendBroadcast(intent);
//                    } else {
//                        intent.putExtra("msg", "请求失败");
//                        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
//                        MyApplication.getContext().sendBroadcast(intent);
//                    }
//                } else {
//                    intent.putExtra("msg", "网络异常，请重新加载");
//                    intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
//                    MyApplication.getContext().sendBroadcast(intent);
//                }
////                Map map = (Map)payloadtoMap.getPayloadMap().get("InvokeList");
////                Map projectsBeans = (Map) map.get("buzOnlinePricingProject");
////                List<PriProBidBean> priProBidBeans = (List)map.get("buzOnlinePriProBid");
////                List<PriProBidSuBean> priProBidSuBeans = (List)map.get("buzOnlinePriProBidSup");
////                List<DetailBean> detailBeans = (List)map.get("factoryDetail");
////                List<Map> a = new ArrayList<>();
////                Logger.i(TAG,projectsBeans.toString());
////                projectsBeans.put("status","y");
////                a.add(projectsBeans);
////                ImHelper.ImInvokeSeed("2A3874019EFFA0CEE05001A8A90623EF", "gatApprove", a);
//
////                Logger.i(TAG, detailBeans.get(0).toString()+"===="+priProBidSuBeans.get(0).toString()+"===="+priProBidBeans.get(0).toString()+"===="+projectsBeans.get(0).toString());
//
//                //执行一次接收
//                return true;
//            }
//        });
//    }
}
