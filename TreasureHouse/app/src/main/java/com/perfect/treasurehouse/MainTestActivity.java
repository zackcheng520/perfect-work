package com.perfect.treasurehouse;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.Button;
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
import java.util.TreeMap;

import static android.R.attr.entries;

public class MainTestActivity extends Activity {

    Context mContext;
    WebView webView;
    TextView textView;
    Document doc = null;
    String title;

    TableLayout tableLayout;
    TableLayout tableLayoutSubArea;
    private Map<String, ?> global_area_map = new HashMap<String, String>();

    private Map<String, ?> sub_area_value_map = new HashMap<String, String>();
    private Map<String, ?> sub_area_link_map = new HashMap<String, String>();
    private Map<String, ?> sub_area_group_map = new HashMap<String, String>();
    private Map<String, String> sub_area_group_with_global_map = new LinkedHashMap<String, String>();
    private Map<String, String> sub_area_group_with_global_treemap = new TreeMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);

        mContext = getApplicationContext();
        textView = (TextView) findViewById(R.id.textView);
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        tableLayoutSubArea = (TableLayout) findViewById(R.id.tableLayoutSubArea);
        tableLayout.setStretchAllColumns(true);
        GameRegional gr = new GameRegional(mContext);
        gr.init();
        GameSubArea gs = new GameSubArea(mContext);
        gs.init();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        initMapVal();
        initGlobalView();
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
        super.onStop();
    }

    private Map<String, ?> getAreaInfoMap(String tableName) {
        SharedPreferences sp = mContext.getSharedPreferences(tableName,
                Context.MODE_PRIVATE);

        TLog.d("ed = " + sp.getAll());
        return sp.getAll();
    }

    private void initGlobalView() {
        TableRow tr = new TableRow(this);
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

}
