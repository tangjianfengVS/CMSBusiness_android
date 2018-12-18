package com.example.jianfeng.cmsbusiness_android.mine;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.jianfeng.cmsbusiness_android.R;
import com.example.jianfeng.cmsbusiness_android.utils.WisdomScreenUtils;

import java.math.BigDecimal;

/**
 * Created by jianfeng on 18/12/18.
 */
public class CMSMineFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.minefragment, container, false);
        ImageView headerImageView = (ImageView)view.findViewById(R.id.headerImageView);

        RelativeLayout.LayoutParams layoutParamsIocn = (RelativeLayout.LayoutParams) headerImageView.getLayoutParams();

        WisdomScreenUtils screenUtils = new WisdomScreenUtils();
        double height = div(9,16,2) * screenUtils.getScreenWidthPixels(getContext());
        layoutParamsIocn.height = (int) height;
        return view;
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
