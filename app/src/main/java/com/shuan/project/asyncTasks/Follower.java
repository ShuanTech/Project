package com.shuan.project.asyncTasks;


import android.content.Context;
import android.os.AsyncTask;

import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONObject;

import java.util.HashMap;

public class Follower extends AsyncTask<String, String, String> {

    private Context mContext;
    private String uId, cmpnyName;
    private HashMap<String, String> cData;

    public Follower(Context mContext, String uId) {
        this.mContext = mContext;
        this.uId = uId;

    }

    @Override
    protected String doInBackground(String... params) {

        cData = new HashMap<String, String>();
        cData.put("u_id", uId);

        try {
            JSONObject json = Connection.UrlConnection(php.followers, cData);
            int succ = json.getInt("success");

        } catch (Exception e) {
        }

        return null;
    }
}
