package com.shuan.Project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.shuan.Project.R;
import com.shuan.Project.adapter.LocationAdapter;
import com.shuan.Project.list.Sample;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class GetLocation extends AsyncTask<String, String, String> {

    private Context mContext;
    private ScrollView scroll;
    private AutoCompleteTextView loc;
    private ProgressBar progressBar;
    private ArrayList<Sample> list;
    private LocationAdapter adapter;
    private HashMap<String, String> pData;

    public GetLocation(Context mContext, ScrollView scroll, AutoCompleteTextView loc, ProgressBar progressBar) {
        this.mContext = mContext;
        this.scroll = scroll;
        this.loc = loc;
        this.progressBar = progressBar;
        list=new ArrayList<Sample>();
    }

    @Override
    protected String doInBackground(String... params) {
        pData = new HashMap<String, String>();
        pData.put("id", "city");
        try {

            JSONObject json = Connection.UrlConnection(php.getCity, pData);
            int succ = json.getInt("success");

            if (succ == 0) {
            } else {

                JSONArray jsonArray = json.getJSONArray("city");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject child = jsonArray.getJSONObject(i);
                    String xcty = child.optString("city");
                    String distrt = child.optString("district");
                    String stea = child.optString("state");
                    String con = child.optString("country");

                    list.add(new Sample(xcty + "," + distrt, xcty, distrt, stea, con));
                }


            }

        } catch (Exception e) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressBar.setVisibility(View.GONE);
        scroll.setVisibility(View.VISIBLE);
        adapter = new LocationAdapter(mContext, R.layout.custom_auto_complete_item, R.id.display, list);
        loc.setThreshold(1);
        loc.setAdapter(adapter);
    }
}
