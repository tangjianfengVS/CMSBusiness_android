package com.example.jianfeng.cmsbusiness_android.rootMain;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.jianfeng.cmsbusiness_android.R;
import com.example.jianfeng.cmsbusiness_android.contacts.CMSContactsFragment;
import com.example.jianfeng.cmsbusiness_android.hander.CMSClickHander;
import com.example.jianfeng.cmsbusiness_android.hander.CMSLoginHander;
import com.example.jianfeng.cmsbusiness_android.im.ClientManager;
import com.example.jianfeng.cmsbusiness_android.im.helper.ImHelper;
import com.example.jianfeng.cmsbusiness_android.loginInfo.CMSLoginNI;
import com.example.jianfeng.cmsbusiness_android.loginInfo.CMSLoginView;
import com.example.jianfeng.cmsbusiness_android.loginInfo.CMSUseinfo;
import com.example.jianfeng.cmsbusiness_android.message.CMSMessageFragment;
import com.example.jianfeng.cmsbusiness_android.mine.CMSMineFragment;
import com.example.jianfeng.cmsbusiness_android.utils.WisdomScreenUtils;
import com.example.jianfeng.cmsbusiness_android.utils.WisdomTabBar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CMSRootMainActivity extends FragmentActivity {

    private Context context;

    private CMSLoginView loginView;

    private CMSLoginNI loginNI;

    private RelativeLayout tabBarLayout;

    private WisdomTabBar tabBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmsroot_main);
        context = this;
        tabBarLayout = (RelativeLayout)findViewById(R.id.wisdomTabbar);

        setupUI();
    }

    private void setupUI(){
        Boolean hasUserInfo = CMSUseinfo.shared().resultInfo(context);

        if (!hasUserInfo){
            loginView = new CMSLoginView(this);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            addContentView(loginView,params);

            loginView.clickHander = new CMSClickHander() {
                @Override
                public void clickBtnHander() {

                }

                @Override
                public void clickBtnHander(String name, String password) {
                    setLoginNetwork(name,password);
                }
            };
        }else {
            setCMSTabbar();
            setIM();
        }
    }

    private void setCMSTabbar(){
        WisdomScreenUtils screenUtils = new WisdomScreenUtils();

        tabBar = new WisdomTabBar(context, getFragmentManager());
        tabBar.register(CMSMessageFragment.class, R.mipmap.success, R.mipmap.success, "消息" ,R.id.fragmentLayout);
        tabBar.register(CMSContactsFragment.class, R.mipmap.success, R.mipmap.success, "通讯录",R.id.fragmentLayout);
        tabBar.register(CMSMineFragment.class, R.mipmap.success, R.mipmap.success, "我的", R.id.fragmentLayout);
        tabBar.setup();
        tabBarLayout.addView(tabBar, screenUtils.getScreenWidthPixels(context), screenUtils.dip2px(context, 49));
    }

    private void setLoginNetwork(String name, String password) {

        loginNI = new CMSLoginNI();
        loginNI.loginWith(name, password, context, new CMSLoginHander() {

            @Override
            public void loginHander(Boolean res, String result) {
                if (res) {
                    mianUpdateUI();
                }
            }
        });
    }

    /** 发起长连接 */
    private void setIM(){
        ImHelper.shared().connect();
    }

    private void mianUpdateUI(){
        Boolean hasUserInfo = CMSUseinfo.shared().resultInfo(context);
        if (hasUserInfo) {
            /** 主线程UI操作 */
            new Thread(new Runnable() {
                @Override
                public void run() {

                    runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                ((ViewGroup) loginView.getParent()).removeView(loginView);
                                loginView = null;
                                setCMSTabbar();
                                setIM();
                            }
                        }
                    );
                }
            }).start();
        }
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.admin.cmsandroid.init")) {
                //loading.dismiss();

                ArrayList ContactList = new ArrayList<>();
                final String news = intent.getExtras().getString("msg");
                Object object = intent.getSerializableExtra("init");
                Gson gson = new Gson();

                if (news != null) {
//                    Logger.i(TAG, "异常" + news.toString());
//					Intent caigou = new Intent(MainActivity.this,QiYeActivity.class);
//					startActivity(caigou);
                } else {
                    //CMSSQLiteHelper helper = new CMSSQLiteHelper(getApplicationContext(), null, CMSConstants.CMS_DB_VERSION);


                    Map map = ((Map) object);
                    List<Map> keys = (List<Map>) map.get("SyncKey");
                    if (map.containsKey("User")) {
                        Map user = (Map) map.get("User");
//                        username = user.get("Username") + "";
//                        userid_base64 = user.get("UserID") + "";
//                        mobile = user.get("Mobile") + "";
//                        NickName = user.get("NickName") + "";
//                        UserHeader = user.get("UserHeader") + "";
//                        Identification = user.get("Identification") + "";
//                        Email = user.get("Email") + "";
//                        Address = user.get("Address") + "";
//
//
//                        ContactList = (List<Map>) map.get("ContactList");

//
//                        ContentValues cv = new ContentValues();
//                        cv.put("SupAlias", map.get("SupAlias").toString());
//                        cv.put("UserId", user_id);
//                        cv.put("User", map.get("User").toString());
//                        cv.put("ContactList", gson.toJson((List<Map>) map.get("ContactList")));
//
//                        helper.addIm(cv, user_id);
////                        SharedPrefsUtil.putValue(MainActivity.this, "init", "user", map.get("User").toString());
//                    } else {
//
//
//                        Map im = helper.getSupAlias(user_id);
//
////                        String init = SharedPrefsUtil.getValue(MainActivity.this, "init", "user", "no");
//                        //个人信息
//                        String init = im.get("User") + "";
////                            Map<String, Object> userMap = new HashMap<String, Object>();
////                            userMap = gson.fromJson(init, map.getClass());
//                        HashMap userMap = (HashMap) com.admin.cmsandroid.pythonsh.utils.CodeUtils.transStringToMap(init.substring(1, init.length() - 1));
//                        username = userMap.get("Username") + "";
//                        userid_base64 = userMap.get("UserID") + "";
//                        mobile = userMap.get("Mobile") + "";
//                        NickName = userMap.get("NickName") + "";
//                        UserHeader = userMap.get("UserHeader") + "";
//                        Identification = userMap.get("Identification") + "";
//                        Email = userMap.get("Email") + "";
//                        Address = userMap.get("Address") + "";
//
//                        //联系人列表
//                        String contactlist = im.get("ContactList") + "";
//
//                        // json转为带泛型的list
//                        List<Map> retList = gson.fromJson(contactlist,
//                                new TypeToken<List<Map>>() {
//                                }.getType());
//                        ContactList = retList;
//
//                    }
//
////                    if (map.containsKey("ContactList")) {
////                        if (map.get("ContactList").toString().length() > 2) {
////                            ContactList = (List<Map>) map.get("ContactList");
////                            Gson gson = new Gson();
////                            String list = gson.toJson(ContactList);
////                            SharedPrefsUtil.putValue(MainActivity.this, "init", "ContactList", list);
////                        }
////                    } else {
////                        ContactList = new ArrayList<>();
////                        String init = SharedPrefsUtil.getValue(MainActivity.this, "init", "ContactList", "no");
////                        if (!init.equals("no")) {
//////                            GsonUtils.toList(init);
////                            // json转为带泛型的list
////                            Gson gson = new Gson();
////                            List<Map> retList = gson.fromJson(init,
////                                    new TypeToken<List<Map>>() {
////                                    }.getType());
////                            ContactList = retList;
////                        }
////                    }
//
////                    if (map.containsKey("SupAlias")) {
////                        CMSSQLiteHelper helper1 = new CMSSQLiteHelper(getApplicationContext(), null, CMSConstants.CMS_DB_VERSION);
////                        ContentValues cv = new ContentValues();
////                        cv.put("SupAlias", map.get("SupAlias").toString());
////                        helper1.addSupAlias(cv, user_id);
////                        SharedPrefsUtil.putValue(MainActivity.this, "init", "SupAlias", map.get("SupAlias").toString());
////                    } else {
////                        String init = SharedPrefsUtil.getValue(MainActivity.this, "init", "SupAlias", "no");
////                        if (!init.equals("no")) {
////                            CMSSQLiteHelper helper1 = new CMSSQLiteHelper(getApplicationContext(), null, CMSConstants.CMS_DB_VERSION);
////                            ContentValues cv = new ContentValues();
////                            cv.put("SupAlias", init);
////                            helper1.addSupAlias(cv, user_id);
////                        }
////                    }
//
//                    if (isStart == false) {
//                        isStart = true;
//                        ImHelper.ImSync();
//                    }
//
//                }
//
//                //第一次登录发送入库消息
////                if (outbound == true) {
////                    List lists = new ArrayList();
////                    Map map = new HashMap();
////                    map.put("status", "");
////                    lists.add(map);
////                    ImHelper.ImInvokeThrift("bdPreEntryManage", "inStore", lists, ImHelper.StorageServiceId, MainActivity.this);
////                    outbound = false;
////                }
//
//                try {
//                    if (!UserHeader.equals("None")) {
//                        String url = "http://fs.cshuanyu.com/showfiles/" + UserHeader + "?access_token=" + CodeUtils.shaEncrypt(M1) + "&uid=" + MainActivity.mobile;
//                        OkHttpUtil.dowload(MainActivity.this, url, UserHeader, "1", "", "");
//                    }
//                    for (int i = 0; i < ContactList.size(); i++) {
//                        if (!ContactList.get(i).get("UserHeader").toString().equals("None")) {
//                            String url = "http://fs.cshuanyu.com/showfiles/" + ContactList.get(i).get("UserHeader").toString() + "?access_token=" + CodeUtils.shaEncrypt(M1) + "&uid=" + MainActivity.mobile;
//                            OkHttpUtil.dowload(MainActivity.this, url, ContactList.get(i).get("UserHeader").toString(), "1", "", "");
//                        }
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                setSelect(SWITCH_TO_NEWS);
                    }
                }
            }
        }
    };
}
