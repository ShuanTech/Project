package com.shuan.Project.launcher;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shuan.Project.R;
import com.shuan.Project.Utils.Common;
import com.shuan.Project.Utils.Helper;
import com.shuan.Project.asyncTasks.GetInfo;
import com.shuan.Project.employee.JuniorActivity;
import com.shuan.Project.employee.SeniorActivity;
import com.shuan.Project.employer.EmployerActivity;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;
import com.shuan.Project.signup.SelectionActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    private Common mApp;
    private Context context;
    private EditText usr, pass;
    private Button reg, login;
    private HashMap<String, String> loginData;
    private ProgressDialog pDialog;
    private Helper helper = new Helper();
    private TextInputLayout layout_usr;
    private TextView fp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        context = getApplicationContext();
        mApp = (Common) context.getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usr = (EditText) findViewById(R.id.usr);
        pass = (EditText) findViewById(R.id.pass);
        reg = (Button) findViewById(R.id.reg);
        login = (Button) findViewById(R.id.login);
        fp = (TextView) findViewById(R.id.fp);


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SelectionActivity.class));
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usr.getText().toString().length() == 0) {
                    usr.setError("Enter Email / Phone Number");
                    usr.requestFocus();
                } else if (pass.getText().toString().length() == 0) {
                    pass.setError("Enter Password");
                    pass.requestFocus();
                } else {
                    new Login().execute();
                }
            }
        });
        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ForgotPassword.class));
            }
        });


    }

    public class Login extends AsyncTask<String, String, String> {

        String u = usr.getText().toString();
        String p = pass.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Logging In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            loginData = new HashMap<String, String>();
            loginData.put("usr", u);
            loginData.put("pass", p);

            try {
                JSONObject json = Connection.UrlConnection(php.login, loginData);
                int succ = json.getInt("success");
                if (succ == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "User Name or Password Incorrect", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {

                    JSONArray jsonArray = json.getJSONArray("login");

                    JSONObject child = jsonArray.getJSONObject(0);

                    final String level = child.optString("level");
                    final String uId = child.optString("u_id");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mApp.getPreference().edit().putBoolean(Common.Login, true).commit();
                            mApp.getPreference().edit().putBoolean(Common.USRINFO, true).commit();
                            mApp.getPreference().edit().putBoolean("start", true).commit();

                            new GetInfo(getApplicationContext(), uId).execute();
                            mApp.getPreference().edit().putString(Common.u_id, uId).commit();
                            mApp.getPreference().edit().putString(Common.LEVEL, level).commit();
                            if (level.equalsIgnoreCase("3")) {
                                mApp.getPreference().edit().putBoolean(Common.COMPANY, true).commit();
                                mApp.getPreference().edit().putBoolean(Common.OTP, true).commit();
                                new Pushto(LoginActivity.this, mApp.getPreference().getString(Common.u_id, "")).execute();
                                startActivity(new Intent(LoginActivity.this, EmployerActivity.class));
                                finish();
                            } else {
                                mApp.getPreference().edit().putBoolean(Common.QUALIFICATION, true).commit();
                                mApp.getPreference().edit().putBoolean(Common.HSC, true).commit();
                                mApp.getPreference().edit().putBoolean(Common.SSLC, true).commit();
                                mApp.getPreference().edit().putBoolean(Common.SKILL, true).commit();
                                mApp.getPreference().edit().putBoolean(Common.PROJECT, true).commit();
                                mApp.getPreference().edit().putBoolean(Common.HOBBIES, true).commit();
                                mApp.getPreference().edit().putBoolean(Common.PERSONALINFO, true).commit();
                                mApp.getPreference().edit().putBoolean(Common.WORKINFO, true).commit();
                                mApp.getPreference().edit().putBoolean(Common.PROFILESUMMARY, true).commit();
                                mApp.getPreference().edit().putBoolean(Common.WORKEXPERIENCE, true).commit();
                                mApp.getPreference().edit().putBoolean(Common.OTP, true).commit();
                                mApp.getPreference().edit().putBoolean(Common.PAGE1, true).commit();
                                mApp.getPreference().edit().putBoolean(Common.PAGE2, true).commit();
                                mApp.getPreference().edit().putInt(Common.PROFILESTRENGTH, 50).commit();
                                if (level.equalsIgnoreCase("2")) {
                                    new Pushto(LoginActivity.this, mApp.getPreference().getString(Common.u_id, "")).execute();
                                    startActivity(new Intent(LoginActivity.this, SeniorActivity.class));
                                    finish();
                                } else {
                                    new Pushto(LoginActivity.this, mApp.getPreference().getString(Common.u_id, "")).execute();
                                    startActivity(new Intent(LoginActivity.this, JuniorActivity.class));
                                    finish();
                                }

                            }
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
            pDialog.cancel();
        }

        private class Pushto extends AsyncTask<String, String, String> {
            private Context mContext;
            private String uId, s = "";
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

            public Pushto(Context mContext, String u_id) {
                this.mContext = mContext;
                this.uId = u_id;
            }

            @Override
            protected String doInBackground(String... params) {

                pData = new HashMap<String, String>();
                pData.put("u_id", uId);
                pData.put("fcm_token", token);


                try {
                    JSONObject json = Connection.UrlConnection(php.pushnot, pData);
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
                pDialog.dismiss();

            /*Toast.makeText(getApplicationContext(), uId, Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();*/
            }
        }

    }
}
