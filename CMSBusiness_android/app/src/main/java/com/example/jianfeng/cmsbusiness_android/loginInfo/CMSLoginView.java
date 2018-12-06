package com.example.jianfeng.cmsbusiness_android.loginInfo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.jianfeng.cmsbusiness_android.R;

/**
 * TODO: document your custom view class.
 */
public class CMSLoginView extends RelativeLayout {
    //private Context mContext;

    public CMSLoginView(Context context) {
        super(context);
        //init(null, 0);
        setupUI();
    }

    public CMSLoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //init(attrs, 0);
        setupUI();
    }

    public CMSLoginView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //init(attrs, defStyle);
        setupUI();
    }

    private void setupUI(){
        LayoutInflater.from(getContext()).inflate(R.layout.cmsloginview, this);

        View coverview = (View)findViewById(R.id.coverView);
        View nameText = (View)findViewById(R.id.nameText);
        View passwordText = (View)findViewById(R.id.passwordText);
        View topcoverview = (View)findViewById(R.id.coverTopView);
        View lineView = (View)findViewById(R.id.lineView);
        Button btn = (Button)findViewById(R.id.loginBtn);

        coverview.setBackgroundColor(0x00000000);
        nameText.setBackgroundColor(0x00000000);
        passwordText.setBackgroundColor(0x00000000);
        topcoverview.setBackgroundColor(0xAD444444);
        lineView.setBackgroundColor(0x99ffffff);
        btn.setBackgroundColor(0xAD444444);

//        nameText = (EditText)findViewById(R.id.nameEdit);
//        passwordText = (EditText)findViewById(R.id.passwordEdit);
//        loginBtn = (Button)findViewById(R.id.login);
//
//        loginBtn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                System.out.println(nameText.getText().toString());
//
//                if (callback != null) {
//                    callback.hashCode();
//                }
//            }
//        });
    }
}
