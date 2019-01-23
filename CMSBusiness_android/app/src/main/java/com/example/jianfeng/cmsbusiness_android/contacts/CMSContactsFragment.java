package com.example.jianfeng.cmsbusiness_android.contacts;

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.jianfeng.cmsbusiness_android.R;
import com.example.jianfeng.cmsbusiness_android.loginInfo.CMSSQLManager;
import com.example.jianfeng.cmsbusiness_android.utils.WisdomScreenUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jianfeng on 18/12/18.
 */
public class CMSContactsFragment extends Fragment {

    private ListView listView;
    //private RelativeLayout headerView;

    private List<CMSContactsVO> contactsVOLsit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contactsfragment, container, false);
        listView = (ListView)view.findViewById(R.id.ListView);
        contactsVOLsit = CMSSQLManager.shared(getContext()).loadContacts();
        setupUI();
        return view;
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    private void setupUI(){
        //注册广播
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("com.admin.cmsbusiness_android.addressbook");
        getContext().registerReceiver(mBroadcastReceiver, myIntentFilter);

        //WisdomScreenUtils screenUtils = new WisdomScreenUtils();
        CMSContactsAdapter adapter = new CMSContactsAdapter(getContext(), R.layout.contactsadapter, contactsVOLsit);
        listView.setAdapter(adapter);

        CMSContactsHeaderView contactsHeaderView = new CMSContactsHeaderView(getContext());
        listView.addHeaderView(contactsHeaderView);
        contactsHeaderView.setOnButtonClick(new CMSContactsHeaderView.OnButtonClickListener() {
            @Override
            public void onButtonClick() {
                System.out.print("新的朋友");
            }
        });
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals("com.admin.cmsbusiness_android.addressbook")) {
                Object object = intent.getSerializableExtra("ContactList");
                Map map = ((Map) object);
                List<Map> ContactList = (List<Map>) map.get("ContactList");

                Gson gson = new Gson();
                List<CMSContactsVO> list = new ArrayList();

                for (Map obj :ContactList){
                    String jsonStr =new JSONObject(obj).toString();
                    CMSContactsVO VO = gson.fromJson(jsonStr,CMSContactsVO.class);
                    list.add(VO);
                }
                contactsVOLsit = list;
                CMSContactsAdapter adapter = new CMSContactsAdapter(getContext(), R.layout.contactsadapter, contactsVOLsit);
                listView.setAdapter(adapter);

                /**
                 * 调用getWritableDatabase() 方法
                 * 自动检测当前程序中 BookStore.db 这个数据库
                 * 如果不存在则创建该数据库并调用 onCreate() 方法
                 * 同时Book表也会被创建
                 */
                CMSSQLManager.shared(getContext()).saveContacts(list);
            }
        }
    };
}

