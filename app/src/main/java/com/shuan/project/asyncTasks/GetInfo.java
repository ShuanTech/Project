package com.shuan.project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.shuan.project.Utils.Common;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Android on 8/6/2016.
 */
public class GetInfo extends AsyncTask<String, String, String> {

    private Common mApp;
    private Context mContext;
    private HashMap<String, String> iData;
    private String u_id;

    public GetInfo(Context mContext, String u_id) {
        mApp = (Common) mContext.getApplicationContext();
        this.mContext = mContext;
        this.u_id = u_id;
    }

    @Override
    protected String doInBackground(String... params) {
        iData = new HashMap<String, String>();
        iData.put("u_id", u_id);
        try {
            JSONObject json = Connection.UrlConnection(php.getUser, iData);
            int succ = json.getInt("success");

            if (succ == 0) {
            } else {
                JSONArray jsonArray = json.getJSONArray("info");
                JSONObject child = jsonArray.getJSONObject(0);
                mApp.getPreference().edit().putString(Common.FULLNAME, child.optString("full_name")).commit();
                mApp.getPreference().edit().putString(Common.PROPIC, child.optString("pro_pic")).commit();
                mApp.getPreference().edit().putString(Common.COVER, child.optString("cover_pic")).commit();
                mApp.getPreference().edit().putString(Common.CONNECTION, child.optString("connection")).commit();
                mApp.getPreference().edit().putString(Common.FOLLOWER, child.optString("follower")).commit();
                mApp.getPreference().edit().putString(Common.FOLLOWING, child.optString("following")).commit();
            }

        } catch (Exception e) {
        }
        return null;
    }
}
