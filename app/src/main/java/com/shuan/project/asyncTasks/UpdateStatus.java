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


public class UpdateStatus extends AsyncTask<String, String, String> {

    private Context mContext;
    private String u_id, status, s;
    private HashMap<String, String> uData;
    private ProgressDialog pDialog;
    private Common mApp;
    public UpdateStatus(Context mContext, String u_id, String status) {
        this.mContext = mContext;
        this.u_id = u_id;
        this.status = status;
        this.mApp = (Common) mContext.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Updating");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {

        uData = new HashMap<String, String>();
        uData.put("u_id", u_id);
        uData.put("status", status);

        try {
            JSONObject json= Connection.UrlConnection(php.update_status,uData);

            int succ=json.getInt("success");
            if(succ==0){
                s="false";
            }else{
                s="true";
            }

        } catch (Exception e) {}

        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pDialog.cancel();
        if(s.equalsIgnoreCase("true")){
            Toast.makeText(mContext,"Status Updated Successfully",Toast.LENGTH_SHORT).show();
            int val = mApp.getPreference().getInt(Common.PROFILESTRENGTH, 0);
            mApp.getPreference().edit().putInt(Common.PROFILESTRENGTH, val + 2).commit();
            Intent in = new Intent(mContext, ResumeEditActivity.class);
            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(in);
            ((AppCompatActivity) mContext).finish();
        }else{
            Toast.makeText(mContext,"Something Went Wrong! Status couldn't update",Toast.LENGTH_SHORT).show();
        }
    }
}
