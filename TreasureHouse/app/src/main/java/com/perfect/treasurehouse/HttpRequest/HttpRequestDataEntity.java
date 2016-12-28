package com.perfect.treasurehouse.HttpRequest;

import android.os.AsyncTask;

import com.perfect.treasurehouse.area.GameAreaAbstract;
import com.perfect.treasurehouse.utils.TLog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zack on 2016/12/22.
 */

public class HttpRequestDataEntity extends HttpRequestDataEntityAbstract{

    private String mElementTag;
    private String mElementKeyValue;
    private String mHrefTag = "";
    private String mElementKey;
    private String mUrl;

    Document doc = null;

    private String mLinksContent = "";
    private String mLinksHrefContent = "";
    private String mLinksHrefContentClass = "";

    List mListOfContents = new ArrayList();
    List mListOfHrefContents = new ArrayList();
    List mListOfHrefContentsClass = new ArrayList();
    GameAreaAbstract mGaa;
    private Elements mListElements;

    public HttpRequestDataEntity(String Url, String elementsTag, String elementKeyValue, String elementKey,
                                 String hrefTag, GameAreaAbstract gaa) {
        TLog.i("HttpRequestDataEntity 5");
        mUrl = Url;
        mElementTag = elementsTag;
        mElementKeyValue = elementKeyValue;
        mHrefTag = hrefTag;
        mElementKey = elementKey;
        mGaa = gaa;
    }

    public HttpRequestDataEntity(String Url, String elementsTag, String elementKeyValue, String elementKey,
                                 GameAreaAbstract gaa) {
        TLog.i("HttpRequestDataEntity 4");
        mUrl = Url;
        mElementTag = elementsTag;
        mElementKeyValue = elementKeyValue;
        mElementKey = elementKey;
        mGaa = gaa;
    }

    public void getRequestWebSiteTargetContent() {
        HttpContentObtain httpContentObtain = new HttpContentObtain();
        httpContentObtain.execute();
    }

    private class HttpContentObtain extends AsyncTask<String, Integer, String> {
        //onPreExecute方法用于在执行后台任务前做一些UI操作
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //doInBackground方法内部执行后台任务,不可在此方法内修改UI
        @Override
        protected String doInBackground(String... params) {
            TLog.i("doInBackground(Params... params) called");
            try {
                doc = Jsoup.connect(mUrl).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            initElementsList();
            //Element body = doc.body();
            if (mHrefTag.length() <= EMPTY_STRING) {
                parseHttpData(doc, false);
            } else {
                parseHttpData(doc, true);
            }
            return null;
        }

        //onProgressUpdate方法用于更新进度信息
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果
        @Override
        protected void onPostExecute(String s) {
            //GameRegional gr = new GameRegional();
            TLog.d("mListOfContents size = " + mListOfContents.size() + "; mListOfHrefContents size = " + mListOfHrefContents.size());
            if (mListOfContents.size() > 0 && mListOfHrefContents.size() <= 0) {
                mGaa.saveAreaToXML(mListOfContents);
            } else if (mListOfContents.size() > 0 && mListOfContents.size() > 0) {
                mGaa.saveAreaToXML(mListOfContents, mListOfHrefContents, mListOfHrefContentsClass);
            }

            super.onPostExecute(s);
        }

        //onCancelled方法用于在取消执行中的任务时更改UI
        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }
    }

    public void parseHttpData(Document doc, boolean isLink) {
        if (isLink) {
            for (Element element : mListElements) {
                Elements links = element.getElementsByTag(mHrefTag);
                for (Element link : links) {
                    mLinksContent = link.text().trim();
                    mListOfContents.add(mLinksContent);
                    mLinksHrefContent = link.attr("href");
                    mListOfHrefContents.add(mLinksHrefContent);
                    mLinksHrefContentClass = link.getElementsByTag("span").attr("class");
                    mListOfHrefContentsClass.add(mLinksHrefContentClass);

                    TLog.d("GameSubArea mLinksHrefContent = " + mLinksHrefContent.toString() + " mLinksContent = " + mLinksContent);
                }
            }
        } else {
            for (Element element : mListElements) {
                Elements links = element.getElementsByTag(mElementTag);
                for (Element link : links) {
                    mLinksContent = link.text().trim();
                    mListOfContents.add(mLinksContent);
                    TLog.d("test link mLinksContent = " + mLinksContent.toString());
                }
            }
        }
    }
    private void initElementsList(){
        mListElements = doc.getElementsByAttributeValue(mElementKey, mElementKeyValue);
    }
}
