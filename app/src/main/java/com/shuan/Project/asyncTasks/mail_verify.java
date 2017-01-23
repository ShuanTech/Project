package com.shuan.Project.asyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.shuan.Project.Utils.Common;
import com.shuan.Project.employee.JuniorActivity;
import com.shuan.Project.employee.SeniorActivity;
import com.shuan.Project.employer.EmployerActivity;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Android on 1/19/2017.
 */
public class mail_verify extends AsyncTask<String, String, String> {

    private Context mContext;
    private Common mApp;
    private ProgressDialog pDialog;
    private HashMap<String, String> mData;
    private String uId,vcode,s;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Verifying......!");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public mail_verify(Context mContext, String uId, String vercode) {

        this.mContext = mContext;
        this.uId = uId;
        this.vcode = vercode;
        mApp = (Common) mContext.getApplicationContext();

    }
    @Override
    protected String doInBackground(String... params) {

        mData = new HashMap<String, String>();
        mData.put("u_id",uId);
        mData.put("code",vcode);

        try{
            JSONObject json= Connection.UrlConnection(php.verifymail,mData);
            int succ = json.getInt("success");

            if (succ==0){
                s="false";
            }else if (succ==1){
                s="true";
            }

        } catch (JSONException e) {
        }
        return s;
    }

    @Override
    protected void onPostExecute(String s){
        super.onPostExecute(s);
        pDialog.dismiss();
        if (s.equalsIgnoreCase("true")) {

            Intent in = null;
            mApp.getPreference().edit().putBoolean(Common.PAGE1, true).commit();
            if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("1")) {
                in = new Intent(mContext, JuniorActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            }else if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("2")){
                in = new Intent(mContext, SeniorActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            }else {
                in=new Intent(mContext, EmployerActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            mContext.startActivity(in);
            ((AppCompatActivity) mContext).finish();

        }else {
            Toast.makeText(mContext, "Failed. Try Again", Toast.LENGTH_SHORT).show();
        }

    }
}

