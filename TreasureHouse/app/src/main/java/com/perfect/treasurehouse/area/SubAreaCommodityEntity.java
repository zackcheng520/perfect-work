package com.perfect.treasurehouse.area;

import android.content.Context;

import com.perfect.treasurehouse.utils.TLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by Administrator on 2016/12/29.
 */

public class SubAreaCommodityEntity extends GameAreaAbstract {
    Context mContext;

    public SubAreaCommodityEntity(Context context) {
        mContext = context;
        TLog.i("SubAreaCommodityEntity Init");
    }

    public void init(String s) {

    }

    ;

    public void saveAreaToXML(List list) {

    }

    ;

    public void saveAreaToXML(List list, List list2, List list3) {

    }

    ;

    public void clearSharedPreference() {

    }

    ;

    public Context getContext() {
        return mContext;
    }

    ;


}
