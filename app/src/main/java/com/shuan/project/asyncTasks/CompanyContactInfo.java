package com.shuan.Project.asyncTasks;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.shuan.Project.Utils.Common;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;
import com.shuan.Project.signup.employer.AboutCompanyActivity;

import org.json.JSONObject;

import java.util.HashMap;

public class CompanyContactInfo extends AsyncTask<String, String, String> {

    private Common mApp;
    private Context mContext;
    private String uId, cPrsn, cMail, cPh, cAvlTime, s="";
    private HashMap<String, String> cData;
    private Button but;

    public CompanyContactInfo(Context mContext, String uId, String cPrsn, String cMail, String cPh, String cAvlTime,Button but) {
        this.mContext = mContext;
        this.uId = uId;
        this.cPrsn = cPrsn;
        this.cMail = cMail;
        this.cPh = cPh;
        this.cAvlTime = cAvlTime;
        this.but=but;
        mApp = (Common) mContext.getApplicationContext();
    }

    @Override
    protected String doInBackground(String... params) {

        cData = new HashMap<String, String>();
        cData.put("u_id", uId);
        cData.put("contprsn", cPrsn);
        cData.put("contphn", cPh);
        cData.put("contmail", cMail);
        cData.put("availtime", cAvlTime);

        try {
            JSONObject json = Connection.UrlConnection(php.cmpntContact, cData);
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
            Toast.makeText(mContext, "Contact Details! Saved Successfully...", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(mContext, AboutCompanyActivity.class);
            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(in);
            ((AppCompatActivity)mContext).finish();
        } else {
            but.setEnabled(true);
            Toast.makeText(mContext, "Something went wrong! Try Again...", Toast.LENGTH_SHORT).show();
        }
    }
}
