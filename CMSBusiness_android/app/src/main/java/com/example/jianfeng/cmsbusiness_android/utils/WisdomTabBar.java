package com.example.jianfeng.cmsbusiness_android.utils;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianfeng on 18/9/29.
 */
public class WisdomTabBar extends RelativeLayout{
    /** 默认情况下的字体颜色 */
    public int defaultColor = Color.DKGRAY;

    /** 选中时的文字颜色 */
    public int selectColor = Color.BLUE;

    /** 变量，初始化选中Item的Tag值，只在初始化起作用 */
    public int initTagNormal = 0;

    /** 切换Fragment 显示集合 */
    private List<Fragment> fragmentLst = new ArrayList<>();

    /** TabBar 的显示的标题 */
    private List<String> textList = new ArrayList<>();

    /** TabBar 正常情况下的图标 */
    private List<Integer> iconNormalList = new ArrayList<>();

    /** TabBar 选中情况下的图标 */
    private List<Integer> iconSelectList = new ArrayList<>();

    /** 全局变量，记录当前选中的Item  */
    private LinearLayout selectLineLayout;

    /** 全局变量，记录当前选中Item的Tag值 */
    private int selectTag = 0;

    private OnItemMenuClickListener onItemMenuClickListener;

    /** item线性布局管理 */
    private LinearLayout itemLayout;

    private Context context;

    /** 用于对Fragment进行管理 */
    private FragmentManager fragmentManager;

    public void setOnItemMenuClick(OnItemMenuClickListener onItemMenuClickListener) {
        this.onItemMenuClickListener = onItemMenuClickListener;
    }

    public WisdomTabBar(Context context, FragmentManager fragmentManager){
        super(context);
        this.context = context;
        this.fragmentManager = fragmentManager;

        WisdomScreenUtils screenUtils = new WisdomScreenUtils();

        itemLayout = new LinearLayout(context);
        itemLayout.setBackgroundColor(Color.CYAN);
        itemLayout.setOrientation(LinearLayout.HORIZONTAL);
        addView(itemLayout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        RelativeLayout lineView = new RelativeLayout(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, screenUtils.dip2px(context, 1));
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        lineView.setBackgroundColor(Color.RED);
        addView(lineView,params);
    }

    public WisdomTabBar(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
    }

    /** 注册  */
    public WisdomTabBar register(Class fragmentClass, int iconNormal,int iconSelect, String text, @IdRes int containerViewId){
        textList.add(text);
        iconNormalList.add(iconNormal);
        iconSelectList.add(iconSelect);

        FragmentTransaction tran = fragmentManager.beginTransaction();
        try {
            Fragment frag = (Fragment)fragmentClass.newInstance();
            tran.add(containerViewId, frag);

            fragmentLst.add(frag);

            if (fragmentLst.size() - 1 == initTagNormal){
                tran.show(frag);
            }else {
                tran.hide(frag);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        tran.commit();
        return this;
    }

    /** 注册完成,展示 */
    public void setup(){
        if (itemLayout.getChildCount() > 0){
            return;
        }
        WisdomScreenUtils screenUtils = new WisdomScreenUtils();
        selectTag = initTagNormal;

        RelativeLayout lineView = new RelativeLayout(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, screenUtils.dip2px(context, 1));
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        lineView.setBackgroundColor(Color.RED);
        addView(lineView,params);

        /** 根据text 数组长度实例化Item 个数*/
        for(int i=0 ; i < textList.size(); i++) {
            /** 实例化子布局 */
            final LinearLayout linearLayoutChild = new LinearLayout(context);
            linearLayoutChild.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT,1));
            linearLayoutChild.setGravity(Gravity.CENTER);
            linearLayoutChild.setOrientation(LinearLayout.VERTICAL);
            linearLayoutChild.setWeightSum(1);
            linearLayoutChild.setTag(i);

            linearLayoutChild.setBackgroundColor(Color.YELLOW);
            if (i == 1){
                linearLayoutChild.setBackgroundColor(Color.WHITE);
            }else if (i == 2){
                linearLayoutChild.setBackgroundColor(Color.GREEN);
            }

            /** 实例化图标 */
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(screenUtils.dip2px(context, 23),screenUtils.dip2px(context, 23));
            lp.setMargins(0, 6, 0, 0);
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(lp);
            imageView.setImageResource(iconNormalList.get(i));
            imageView.setTag("image_" + i);
            linearLayoutChild.addView(imageView);

            /** 实例化文字 */
            lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 0, 4);
            TextView textView = new TextView(context);
            textView.setText(textList.get(i));
            textView.setTextSize(13);
            textView.setTextColor(defaultColor);
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(lp);
            textView.setTag("text_" + i);
            linearLayoutChild.addView(textView);

            /** 点击事件监听 */
            linearLayoutChild.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    /** item界面更新 */
                    setSelect(v);

                    /** 回调当前将要切换的 Fragment */
                    if (onItemMenuClickListener != null) {
                        onItemMenuClickListener.onTabClick(selectTag, fragmentLst.get(selectTag));
                    }

                    /** 当前切换Fragment */
                    changeFragment();
                }
            });
            /** 将该布局添加到根布局中 */
            itemLayout.addView(linearLayoutChild);
        }
        setSelector();
    }


    /** 设置选中item属性 */
    private void setSelector(){
        /** 设置默认选中项，即第一项 */
        selectLineLayout = (LinearLayout) (findViewWithTag(selectTag));

        /** 设置选中的图片 */
        ImageView imageViewS = (ImageView)(selectLineLayout.findViewWithTag("image_"+selectLineLayout.getTag()));
        imageViewS.setImageResource(iconSelectList.get((int)selectLineLayout.getTag()));

        /** 设置选中的字体颜色 */
        TextView textViewS = (TextView)(selectLineLayout.findViewWithTag("text_"+selectLineLayout.getTag()));
        textViewS.setTextColor(selectColor);
    }


    /** 设置选中和未选中状态的颜色 */
    private void setSelect(View view){
        if(selectTag != (int) view.getTag()) {
            /** 将上一次选中的图片颜色回复为未选中状态 */
            /** 设置选中的图片 */
            ImageView imageView1 = (ImageView)(selectLineLayout.findViewWithTag("image_"+selectTag));
            imageView1.setImageResource(iconNormalList.get(selectTag));
            /** 设置选中的字体颜色 */
            TextView textView1 = (TextView)(selectLineLayout.findViewWithTag("text_"+selectTag));
            textView1.setTextColor(defaultColor);

            selectTag = (int) view.getTag();
            /** 设置选中的图片,文字 */
            setSelector();
            selectLineLayout = (LinearLayout) view;
        }
    }

    /** 
     *  将所有的Fragment都置为隐藏状态。 
     *  @param transaction 用于对Fragment执行操作的事务 
     */
    @SuppressLint("WisdomTabBar")
    private void changeFragment(){
        if (fragmentLst == null || fragmentLst.size() == 0){
            return;
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        for (int i = 0; i < fragmentLst.size(); ++i) {
            Fragment fragment = fragmentLst.get(i);
            transaction.hide(fragment);
        }

        transaction.show(fragmentLst.get(selectTag));
        transaction.commit();
    }

    public interface OnItemMenuClickListener {
        void onTabClick(int tabItem, Fragment currentFragment);
    }
}