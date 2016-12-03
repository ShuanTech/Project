package com.shuan.Project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.shuan.Project.adapter.AppliedDetailAdapter;
import com.shuan.Project.list.Sample;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class GetAppliedDetail extends AsyncTask<String, String, String> {

    private Context mContext;
    private String jId;
    private ListView listView;
    private ProgressBar progressBar;
    private HashMap<String, String> aData;
    private ArrayList<Sample> list;
    private AppliedDetailAdapter adapter;
    private String s = "";


    public GetAppliedDetail(Context mContext, String jId, ListView listView, ProgressBar progressBar) {
        this.mContext = mContext;
        this.jId = jId;
        this.listView = listView;
        this.progressBar = progressBar;
        list = new ArrayList<Sample>();
    }

    @Override
    protected String doInBackground(String... params) {
        aData = new HashMap<String, String>();
        aData.put("jId", jId);
        try {
            JSONObject json = Connection.UrlConnection(php.shrt_list, aData);

            int succ = json.getInt("success");

            if (succ == 0) {
                s = "false";
            } else {

                JSONArray jsonArray = json.getJSONArray("job");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject child = jsonArray.getJSONObject(i);
                    String applied = child.optString("applied");
                    String refer = child.optString("refer");
                    String a_id = child.optString("applied_usr");
                    String r_id = child.optString("refer_id");
                    String resume = child.optString("resume");


                    list.add(new Sample(applied, refer, a_id, r_id, resume));
                }
                s = "true";

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
            adapter = new AppliedDetailAdapter(mContext, list);
            listView.setAdapter(adapter);
        } else {
            Toast.makeText(mContext, "No Data Found", Toast.LENGTH_SHORT).show();
        }

    }
}
