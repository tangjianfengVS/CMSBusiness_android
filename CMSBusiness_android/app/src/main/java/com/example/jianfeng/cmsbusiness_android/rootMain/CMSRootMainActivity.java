package com.example.jianfeng.cmsbusiness_android.rootMain;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.jianfeng.cmsbusiness_android.R;
import com.example.jianfeng.cmsbusiness_android.hander.CMSClickHander;
import com.example.jianfeng.cmsbusiness_android.hander.CMSLoginHander;
import com.example.jianfeng.cmsbusiness_android.loginInfo.CMSLoginNI;
import com.example.jianfeng.cmsbusiness_android.loginInfo.CMSLoginView;
import com.example.jianfeng.cmsbusiness_android.utils.WisdomProgressHUD;

public class CMSRootMainActivity extends FragmentActivity {

    private Context context;

    private CMSLoginView loginView;

    private CMSLoginNI loginNI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmsroot_main);

        context = this;

        setupUI();
    }

    private void setupUI(){
        if (true){
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
        }
    }

    private void setLoginNetwork(String name, String password){

        if (name == null || name.toString().length() == 0){

            WisdomProgressHUD.showError("请输入账号",context);

        }else if (password == null || password.toString().length() == 0){

            WisdomProgressHUD.showError("请输入密码",context);
        }

        loginNI = new CMSLoginNI();

        WisdomProgressHUD.startLoading("正在登录",this);

        loginNI.loginWith(name, password, new CMSLoginHander() {
            @Override
            public void loginHander(Boolean res, String result) {

                if (res){
                    WisdomProgressHUD.showSucceed("登录成功",context);

                }else {

                    WisdomProgressHUD.showSucceed("登录成功",context);
                }
            }
        });
    }

}
