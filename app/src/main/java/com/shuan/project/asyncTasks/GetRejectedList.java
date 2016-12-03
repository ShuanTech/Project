package com.shuan.Project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.shuan.Project.adapter.RejectedAdapter;
import com.shuan.Project.list.Sample;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class GetRejectedList extends AsyncTask<String, String, String> {

    private Context mContext;
    private String jId;
    private ListView listView;
    private ProgressBar progressBar;
    private ArrayList<Sample> list;
    private RejectedAdapter adapter;
    private HashMap<String, String> rData;

    public GetRejectedList(Context mContext, String jId, ListView listView, ProgressBar progressBar) {
        this.mContext = mContext;
        this.jId = jId;
        this.listView = listView;
        this.progressBar = progressBar;
        list = new ArrayList<Sample>();
    }

    @Override
    protected String doInBackground(String... params) {
        rData = new HashMap<String, String>();
        rData.put("jId", jId);
        try {
            JSONObject json = Connection.UrlConnection(php.rjct_candidate_list, rData);
            int succ = json.getInt("success");
            if (succ == 0) {
            } else {
                JSONArray jsonArray = json.getJSONArray("job");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject child = jsonArray.getJSONObject(i);
                    String proPic = child.optString("proPic");
                    String name = child.optString("name");
                    String commnts = child.optString("commnts");

                    list.add(new Sample(proPic, name, commnts));
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
        adapter = new RejectedAdapter(mContext, list);
        listView.setAdapter(adapter);
    }
}
