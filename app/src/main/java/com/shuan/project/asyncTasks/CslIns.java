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
import com.shuan.Project.signup.MailVerify;

import org.json.JSONObject;

import java.util.HashMap;


public class CslIns extends AsyncTask<String, String, String> {

    private Context mContext;
    private Common mApp;
    private String uId, name, qualify, clgName, conCent, frmYr, toYr, skill, loc, s;
    private boolean ins, cIns;
    private ProgressDialog pDialog;
    private HashMap<String, String> csData;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public CslIns(Context mContext, String uId, String name, String qualify, String clgName, String conCent, String frmYr, String toYr, String skill, String loc, boolean ins, boolean cIns) {
        this.mContext = mContext;
        this.uId = uId;
        this.name = name;
        this.qualify = qualify;
        this.clgName = clgName;
        this.conCent = conCent;
        this.frmYr = frmYr;
        this.toYr = toYr;
        this.skill = skill;
        this.loc = loc;
        this.ins = ins;
        this.cIns = cIns;
        mApp = (Common) mContext.getApplicationContext();
    }

    @Override
    protected String doInBackground(String... params) {

        csData = new HashMap<String, String>();
        csData.put("u_id", uId);
        csData.put("name", name);
        csData.put("qualify", qualify);
        csData.put("clgName", clgName);
        csData.put("conCent", conCent);
        csData.put("frmYr", frmYr);
        csData.put("toYr", toYr);
        csData.put("skill", skill);
        csData.put("loc", loc);
        if (ins == true) {
            csData.put("insrt", "false");
        } else {
            csData.put("insrt", "true");
        }

        if (cIns == true) {
            csData.put("cInsrt", "false");
        } else {
            csData.put("cInsrt", "true");
        }

        try {
            JSONObject json = Connection.UrlConnection(php.cslIns, csData);
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
        pDialog.dismiss();
        if (s.equalsIgnoreCase("true")) {
            Intent in = null;
            mApp.getPreference().edit().putBoolean(Common.PAGE1, true).commit();
            new Connect(mContext, mApp.getPreference().getString(Common.u_id, ""),
                    mApp.getPreference().getString(Common.LEVEL, ""),
                    clgName, conCent,loc,skill).execute();
            new GetInfo(mContext, mApp.getPreference().getString(Common.u_id, "")).execute();
            //new FrsherDefault(mContext, mApp.getPreference().getString(Common.u_id, ""), loc).execute();
            mApp.getPreference().edit().putBoolean(Common.QUALIFICATION, true).commit();
            mApp.getPreference().edit().putBoolean(Common.HSC, false).commit();
            mApp.getPreference().edit().putBoolean(Common.SSLC, false).commit();
            mApp.getPreference().edit().putBoolean(Common.SKILL, true).commit();
            mApp.getPreference().edit().putBoolean("start", false).commit();
            mApp.getPreference().edit().putBoolean(Common.PERSONALINFO, true).commit();
            mApp.getPreference().edit().putBoolean(Common.HOBBIES, false).commit();
            mApp.getPreference().edit().putBoolean(Common.PROJECT, false).commit();
            mApp.getPreference().edit().putBoolean(Common.USRINFO, true).commit();

            int val = mApp.getPreference().getInt(Common.PROFILESTRENGTH, 0);
            mApp.getPreference().edit().putInt(Common.PROFILESTRENGTH, val + 15).commit();

            if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("1")) {
                mApp.getPreference().edit().putString(Common.RESUME, "junior").commit();
                in = new Intent(mContext, MailVerify.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            } else {
                mApp.getPreference().edit().putString(Common.RESUME, "senior").commit();
                mApp.getPreference().edit().putBoolean(Common.PROFILESUMMARY, false).commit();
                mApp.getPreference().edit().putBoolean(Common.WORKEXPERIENCE, false).commit();
                in = new Intent(mContext, MailVerify.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            mContext.startActivity(in);
            ((AppCompatActivity) mContext).finish();
        } else {
            Toast.makeText(mContext, "Failed. Try Again", Toast.LENGTH_SHORT).show();
        }

    }
}
