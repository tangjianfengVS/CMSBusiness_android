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
