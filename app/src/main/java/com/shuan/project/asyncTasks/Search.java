package com.shuan.project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.shuan.project.adapter.SerachAdapter;
import com.shuan.project.list.Sample;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;


public class Search extends AsyncTask<String, String, String> {

    private Context mContext;
    private ListView list;
    private ProgressBar progressBar;
    private EditText search;
    private HashMap<String, String> sData;
    private ArrayList<Sample> sList;
    private SerachAdapter adapter;

    public Search(Context mContext, ListView list, ProgressBar progressBar,EditText search) {
        this.mContext = mContext;
        this.list = list;
        this.progressBar = progressBar;
        this.search=search;
        sList = new ArrayList<Sample>();
    }

    @Override
    protected String doInBackground(String... params) {

        sData = new HashMap<String, String>();
        sData.put("id", "karthi");
        try {

            JSONObject json = Connection.UrlConnection(php.serach_critria, sData);
            int succ = json.getInt("success");

            if (succ == 0) {
            } else {
                JSONArray jsonArray = json.getJSONArray("search");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject child = jsonArray.getJSONObject(i);

                    String uId = child.optString("u_id");
                    String full_name = child.optString("full_name");
                    String level = child.optString("level");

                    sList.add(new Sample(uId, full_name, level));
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
        list.setVisibility(View.VISIBLE);
        adapter = new SerachAdapter(mContext, sList);
        list.setAdapter(adapter);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str=search.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(str);
            }
        });
    }
}
