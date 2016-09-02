package com.shuan.project.asyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.shuan.project.Utils.Common;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;
import com.shuan.project.resume.ResumeEditActivity;

import org.json.JSONObject;

import java.util.HashMap;


public class AddProject extends AsyncTask<String, String, String> {

    private Context mContext;
    private String uId, title, pltfrm, role, teamSze, dur, url, desc, isAcd,type, s;
    private HashMap<String, String> seniorData;
    private ProgressDialog pDialog;
    private Common mApp;


    public AddProject(Context mContext, String uId, String title, String pltfrm, String role, String teamSze, String dur, String url,
                      String desc, String isAcd,String type) {
        this.mContext = mContext;
        this.uId = uId;
        this.title = title;
        this.pltfrm = pltfrm;
        this.role = role;
        this.teamSze = teamSze;
        this.dur = dur;
        this.url = url;
        this.desc = desc;
        this.isAcd=isAcd;
        this.type = type;
        this.mApp= (Common) mContext.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Adding Project Detail");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }


    @Override
    protected String doInBackground(String... params) {
        seniorData = new HashMap<String, String>();
        seniorData.put("u_id", uId);
        seniorData.put("title", title);
        seniorData.put("platform", pltfrm);
        seniorData.put("role", role);
        seniorData.put("teamSze", teamSze);
        seniorData.put("dur", dur);
        seniorData.put("desc", desc);
        seniorData.put("url", url);
        seniorData.put("isAcd",isAcd);
        seniorData.put("type", type);

        try {
            JSONObject json = Connection.UrlConnection(php.project, seniorData);
            int succ = json.getInt("success");
            if (succ == 0) {
                s = "false";
            } else {
                s = "true";
            }
        } catch (Exception e) {
        }

        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pDialog.cancel();
        if (s.equalsIgnoreCase("true")) {
            int val=mApp.getPreference().getInt(Common.PROFILESTRENGTH,0);
            mApp.getPreference().edit().putInt(Common.PROFILESTRENGTH, val+5).commit();
            Toast.makeText(mContext, "Successfully Project Details Added", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(mContext, ResumeEditActivity.class);
            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(in);
            ((AppCompatActivity) mContext).finish();
        } else {
            Toast.makeText(mContext, "Something went wrong!... Try Again", Toast.LENGTH_SHORT).show();
        }
    }
}
