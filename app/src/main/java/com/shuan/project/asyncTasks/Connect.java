package com.shuan.project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.shuan.project.Utils.Common;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONObject;

import java.util.HashMap;


public class Connect extends AsyncTask<String, String, String> {

    private Common mApp;
    private Context mContext;
    private String u_id, level,clgName,course;
    private HashMap<String, String> cData;

    public Connect(Context mContext, String u_id, String level,String clgName,String course) {
        this.mContext = mContext;
        this.u_id = u_id;
        this.level = level;
        this.clgName=clgName;
        this.course=course;
    }

    @Override
    protected String doInBackground(String... params) {
        cData = new HashMap<String, String>();

        cData.put("u_id", u_id);
        cData.put("level",level);
        cData.put("clgName",clgName);
        cData.put("course",course);

        try {
            JSONObject json = Connection.UrlConnection(php.defaultFollow, cData);
            int succ = json.getInt("success");
        } catch (Exception e) {
        }
        return null;
    }
}
