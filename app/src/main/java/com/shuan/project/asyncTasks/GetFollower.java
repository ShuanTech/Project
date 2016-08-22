package com.shuan.project.asyncTasks;


import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.shuan.project.Utils.Common;
import com.shuan.project.adapter.ConnectAdapter;
import com.shuan.project.list.Sample;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GetFollower extends AsyncTask<String,String,String>{

    private Context mContext;
    private ListView listView;
    private ProgressBar progressBar;
    private HashMap<String, String> cData;
    private Common mApp;
    private ArrayList<Sample> list;
    private ConnectAdapter adapter;
    private String u_id;

    public GetFollower(Context mContext, ListView listView, ProgressBar progressBar,String id) {
        this.mContext = mContext;
        this.listView = listView;
        this.progressBar = progressBar;
        this.u_id=id;
        list=new ArrayList<Sample>();
    }

    @Override
    protected String doInBackground(String... params) {
        cData = new HashMap<String, String>();
        cData.put("u_id",u_id);

        try {
            JSONObject json= Connection.UrlConnection(php.get_followers,cData);
            int succ=json.getInt("success");
            if(succ==0){
                Toast.makeText(mContext,"No Data",Toast.LENGTH_SHORT).show();
            }else{
                JSONArray jsonArray=json.getJSONArray("follower");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject child=jsonArray.getJSONObject(i);

                    String u_id=child.optString("u_id");
                    String pro_pic=child.optString("pro_pic");
                    String name=child.optString("full_name");
                    String level=child.optString("level");
                    String position=child.optString("position");
                    String org_name=child.optString("org_name");
                    list.add(new Sample(u_id,pro_pic,name,level,position,org_name));
                }

            }
        }catch (Exception e){}
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressBar.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        adapter=new ConnectAdapter(mContext,list);
        listView.setAdapter(adapter);

    }
}
