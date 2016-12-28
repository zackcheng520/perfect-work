package com.perfect.treasurehouse.area;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.perfect.treasurehouse.HttpRequest.HttpRequestDataEntity;
import com.perfect.treasurehouse.utils.TLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zack on 2016/12/22.
 */

public class GameRegional extends GameAreaAbstract {
    private Context mContext;
    private String ELEMENT_TAG = "span";
    private String ELEMENT_VALUE = "ser_area_nav_special";
    private String ELEMENT_KEY = "class";

    private List global_areaList = new ArrayList();

    HttpRequestDataEntity gameRegional;

    private String mAreaNmae;


    public GameRegional(Context context) {
        mContext = context;
        TLog.i("GameRegional Init");
    }

    public void init() {
        gameRegional = new HttpRequestDataEntity(URL, ELEMENT_TAG,
                ELEMENT_VALUE, ELEMENT_KEY, this);
        TLog.d("test start");
        gameRegional.getRequestWebSiteTargetContent();
    }

    public void saveAreaToXML(List area) {
        global_areaList = area;
        SharedPreferences sp = mContext.getSharedPreferences("global_area",
                Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        for (int i = 0; i < global_areaList.size(); i++) {
            TLog.d("Area = " + global_areaList.get(i));
            editor.putString(Integer.toString(i), global_areaList.get(i).toString());
            editor.commit();
        }
    }

    public void saveAreaToXML(List area, List areaLink, List areaGroup) {
        TLog.d("saveAreaToXML nothing to do it");
    }
}
