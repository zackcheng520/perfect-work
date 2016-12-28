package com.perfect.treasurehouse.area;

import android.content.Context;

import java.util.List;

import static android.R.id.list;

/**
 * Created by Zack on 2016/12/23.
*/

public abstract class GameAreaAbstract {

    public abstract void init(String s);
    public abstract void saveAreaToXML(List list);
    //public abstract void saveAreaToXML(List list,List list2);
    public abstract void saveAreaToXML(List list,List list2, List list3);
    public abstract void clearSharedPreference();
    public abstract Context getContext();
}
