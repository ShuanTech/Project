package com.shuan.project.asyncTasks;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;
import com.shuan.project.resume.ResumeEditActivity;

import org.json.JSONObject;

import java.util.HashMap;

public class EditWrkDetail extends AsyncTask<String,String,String>{

    public Context mContext;
    public String uId, org, loc, pos, frmDate, toDate, s;
    public HashMap<String, String> rData;
    public ProgressDialog pDialog;

    public EditWrkDetail(Context mContext, String uId, String org, String loc, String pos, String frmDate, String toDate) {
        this.mContext = mContext;
        this.uId = uId;
        this.org = org;
        this.loc = loc;
        this.pos = pos;
        this.frmDate = frmDate;
        this.toDate = toDate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Updating Work Detail");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        rData = new HashMap<String, String>();
        rData.put("u_id", uId);
        rData.put("org_name", org);
        rData.put("position", pos);
        rData.put("loc", loc);
        rData.put("frm", frmDate);
        rData.put("to", toDate);
        rData.put("type","edit");

        try {
            JSONObject json = Connection.UrlConnection(php.work_info, rData);
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
        if (s.equalsIgnoreCase("true")) {
            Toast.makeText(mContext, "Successfully Work Details Updated", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(mContext, ResumeEditActivity.class);
            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(in);
            ((AppCompatActivity) mContext).finish();
        } else {
            Toast.makeText(mContext, "Something went wrong!... Try Again", Toast.LENGTH_SHORT).show();
        }
    }
}
