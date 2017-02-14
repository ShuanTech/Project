package com.shuan.Project.asyncTasks;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.shuan.Project.Utils.Common;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONObject;

import java.util.HashMap;

public class RejectCandidate extends AsyncTask<String, String, String> {

    private Context mContext;
    private String aId, txt, s;
    private HashMap<String, String> rData;
    private Common mApp;
    private ProgressDialog pDialog;


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public RejectCandidate(Context mContext, String aId, String txt) {
        this.mContext = mContext;
        this.aId = aId;
        this.txt = txt;
        mApp = (Common) mContext.getApplicationContext();
    }

    @Override
    protected String doInBackground(String... params) {
        rData = new HashMap<String, String>();
        rData.put("a_id", aId);
        rData.put("txt", txt);
        try {

            JSONObject json = Connection.UrlConnection(php.rjct_candidate, rData);
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
            Toast.makeText(mContext, "Successfully Rejected", Toast.LENGTH_SHORT).show();
            new GetInfo(mContext, mApp.getPreference().getString(Common.u_id, "")).execute();
            AppCompatActivity activity = (AppCompatActivity) mContext;
            Intent in = activity.getIntent();
            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.finish();
            mContext.startActivity(in);
        } else {
            Toast.makeText(mContext, "Something Wrong! Try Again", Toast.LENGTH_SHORT).show();
        }
    }
}
