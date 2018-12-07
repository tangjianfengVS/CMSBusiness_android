package com.example.jianfeng.cmsbusiness_android.loginInfo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.jianfeng.cmsbusiness_android.R;
import com.example.jianfeng.cmsbusiness_android.hander.CMSClickHander;

/**
 * TODO: document your custom view class.
 */
public class CMSLoginView extends RelativeLayout {

    private EditText nameEditText;

    private EditText passwordEditText;

    public CMSClickHander clickHander;

    public CMSLoginView(Context context) {
        super(context);
        setupUI();
    }

    public CMSLoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupUI();
    }

    public CMSLoginView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupUI();
    }

    private void setupUI(){
        /** 关联XML */
        LayoutInflater.from(getContext()).inflate(R.layout.cmsloginview, this);

        View coverview = (View)findViewById(R.id.coverView);
        View nameText = (View)findViewById(R.id.nameText);
        View passwordText = (View)findViewById(R.id.passwordText);
        View topcoverview = (View)findViewById(R.id.coverTopView);
        View lineView = (View)findViewById(R.id.lineView);
        Button loginBtn = (Button)findViewById(R.id.loginBtn);

        nameEditText = (EditText)findViewById(R.id.nameEdit);
        passwordEditText = (EditText)findViewById(R.id.passwordEdit);

        coverview.setBackgroundColor(0x00000000);
        nameText.setBackgroundColor(0x00000000);
        passwordText.setBackgroundColor(0x00000000);
        topcoverview.setBackgroundColor(0xAD444444);
        lineView.setBackgroundColor(0x99ffffff);
        loginBtn.setBackgroundColor(0xAD444444);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickHander != null){
                    clickHander.clickBtnHander(nameEditText.toString(),passwordEditText.toString());
                }
            }
        });
    }
}
