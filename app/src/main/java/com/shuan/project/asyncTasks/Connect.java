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
    private String u_id, clgName, course;
    private HashMap<String, String> cData;

    public Connect(Context mContext, String u_id) {
        this.mContext = mContext;
        mApp = (Common) mContext;
        this.u_id = u_id;
    }

    @Override
    protected String doInBackground(String... params) {
        cData = new HashMap<String, String>();

        cData.put("u_id", u_id);

        try {
            JSONObject json = Connection.UrlConnection(php.connect, cData);
            int succ = json.getInt("success");
        } catch (Exception e) {
        }
        return null;
    }
}
