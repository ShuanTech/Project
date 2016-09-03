package com.shuan.project.signup;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.Utils.EmailValidator;
import com.shuan.project.Utils.Helper;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;
import com.shuan.project.signup.employee.EducationActivity;
import com.shuan.project.signup.employee.WorkActivity;
import com.shuan.project.signup.employer.CompanyDetails;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private Helper helper = new Helper();
    private Common mApp;
    private EditText name, emailId, phNo, pass, confrmPass;
    private RadioGroup level;
    private RadioButton radio, r1, r2;
    private Button signUp;
    private String type;
    private HashMap<String, String> sData;
    private ProgressDialog pDialog;
    private String select;
    private EmailValidator emailValidator;
    private CheckBox agree;
    private TextView term;
    private boolean agre=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mApp = (Common) getApplicationContext();
        select = getIntent().getStringExtra("select");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = (EditText) findViewById(R.id.name);
        emailId = (EditText) findViewById(R.id.email);
        phNo = (EditText) findViewById(R.id.phone);
        pass = (EditText) findViewById(R.id.pass);
        confrmPass = (EditText) findViewById(R.id.confrm_pass);
        level = (RadioGroup) findViewById(R.id.level);
        r1 = (RadioButton) findViewById(R.id.r1);
        r2 = (RadioButton) findViewById(R.id.r2);
        signUp = (Button) findViewById(R.id.sign_up);
        emailValidator = new EmailValidator();
        agree = (CheckBox) findViewById(R.id.agree);

        term = (TextView) findViewById(R.id.term);

        term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebView myWebView = new WebView(SignupActivity.this);
                myWebView.loadUrl("file:///android_asset/privacy.html");
                myWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });

                new AlertDialog.Builder(SignupActivity.this).setView(myWebView)
                        .setTitle("Term and Conditions")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                            }

                        }).show();

            }
        });

        name.setTypeface(helper.droid(getApplicationContext()));
        emailId.setTypeface(helper.droid(getApplicationContext()));
        phNo.setTypeface(helper.droid(getApplicationContext()));
        pass.setTypeface(helper.droid(getApplicationContext()));
        confrmPass.setTypeface(helper.droid(getApplicationContext()));
        r1.setTypeface(helper.droid(getApplicationContext()));
        r2.setTypeface(helper.droid(getApplicationContext()));
        signUp.setTypeface(helper.droid(getApplicationContext()));

        if (select.equalsIgnoreCase("employer")) {
            level.setVisibility(View.GONE);
        }


        emailId.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        agree.setOnClickListener(this);

       signUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.agree:
                if (((CheckBox) v).isChecked()) {
                    agre = true;
                } else {
                    agre = false;
                }
                break;
            case R.id.sign_up:
                if (select.equalsIgnoreCase("employer")) {
                    type = "3";
                } else {
                    int getLevel = level.getCheckedRadioButtonId();
                    radio = (RadioButton) findViewById(getLevel);
                    if (radio.getText().toString().equalsIgnoreCase("fresher")) {
                        type = "1";
                    } else {
                        type = "2";
                    }
                }
                if (name.getText().toString().length() == 0) {
                    name.setError("Name Mandatory");
                    name.requestFocus();
                } else if (emailId.getText().toString().length() == 0) {
                    emailId.setError("Email Id Mandatory");
                    emailId.requestFocus();
                } else if (!emailValidator.validate(emailId.getText().toString())) {
                    emailId.setError("Invalid Email Id");
                } else if (phNo.getText().toString().length() == 0) {
                    phNo.setError("Phone Number Mandatory");
                    phNo.requestFocus();
                } else if (phNo.getText().length() < 10) {
                    phNo.setError("Invalid Phone Number");
                    phNo.requestFocus();
                } else if (pass.getText().toString().length() == 0) {
                    pass.setError("Password Mandatory");
                    pass.requestFocus();
                } else if (confrmPass.getText().toString().length() == 0) {
                    confrmPass.setError("Conform Password Mandatory");
                } else if (!pass.getText().toString().equals(confrmPass.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Password miss match", Toast.LENGTH_SHORT).show();
                    //
                } else if (agre) {
                    new Signup().execute();
                } else {
                    Toast.makeText(getApplicationContext(), "Agree the Term and Conditions.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    public class Signup extends AsyncTask<String, String, String> {

        String uname = name.getText().toString();
        String umail = emailId.getText().toString();
        String uph = phNo.getText().toString();
        String upass = pass.getText().toString();
        String ucpass = confrmPass.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SignupActivity.this);
            pDialog.setMessage("Signing up!...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            sData = new HashMap<String, String>();
            sData.put("name", uname);
            sData.put("email", umail);
            sData.put("phno", uph);
            sData.put("pass", upass);
            sData.put("cnfrmpass", ucpass);
            sData.put("level", type);

            try {
                JSONObject json = Connection.UrlConnection(php.signup, sData);

                int succ = json.getInt("success");

                if (succ == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Error...Try Again!", Toast.LENGTH_SHORT).show();

                        }
                    });
                } else if (succ == 2) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            emailId.setError("Email Already Exist");
                            emailId.requestFocus();
                        }
                    });

                } else if (succ == 3) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            phNo.setError("Phone Already Exist");
                            phNo.requestFocus();
                        }
                    });
                } else {
                    JSONArray jsonArray = json.getJSONArray("user");
                    JSONObject child = jsonArray.getJSONObject(0);

                    final String u_id = child.optString("u_id");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mApp.getPreference().edit().putString(Common.u_id, u_id).commit();
                            mApp.getPreference().edit().putString(Common.LEVEL, type).commit();
                            mApp.getPreference().edit().putBoolean(Common.Login, true).commit();
                            mApp.getPreference().edit().putBoolean(Common.USRINFO, true).commit();
                            if (type.toString().equalsIgnoreCase("1")) {
                                startActivity(new Intent(getApplicationContext(), EducationActivity.class));
                                finish();
                            } else if (type.toString().equalsIgnoreCase("2")) {
                                startActivity(new Intent(getApplicationContext(), WorkActivity.class));
                                finish();
                            } else {
                                startActivity(new Intent(getApplicationContext(), CompanyDetails.class));
                                finish();
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
    }
}
