package com.example.jianfeng.cmsbusiness_android.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jianfeng.cmsbusiness_android.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 *   Created by jianfeng on 18/10/16.
 */
public class WisdomProgressHUD extends FrameLayout {
    private Boolean isShowing = false;

    public long showTime = 2000;

    private boolean toolHighlight = true;

    /** 中心底层视图 */
    private RelativeLayout coverView;

    /** 无动画版图片 */
    private ImageView showImageView;

    /** 动画图片 */
    private ImageView loadImageView;

    /** 文字 */
    private TextView textView;

    private float baseSize = 78;

    /** 白色半透明 */
    private final int whiteTranslucent = 0x99000000;

    private final int whiteTranslucentCover = 0xE6FFFFFF;

    /** 黑色半透明 */
    private final int blackTranslucent = 0x40000000;

    /** -----------------------更新背景色 ----------------------- */
    public void setToolHighlight(boolean isHighlight){
        toolHighlight = isHighlight;
        if (toolHighlight){
            setBackgroundColor(blackTranslucent);
            //showCoverView.setBackgroundColor(whiteTranslucentCover);
            //loadCoverView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }else {
            setBackgroundColor(whiteTranslucent);
            //showCoverView.setBackgroundColor(Color.parseColor("#40000000"));
            //loadCoverView.setBackgroundColor(Color.parseColor("#40000000"));
        }
    }

    /** -----------------------短暂的失败界面展示，提示---------------------- */
    public static WisdomProgressHUD showError(final String text, Context context){
        final WisdomProgressHUD hud = WisdomProgressHUD.shared(context);;

        ((Activity) hud.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hud.setupUI();
                hud.show(false, text);
            }
        });
        return hud;
    }

    /** -----------------------短暂的成功界面展示，提示---------------------- */
    public static WisdomProgressHUD showSucceed(final String text, Context context){
        final WisdomProgressHUD hud = WisdomProgressHUD.shared(context);;

        ((Activity) hud.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hud.setupUI();
                hud.show(true, text);
            }
        });
        return hud;
    }

    /** -----------------------开始加载旋转动画 ------------------------ */
    public static WisdomProgressHUD startLoading(final String text, Context context){
        final WisdomProgressHUD hud = WisdomProgressHUD.shared(context);;

        ((Activity) hud.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hud.setupUI();
                hud.loading(text);
            }
        });
        return hud;
    }

    /** ----------------------- 强制摧毁 -----------------------*/
    public static WisdomProgressHUD dismiss(Context context){
        final WisdomProgressHUD hud = WisdomProgressHUD.shared(context);

        ((Activity) hud.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (hud.isShowing) {
                    hud.dismiss();
                }
            }
        });
        return hud;
    }


    private static WisdomProgressHUD instance = null;

    /** 单利创建，重置 context对象 */
    public static WisdomProgressHUD shared(Context context){
        if (instance == null ){
            //boolean isHighlight = (instance == null)? true:instance.toolHighlight;
            instance = new WisdomProgressHUD(context);
            //instance.setToolHighlight(isHighlight);
        }
        return instance;
    }

    /** 类方法 */
    private WisdomProgressHUD(Context context) {
        super(context);
        /** 关联html */
        LayoutInflater.from(context).inflate(R.layout.wisdomprogresshud, this);
        coverView = (RelativeLayout)findViewById(R.id.coverView);
        loadImageView = (ImageView)findViewById(R.id.loadImageView);
        showImageView = (ImageView)findViewById(R.id.showImageView);
        textView = (TextView)findViewById(R.id.textView);

        showImageView.setVisibility(View.GONE);
        loadImageView.setVisibility(View.GONE);
    }

    private void setupUI(){
        if (isShowing) {
            return;
        }
        WindowManager mWindowManager = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

        /** 该Type描述的是形成的窗口的层级关系，下面会详细列出它的属性 */
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;

        /** 设置窗口的位置 */
        layoutParams.gravity = Gravity.CENTER;

        /** 不设置这个弹出框的透明遮罩显示为黑色 */
        layoutParams.format = PixelFormat.TRANSLUCENT;

        /** 窗口的宽 */
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;

        /** 窗口的高 */
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;

        //获取当前Activity中的View中的TOken,来依附Activity，
        //因为设置了该值，纳闷写的这些代码不能出现在onCreate();否则会报错。
        layoutParams.token =  getWindowToken();

        mWindowManager.addView(this, layoutParams);

        //该flags描述的是窗口的模式，是否可以触摸，可以聚焦等
        //layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                             //WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                             //WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
    }

    /** 界面提示语 */
    private void show(boolean isSucceed, String text) {
        isShowing = true;
        loadImageView.clearAnimation();

        showImageView.setVisibility(View.VISIBLE);
        loadImageView.setVisibility(View.GONE);

        WisdomScreenUtils screenUtils = new WisdomScreenUtils();
        RelativeLayout.LayoutParams layoutParamsIocn = (RelativeLayout.LayoutParams) showImageView.getLayoutParams();
        RelativeLayout.LayoutParams layoutParamsSup = (RelativeLayout.LayoutParams) coverView.getLayoutParams();

        /** 更新iconRect */
        layoutParamsIocn.width = screenUtils.dip2px(getContext(), 32);
        layoutParamsIocn.height = screenUtils.dip2px(getContext(), 32);
        layoutParamsIocn.topMargin = screenUtils.dip2px(getContext(), 16);
        layoutParamsIocn.addRule(RelativeLayout.CENTER_HORIZONTAL);

        if (isSucceed) {
            showImageView.setImageResource(R.mipmap.success);
        } else {
            showImageView.setImageResource(R.mipmap.error);
        }

        if (text == null || text.length() == 0){
            text = "NO ERROR";
        }
        textView.setText(text);
        String dt = textView.getText().toString();
        Rect bounds = new Rect();
        TextPaint paint = textView.getPaint();
        paint.getTextBounds(dt, 0, dt.length(), bounds);

        float width = screenUtils.px2dip(getContext(), bounds.width());
        if (width + 16 >= 78 ){
            baseSize = width + 16;
        } else {
            baseSize = 78;
        }
        layoutParamsSup.width = screenUtils.dip2px(getContext(), baseSize);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                dismiss();
            }
        };
        if (showTime < 500){
            showTime = 1800;
        }
        new Timer().schedule(task, showTime);
    }

    /** 动画加载 */
    private void loading(String text) {
        isShowing = true;
        /** 旋转动画 */
        Animation rotate = AnimationUtils.loadAnimation(getContext(), R.anim.wisdow_rotate_anim);
        LinearInterpolator lin = new LinearInterpolator();
        rotate.setInterpolator(lin);
        loadImageView.startAnimation(rotate);

        showImageView.setVisibility(View.GONE);
        loadImageView.setVisibility(View.VISIBLE);

        WisdomScreenUtils screenUtils = new WisdomScreenUtils();
        RelativeLayout.LayoutParams layoutParamsIocn = (RelativeLayout.LayoutParams) loadImageView.getLayoutParams();
        RelativeLayout.LayoutParams layoutParamsSup = (RelativeLayout.LayoutParams) coverView.getLayoutParams();

        layoutParamsIocn.width = screenUtils.dip2px(getContext(), 40);
        layoutParamsIocn.height = screenUtils.dip2px(getContext(), 40);

        if (text == null || text.length() == 0){
            textView.setText("");
            layoutParamsIocn.addRule(RelativeLayout.CENTER_IN_PARENT);
            baseSize = 75;
            layoutParamsSup.height = screenUtils.dip2px(getContext(), baseSize);
        }else {
            textView.setText(text);
            layoutParamsIocn.topMargin = screenUtils.dip2px(getContext(), 10);
            layoutParamsIocn.addRule(RelativeLayout.CENTER_HORIZONTAL);

            String dt = textView.getText().toString();
            Rect bounds = new Rect();
            TextPaint paint = textView.getPaint();
            paint.getTextBounds(dt, 0, dt.length(), bounds);

            float width = screenUtils.px2dip(getContext(), bounds.width());
            if (width + 16 >= 78 ){
                baseSize = width + 16;
            } else {
                baseSize = 78;
            }
        }
        layoutParamsSup.width = screenUtils.dip2px(getContext(), baseSize);
    }

    private void dismiss(){
        isShowing = false;
        WindowManager mWindowManager = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.removeView(this);
    }
}