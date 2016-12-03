package com.shuan.Project.launcher;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.shuan.Project.R;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout linear0, linear, linear1;
    private EditText mail, otp, new_pass, con_new_pass;
    private Button button, confirm, get_code, submit, discard;
    private ProgressDialog pDialog;
    private HashMap<String, String> fData;
    private String uId;
    private boolean exit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        linear0 = (LinearLayout) findViewById(R.id.linear0);
        linear = (LinearLayout) findViewById(R.id.linear);
        linear1 = (LinearLayout) findViewById(R.id.linear1);

        mail = (EditText) findViewById(R.id.mail);
        otp = (EditText) findViewById(R.id.otp);
        new_pass = (EditText) findViewById(R.id.new_passwd);
        con_new_pass = (EditText) findViewById(R.id.con_new_pass);

        button = (Button) findViewById(R.id.button);
        confirm = (Button) findViewById(R.id.confirm);
        get_code = (Button) findViewById(R.id.get_code);
        submit = (Button) findViewById(R.id.submit);
        //discard = (Button) findViewById(R.id.discard);


        button.setOnClickListener(this);
        confirm.setOnClickListener(this);
        get_code.setOnClickListener(this);
        submit.setOnClickListener(this);
       /* discard.setOnClickListener(this);*/

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button:
                if (mail.getText().toString().length() == 0) {
                    mail.setError("Field Cannot be Empty");
                    mail.requestFocus();
                } else {

                    new Verify().execute();

                }
                break;
            case R.id.confirm:
                linear.setVisibility(View.GONE);
                linear0.setVisibility(View.GONE);
                linear1.setVisibility(View.VISIBLE);
                break;

            case R.id.get_code:

                break;
            case R.id.submit:
                if (!new_pass.getText().toString().equalsIgnoreCase(con_new_pass.getText().toString())) {
                    con_new_pass.setError("Password mismatch");
                    con_new_pass.requestFocus();
                } else {
                    new UpdatePass().execute();
                }
                break;
            /*case R.id.discard:
                startActivity(new Intent(getApplicationContext(), LoginActivity.Login.class));
                return;*/

        }
    }

    public class UpdatePass extends AsyncTask<String, String, String> {
        String pass = new_pass.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ForgotPassword.this);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.setMessage("Please Wait...");
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            fData=new HashMap<String, String>();
            fData.put("usr",uId);
            fData.put("type","update");
            fData.put("pass",pass);
            try{
                JSONObject json=Connection.UrlConnection(php.forgetPasswrd,fData);
                int succ=json.getInt("success");
                if(succ==0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"Failed!Try Again",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"Successfully Update Password",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                            finish();
                        }
                    });
                }
            }catch (Exception e){}
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    public class Verify extends AsyncTask<String, String, String> {

        String chk = mail.getText().toString();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ForgotPassword.this);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.setMessage("Please Wait...");
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            fData = new HashMap<String, String>();
            fData.put("usr", chk);
            fData.put("type", "verify");

            try {
                JSONObject json = Connection.UrlConnection(php.forgetPasswrd, fData);
                int succ = json.getInt("success");
                if (succ == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Enter Email or Phone Number incorrect", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    JSONArray jsonArray = json.getJSONArray("login");
                    JSONObject child = jsonArray.getJSONObject(0);
                    uId = child.optString("u_id");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            linear0.setVisibility(View.GONE);
                            linear1.setVisibility(View.VISIBLE);

                        }
                    });
                }
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();
        }
    }


}
