package com.example.jianfeng.cmsbusiness_android.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jianfeng.cmsbusiness_android.R;
import com.example.jianfeng.cmsbusiness_android.loginInfo.CMSUseinfo;

import java.util.List;

/**
 * Created by jianfeng on 19/1/1.
 */
public class CMSContactsAdapter extends ArrayAdapter<CMSContactsVO> {
    private int resourceId;

    private String LoadFileBaseURL = "http://fs.cshuanyu.com/showfiles/";
    private String Access_tokenURL = "?access_token=";
    private String UidURL = "&uid";


    private String loadImageURL(String contextStr){
        //let str = LoadFileBaseURL + str + "?access_token=" + m1!.sha256() + "&uid=" + mobile!
        String m1 = CMSUseinfo.shared().m1;
        String mobile = CMSUseinfo.shared().mobile;
        String str = LoadFileBaseURL + contextStr + Access_tokenURL + m1 + UidURL + mobile;
        return str;
    }

    //private OnButtonClickListener onButtonClick;

    //public void setOnButtonClick(OnButtonClickListener onButtonClick){
    //    this.onButtonClick = onButtonClick;
    //}

    public CMSContactsAdapter(Context context, int resource, List<CMSContactsVO> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    /** 关联Adapter子视图 */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CMSContactsVO contactsVO = getItem(position);//获取当前项的实例

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        }

        ((TextView)convertView.findViewById(R.id.contactsName)).setText(contactsVO.NickName);
        ImageView houdongIocn = (ImageView)convertView.findViewById(R.id.contactsIocn);

        String url = loadImageURL(contactsVO.UserHeader);
        Glide.with(getContext()).load(url).placeholder(R.mipmap.defaultheaderiocn).error(R.mipmap.defaultheaderiocn).into(houdongIocn);

        return convertView;
    }
}