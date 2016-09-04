package com.shuan.project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.shuan.project.adapter.JobAdapter;
import com.shuan.project.list.Sample;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class GetJobDetail extends AsyncTask<String, String, String> {

    private Context mContext;
    private String uId, type,s;
    private ListView listView;
    private ProgressBar progressBar;
    private HashMap<String, String> jData;
    private JobAdapter adapter;
    private ArrayList<Sample> list;

    public GetJobDetail(Context mContext, String uId, ListView listView, ProgressBar progressBar,String type) {
        this.mContext = mContext;
        this.uId = uId;
        this.listView = listView;
        this.progressBar = progressBar;
        this.type=type;
        list = new ArrayList<Sample>();
    }

    @Override
    protected String doInBackground(String... params) {

        jData = new HashMap<String, String>();
        jData.put("u_id", uId);
        jData.put("type",type);
        try {

            JSONObject json = Connection.UrlConnection(php.job_detail, jData);
            int succ = json.getInt("success");

            if (succ == 0) {
            } else {
                JSONArray jsonArray = json.getJSONArray("job");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject child = jsonArray.getJSONObject(i);
                    String job_id = child.optString("job_id");
                    String title = child.optString("title");
                    String viewed = child.optString("viewed");
                    String shared = child.optString("shared");
                    String applied = child.optString("applied");
                    String close=child.optString("close");

                    list.add(new Sample(job_id, title, viewed, shared, applied,close));
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
        listView.setVisibility(View.VISIBLE);
        adapter=new JobAdapter(mContext,list);
        listView.setAdapter(adapter);

    }
}
