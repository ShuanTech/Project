package com.shuan.project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.shuan.project.R;
import com.shuan.project.list.Sample;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class GetSkillSet extends AsyncTask<String, String, String> {

    private Context mContext;
    private ScrollView scroll;
    private ProgressBar progressBar;
    private MultiAutoCompleteTextView txt;
    private ArrayList<Sample> list;
    private String[] skll = new String[0];
    private HashMap<String, String> seniorData;

    public GetSkillSet(Context mContext, ScrollView scroll, ProgressBar progressBar, MultiAutoCompleteTextView txt) {
        this.mContext = mContext;
        this.scroll = scroll;
        this.progressBar = progressBar;
        this.txt = txt;
        list = new ArrayList<Sample>();
    }

    @Override
    protected String doInBackground(String... params) {
        seniorData = new HashMap<String, String>();
        seniorData.put("id", "skll");
        try {

            JSONObject json = Connection.UrlConnection(php.get_skill, seniorData);
            int succ = json.getInt("success");

            if (succ == 0) {
            } else {
                JSONArray jsonArray = json.getJSONArray("skill");
                skll = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject child = jsonArray.getJSONObject(i);
                    String course = child.optString("skill");
                    skll[i] = course;

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
        txt.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.custom_auto_complete_item, R.id.display, skll);
        txt.setThreshold(1);
        txt.setAdapter(adapter);

    }
}
