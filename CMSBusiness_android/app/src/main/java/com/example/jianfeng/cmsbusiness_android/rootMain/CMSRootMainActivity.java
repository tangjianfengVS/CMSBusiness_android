package com.example.jianfeng.cmsbusiness_android.rootMain;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.jianfeng.cmsbusiness_android.R;
import com.example.jianfeng.cmsbusiness_android.hander.CMSClickHander;
import com.example.jianfeng.cmsbusiness_android.loginInfo.CMSLoginView;

public class CMSRootMainActivity extends FragmentActivity {

    private CMSLoginView loginView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmsroot_main);

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
                public void clickBtnHander(String name, String passwork) {

                }
            };
        }
    }
}
