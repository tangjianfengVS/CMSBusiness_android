package com.example.jianfeng.cmsbusiness_android.loginInfo;

import android.content.Context;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by jianfeng on 18/12/8.
 */
public class CMSUseinfo {

    private String uidStr;        //注册,登录            UID

    private String cert_pass;     //注册,登录            CERPASSWORD

    private String cert_expired;  //注册,登录过期日       CEREXPIRED

    //private String isactivate;    //注册,登录有返回       ISACTIVATE

    //private String userpassword;  //注册,登录有返回       USERPASSWORD

    private String user_expired;  //登录过期日           USEREXPIRED

    /** --------------security----------- */
    private String loginId;

    private String m1;

    private String randomKey;

    private String seqId;

    private String sid;

    private String skey;

    private String uin;

    private String userId;

    private String userName;

    private String mobile;

    private String deviceId;

    private static CMSUseinfo instance = null;

    private CMSUseinfo(){}

    public static CMSUseinfo shared(){
        if (instance == null){
            instance = new CMSUseinfo();
        }
        return instance;
    }

    private static String FILENAME = "CMSBusines_Android.txt";

    public Boolean resultInfo(Context context) {
        /** 获取本地 */
        updateUser(context);

        if (instance.userName != null && instance.uidStr != null &&
            instance.cert_pass != null && instance.cert_expired != null &&
            //isactivate != null && userpassword != null &&
            instance.user_expired != null && instance.loginId != null &&
            instance.m1 != null && instance.mobile != null &&
            instance.randomKey != null && instance.seqId != null &&
            instance.sid != null && instance.skey != null &&
            instance.uin != null && instance.userId != null) {

            return true;
        }
        return false;
    }

    /** 退出登录 */
    public void loginOut(Context context){
        write("",context);
    }

    /** 更新有户信息 */
    private void updateUser(Context context)  {
        String result = getReadFile(context);

        Gson gson = new Gson();
        CMSUseinfo info = gson.fromJson(result,CMSUseinfo.class);

        if (info != null){
            instance = info;
        }
    }

    /** 内部读取 */
    private String getReadFile(Context context){

        try {
            FileInputStream file = context.openFileInput(FILENAME);
            int len = file.available();
            byte[] buffer = new byte[len];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            while (file.read(buffer) != -1){
                baos.write(buffer);
            }

            byte[] data = baos.toByteArray();
            baos.close();
            file.close();
            return new String(data);

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return "";
    }

    /** 内部存储 */
    public Boolean write(String info, Context context){
        if (info == null){
            return false;
        }

        FileOutputStream fos = null;

        try {
            fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(info.getBytes());
            fos.close();
            return true;

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }
}


