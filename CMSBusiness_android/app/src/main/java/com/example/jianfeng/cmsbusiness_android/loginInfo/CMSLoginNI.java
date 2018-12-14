package com.example.jianfeng.cmsbusiness_android.loginInfo;

import android.content.Context;

import com.example.jianfeng.cmsbusiness_android.hander.CMSLoginHander;

import org.apache.commons.codec.binary.Hex;
import org.json.JSONException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jianfeng on 18/12/8.
 */
public class CMSLoginNI {

    /** 登录 */
    public void loginWith(String name, String password, CMSLoginHander hander){



    }

//    public Map<String, Object> login(String username, String password, Context context,String deviceid) {
//        return login(username, password, this.DEFAULT_ENTERPRISE_ID, context,deviceid);
//    }


    private Map<String, Object> login(String username, String password, String eid, Context context,String deviceid) throws NoSuchAlgorithmException {
        String p = password;



        //try {
            /** 加密类型设置 */
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] b = p.getBytes();
            sha.update(b, 0, b.length);

            p = new String(Hex.encodeHex(sha.digest()));

            Map<String, String> params = new HashMap<String, String>();
            params.put("userpk", username);
            params.put("e", eid);

            if (context != null) {
                params.put("deviceid", deviceid);
                params.put("devicemodel", String.valueOf(android.os.Build.BRAND) + String.valueOf(android.os.Build.MODEL) + String.valueOf(android.os.Build.VERSION.RELEASE));
                //params.put("deviceid","aab3d27c2c4d2ef0");
                //params.put("devicemodel","samsungSM-G93507.0");
                //params.put("deviceid","pc");
            }

//            JsonRpcHttpClient client = new JsonRpcHttpClient(
//                    new URL("https://auth.cshuanyu.com/auth"));
//            SSLContext sslContext = SSLContext.getInstance("TLS");


//            sslContext.init(null, new TrustManager[]{truseAllManager}, new SecureRandom());
//            client.setSslContext(sslContext);

//            client.setConnectionTimeoutMillis(2000);
//            //账号验证
//            AuthResult ret = client.invoke("setUser", params, AuthResult.class);
//
//            String s, I, B, M2;
//
//            s = ret.getValue("s").toString();
//
//            I = ret.getValue("m").toString();
//
//            SRPConstants c = new SRPConstants(new BigInteger(SRPConstants.N_2048[0], 16),
//                    new BigInteger(SRPConstants.N_2048[1]));
//            SRPClientSession session = new SRPClientSession(c, p.getBytes(), I.getBytes());
//            session.setSalt_s(new BigInteger(s.substring(2, s.length() - 1), 16));
//
//            params.put("Av", session.getA().toString(16));
//            //密码验证
//            ret = client.invoke("setA", params, AuthResult.class);
//            B = ret.getValue("B").toString();
//            session.setServerPublicKey_B(new BigInteger(B.substring(2, B.length() - 1), 16));
//
//            params.clear();
//            params.put("userpk", username);
//            params.put("e", eid);
//            String M1 = session.getEvidenceValue_M1().toString(16);
//            params.put("M1v", M1);
//            //数据加密保存
//            Map<String, Object> map = new HashMap();
////            map= client.invoke("setM1", params, LinkedHashMap.class);
//            ret = client.invoke("setM1", params, AuthResult.class);
//            map.put("cert", ret.getValue("cert"));
//            map.put("security", ret.getValue("security"));
//            map.put("expired", ret.getValue("expired"));
//            map.put("error", ret.getErrMsg());
//
////            if (ret.getErrMsg() == null) {
////                //M2 = ret.getValue("M2").toString();
////                map.put("code", M1);
////                map.put("expired", ret.getValue("expired").toString());
////                map.put("cert", ret.getValue("cert"));
////                map.put("error", "");
////            } else if(!(ret.getErrMsg().equals(""))) {
////                map.put("code", "");
////                map.put("expired", "");
////                map.put("cert", null);
////                map.put("error", ret.getErrMsg());
////            } else {
////                //M2 = ret.getValue("M2").toString();
////                map.put("code", M1);
////                map.put("expired", ret.getValue("expired").toString());
////                map.put("cert", null);
////                map.put("error", "");
////            }
//
////            map.put("session", session);
////            map.put("userid", I);
//
//            return map;
//        } catch(JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

}
