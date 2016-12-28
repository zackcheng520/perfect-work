package com.perfect.treasurehouse.area;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.perfect.treasurehouse.HttpRequest.HttpRequestDataEntity;
import com.perfect.treasurehouse.utils.TLog;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.value;

/**
 * Created by Zack on 2016/12/23.
 */

public class GameSubArea extends GameAreaAbstract {
    private Context mContext;
    private String ELEMENT_TAG = "div";
    private String ELEMENT_VALUE = "ser_area_list";
    private String ELEMENT_KEY = "class";
    private String HREF_TAG = "a";

    private String LINK_TARGET;

    HttpRequestDataEntity gameRegional;

    private List sub_area = new ArrayList();
    private List sub_areaLink = new ArrayList();
    private List sub_areaGroup = new ArrayList();
    public GameSubArea(Context context) {
        mContext = context;
        TLog.i("GameSubArea Init");
    }

    public void init() {
        gameRegional = new HttpRequestDataEntity(URL, ELEMENT_TAG,
                ELEMENT_VALUE, ELEMENT_KEY, HREF_TAG, this);
        TLog.d("test start");
        gameRegional.getRequestWebSiteTargetContent();
    }
    public void saveAreaToXML(List area) {
        TLog.d("saveAreaToXML nothing to do it");
    }
    public void saveAreaToXML(List area, List areaLink, List areaGroup) {
        sub_area = area;
        sub_areaLink = areaLink;
        sub_areaGroup = areaGroup;
        SharedPreferences spName = mContext.getSharedPreferences("sub_area_name",
                Context.MODE_PRIVATE);
        SharedPreferences spLink = mContext.getSharedPreferences("sub_area_link",
                Context.MODE_PRIVATE);
        SharedPreferences spGroup = mContext.getSharedPreferences("sub_area_group",
                Context.MODE_PRIVATE);
        Editor editorName = spName.edit();
        Editor editoLinkr = spLink.edit();
        Editor editorGroup = spGroup.edit();
        for (int i = 0; i < sub_area.size(); i++) {
            TLog.d("Area = " + sub_area.get(i));
            TLog.d("AreaLink = " + sub_areaLink.get(i));
            TLog.d("AreaGroup = " + sub_areaGroup.get(i));

            saveToSharedPreference(editorName, sub_area.get(i).toString(), i);
            saveToSharedPreference(editoLinkr, sub_areaLink.get(i).toString(), i);
            saveToSharedPreference(editorGroup, sub_areaGroup.get(i).toString(), i);
        }

    }
    private void saveToSharedPreference(Editor ed, String value, int key){
        ed.putString(Integer.toString(key), value.toString());
        ed.commit();
    }
}
