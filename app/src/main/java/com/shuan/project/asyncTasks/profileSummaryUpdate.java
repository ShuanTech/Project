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


public class profileSummaryUpdate extends AsyncTask<String, String, String> {

    private Context mContext;
    private String uId;
    private String profile, table, s;
    private ProgressDialog pDialog;
    private HashMap<String, String> seniorData;
    private Common mApp;


    public profileSummaryUpdate(Context mContext, String uId, String profile, String table) {
        this.mContext = mContext;
        this.uId = uId;
        this.profile = profile;
        this.table = table;
        this.mApp = (Common) mContext.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Updating Profile Summary");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }


    @Override
    protected String doInBackground(String... params) {

        seniorData = new HashMap<String, String>();
        seniorData.put("u_id", uId);
        seniorData.put("summary", profile);
        seniorData.put("table", table);
        try {
            JSONObject json = Connection.UrlConnection(php.profile_summary, seniorData);
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
            if (table.equalsIgnoreCase("skill")) {
                mApp.getPreference().edit().putBoolean(Common.SKILL, true).commit();
                int val=mApp.getPreference().getInt(Common.PROFILESTRENGTH,0);
                mApp.getPreference().edit().putInt(Common.PROFILESTRENGTH, val+1).commit();
            }else if(table.equalsIgnoreCase("proSum")){
                mApp.getPreference().edit().putBoolean(Common.PROFILESUMMARY, true).commit();
                int val=mApp.getPreference().getInt(Common.PROFILESTRENGTH,0);
                mApp.getPreference().edit().putInt(Common.PROFILESTRENGTH, val+1).commit();
            }else if(table.equalsIgnoreCase("wrkExp")){
                mApp.getPreference().edit().putBoolean(Common.WORKEXPERIENCE, true).commit();
                int val=mApp.getPreference().getInt(Common.PROFILESTRENGTH,0);
                mApp.getPreference().edit().putInt(Common.PROFILESTRENGTH, val+1).commit();
            }
            Toast.makeText(mContext, "Successfully Update Profile Summary", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(mContext, ResumeEditActivity.class);
            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(in);
            ((AppCompatActivity) mContext).finish();
        } else {
            Toast.makeText(mContext, "Try Again Not updated", Toast.LENGTH_SHORT).show();
        }

    }
}
