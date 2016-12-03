package com.shuan.Project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.shuan.Project.Utils.Common;
import com.shuan.Project.adapter.EmplySearchResAdapter;
import com.shuan.Project.list.Sample;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class EmployeeSerchResult extends AsyncTask<String, String, String> {
    private Context mContext;
    private ListView listView;
    private ProgressBar progressBar;
    private HashMap<String, String> cData;
    private Common mApp;
    private ArrayList<Sample> list;
    private EmplySearchResAdapter adapter;
    private String u_id, type,s;


    public EmployeeSerchResult(Context mContext, ProgressBar progressBar, ListView listView, String u_id, String type) {
        this.mContext = mContext;
        this.listView = listView;
        this.progressBar = progressBar;
        this.u_id = u_id;
        this.type = type;
        list = new ArrayList<Sample>();
        mApp = (Common) mContext.getApplicationContext();
    }

    @Override
    protected String doInBackground(String... params) {
        cData = new HashMap<String, String>();
        cData.put("ser", u_id);
        cData.put("type", type);

        try {
            JSONObject json = Connection.UrlConnection(php.emplySerResult, cData);
            int succ = json.getInt("success");
            if (succ == 0) {
                s="false";
            } else {
                JSONArray jsonArray = json.getJSONArray("result");
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


                    list.add(new Sample(cName, pPic, jId, jTitle, jSkill, jLevel, jLoc, jDated, jView, jApplied, jShare));
                }
                s="true";
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
            adapter = new EmplySearchResAdapter(mContext, list);
            listView.setAdapter(adapter);
        }else{
            Toast.makeText(mContext,"No Data Found",Toast.LENGTH_SHORT).show();
        }


    }
}
