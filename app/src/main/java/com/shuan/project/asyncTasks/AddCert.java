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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AddCert extends AsyncTask<String, String, String> {

    private Context mContext;
    private String uId, certName, certCentre, cetDur, type, s="";
    private HashMap<String, String> seniorData;
    private ProgressDialog pDialog;
    private Common mApp;
    public AddCert(Context mContext, String uId, String certName, String certCentre, String cetDur, String type) {
        this.mContext = mContext;
        this.uId = uId;
        this.certName = certName;
        this.certCentre = certCentre;
        this.cetDur = cetDur;
        this.type = type;
        this.mApp= (Common) mContext.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Adding Certification Detail");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        seniorData = new HashMap<String, String>();
        seniorData.put("u_id", uId);
        seniorData.put("cer_name", certName);
        seniorData.put("cer_centre", certCentre);
        seniorData.put("cer_dur", cetDur);
        seniorData.put("type", type);
        try {
            JSONObject json = Connection.UrlConnection(php.certification, seniorData);
            int succ = json.getInt("success");
            if (succ == 0) {
                s = "false";
            } else {
                s = "true";
            }
        } catch (JSONException e) {

        }
        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pDialog.cancel();
        if (s.equalsIgnoreCase("true")) {

            if(type.equalsIgnoreCase("add")){
                int val = mApp.getPreference().getInt(Common.PROFILESTRENGTH, 0);
                mApp.getPreference().edit().putInt(Common.PROFILESTRENGTH, val + 1).commit();
            }

            Toast.makeText(mContext, "Successfully Certification Details Added", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(mContext, ResumeEditActivity.class);
            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(in);
            ((AppCompatActivity) mContext).finish();
        } else {
            Toast.makeText(mContext, "Something went wrong!... Try Again", Toast.LENGTH_SHORT).show();
        }
    }
}
