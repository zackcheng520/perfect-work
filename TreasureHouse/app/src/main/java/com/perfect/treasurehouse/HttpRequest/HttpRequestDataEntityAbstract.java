package com.perfect.treasurehouse.HttpRequest;

import org.jsoup.nodes.Document;

/**
 * Created by Administrator on 2016/12/26.
 */

public abstract class HttpRequestDataEntityAbstract {
    public int EMPTY_STRING = 0;
    public abstract void getRequestWebSiteTargetContent();
    public abstract void parseHttpData(Object doc, boolean isLink);
}
