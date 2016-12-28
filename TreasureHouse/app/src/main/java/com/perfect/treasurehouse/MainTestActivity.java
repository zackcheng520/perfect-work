package com.perfect.treasurehouse;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.perfect.treasurehouse.area.GameRegional;
import com.perfect.treasurehouse.area.GameSubArea;
import com.perfect.treasurehouse.utils.TLog;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;
import java.util.logging.Filter;

import static android.R.attr.entries;

public class MainTestActivity extends Activity {

    Context mContext;
    WebView webView;
    TextView textView;
    Document doc = null;
    String title;

    TableLayout tableLayout;
    TableLayout tableLayoutSubArea;
    Spinner urlSelectSp;
    private Map<String, ?> global_area_map = new HashMap<String, String>();

    private Map<String, ?> sub_area_value_map = new HashMap<String, String>();
    private Map<String, ?> sub_area_link_map = new HashMap<String, String>();
    private Map<String, ?> sub_area_group_map = new HashMap<String, String>();
    private Map<String, String> sub_area_group_with_global_map = new LinkedHashMap<String, String>();
    private Map<String, String> sub_area_group_with_global_treemap = new TreeMap<String, String>();

    public String URL_GJ = "http://www.xunbao178.com/wmgj";
    public String URL_SJ = "http://www.xunbao178.com/wmsj";
    public String URL_ZX = "http://www.xunbao178.com/zx";

    private final String action = "com.perfect.treasurehouse.ACTION_DATA_SAVE_DONE";

    final Object netLock = new Object();
    GameRegional gr;
    GameSubArea gs;
    DataSaveDoneReceiver mDataSaveDoneReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);

        mDataSaveDoneReceiver = new DataSaveDoneReceiver();
        registerReceiver(mDataSaveDoneReceiver, new IntentFilter(action));
        mContext = getApplicationContext();
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        tableLayoutSubArea = (TableLayout) findViewById(R.id.tableLayoutSubArea);
        urlSelectSp = (Spinner) findViewById(R.id.spinner_url_select);
        tableLayout.setStretchAllColumns(true);
        gr = new GameRegional(mContext);
        gs = new GameSubArea(mContext);
        initSpinnerValue();

    }
    private synchronized void initAreaDateFromWWW(String web_url){
        TLog.i("init Web site" + web_url);
        gr.init(web_url);
        gs.init(web_url);

    }
    private void initSpinnerValue(){
        urlSelectSp.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TLog.d("onItemSelect change");
                gr.clearSharedPreference();
                gs.clearSharedPreference();
                initAreaDateFromWWW(urlSelectSp.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }
    private void initMapVal() {
        global_area_map = getAreaInfoMap("global_area");
        sub_area_value_map = getAreaInfoMap("sub_area_name");
        sub_area_link_map = getAreaInfoMap("sub_area_link");
        sub_area_group_map = getAreaInfoMap("sub_area_group");
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        unregisterReceiver(mDataSaveDoneReceiver);
        super.onStop();
    }

    private Map<String, ?> getAreaInfoMap(String tableName) {
        SharedPreferences sp = mContext.getSharedPreferences(tableName,
                Context.MODE_PRIVATE);

        TLog.d("ed = " + sp.getAll().toString());
        return sp.getAll();
    }

    private void initGlobalView() {
        TableRow tr = new TableRow(this);
        tableLayout.removeAllViews();
        if(global_area_map.size() == 0) {
            TLog.d("no found area");
            return;
        }
        for (int j = 0; j < global_area_map.size(); j++) {
            final int key = j;
            Button bn = new Button(this);
            TableRow.LayoutParams param = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT, 1.0f);
            bn.setLayoutParams(param);
            bn.setText(global_area_map.get(Integer.toString(j)).toString());
            tr.addView(bn);
            bn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    subAreaInfoGroup(Integer.toString(key));
                    showSubArea(key);
                }
            });
        }
        tableLayout.addView(tr);

    }

    private void showSubArea(int key) {
        tableLayoutSubArea.removeAllViews();
        TableRow tr = new TableRow(this);
        TableRow tr2;
        boolean needLineFeed = false;
        int countSum = 0;
        if (sub_area_group_with_global_map.size() > 4) needLineFeed = true;
        for (Map.Entry<String, String> entry : sub_area_group_with_global_map.entrySet()) {
            countSum++;
            if (countSum % 4 == 1 && countSum != 1) {//line feed for display button view.
                tableLayoutSubArea.addView(tr);
                tr = new TableRow(this);
            }
            Button bn = new Button(this);
            TableRow.LayoutParams param = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT, 1.0f);
            bn.setLayoutParams(param);
            bn.setText(entry.getValue());
            tr.addView(bn);
        }
        tableLayoutSubArea.addView(tr);

    }

    private void subAreaInfoGroup(String key) {
        TLog.i("key = " + key);
        sub_area_group_with_global_map.clear();
        for (int i = 0; i < sub_area_value_map.size(); i++) {
            String value = sub_area_group_map.get(Integer.toString(i)).toString();
            if ((value.substring(value.indexOf("group") + 5, value.length())).equals(key)) {
                sub_area_group_with_global_map.put(Integer.toString(i), sub_area_value_map.get(Integer.toString(i)).toString());
            }
        }

        List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(sub_area_group_with_global_map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            //升序排序
            public int compare(Entry<String, String> o1, Entry<String, String> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        sub_area_group_with_global_map.clear();
        for (int i = 0; i < list.size(); i++) {
            TLog.d("sort key = " + list.get(i).toString().substring(0, list.get(i).toString().indexOf("=")));
            TLog.d("sort value = " + list.get(i).toString().substring(list.get(i).toString().indexOf("=") + 1));
            sub_area_group_with_global_map.put(list.get(i).toString().substring(0, list.get(i).toString().indexOf("=")),
                    list.get(i).toString().substring(list.get(i).toString().indexOf("=") + 1));

        }
        for (Map.Entry<String, String> entry : sub_area_group_with_global_map.entrySet()) {
            TLog.d("key = " + entry.getKey() + " ;value = " + entry.getValue());
        }
    }
    private class DataSaveDoneReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            TLog.d("Intent action = " + intent.getAction());
            initMapVal();
            initGlobalView();
        }
    }

}
