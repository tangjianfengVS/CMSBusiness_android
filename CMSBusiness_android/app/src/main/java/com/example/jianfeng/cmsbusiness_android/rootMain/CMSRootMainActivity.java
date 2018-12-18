package com.example.jianfeng.cmsbusiness_android.rootMain;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.jianfeng.cmsbusiness_android.R;
import com.example.jianfeng.cmsbusiness_android.contacts.CMSContactsFragment;
import com.example.jianfeng.cmsbusiness_android.hander.CMSClickHander;
import com.example.jianfeng.cmsbusiness_android.hander.CMSLoginHander;
import com.example.jianfeng.cmsbusiness_android.loginInfo.CMSLoginNI;
import com.example.jianfeng.cmsbusiness_android.loginInfo.CMSLoginView;
import com.example.jianfeng.cmsbusiness_android.loginInfo.CMSUseinfo;
import com.example.jianfeng.cmsbusiness_android.message.CMSMessageFragment;
import com.example.jianfeng.cmsbusiness_android.mine.CMSMineFragment;
import com.example.jianfeng.cmsbusiness_android.utils.WisdomScreenUtils;
import com.example.jianfeng.cmsbusiness_android.utils.WisdomTabBar;

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
                    Boolean hasUserInfo = CMSUseinfo.shared().resultInfo(context);
                    if (hasUserInfo){
                        /** 主线程UI操作 */
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                runOnUiThread(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            ((ViewGroup)loginView.getParent()).removeView(loginView);
                                            loginView = null;
                                            setCMSTabbar();
                                        }
                                    }
                                );
                            }
                        }).start();
                    }
                }
            }
        });
    }
}
