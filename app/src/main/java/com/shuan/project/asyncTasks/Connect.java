package com.shuan.Project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.shuan.Project.Utils.Common;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONObject;

import java.util.HashMap;


public class Connect extends AsyncTask<String, String, String> {

    private Common mApp;
    private Context mContext;
    private String u_id, level,clgName,course,loc,skill,s;
    private HashMap<String, String> cData;

    public Connect(Context mContext, String u_id, String level,String clgName,String course,String loc,String skill) {
        this.mContext = mContext;
        this.u_id = u_id;
        this.level = level;
        this.clgName=clgName;
        this.course=course;
        this.loc=loc;
        this.skill=skill;
    }

    @Override
    protected String doInBackground(String... params) {
        cData = new HashMap<String, String>();

        cData.put("u_id", u_id);
        cData.put("level",level);
        cData.put("clgName",clgName);
        cData.put("course",course);
        cData.put("loc",loc);
        cData.put("skill",skill);
        try {
            JSONObject json = Connection.UrlConnection(php.defaultFollow, cData);
            int succ = json.getInt("success");

        } catch (Exception e) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }
}
