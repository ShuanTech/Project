package com.shuan.project.asyncTasks;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.shuan.project.profile.JunUpdateInfo;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONObject;

import java.util.HashMap;

public class UpdateUsrInfo extends AsyncTask<String, String, String> {

    private Context mContext;
    private String u_id, fullName, dob, gender, relation, blood, addr, loc, distrct, ste, coutry, pin;
    private boolean ins;
    private HashMap<String, String> pData;
    private String s = "";

    public UpdateUsrInfo(Context mContext, String u_id, String fullName, String dob, String gender, String relation, String blood, String addr, String loc, String distrct, String ste, String coutry, String pin, boolean ins) {
        this.mContext = mContext;
        this.u_id = u_id;
        this.fullName = fullName;
        this.dob = dob;
        this.gender = gender;
        this.relation = relation;
        this.blood = blood;
        this.addr = addr;
        this.loc = loc;
        this.distrct = distrct;
        this.ste = ste;
        this.coutry = coutry;
        this.pin = pin;
        this.ins = ins;
    }

    @Override
    protected String doInBackground(String... params) {

        pData = new HashMap<String, String>();
        pData.put("u_id", u_id);
        pData.put("name", fullName);
        pData.put("dob", dob);
        pData.put("gender", gender);
        pData.put("rel", relation);
        pData.put("bld", blood);
        pData.put("address", addr);
        pData.put("city", loc);
        pData.put("district", distrct);
        pData.put("state", ste);
        pData.put("country", coutry);
        pData.put("pincode", pin);

        if (ins == true) {
            pData.put("insrt", "false");
        } else {
            pData.put("insrt", "true");
        }

        try {
            JSONObject json = Connection.UrlConnection(php.update_info, pData);

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
            Toast.makeText(mContext, "Successfully Updated Your Information", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(mContext, JunUpdateInfo.class);
            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(in);
            ((AppCompatActivity)mContext).finish();
        } else {
            Toast.makeText(mContext, "Try Again", Toast.LENGTH_SHORT).show();
        }
    }
}
