package com.shuan.project.asyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.shuan.project.Utils.Common;
import com.shuan.project.employee.JuniorActivity;
import com.shuan.project.employee.SeniorActivity;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONObject;

import java.util.HashMap;


public class Apply extends AsyncTask<String, String, String> {

    private Context mContext;
    private String uId, jId, refer, s;
    private HashMap<String, String> aData;
    private ProgressDialog pDialog;
    private Common mApp;

    public Apply(Context mContext, String uId, String jId, String refer) {
        this.mContext = mContext;
        this.uId = uId;
        this.jId = jId;
        this.refer = refer;
        mApp= (Common) mContext.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Applying...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        aData = new HashMap<String, String>();
        aData.put("u_id", uId);
        aData.put("j_id", jId);
        aData.put("refer", refer);

        try{
            JSONObject json= Connection.UrlConnection(php.applyPost,aData);
            int succ=json.getInt("success");
            if(succ==0){
                s="false";
            }else{
                s="true";
            }
        }catch (Exception e){}

        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pDialog.cancel();
        if(s.equalsIgnoreCase("true")){
            Toast.makeText(mContext, "Applied Successfully", Toast.LENGTH_SHORT).show();
            AppCompatActivity activity = (AppCompatActivity) mContext;
            if (mApp.getPreference().getString(Common.LEVEL,"").equalsIgnoreCase("2")) {
                Intent in = new Intent(mContext, SeniorActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(in);
                activity.finish();
            } else {
                Intent in = new Intent(mContext, JuniorActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(in);
                activity.finish();
            }
        }else{
            Toast.makeText(mContext, "Job cannot Applied!Try again..", Toast.LENGTH_SHORT).show();
        }
    }
}
