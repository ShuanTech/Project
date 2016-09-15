package com.shuan.project.asyncTasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.shuan.project.Utils.Common;
import com.shuan.project.employer.EmployerActivity;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;
import com.shuan.project.signup.employer.CompanyContactInfoActivity;

import org.json.JSONObject;

import java.util.HashMap;


public class CompanyDetail extends AsyncTask<String, String, String> {

    private Context mContext;
    private String uId, orgName, type, addr, land, city, ste, cntry, pin;
    private boolean ins;
    private HashMap<String, String> cData;
    private String s = "";
    private Common mApp;
    private Button but;

    public CompanyDetail(Context mContext, String u_id, String orgName, String type, String addr, String land, String city, String ste,
                         String cntry, String pin, boolean ins, Button but) {
        this.mContext = mContext;
        this.uId = u_id;
        this.orgName = orgName;
        this.type = type;
        this.addr = addr;
        this.land = land;
        this.city = city;
        this.ste = ste;
        this.cntry = cntry;
        this.pin = pin;
        this.ins = ins;
        this.but = but;
        mApp = (Common) mContext.getApplicationContext();
    }

    @Override
    protected String doInBackground(String... params) {

        cData = new HashMap<String, String>();
        cData.put("u_id", uId);
        cData.put("compname", orgName);
        cData.put("compnytype", type);
        cData.put("cdoorno", addr);
        cData.put("location", land);
        cData.put("country", cntry);
        cData.put("state", ste);
        cData.put("city", city);
        cData.put("pin", pin);
        if (ins == true) {
            cData.put("insrt", "false");
        } else {
            cData.put("insrt", "true");
        }

        try {

            JSONObject json = Connection.UrlConnection(php.cmpnyDetail, cData);
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
            if (mApp.getPreference().getBoolean("frm", false) == false) {
                mApp.getPreference().edit().putBoolean("frm", true).commit();
                mApp.getPreference().edit().putBoolean(Common.COMPANY, true).commit();
                Intent in = new Intent(mContext, EmployerActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(in);
                ((AppCompatActivity) mContext).finish();
            } else {
                mApp.getPreference().edit().putBoolean(Common.COMPANY, true).commit();
                Intent in = new Intent(mContext, CompanyContactInfoActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(in);
                ((AppCompatActivity) mContext).finish();
            }

            new Follower(mContext, uId, orgName, city).execute();
        } else {
            but.setEnabled(true);
            Toast.makeText(mContext, "Something went wrong! Try again...", Toast.LENGTH_SHORT).show();
        }
    }
}
