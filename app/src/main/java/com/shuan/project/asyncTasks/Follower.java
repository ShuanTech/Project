package com.shuan.Project.asyncTasks;


import android.content.Context;
import android.os.AsyncTask;

import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONObject;

import java.util.HashMap;

public class Follower extends AsyncTask<String, String, String> {

    private Context mContext;
    private String uId,cmpnyName, distrit;
    private HashMap<String, String> cData;

    public Follower(Context mContext, String uId,String cmpnyName, String distrit) {
        this.mContext = mContext;
        this.uId=uId;
        this.cmpnyName = cmpnyName;
        this.distrit = distrit;

    }

    @Override
    protected String doInBackground(String... params) {

        cData = new HashMap<String, String>();
        cData.put("u_id",uId);
        cData.put("cName", cmpnyName);
        cData.put("cDistrict", distrit);
        try {
            JSONObject json = Connection.UrlConnection(php.followers, cData);
            int succ = json.getInt("success");

        } catch (Exception e) {
        }

        return null;
    }
}
