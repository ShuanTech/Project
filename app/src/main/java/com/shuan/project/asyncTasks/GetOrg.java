package com.shuan.project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.shuan.project.R;
import com.shuan.project.adapter.OrgAdapter;
import com.shuan.project.list.Sample;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class GetOrg extends AsyncTask<String, String, String> {

    private Context mContext;
    private ProgressBar progressBar;
    private ScrollView scroll;
    private AutoCompleteTextView txt;
    private TextView pin;
    private ArrayList<Sample> list;
    private OrgAdapter adapter;
    private HashMap<String,String> oData;


    public GetOrg(Context mContext, ProgressBar progressBar, ScrollView scroll, AutoCompleteTextView txt) {
        this.mContext = mContext;
        this.progressBar = progressBar;
        this.scroll = scroll;
        this.txt = txt;
        list=new ArrayList<Sample>();
    }

    @Override
    protected String doInBackground(String... params) {

        oData=new HashMap<String, String>();
        oData.put("id","org");

        try{

            JSONObject json= Connection.UrlConnection(php.getOrg,oData);
            int succ=json.getInt("success");

            if(succ==0){}else{
                JSONArray jsonArray=json.getJSONArray("org");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject child=jsonArray.getJSONObject(i);

                    String orgNme=child.optString("cmpny_name");
                    String land=child.optString("city");


                    list.add(new Sample(orgNme+", "+land,orgNme,land));

                }
            }

        }catch (Exception e){}

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressBar.setVisibility(View.GONE);
        scroll.setVisibility(View.VISIBLE);
        txt.setThreshold(3);
        adapter=new OrgAdapter(mContext, R.layout.custom_auto_complete_item,R.id.display,list);
        txt.setAdapter(adapter);
    }
}
