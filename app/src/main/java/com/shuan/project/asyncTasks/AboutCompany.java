package com.shuan.project.asyncTasks;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.shuan.project.Utils.Common;
import com.shuan.project.employer.EmployerActivity;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONObject;

import java.util.HashMap;

public class AboutCompany extends AsyncTask<String, String, String> {

    private Common mApp;
    private Context mContext;
    private String uId,yrSt, noWk, cDes, s;
    private HashMap<String, String> aData;

    public AboutCompany(Context mContext, String u_id,String yrSt, String noWk, String cDes) {
        this.mContext = mContext;
        this.uId=u_id;
        this.yrSt = yrSt;
        this.noWk = noWk;
        this.cDes = cDes;
        mApp = (Common) mContext.getApplicationContext();
    }

    @Override
    protected String doInBackground(String... params) {

        aData = new HashMap<String, String>();
        aData.put("u_id",uId);
        aData.put("yearstrt",yrSt);
        aData.put("workers",noWk);
        aData.put("descrp",cDes);

        try {

            JSONObject json= Connection.UrlConnection(php.abtCmpny,aData);
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
        save(s);
    }

    private void save(String s) {
        if(s.equalsIgnoreCase("true")){
            mApp.getPreference().edit().putBoolean(Common.COMPANY3,true).commit();
            mApp.getPreference().edit().putBoolean(Common.ABOUTCOMPNAY,true).commit();
            mApp.getPreference().edit().putBoolean(Common.Login, true).commit();
            Toast.makeText(mContext,"Successfully Completed Your Signup Process. Let's begin",Toast.LENGTH_SHORT).show();
            mApp.getPreference().edit().putBoolean(Common.USRINFO,true).commit();
            new GetInfo(mContext,mApp.getPreference().getString(Common.u_id,"")).execute();
            Intent in=new Intent(mContext, EmployerActivity.class);
            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(in);
            ((AppCompatActivity)mContext).finish();
        }else{
            Toast.makeText(mContext,"Something went Wrong. Try Again...",Toast.LENGTH_SHORT).show();
        }
    }
}
