package com.shuan.Project.setting;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.shuan.Project.R;
import com.shuan.Project.Utils.Common;
import com.shuan.Project.Utils.PasswordValidator;
import com.shuan.Project.launcher.LoginActivity;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONObject;

import java.util.HashMap;

public class ChangePasswrd extends AppCompatActivity {

    private Toolbar toolbar;
    private Common mApp;
    private EditText oldPass, newPass, cnfrmPass;
    private Button change;
    private PasswordValidator pasval;
    private LinearLayout scroll;
    private HashMap<String, String> cData;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mApp = (Common) getApplicationContext();

        if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("1")) {
            setTheme(R.style.Junior);
        } else if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("2")) {
            setTheme(R.style.Senior);
        } else {
            setTheme(R.style.AppBaseTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_passwrd);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("1")) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.junPrimary));
        } else if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("2")) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.senPrimary));
        } else {
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Change Password");

        scroll = (LinearLayout) findViewById(R.id.scroll);
        //oldPass = (EditText) findViewById(R.id.old_pass);
        newPass = (EditText) findViewById(R.id.new_pass);
        cnfrmPass = (EditText) findViewById(R.id.cnfrm_pass);
        pasval = new PasswordValidator();


        change = (Button) findViewById(R.id.update);

        /*oldPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!oldPass.getText().toString().equalsIgnoreCase(mApp.getPreference().getString(Common.PASSWRD, ""))) {
                    oldPass.setError("Incorrect Password");
                } else {
                    oldPass.setError(null);
                }
            }
        });*/

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (oldPass.getText().toString().length() == 0) {
                    oldPass.setError("Enter Old Password");
                    oldPass.requestFocus();
                } else*/ if (newPass.getText().toString().length() == 0) {
                    newPass.setError("Enter New Password");
                    newPass.requestFocus();
                } else if (!pasval.validate(newPass.getText().toString())) {
                    newPass.setError("Password must contain an Alphabet and a Number");
                    newPass.requestFocus();
                } else if (newPass.getText().toString().length() < 8) {
                    newPass.setError("Password must contain at least 8 Characters");
                    newPass.requestFocus();
                } else if (cnfrmPass.getText().toString().length() == 0) {
                    cnfrmPass.setError("Enter Confirm Password");
                    cnfrmPass.requestFocus();
                } else if (!newPass.getText().toString().equalsIgnoreCase(cnfrmPass.getText().toString())) {
                    cnfrmPass.setError("Password Mismatch");
                    cnfrmPass.requestFocus();
                } else {
                    new UpdatePassword().execute();
                }
            }
        });

    }

    public class UpdatePassword extends AsyncTask<String, String, String> {

        String cNew = newPass.getText().toString();
        String cCnfrm = cnfrmPass.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ChangePasswrd.this);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.setMessage("Updating...");
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            cData = new HashMap<String, String>();
            cData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            cData.put("pass", cNew);
            cData.put("cfrm", cCnfrm);

            try {
                JSONObject json = Connection.UrlConnection(php.updatePass, cData);
                int succ = json.getInt("success");
                if (succ == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Password Not Update...Try Again!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Password successfully Updated", Toast.LENGTH_SHORT).show();
                            mApp.getPreference().edit().putBoolean(Common.Login, false).commit();
                            mApp.getPreference().edit().putBoolean(Common.USRINFO, false).commit();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
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
