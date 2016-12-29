package com.perfect.treasurehouse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.perfect.treasurehouse.utils.TLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class CommodityTestActivity extends AppCompatActivity {

    private String mCommodityWebUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_test);
        mCommodityWebUrl = getIntent().getStringExtra("subarea_commodity_url");
        try {
            IP();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String IP() throws IOException {
        TLog.d("IP");
        final String IP = null;
        final URL url = new URL(mCommodityWebUrl);
        final URLConnection[] conn = new URLConnection[1];
        Runnable networkTask = new Runnable() {
            @Override
            public void run() {
                try {
                    conn[0] = url.openConnection();
                    conn[0].setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.15) Gecko/20110303 Firefox/3.6.15");
                    conn[0].setRequestProperty("Content-Type", "text/html");
                    conn[0].setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
                    InputStream is = conn[0].getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
                    String line = null;
                    TLog.d("test test test test test test ");
                    while ((line = br.readLine()) != null) {
                        TLog.d(line);
                    }
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(networkTask).start();
        return IP;
    }
}
