package com.shuan.project.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.shuan.project.adapter.NotifyAdapter;
import com.shuan.project.list.Sample;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class GetNotifyDetail extends AsyncTask<String, String, String> {

    private Context mContext;
    private String uId;
    private ListView listView;
    private ProgressBar progressBar;
    private ArrayList<Sample> list;
    private NotifyAdapter adapter;
    private HashMap<String, String> nData;

    public GetNotifyDetail(Context mContext, String uId, ListView listView, ProgressBar progressBar) {
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
            } else {
                JSONArray jsonArray = json.getJSONArray("notify");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject child = jsonArray.getJSONObject(i);
                    String title = child.optString("title");
                    String frm_id = child.optString("frm_id");
                    String post_id = child.optString("post_id");
                    String content = child.optString("content");
                    String type = child.optString("type");

                    list.add(new Sample(title, frm_id, post_id,content, type));
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
        adapter = new NotifyAdapter(mContext, list);
        listView.setAdapter(adapter);

    }
}
