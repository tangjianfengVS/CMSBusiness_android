package com.example.jianfeng.cmsbusiness_android.base.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by oracle on 16/11/1.
 */

public class BaseResult {
    public long version;
    public long total;
    public ArrayList data;

    public boolean success;

    public String getValue(String key){
        if (this.data == null || this.data.size() == 0) return null;

        //check if success equal to 'true'

        if (this.success) {
            Map ret = (HashMap)this.data.get(0);
            return ret.get(key).toString();
        } else {
            return "";
        }

    }
}
