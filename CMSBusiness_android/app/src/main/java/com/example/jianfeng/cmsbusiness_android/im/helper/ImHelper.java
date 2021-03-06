package com.example.jianfeng.cmsbusiness_android.im.helper;

import android.content.Context;
import android.content.Intent;

import com.example.jianfeng.cmsbusiness_android.im.ClientManager;
import com.example.jianfeng.cmsbusiness_android.loginInfo.CMSUseinfo;
import com.example.jianfeng.cmsbusiness_android.rootMain.CMSRootMainActivity;
import com.pythonsh.common.ApplicationException;
import com.pythonsh.common.ClientVersion;
import com.pythonsh.common.Constant;
import com.pythonsh.common.KeyBean;
import com.pythonsh.common.adaptor.IMInitAdaptor;
import com.pythonsh.common.adaptor.SyncAdaptor;
import com.pythonsh.common.codec.coder.Encoder;
import com.pythonsh.common.codec.compressor.Compressor;
import com.pythonsh.common.payload.BasePayloadMap;
import com.pythonsh.common.payload.pojos.BaseRequest;
import com.pythonsh.common.payload.pojos.PojosConstants;
import com.pythonsh.common.utils.BytesUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Created by dt on 2017/12/20.
 */
public class ImHelper {
    private String ip = "222.66.158.238";

    private int port = 61613;

    private static final String TAG = "ImHelper";

//    private static String skey = "";
//
//    private static String deviceid = "";
//
//    private static String uin = "";

    public static int major = 2;

    public static int minor = 3;

    public static int build = 16;

//    //查询报价内容 查询审批内容
//    public static String ThriftServiceId = "20171208112330";
//    //提交报价  提交绑定公司
//    public static String SeedServiceId = "2A3874019EFFA0CEE05001A8A90623EF";
//    //入库
//    public static String StorageServiceId = "2CE5DEB098T7RF54E05001A8A90612CA";
//    //质检
//    public static String CheckServiceId = "2BCB1B13DF7727E5E05001A8A906271B";
//    //条码
//    public static String BarcodeServiceid = "20180124100540";
//    //    DEFAULT_MODE
//    //private static byte DEFAULT_MODE = Constant.DEFAULT_MODE;
    private static byte DEFAULT_MODE = Constant.MODE_PRO;

    private static ImHelper instance = null;

    private ImHelper(){}

    public static ImHelper shared(){
        if (instance == null){
            instance = new ImHelper();
        }
        return instance;
    }

    /** 开启连接 */
    public void connect(final Context context){
        try {
            //开启连接
            ClientManager.start(ip, port, new ImResultHandler() {

                @Override
                public void connect(Boolean result) {
                    if (result){
                        Map<String, String> contacts1 = new HashMap<String, String>();
                        contacts1.put("UserID","@9f0edbaceb86d51228a9696bf0b72242591d8b2c014b2e33de95c2cf9cf40669");
                        contacts1.put("NickName","咔咔咔咔咔");
                        contacts1.put("PYQuanPin","18501623520");
                        contacts1.put("ContactFlag","1");
                        contacts1.put("Username","18501623520");
                        contacts1.put("UserHeader","1938e4483a001ce499114bdb8dd62f71");
                        contacts1.put("Mobile","18501623520");

                        Map<String, String> contacts2 = new HashMap<String, String>();
                        contacts2.put("UserID","@9b4380f48a458dc186f188b7fcdd32e56aea268762f82b316c0d3ec8cabd932c");
                        contacts2.put("NickName","王晓君");
                        contacts2.put("PYQuanPin","wxj");
                        contacts2.put("ContactFlag","1");
                        contacts2.put("Username","wxj");
                        contacts2.put("UserHeader","f5afd6c814b9e8779de15dd7b5592245");
                        contacts2.put("Mobile","13601943848");

                        Map<String, String> contacts3 = new HashMap<String, String>();
                        contacts3.put("UserID","@62e55189f99d592dbd7673f216dfcbbce1f76f36c28159735e33356b3c101eb6");
                        contacts3.put("NickName","YYLYG");
                        contacts3.put("PYQuanPin","yinyong");
                        contacts3.put("ContactFlag","1");
                        contacts3.put("Username","尹勇");
                        contacts3.put("UserHeader","None");
                        contacts3.put("Mobile","18396451625");

                        Map<String, String> contacts4 = new HashMap<String, String>();
                        contacts4.put("UserID","@10a1b48656df1777c4b9fe38829986b42986911b73910ae0cdf48d4bcf53210c");
                        contacts4.put("NickName","须佳薇连云港");
                        contacts4.put("PYQuanPin","xujiawei");
                        contacts4.put("ContactFlag","1");
                        contacts4.put("Username","须佳薇");
                        contacts4.put("UserHeader","21cb86a703d45e6affb3d2acc748dbdb");
                        contacts4.put("Mobile","13916161111");

                        List list = new ArrayList<>();
                        list.add(contacts1);
                        list.add(contacts2);
                        list.add(contacts3);
                        list.add(contacts4);

                        Map<String, List> params = new HashMap<String, List>();
                        params.put("ContactList",list);

                        Intent intent = new Intent();
                        intent.setAction("com.admin.cmsbusiness_android.addressbook");
                        intent.putExtra("ContactList", (Serializable) params);
                        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                        CMSRootMainActivity.getContext().sendBroadcast(intent);
                        //imInit(context);
                    }
                }
            });
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    /** 主动断开连接 */
    public void disconnect(){


    }

    /** 初始化 */
    private void imInit(Context context) {

        if (isNetworkConnected(context) == true) {
            CMSUseinfo info = CMSUseinfo.shared();
            String skeyS = "df97935c9c72eaa7e5abdbfb94ee6c9a";//CMSUseinfo.shared().skey;
            String uinS = "11971";//CMSUseinfo.shared().uin;
            String sidS = "e5arwcwtw1by2nmy";//CMSUseinfo.shared().sid;
            String seqidS = "388";//CMSUseinfo.shared().seqId;
            String randomkeyS = "q4hynprjkoq1in4z";//CMSUseinfo.shared().randomKey;
            String deviceid = "f102c6d5a8bfe136-K258XWSWUv5p";//CMSUseinfo.shared().deviceId;

//            MainActivity.M1 = (String) user.get("token");
//            MainActivity.sid = (String) user.get("sid");
//            MainActivity.randomkey = (String) user.get("randomkey");
//            MainActivity.secureid = Integer.parseInt(user.get("seqid").toString());
//            deviceid = "-" + RandomUtil.getStringRandom(12);
            //Logger.i("deviceid", "user----" + user.toString());
            BaseRequest r = new BaseRequest();
            r.setUin(uinS);
            r.setSkey(skeyS);
            r.setDeviceID(deviceid);
            r.setSid(sidS);
            Map payloadss = new BasePayloadMap();

            KeyBean bean = new KeyBean();
            bean.setSecureId(Integer.parseInt(seqidS));
            bean.setRandomKey(randomkeyS);
            payloadss.put(PojosConstants.PAYLOAD_BASEREQUEST, r);
            payloadss.put("ClientVersion", new ClientVersion(major, minor, build).getVersion());

            IMInitAdaptor p = new IMInitAdaptor(null);
            p.initHeader(Constant.HEADER_SECUREID, BytesUtils.intToByteArray(bean.getSecureId()));
            p.initHeader(Constant.HEADER_ENCODER, BytesUtils.shortToByteArray(Encoder.ENCODE_AES128));
            p.initHeader(Constant.HEADER_COMPRESSOR, BytesUtils.shortToByteArray(Compressor.COMPRESSOR_LZ4));
            p.initHeader(Constant.HEADER_MODE, new byte[]{DEFAULT_MODE});
            p.initOption(Constant.OPTION_ENCODER, true);
            p.initOption(Constant.OPTION_COMPRESSOR, true);
            p.setKeyMap(bean.toMap());
            p.setPayloadMap(payloadss);

            try {
                ClientManager.write(p);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ApplicationException e) {
                e.printStackTrace();
            }
        } else {
            //ToolAlert.showToast(context, "当前无网络，请连接网络!", false);
        }
    }

    //服务调用seed
//    public static void ImInvokeSeed(String method, List<Map> list, String serviceId, Context context) {
//        if (isNetworkConnected(context) == true) {
//
//            BaseRequest base = new BaseRequest();
//            base.setUin(uin);
//            base.setSkey(skey);
//            base.setDeviceID(deviceid);
//            base.setSid(MainActivity.sid);
//
//            KeyBean b = new KeyBean();
//            b.setSecureId(MainActivity.secureid);
//            b.setRandomKey(MainActivity.randomkey);
//
//            Map payloads = new BasePayloadMap();
//            payloads.put(PojosConstants.PAYLOAD_BASEREQUEST, base);
//
//            //微服务调用
//            payloads.put("ServiceID", serviceId);
//            payloads.put("Method", method);
////        Map map = new HashMap();
////				map.put("option", "batcha");
////        List<Map> lists = new ArrayList<>();
////        map.put("onlinePriProBidId", "8a91ebf76049bc30016049c6dfd80001");
////        lists.add(map);
////			params.put("data", list);
//            payloads.put("Params", list);
//            payloads.put("Path", "/common.json-rpc");
////        payloads.put("Path", "/bdPreEntry.json-rpc");
//            InvokeAdaptor f = new InvokeAdaptor(null);
////				IMInitAdaptor p = new IMInitAdaptor(null);
//            f.initHeader(Constant.HEADER_SECUREID, BytesUtils.intToByteArray(b.getSecureId()));
//            f.initHeader(Constant.HEADER_ENCODER, BytesUtils.shortToByteArray(Encoder.ENCODE_AES128));
//            f.initHeader(Constant.HEADER_COMPRESSOR, BytesUtils.shortToByteArray(Compressor.COMPRESSOR_LZ4));
//            f.initHeader(Constant.HEADER_MODE, new byte[]{DEFAULT_MODE});
//            f.initHeader("stype", new byte[]{Constant.SERVICE_TYPE_SEED});
//            f.initOption(Constant.OPTION_ENCODER, true);
//            f.initOption(Constant.OPTION_COMPRESSOR, true);
//            f.setKeyMap(b.toMap());
//            f.setPayloadMap(payloads);
//            try {
//                ClientManager.write(f);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ApplicationException e) {
//                e.printStackTrace();
//            }
//        } else {
//            ToolAlert.showToast(context, "当前无网络，请连接网络!", false);
//        }
//    }

    //服务调用thrift
//    public static void ImInvokeThrift(String method, String option, List list, String serviceId, Context context) {
//        if (isNetworkConnected(context) == true) {
//            if (uin.equals("")) {
//                final Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(intent);
//            }
//            BaseRequest base = new BaseRequest();
//            base.setUin(uin);
//            base.setSkey(skey);
//            base.setDeviceID(deviceid);
//            base.setSid(MainActivity.sid);
//
//            KeyBean b = new KeyBean();
//            b.setSecureId(MainActivity.secureid);
//            b.setRandomKey(MainActivity.randomkey);
//
//            Map payloads = new BasePayloadMap();
//            payloads.put(PojosConstants.PAYLOAD_BASEREQUEST, base);
//
//            //微服务调用
//            Map map = new HashMap();
//            map.put("option", option);
//            map.put("data", list);
//
//            payloads.put("ServiceID", serviceId);
//            payloads.put("Method", method);
//            payloads.put("Params", map);
//            payloads.put("Path", "");
//            payloads.put("Tag", option);
//
//            InvokeAdaptor f = new InvokeAdaptor(null);
//            f.initHeader(Constant.HEADER_SECUREID, BytesUtils.intToByteArray(b.getSecureId()));
//            f.initHeader(Constant.HEADER_ENCODER, BytesUtils.shortToByteArray(Encoder.ENCODE_AES128));
//            f.initHeader(Constant.HEADER_COMPRESSOR, BytesUtils.shortToByteArray(Compressor.COMPRESSOR_LZ4));
//            f.initHeader(Constant.HEADER_MODE, new byte[]{DEFAULT_MODE});
//            f.initHeader("stype", new byte[]{Constant.SERVICE_TYPE_THRIFT});
//            f.initOption(Constant.OPTION_ENCODER, true);
//            f.initOption(Constant.OPTION_COMPRESSOR, true);
//            f.setKeyMap(b.toMap());
//            f.setPayloadMap(payloads);
//            try {
//                ClientManager.write(f);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ApplicationException e) {
//                e.printStackTrace();
//            }
//        } else {
//            ToolAlert.showToast(context, "当前无网络，请连接网络!", false);
//        }
//    }
//
    //消息获取
    public static void ImSync() {
//        if (!skey.equals("")) {
//            if (MainActivity.keys != null && MainActivity.keys.size() > 0) {
//                if (!MainActivity.keys.get(0).get("Val").toString().equals("-1")) {

        String skeyS = CMSUseinfo.shared().skey;
        String uinS = CMSUseinfo.shared().uin;
        String sidS = CMSUseinfo.shared().sid;
        String seqidS = CMSUseinfo.shared().seqId;
        String randomkeyS = CMSUseinfo.shared().randomKey;
        String deviceid = CMSUseinfo.shared().deviceId;
        int secureidS = 123;//CMSUseinfo.shared().secureId;

                    BaseRequest aaa = new BaseRequest();
                    aaa.setUin(uinS);
                    aaa.setSkey(skeyS);
                    aaa.setDeviceID(deviceid);
                    aaa.setSid(sidS);

                    KeyBean bbb = new KeyBean();
                    bbb.setSecureId(secureidS);//MainActivity.secureid);
                    bbb.setRandomKey(randomkeyS);

                    Map payload = new BasePayloadMap();
                    payload.put(PojosConstants.PAYLOAD_BASEREQUEST, aaa);
                    payload.put(PojosConstants.PAYLOAD_SYNCKEY, skeyS);
                    payload.put("ClientVersion", new ClientVersion(major, minor, build).getVersion());

                    SyncAdaptor d = new SyncAdaptor(null);
                    d.initHeader(Constant.HEADER_SECUREID, BytesUtils.intToByteArray(bbb.getSecureId()));
                    d.initHeader(Constant.HEADER_ENCODER, BytesUtils.shortToByteArray(Encoder.ENCODE_AES128));
                    d.initHeader(Constant.HEADER_COMPRESSOR, BytesUtils.shortToByteArray(Compressor.COMPRESSOR_LZ4));
                    d.initHeader(Constant.HEADER_MODE, new byte[]{DEFAULT_MODE});
                    d.initOption(Constant.OPTION_ENCODER, true);
                    d.initOption(Constant.OPTION_COMPRESSOR, true);
                    d.setKeyMap(bbb.toMap());
                    d.setPayloadMap(payload);
                    try {
                        ClientManager.write(d);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ApplicationException e) {
                        e.printStackTrace();
                    }
//                }
//            }
//        }
    }


    //发送消息
//    public static void ImSend(String content, String to, Context context) {
//        if (isNetworkConnected(context) == true) {
//            if (!skey.equals("")) {
//                BaseRequest request = new BaseRequest();
//                request.setUin(uin);
//                request.setSkey(skey);
//                request.setDeviceID(deviceid);
//                request.setSid(MainActivity.sid);
//
//                KeyBean key = new KeyBean();
//                key.setSecureId(MainActivity.secureid);
//                key.setRandomKey(MainActivity.randomkey);
////              int a = (int)(Math.random() * (9999 - 1000 +1))+1000;
//
//                MessageText messagetext = new MessageText(MainActivity.userid_base64, to);
//                messagetext.setContent(content);
//                messagetext.setShowName(MainActivity.NickName);
//
//                Map payload = new BasePayloadMap();
//                payload.put(PojosConstants.PAYLOAD_BASEREQUEST, request);
//                payload.put("Msg", messagetext);
//                payload.put("ClientVersion", new ClientVersion(major, minor, build).getVersion());
//                payload.put("Path", "/common.json-rpc");
//
//                SendMsgAdaptor sendMsgAdaptor = new SendMsgAdaptor(null);
//                sendMsgAdaptor.initHeader(Constant.HEADER_SECUREID, BytesUtils.intToByteArray(key.getSecureId()));
//                sendMsgAdaptor.initHeader(Constant.HEADER_ENCODER, BytesUtils.shortToByteArray(Encoder.ENCODE_AES128));
//                sendMsgAdaptor.initHeader(Constant.HEADER_COMPRESSOR, BytesUtils.shortToByteArray(Compressor.COMPRESSOR_LZ4));
//                sendMsgAdaptor.initHeader(Constant.HEADER_MODE, new byte[]{DEFAULT_MODE});
//                sendMsgAdaptor.initOption(Constant.OPTION_ENCODER, true);
//                sendMsgAdaptor.initOption(Constant.OPTION_COMPRESSOR, true);
//                sendMsgAdaptor.setKeyMap(key.toMap());
//                sendMsgAdaptor.setPayloadMap(payload);
//                try {
//                    ClientManager.write(sendMsgAdaptor);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ApplicationException e) {
//                    e.printStackTrace();
//                }
//            }
//        } else {
//            ToolAlert.showToast(context, "当前无网络，请连接网络!", false);
//        }
//    }


    //添加好友
//    public static void ImAdd(String to, int status, Context context) {
//        if (isNetworkConnected(context) == true) {
//            if (!skey.equals("")) {
//                BaseRequest request = new BaseRequest();
//                request.setUin(uin);
//                request.setSkey(skey);
//                request.setDeviceID(deviceid);
//                request.setSid(MainActivity.sid);
//
//                KeyBean key = new KeyBean();
//                key.setSecureId(MainActivity.secureid);
//                key.setRandomKey(MainActivity.randomkey);
//                ContactEvent contactEvent = new ContactEvent(MainActivity.userid_base64, to);
//                contactEvent.setStatus(status);
//                contactEvent.setShowName(MainActivity.NickName);
//                contactEvent.setAliasId(MainActivity.userid_base64);
//                contactEvent.setMobile(MainActivity.mobile);
//                contactEvent.setName(MainActivity.username);
//                Map payload = new BasePayloadMap();
//                payload.put(PojosConstants.PAYLOAD_BASEREQUEST, request);
//                payload.put("Msg", contactEvent);
//                payload.put("ClientVersion", new ClientVersion(major, minor, build).getVersion());
//                payload.put("Path", "/common.json-rpc");
//
//                SendMsgAdaptor sendMsgAdaptor = new SendMsgAdaptor(null);
//                sendMsgAdaptor.initHeader(Constant.HEADER_SECUREID, BytesUtils.intToByteArray(key.getSecureId()));
//                sendMsgAdaptor.initHeader(Constant.HEADER_ENCODER, BytesUtils.shortToByteArray(Encoder.ENCODE_AES128));
//                sendMsgAdaptor.initHeader(Constant.HEADER_COMPRESSOR, BytesUtils.shortToByteArray(Compressor.COMPRESSOR_LZ4));
//                sendMsgAdaptor.initHeader(Constant.HEADER_MODE, new byte[]{DEFAULT_MODE});
//                sendMsgAdaptor.initOption(Constant.OPTION_ENCODER, true);
//                sendMsgAdaptor.initOption(Constant.OPTION_COMPRESSOR, true);
//                sendMsgAdaptor.setKeyMap(key.toMap());
//                sendMsgAdaptor.setPayloadMap(payload);
//                try {
//                    ClientManager.write(sendMsgAdaptor);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ApplicationException e) {
//                    e.printStackTrace();
//                }
//            }
//        } else {
//            ToolAlert.showToast(context, "当前无网络，请连接网络!", false);
//        }
//    }
//

    //发图片
//    public static void ImImage(String to, String picUrl, Context context) {
//        if (isNetworkConnected(context) == true) {
//
//            if (!skey.equals("")) {
//                BaseRequest request = new BaseRequest();
//                request.setUin(uin);
//                request.setSkey(skey);
//                request.setDeviceID(deviceid);
//                request.setSid(MainActivity.sid);
//
//                KeyBean key = new KeyBean();
//                key.setSecureId(MainActivity.secureid);
//                key.setRandomKey(MainActivity.randomkey);
//
//                MessageImage msg = new MessageImage(MainActivity.userid_base64, to);
//                msg.setPicUrl(picUrl);
//                msg.setShowName(MainActivity.NickName);
//
//                Map payload = new BasePayloadMap();
//
//                payload.put(PojosConstants.PAYLOAD_BASEREQUEST, request);
//                payload.put("Msg", msg);
//                payload.put("ClientVersion", new ClientVersion(major, minor, build).getVersion());
//                payload.put("Path", "/common.json-rpc");
//
//                SendMsgAdaptor sendMsgAdaptor = new SendMsgAdaptor(null);
//                sendMsgAdaptor.initHeader(Constant.HEADER_SECUREID, BytesUtils.intToByteArray(key.getSecureId()));
//                sendMsgAdaptor.initHeader(Constant.HEADER_ENCODER, BytesUtils.shortToByteArray(Encoder.ENCODE_AES128));
//                sendMsgAdaptor.initHeader(Constant.HEADER_COMPRESSOR, BytesUtils.shortToByteArray(Compressor.COMPRESSOR_LZ4));
//                sendMsgAdaptor.initHeader(Constant.HEADER_MODE, new byte[]{DEFAULT_MODE});
//                sendMsgAdaptor.initOption(Constant.OPTION_ENCODER, true);
//                sendMsgAdaptor.initOption(Constant.OPTION_COMPRESSOR, true);
//                sendMsgAdaptor.setKeyMap(key.toMap());
//                sendMsgAdaptor.setPayloadMap(payload);
//                try {
//                    ClientManager.write(sendMsgAdaptor);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ApplicationException e) {
//                    e.printStackTrace();
//                }
//            }
//        } else {
//            ToolAlert.showToast(context, "当前无网络，请连接网络!", false);
//        }
//    }
//
//    //发语音
//    public static void ImVoice(String to, String picUrl, Context context) {
//        if (isNetworkConnected(context) == true) {
//
//            if (!skey.equals("")) {
//                BaseRequest request = new BaseRequest();
//                request.setUin(uin);
//                request.setSkey(skey);
//                request.setDeviceID(deviceid);
//                request.setSid(MainActivity.sid);
//
//                KeyBean key = new KeyBean();
//                key.setSecureId(MainActivity.secureid);
//                key.setRandomKey(MainActivity.randomkey);
//
//                MessageVoice msg = new MessageVoice(MainActivity.userid_base64, to);
//                msg.setMediaId(picUrl);
//                msg.setShowName(MainActivity.NickName);
//
//                Map payload = new BasePayloadMap();
//
//                payload.put(PojosConstants.PAYLOAD_BASEREQUEST, request);
//                payload.put("Msg", msg);
//                payload.put("ClientVersion", new ClientVersion(major, minor, build).getVersion());
//                payload.put("Path", "/common.json-rpc");
//
//                SendMsgAdaptor sendMsgAdaptor = new SendMsgAdaptor(null);
//                sendMsgAdaptor.initHeader(Constant.HEADER_SECUREID, BytesUtils.intToByteArray(key.getSecureId()));
//                sendMsgAdaptor.initHeader(Constant.HEADER_ENCODER, BytesUtils.shortToByteArray(Encoder.ENCODE_AES128));
//                sendMsgAdaptor.initHeader(Constant.HEADER_COMPRESSOR, BytesUtils.shortToByteArray(Compressor.COMPRESSOR_LZ4));
//                sendMsgAdaptor.initHeader(Constant.HEADER_MODE, new byte[]{DEFAULT_MODE});
//                sendMsgAdaptor.initOption(Constant.OPTION_ENCODER, true);
//                sendMsgAdaptor.initOption(Constant.OPTION_COMPRESSOR, true);
//                sendMsgAdaptor.setKeyMap(key.toMap());
//                sendMsgAdaptor.setPayloadMap(payload);
//                try {
//                    ClientManager.write(sendMsgAdaptor);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ApplicationException e) {
//                    e.printStackTrace();
//                }
//            }
//        } else {
//            ToolAlert.showToast(context, "当前无网络，请连接网络!", false);
//        }
//    }


//    //群消息
//    public static void ImGroup(String to, int status, Context context, String groupId, String groupName, List<GroupUser> groupUser, String content) {
//        //0创建   1邀请   2退群    3解散群    4修改群名    5踢人   6查询所有用户
//        if (isNetworkConnected(context) == true) {
//            if (!skey.equals("")) {
//                BaseRequest request = new BaseRequest();
//                request.setUin(uin);
//                request.setSkey(skey);
//                request.setDeviceID(deviceid);
//                request.setSid(MainActivity.sid);
//
//                KeyBean key = new KeyBean();
//                key.setSecureId(MainActivity.secureid);
//                key.setRandomKey(MainActivity.randomkey);
//
//                Map payload = new BasePayloadMap();
//
//                Group group = new Group();
//                group.setGroupId(groupId);
//                group.setGroupName(groupName);
//                group.setGroupUsers(groupUser);
//
//                GroupEvent groupEvent = new GroupEvent(MainActivity.userid_base64, to);
//                groupEvent.setShowName(MainActivity.NickName);
//                groupEvent.setGroupState(status);
//                groupEvent.setMessageGroup(group);
//                groupEvent.setContent(content);
//
//                payload.put(PojosConstants.PAYLOAD_BASEREQUEST, request);
//                payload.put("Msg", groupEvent);
//                payload.put("ClientVersion", new ClientVersion(major, minor, build).getVersion());
//                payload.put("Path", "/common.json-rpc");
//
//                SendMsgAdaptor sendMsgAdaptor = new SendMsgAdaptor(null);
//                sendMsgAdaptor.initHeader(Constant.HEADER_SECUREID, BytesUtils.intToByteArray(key.getSecureId()));
//                sendMsgAdaptor.initHeader(Constant.HEADER_ENCODER, BytesUtils.shortToByteArray(Encoder.ENCODE_AES128));
//                sendMsgAdaptor.initHeader(Constant.HEADER_COMPRESSOR, BytesUtils.shortToByteArray(Compressor.COMPRESSOR_LZ4));
//                sendMsgAdaptor.initHeader(Constant.HEADER_MODE, new byte[]{DEFAULT_MODE});
//                sendMsgAdaptor.initOption(Constant.OPTION_ENCODER, true);
//                sendMsgAdaptor.initOption(Constant.OPTION_COMPRESSOR, true);
//                sendMsgAdaptor.setKeyMap(key.toMap());
//                sendMsgAdaptor.setPayloadMap(payload);
//                try {
//                    ClientManager.write(sendMsgAdaptor);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ApplicationException e) {
//                    e.printStackTrace();
//                }
//            }
//        } else {
//            ToolAlert.showToast(context, "当前无网络，请连接网络!", false);
//        }
//    }


    //当前是否有网络
    public static boolean isNetworkConnected(Context context) {
        return true;
//        if (context != null) {
//            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
//                    .getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
//            if (mNetworkInfo != null) {
//                return mNetworkInfo.isAvailable();
//            }
//        }
//        return false;
    }
}
