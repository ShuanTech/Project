package com.shuan.Project.asyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.shuan.Project.Utils.Common;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Android on 3/24/2017.
 */

public class resendotp extends AsyncTask<String,String,String> {
    private Context mContext;
    private Common mApp;
    private ProgressDialog pDialog;
    private HashMap<String, String> mData;
    private String uId,vcode,s;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading......!");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }
    public resendotp(Context mContext, String uId) {

        this.mContext = mContext;
        this.uId = uId;
    }


    @Override
    protected String doInBackground(String... params) {

        mData = new HashMap<String, String>();
        mData.put("u_id",uId);

        try{
            JSONObject json = Connection.UrlConnection(php.resend_otp,mData);
            int succ = json.getInt("success");

            if (succ==0){
                s="false";
            }else if (succ == 1){
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
            Toast.makeText(mContext," Please Wait for a while..OTP has been send to your Phone number",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(mContext, "Something went wrong..! Please try again", Toast.LENGTH_SHORT).show();
        }
    }
}
