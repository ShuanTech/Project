package com.shuan.Project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.shuan.Project.adapter.SerachAdapter;
import com.shuan.Project.list.Sample;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

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
    private String uId;

    public Search(Context mContext, String uId, ListView list, ProgressBar progressBar, EditText search) {
        this.mContext = mContext;
        this.uId = uId;
        this.list = list;
        this.progressBar = progressBar;
        this.search = search;
        sList = new ArrayList<Sample>();
    }

    @Override
    protected String doInBackground(String... params) {

        sData = new HashMap<String, String>();
        sData.put("id", uId);
        try {

            JSONObject json = Connection.UrlConnection(php.serach_critria, sData);
            int succ = json.getInt("success");

            if (succ == 0) {
            } else {
                JSONArray jsonArray = json.getJSONArray("search");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject child = jsonArray.getJSONObject(i);

                    String uId = child.optString("u_id");
                    String proPic=child.optString("pro_pic");
                    String full_name = child.optString("full_name");
                    String level = child.optString("level");
                    String sec=child.optString("sec");

                    if (!sec.equalsIgnoreCase("")) {
                        sList.add(new Sample(uId, proPic, full_name, level, sec));
                    }
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
                String str = search.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(str);
            }
        });
    }
}
