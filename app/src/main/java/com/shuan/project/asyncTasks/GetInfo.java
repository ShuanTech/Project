package com.shuan.Project.asyncTasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.shuan.Project.Utils.Common;
import com.shuan.Project.launcher.LoginActivity;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

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
    private String name, pPic, cPic, pass, level, connect, follow, follower, alrt, s="";

    public GetInfo(Context mContext, String u_id) {
        this.mContext = mContext;
        this.u_id = u_id;
        mApp = (Common) mContext.getApplicationContext();
    }

    @Override
    protected String doInBackground(String... params) {
        iData = new HashMap<String, String>();
        iData.put("u_id", u_id);
        try {
            JSONObject json = Connection.UrlConnection(php.getUser, iData);
            int succ = json.getInt("success");

            if (succ == 0) {
                s = "false";
            } else {
                JSONArray jsonArray = json.getJSONArray("info");
                JSONObject child = jsonArray.getJSONObject(0);

                name = child.optString("full_name");
                pPic = child.optString("pro_pic");
                cPic = child.optString("cover_pic");
                pass = child.optString("passwrd");
                level = child.optString("level");
                connect = child.optString("connection");
                follow = child.optString("following");
                follower = child.optString("follower");
                alrt = child.optString("alert");
                s = "true";


            }

        } catch (Exception e) {
        }
        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s.equalsIgnoreCase("true")) {
            mApp.getPreference().edit().putString(Common.FULLNAME, name).commit();
            mApp.getPreference().edit().putString(Common.PROPIC, pPic).commit();
            mApp.getPreference().edit().putString(Common.COVER, cPic).commit();
            mApp.getPreference().edit().putString(Common.PASSWRD, pass).commit();
            mApp.getPreference().edit().putString(Common.CONNECTION, connect).commit();
            mApp.getPreference().edit().putString(Common.FOLLOWER, follower).commit();
            mApp.getPreference().edit().putString(Common.FOLLOWING, follow).commit();
            mApp.getPreference().edit().putString(Common.ALERT, alrt).commit();
        } else {
            Toast.makeText(mContext, "Something Wrong. Please Login", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(mContext, LoginActivity.class);
            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(in);
            ((AppCompatActivity)mContext).finish();

        }
    }
}
