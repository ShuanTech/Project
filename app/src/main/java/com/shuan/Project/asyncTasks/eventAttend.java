package com.shuan.Project.asyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.shuan.Project.Utils.Common;
import com.shuan.Project.employee.JuniorActivity;
import com.shuan.Project.employee.SeniorActivity;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Android on 1/3/2017.
 */

public class eventAttend extends AsyncTask<String,String,String>{

    private Context mContext;
    private HashMap<String, String> eData;
    private ProgressDialog pDialog;
    private String s;
    private AlertDialog.Builder builder;
    private Common mApp;
    private String u_id,evnt_id;


    public eventAttend(Context mContext,String u_id,String evnt_id) {
        this.mContext = mContext;
        this.u_id = u_id;
        this.evnt_id = evnt_id;
        mApp = (Common) mContext.getApplicationContext();



    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }


    @Override
    protected String doInBackground(String... params) {

        eData = new HashMap<String, String>();
        eData.put("u_id",u_id);
        eData.put("evnt_id",evnt_id);

        try{
            JSONObject json= Connection.UrlConnection(php.eventAttend,eData);
            int succ = json.getInt("success");
            if (succ==0){
                s="true";
            }else {
                s="false";
            }
        } catch (JSONException e) {

        }

        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pDialog.cancel();
        if(s.equalsIgnoreCase("false")){
            Toast.makeText(mContext, "Thank you for attending", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(mContext, "Try again after sometime..", Toast.LENGTH_SHORT).show();
        }
    }
}
