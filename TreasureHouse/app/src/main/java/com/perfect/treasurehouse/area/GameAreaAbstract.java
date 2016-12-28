package com.perfect.treasurehouse.area;

import java.util.List;

/**
 * Created by Zack on 2016/12/23.
*/

public abstract class GameAreaAbstract {
    public String URL = "http://www.xunbao178.com/zx";
    public abstract void init();
    public abstract void saveAreaToXML(List list);
    //public abstract void saveAreaToXML(List list,List list2);
    public abstract void saveAreaToXML(List list,List list2, List list3);
}
