package com.shuan.Project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.shuan.Project.adapter.NotifyAdapter;
import com.shuan.Project.list.Sample;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class GetNotifyDetail extends AsyncTask<String, String, String> {

    private Context mContext;
    private String uId, s;
    private ListView listView;
    private ProgressBar progressBar;
    private ArrayList<Sample> list;
    private NotifyAdapter adapter;
    private HashMap<String, String> nData;

    public GetNotifyDetail(Context mContext, String uId, ListView listView, ProgressBar progressBar, SwipeRefreshLayout swipe) {
        this.mContext = mContext;
        this.uId = uId;
        this.listView = listView;
        this.progressBar = progressBar;
        list = new ArrayList<Sample>();
    }

    @Override
    protected String doInBackground(String... params) {

        nData = new HashMap<String, String>();
        nData.put("u_id", uId);
        try {
            JSONObject json = Connection.UrlConnection(php.notify_list, nData);

            int succ = json.getInt("success");
            if (succ == 0) {
                s = "false";
            } else {
                JSONArray jsonArray = json.getJSONArray("notify");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject child = jsonArray.getJSONObject(i);
                    String id=child.optString("id");
                    String frmId = child.optString("frm_id");
                    String to_id = child.optString("to_id");
                    String post_id = child.optString("post_id");
                    String content = child.optString("content");
                    String type = child.optString("type");
                    String vwed=child.optString("vwed");
                    String level=child.optString("level");

                    list.add(new Sample(id,frmId, to_id, post_id, content, type,vwed,level));
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
        if(s.equalsIgnoreCase("true")){
            listView.setVisibility(View.VISIBLE);
            adapter = new NotifyAdapter(mContext, list);
            listView.setAdapter(adapter);
        }else{
            Toast.makeText(mContext,"No Notifications yet",Toast.LENGTH_SHORT).show();
        }


    }
}
