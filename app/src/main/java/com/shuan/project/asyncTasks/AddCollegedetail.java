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


public class AddCollegedetail extends AsyncTask<String, String, String> {

    private Context mContext;
    private String uId, level, course, clg, univ, loc, frm, to, agrt, s = "", type;
    private boolean ins, cIns;
    private HashMap<String, String> sData;
    private ProgressDialog pDialog;
    private Common mApp;

    public AddCollegedetail(Context mContext, String uId, String level, String course, String clg, String univ, String loc, String frm,
                            String to, String agrt, boolean ins, boolean cIns, String type) {
        this.mContext = mContext;
        this.uId = uId;
        this.level = level;
        this.course = course;
        this.clg = clg;
        this.univ = univ;
        this.loc = loc;
        this.frm = frm;
        this.to = to;
        this.agrt = agrt;
        this.ins = ins;
        this.cIns = cIns;
        this.type = type;
        this.mApp = (Common) mContext.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Adding Qualification!...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {

        sData = new HashMap<String, String>();
        sData.put("u_id", uId);
        sData.put("level", level);
        sData.put("concent", course);
        sData.put("clgName", clg);
        sData.put("univ", univ);
        sData.put("loc", loc);
        sData.put("frm", frm);
        sData.put("to", to);
        sData.put("agrt", agrt);
        if (ins == true) {
            sData.put("insrt", "false");
        } else {
            sData.put("insrt", "true");
        }

        if (cIns == true) {
            sData.put("cInsrt", "false");
        } else {
            sData.put("cInsrt", "true");
        }
        sData.put("type", type);


        try {
            JSONObject json = Connection.UrlConnection(php.qualify, sData);

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

            if (type.equalsIgnoreCase("add")) {
                int val = mApp.getPreference().getInt(Common.PROFILESTRENGTH, 0);
                mApp.getPreference().edit().putInt(Common.PROFILESTRENGTH, val + 2).commit();
            }
            mApp.getPreference().edit().putBoolean(Common.QUALIFICATION, true).commit();
            /*new Connect(mContext, uId, mApp.getPreference().getString(Common.LEVEL, ""),
                    clg, course).execute();*/
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
