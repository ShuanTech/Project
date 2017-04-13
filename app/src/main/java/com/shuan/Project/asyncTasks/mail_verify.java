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
import com.shuan.Project.signup.employee.CSLActivity;
import com.shuan.Project.signup.employee.WorkActivity;
import com.shuan.Project.signup.employer.CompanyDetails;

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

            if (succ==2){
                s="missmatch";
            }else if (succ==1){
                s="true";
            }else{
                s="false";
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
            mApp.getPreference().edit().putBoolean(Common.OTP, true).commit();
            if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("1")) {
                Toast.makeText(mContext," Verified your phone number successfully ",Toast.LENGTH_SHORT).show();
                in = new Intent(mContext, CSLActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            }else if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("2")){
                Toast.makeText(mContext," Verified your phone number successfully ",Toast.LENGTH_SHORT).show();
                in = new Intent(mContext, WorkActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            }else {
                Toast.makeText(mContext," Verified your phone number successfully ",Toast.LENGTH_SHORT).show();
                in=new Intent(mContext, CompanyDetails.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            mContext.startActivity(in);
            ((AppCompatActivity) mContext).finish();

        }else if (s.equalsIgnoreCase("false")){
            mApp.getPreference().edit().putBoolean(Common.OTP, false).commit();
            Toast.makeText(mContext, "Error try again later", Toast.LENGTH_SHORT).show();
        }else if(s.equalsIgnoreCase("missmatch")){
            mApp.getPreference().edit().putBoolean(Common.OTP, false).commit();
            Toast.makeText(mContext, "Code you entered does not matches", Toast.LENGTH_SHORT).show();

        }

    }
}

