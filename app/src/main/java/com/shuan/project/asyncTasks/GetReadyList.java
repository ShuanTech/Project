package com.shuan.Project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.shuan.Project.Utils.Common;
import com.shuan.Project.adapter.GetReadyAdapter;
import com.shuan.Project.list.Sample;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class GetReadyList extends AsyncTask<String, String, String> {

    private Context mContext;
    private ListView listView;
    private ProgressBar progressBar;
    private HashMap<String, String> cData;
    private Common mApp;
    private ArrayList<Sample> list;
    private GetReadyAdapter adapter;
    private String u_id, s;


    public GetReadyList(Context mContext, ListView listView, ProgressBar progressBar, String id) {
        this.mContext = mContext;
        this.listView = listView;
        this.progressBar = progressBar;
        this.u_id = id;
        list = new ArrayList<Sample>();
    }

    @Override
    protected String doInBackground(String... params) {

        cData = new HashMap<String, String>();
        cData.put("u_id", u_id);

        try {

            JSONObject json = Connection.UrlConnection(php.ready, cData);
            int succ = json.getInt("success");
            if (succ == 0) {
                s = "false";
            } else {
                JSONArray jsonArray = json.getJSONArray("ready");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject child = jsonArray.getJSONObject(i);

                    String title = child.optString("title");
                    String job_id = child.optString("job_id");
                    String type = child.optString("type");

                    list.add(new Sample(job_id,title,type));
                    s="true";
                }
            }
        } catch (Exception e) {
        }

        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressBar.setVisibility(View.GONE);
        if (s.equalsIgnoreCase("true")) {
            listView.setVisibility(View.VISIBLE);
            adapter = new GetReadyAdapter(mContext, list);
            listView.setAdapter(adapter);
        } else {
            Toast.makeText(mContext, "No Data", Toast.LENGTH_SHORT).show();
        }


    }
}
