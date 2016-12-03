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
import com.shuan.Project.resume.ResumeEditActivity;

import org.json.JSONObject;

import java.util.HashMap;

public class AddSchool extends AsyncTask<String, String, String> {

    private Context mContext;
    private String uId, level, conCent, sName, sBoard, sCty, frm, to, agrt, type, s="";
    private boolean ins;
    private ProgressDialog pDialog;
    private HashMap<String, String> seniorData;
    private Common mApp;

    public AddSchool(Context mContext, String uId, String level, String conCent, String sName, String sBoard, String sCty, String frm, String to, String agrt, String type, boolean ins) {
        this.mContext = mContext;
        this.uId = uId;
        this.level = level;
        this.conCent = conCent;
        this.sName = sName;
        this.sBoard = sBoard;
        this.sCty = sCty;
        this.frm = frm;
        this.to = to;
        this.agrt = agrt;
        this.type = type;
        this.ins = ins;
        this.mApp = (Common) mContext.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Adding Schooling!...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        seniorData = new HashMap<String, String>();
        seniorData.put("u_id", uId);
        seniorData.put("level", level);
        seniorData.put("concent", conCent);
        seniorData.put("hName", sName);
        seniorData.put("bName", sBoard);
        seniorData.put("cty", sCty);
        seniorData.put("frmDat", frm);
        seniorData.put("toDat", to);
        seniorData.put("agrt", agrt);
        if (ins == true) {
            seniorData.put("insrt", "false");
        } else {
            seniorData.put("insrt", "true");
        }
        seniorData.put("type", type);

        try {
            JSONObject json = Connection.UrlConnection(php.schooling, seniorData);

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
            if(type.equalsIgnoreCase("add")){
                int val = mApp.getPreference().getInt(Common.PROFILESTRENGTH, 0);
                mApp.getPreference().edit().putInt(Common.PROFILESTRENGTH, val + 1).commit();
            }
            if (level.equalsIgnoreCase("4")) {
                mApp.getPreference().edit().putBoolean(Common.HSC, true).commit();
                int val=mApp.getPreference().getInt(Common.PROFILESTRENGTH,0);
                mApp.getPreference().edit().putInt(Common.PROFILESTRENGTH, val+5).commit();
            } else {
                mApp.getPreference().edit().putBoolean(Common.SSLC, true).commit();
                int val=mApp.getPreference().getInt(Common.PROFILESTRENGTH,0);
                mApp.getPreference().edit().putInt(Common.PROFILESTRENGTH, val+5).commit();
            }
            Toast.makeText(mContext, "Successfully Updated", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(mContext, ResumeEditActivity.class);
            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(in);
            ((AppCompatActivity) mContext).finish();
        } else {
            Toast.makeText(mContext, "Something Wrong!... Try Again...", Toast.LENGTH_SHORT).show();
        }
    }
}

