package com.example.jianfeng.cmsbusiness_android.contacts;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.jianfeng.cmsbusiness_android.R;

/**
 * TODO: document your custom view class.
 */
public class CMSContactsHeaderView extends RelativeLayout {

    private OnButtonClickListener onButtonClick;

    public void setOnButtonClick(OnButtonClickListener onButtonClick){
        this.onButtonClick = onButtonClick;
    }

    public CMSContactsHeaderView(Context context) {
        super(context);
        setupUI();
    }

    public CMSContactsHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupUI();
    }

    public CMSContactsHeaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupUI();
    }

    private void setupUI(){
        /** 关联XML */
        LayoutInflater.from(getContext()).inflate(R.layout.cmscontactsheaderview, this);

        ((Button)findViewById(R.id.seachBut)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonClick != null) {
                    onButtonClick.onButtonClick();
                }
            }
        });
    }

    public interface OnButtonClickListener {
        void onButtonClick();
    }
}
