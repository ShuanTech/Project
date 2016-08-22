package com.shuan.project.asyncTasks;


import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.shuan.project.Utils.Common;
import com.shuan.project.adapter.ConnectAdapter;
import com.shuan.project.adapter.PostAdapter;
import com.shuan.project.list.Sample;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GetPost extends AsyncTask<String, String, String> {

    private Context mContext;
    private ListView listView;
    private ProgressBar progressBar;
    private HashMap<String, String> cData;
    private Common mApp;
    private ArrayList<Sample> list;
    private PostAdapter adapter;
    private String u_id;

    public GetPost(Context mContext, ListView listView, ProgressBar progressBar, String u_id) {
        this.mContext = mContext;
        this.listView = listView;
        this.progressBar = progressBar;
        this.u_id = u_id;
        list = new ArrayList<Sample>();
    }

    @Override
    protected String doInBackground(String... params) {
        cData = new HashMap<String, String>();
        cData.put("u_id", u_id);

        try {
            JSONObject json = Connection.UrlConnection(php.get_post, cData);
            int succ = json.getInt("success");
            if (succ == 0) {
            } else {
                JSONArray jsonArray = json.getJSONArray("post");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject child = jsonArray.getJSONObject(i);

                    String cName = child.optString("cmpny_name");
                    String pPic = child.optString("pro_pic");
                    String jId = child.optString("job_id");
                    String jTitle = child.optString("title");
                    String jSkill = child.optString("skill");
                    String jLevel = child.optString("level");
                    String jLoc = child.optString("location");
                    String jDated = child.optString("date_created");
                    String jView = child.optString("viewed");
                    String jShare = child.optString("shared");
                    String jApplied = child.optString("applied");
                    String jFrmId=child.optString("frm_id");

                    list.add(new Sample(cName,pPic,jId,jTitle,jSkill,jLevel,jLoc,jDated,jView,jApplied,jShare,jFrmId));
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
        adapter=new PostAdapter(mContext,list);
        listView.setAdapter(adapter);

    }
}
