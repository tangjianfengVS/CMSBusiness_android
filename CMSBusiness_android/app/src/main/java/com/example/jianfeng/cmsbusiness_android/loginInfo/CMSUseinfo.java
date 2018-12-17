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

    private String uidStr;       //注册,登录            UID

    private String cer_pass;     //注册,登录            CERPASSWORD

    private String cer_expired;  //注册,登录过期日       CEREXPIRED

    private String isactivate;   //注册,登录有返回       ISACTIVATE

    private String userpassword; //注册,登录有返回       USERPASSWORD

    private String user_expired; //登录过期日           USEREXPIRED

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

        if (userName != null && uidStr != null &&
            cer_pass != null && cer_expired != null &&
            isactivate != null && userpassword != null &&
            user_expired != null && loginId != null &&
            m1 != null && mobile != null &&
            randomKey != null && seqId != null &&
            sid != null && skey != null &&
            uin != null && userId != null) {


            return true;
        }
        return false;
    }


    private void updateUser(Context context)  {
        String result = getReadFile(context);

        Gson gson = new Gson();
        CMSUseinfo info = gson.fromJson(result,CMSUseinfo.class);
//        instance.uidStr = info.uidStr; 
//        instance.cer_pass = info.cer_pass; 
//        instance.cer_expired = info.cer_expired; 
//        instance.isactivate = info.isactivate; 
//        instance.userpassword = info.userpassword; 
//        instance.user_expired = info.user_expired; 

//        instance.loginId = info.loginId; 
//        instance.m1 = info.m1; 
//        instance.randomKey = info.randomKey; 
//        instance.seqId = info.seqId; 
//        instance.sid = info.sid; 
//        instance.skey = info.skey; 
//        instance.uin = info.uin; 
//        instance.userId = info.userId; 
//        instance.userName = info.userName;
//        instance.mobile = info.mobile;
//        instance.deviceId = info.deviceId;
//        //Log.d("ddd", String.valueOf(info));
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
    private void write(String info, Context context){
        if (info == null){
            return;
        }

        FileOutputStream fos = null;

        try {
            fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(info.getBytes());
            fos.close();

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}


