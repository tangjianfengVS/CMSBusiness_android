package com.example.jianfeng.cmsbusiness_android.rootMain;

import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.jianfeng.cmsbusiness_android.R;
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
        }
    }
}
