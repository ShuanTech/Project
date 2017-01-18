package com.shuan.Project.signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shuan.Project.R;
import com.shuan.Project.Utils.Common;
import com.shuan.Project.employee.JuniorActivity;
import com.shuan.Project.employee.SeniorActivity;
import com.shuan.Project.employer.EmployerActivity;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MailVerify extends AppCompatActivity implements View.OnClickListener {

    private Common mApp;
    private EditText code;
    private Button verify;
    private ProgressDialog pDialog;
    private HashMap<String, String> mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_verify);

        code = (EditText) findViewById(R.id.code);
        verify = (Button) findViewById(R.id.verify);


        Toast.makeText(getApplicationContext(),"Check your inbox for verification code",Toast.LENGTH_SHORT).show();

        verify.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (code.getText().toString().length()==0){
            code.setError("Enter the verification code");
            code.requestFocus();
            Toast.makeText(getApplicationContext(),"Check your inbox/spam for verification code ",Toast.LENGTH_SHORT).show();
        }else if (code.getText().toString().length()>6){
            code.setError("Check entered code");
            code.requestFocus();
        }else {
            verify.setEnabled(false);
            new verifymail().execute();
        }
    }
    public class verifymail extends AsyncTask<String,String,String>{

        String vcode = code.getText().toString();

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(MailVerify.this);
            pDialog.setMessage("Verifying...!");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }
        @Override
        protected String doInBackground(String... params) {

            mData = new HashMap<String, String>();
            mData.put("code",vcode);

            try{
                JSONObject json = Connection.UrlConnection(php.verifymail,mData);
                int succ = json.getInt("success");

                if (succ == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Error...Try Again!", Toast.LENGTH_SHORT).show();
                            verify.setEnabled(true);
                        }
                    });
                }else if(succ == 1){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("1")) {
                               /* Intent in = new Intent(getApplicationContext(), JuniorActivity.class);
                                startActivity(in);*/

                                startActivity(new Intent(getApplicationContext(), JuniorActivity.class));
                                finish();
                            } else if (mApp.getPreference().getString(Common.LEVEL,"").equalsIgnoreCase("2")){
                                startActivity(new Intent(getApplicationContext(), SeniorActivity.class));
                                finish();
                            }else {
                                startActivity(new Intent(getApplicationContext(), EmployerActivity.class));
                                finish();
                            }


                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}
