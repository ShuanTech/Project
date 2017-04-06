               /*.......This function is for OTP verification.......*/
package com.shuan.Project.signup;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.shuan.Project.R;
import com.shuan.Project.Utils.Common;
import com.shuan.Project.asyncTasks.mail_verify;
import com.shuan.Project.asyncTasks.resendotp;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MailVerify extends AppCompatActivity implements View.OnClickListener {

    private Common mApp;
    static EditText code;
    private Button verify, resend;
    private ProgressDialog pDialog;
    private HashMap<String, String> mData;
    private String s, vercode;
    private boolean exit = false;
    String app_server_url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_verify);

        code = (EditText) findViewById(R.id.code);
        verify = (Button) findViewById(R.id.verify);
        resend = (Button) findViewById(R.id.resend);



        /*........function for generating token for push notification...*/

        new Push(MailVerify.this,mApp.getPreference().getString(Common.u_id,"")).execute();

        //Toast.makeText(getApplicationContext(),"Check your inbox for verification code",Toast.LENGTH_SHORT).show();

        verify.setOnClickListener(this);
        resend.setOnClickListener(this);

        /*final AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle("Loading..!");
        final AlertDialog alert = dialog.create();
        alert.show();

        new CountDownTimer(5000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub

                alert.dismiss();
            }
        }.start();*/

    }
    public void recivedsms(String message){

        try{
            code.setText(message);
        }catch (Exception e){

        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.verify :

                if (code.getText().toString().length() == 0) {
                    code.setError("Enter the verification code");
                    code.requestFocus();
                    //Toast.makeText(getApplicationContext(),"Check your inbox/spam for verification code ",Toast.LENGTH_SHORT).show();
                } else if (code.getText().toString().length() > 6) {
                    code.setError("Check entered code");
                    code.requestFocus();
                } else {
                    verify.setEnabled(true);
                    vercode = code.getText().toString();
                    new mail_verify(MailVerify.this, mApp.getPreference().getString(Common.u_id, ""), vercode).execute();
                }
                break;
            case  R.id.resend :
                new resendotp(MailVerify.this, mApp.getPreference().getString(Common.u_id,"")).execute();
               // new Push(MailVerify.this,mApp.getPreference().getString(Common.u_id,"")).execute();
        }

    }


    @Override
    public void onBackPressed() {
        if (exit) {
            mApp.getPreference().edit().putBoolean(Common.PAGE1, false).commit();
            super.onBackPressed();
            return;
        }
    }
    private class Push extends AsyncTask<String,String,String > {
        private Context mContext;
        private String uId, s="";
        private HashMap<String, String> pData;

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString(getString(R.string.FCM_TOKEN), "");

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(mContext);
            pDialog.setMessage("Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        public Push(Context mContext, String u_id) {
            this.mContext = mContext;
            this.uId = u_id;
        }

        @Override
        protected String doInBackground(String... params) {

            pData = new HashMap<String, String>();
            pData.put("u_id",uId);
            pData.put("fcm_token",token);


            try{
                JSONObject json = Connection.UrlConnection(php.pushnot, pData);
                int succ= json.getInt("success");

               if(succ == 0){
                   s="false";
               }
               else{
                   s="true";
               }
            } catch (JSONException e) {

            }

            return s;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();

            /*Toast.makeText(getApplicationContext(), uId, Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();*/
        }
    }


}
