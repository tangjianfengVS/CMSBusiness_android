package com.example.jianfeng.cmsbusiness_android.loginInfo;

import android.content.Context;

import com.example.jianfeng.cmsbusiness_android.base.data.AuthResult;
import com.example.jianfeng.cmsbusiness_android.base.srp.SRPAuthenticationFailedException;
import com.example.jianfeng.cmsbusiness_android.base.srp.SRPClientSession;
import com.example.jianfeng.cmsbusiness_android.base.srp.SRPConstants;
import com.example.jianfeng.cmsbusiness_android.hander.CMSLoginHander;
import com.example.jianfeng.cmsbusiness_android.utils.WisdomProgressHUD;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;

import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by jianfeng on 18/12/8.
 */
public class CMSLoginNI {
    private static final String DEFAULT_ENTERPRISE_ID = "00100000001";

    private Context context;

    private CMSLoginHander hander;

    private String errorStr="请检查网络";

    /** 登录 */
    public void loginWith(final String name, final String password, final Context context, CMSLoginHander hander) {
        this.context = context;
        this.hander = hander;

        if (name == null || name.toString().length() == 0){
            WisdomProgressHUD.showError("请输入账号", context);
            return;
        }else if (password == null || password.toString().length() == 0){
            WisdomProgressHUD.showError("请输入密码",context);
            return;
        }

        WisdomProgressHUD.startLoading("正在登录",context);

        /**
         * 网络操作相关的子线程
         */
        new Thread(){
            @Override
            public void run() {
                try {
                    login(name, password, DEFAULT_ENTERPRISE_ID, "Deviceid-Test");

                }catch (NoSuchAlgorithmException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    WisdomProgressHUD.showError(errorStr, context);
               } catch (SRPAuthenticationFailedException e) {
                    e.printStackTrace();
                    WisdomProgressHUD.showError(errorStr, context);
               } catch (Throwable e) {
                    e.printStackTrace();
                    WisdomProgressHUD.showError(errorStr, context);
               }
            }
        }.start();

    }

    private void login(String username, String password, String eid, String deviceid) throws Throwable {
        String p = password;

        /** 摘要算法，密码SHA-256加密设置 */
        MessageDigest sha = MessageDigest.getInstance("SHA-256");//会抛出异常
        byte[] b = p.getBytes();
        sha.update(b, 0, b.length);
        p = new String(Hex.encodeHex(sha.digest()));

        /** 验证参数 */
        Map<String, String> params = new HashMap<String, String>();
        params.put("userpk", username);
        params.put("e", eid);

        if (context != null) {
            params.put("deviceid", deviceid);
            params.put("devicemodel", String.valueOf(android.os.Build.BRAND) + String.valueOf(android.os.Build.MODEL) + String.valueOf(android.os.Build.VERSION.RELEASE));
            //params.put("deviceid","aab3d27c2c4d2ef0");
            //params.put("devicemodel","samsungSM-G93507.0");
        }

        JsonRpcHttpClient client = new JsonRpcHttpClient( new URL("https://auth.cshuanyu.com/auth"));

        /** 实现SSL单双向验证 */
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[]{truseAllManager}, new SecureRandom());

        client.setSslContext(sslContext);
        client.setConnectionTimeoutMillis(2000);

        errorStr = "登录失败，请检查账号";
        /** 账号验证，账号信息获取 */
        AuthResult ret = client.invoke("setUser", params, AuthResult.class);

        if (!ret.success){
            WisdomProgressHUD.showError(errorStr, context);
            return;
        }

        String s, I, B, M2;
        s = ret.getValue("s").toString();
        I = ret.getValue("m").toString();

        SRPConstants c = new SRPConstants(new BigInteger(SRPConstants.N_2048[0], 16), new BigInteger(SRPConstants.N_2048[1]));
        SRPClientSession session = new SRPClientSession(c, p.getBytes(), I.getBytes());
        session.setSalt_s(new BigInteger(s.substring(2, s.length() - 1), 16));

        params.put("Av", session.getA().toString(16));

        ret = client.invoke("setA", params, AuthResult.class);

        B = ret.getValue("B").toString();
        session.setServerPublicKey_B(new BigInteger(B.substring(2, B.length() - 1), 16));

        params.clear();
        params.put("userpk", username);
        params.put("e", eid);
        String M1 = session.getEvidenceValue_M1().toString(16);
        params.put("M1v", M1);

        errorStr = "登录失败，请检查密码";
        /** 密码验证 */
        ret = client.invoke("setM1", params, AuthResult.class);

        if (!ret.success) {
            WisdomProgressHUD.showError(errorStr, context);
            return;
        }

        String cert = ret.getValue("cert");
        String security = ret.getValue("security");
        String expired = ret.getValue("expired");
        HashMap certM = (HashMap) transStringToMap(cert.substring(1));
        HashMap securityM = (HashMap) transStringToMap(security.substring(1));

        /** 存储信息 */
        String uidStr = securityM.get("userId").toString();
        String cert_pass = certM.get("cert_pass").toString();
        String cert_expired = certM.get("cert_expired").toString();
        //String isactivate = securityM.get("isactivate").toString();
        //String userpassword = securityM.get("userpassword").toString();
        String loginId = securityM.get("loginId").toString();
        String m1 = securityM.get("m1").toString();
        String randomKey = securityM.get("randomKey").toString();
        String seqId = securityM.get("seqId").toString();
        String sid = securityM.get("sid").toString();
        String skey = securityM.get("skey").toString();
        String uin = securityM.get("uin").toString();
        String userId = securityM.get("userId").toString();
        String mobile = securityM.get("mobile").toString();

        Map<String, Object> map = new HashMap();
        map.put("userName", username);
        map.put("deviceId", deviceid);
        map.put("uidStr", uidStr);
        map.put("cert_pass", cert_pass);
        map.put("cert_expired", cert_expired);
        map.put("user_expired", expired);
        map.put("loginId", loginId);
        map.put("m1", m1);
        map.put("randomKey", randomKey);
        map.put("seqId", seqId);
        map.put("sid", sid);
        map.put("skey", skey);
        map.put("uin", uin);
        map.put("userId", userId);
        map.put("mobile", mobile);

        String jsonStr =new JSONObject(map).toString();
        Boolean res = CMSUseinfo.shared().write(jsonStr,context);

        WisdomProgressHUD.dismiss(context);

        if (hander != null){
            hander.loginHander(true,"");
        }
    }

    private static TrustManager truseAllManager = new X509TrustManager() {
        public void checkClientTrusted(
                X509Certificate[] arg0, String arg1)
                throws CertificateException {
            // TODO Auto-generated method stub
        }

        public void checkServerTrusted(
                X509Certificate[] arg0, String arg1)
                throws CertificateException {
            // TODO Auto-generated method stub
        }

        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }
    };

    /** Json 转 Map */
    public static Map transStringToMap(String mapString) {
        Map map = new HashMap();
        java.util.StringTokenizer items;
        for (StringTokenizer entrys = new StringTokenizer(mapString, ","); entrys.hasMoreTokens();
             map.put(items.nextToken().trim(), items.hasMoreTokens() ? ((Object) (items.nextToken())) : null))
            items = new StringTokenizer(entrys.nextToken(), "=");
        return map;
    }
}
