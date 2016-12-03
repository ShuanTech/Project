package com.shuan.Project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.shuan.Project.R;
import com.shuan.Project.adapter.InstitutionAdapter;
import com.shuan.Project.list.Sample;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class GetSchool extends AsyncTask<String, String, String> {

    private Context mContext;
    private ScrollView scroll;
    private ProgressBar progressBar;
    private AutoCompleteTextView txt;
    private ArrayList<Sample> list;
    private InstitutionAdapter adapter;
    private HashMap<String, String> seniorData;

    public GetSchool(Context mContext, ScrollView scroll, ProgressBar progressBar, AutoCompleteTextView txt) {
        this.mContext = mContext;
        this.scroll = scroll;
        this.progressBar = progressBar;
        this.txt = txt;
        list = new ArrayList<Sample>();
    }

    @Override
    protected String doInBackground(String... params) {
        seniorData = new HashMap<String, String>();
        seniorData.put("id", "school");
        try {
            JSONObject json = Connection.UrlConnection(php.getSchool, seniorData);
            int succ = json.getInt("success");
            if (succ == 0) {
            } else {

                JSONArray jsonArray = json.getJSONArray("school");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject child = jsonArray.getJSONObject(i);

                    String insName = child.optString("ins_name");
                    String board = child.optString("board");
                    String location = child.optString("location");

                    list.add(new Sample(insName + "," + location, insName, board, location));
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
        adapter = new InstitutionAdapter(mContext, R.layout.custom_auto_complete_item, R.id.display, list);
        txt.setThreshold(1);
        txt.setAdapter(adapter);
    }
}
