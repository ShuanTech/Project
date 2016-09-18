package com.shuan.project.launcher;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.Utils.Helper;
import com.shuan.project.Utils.NetworkUtil;
import com.shuan.project.employee.JuniorActivity;
import com.shuan.project.employee.SeniorActivity;
import com.shuan.project.employer.EmployerActivity;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;
import com.shuan.project.signup.employee.CSLActivity;
import com.shuan.project.signup.employer.CompanyDetails;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class Launcher extends AppCompatActivity {

    private Common mApp;
    private Context context;
    private TextView ud, ud1;
    private Helper helper = new Helper();
    private static int SPLASH_TIME_OUT = 7000;
    private AlertDialog.Builder builder;
    private HashMap<String, String> iData;
    private String u_id;
    private String name, pPic, cPic, pass, level, connect, follow, follower, alrt, s = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        context = getApplicationContext();
        mApp = (Common) context.getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        ud = (TextView) findViewById(R.id.ud);
        ud1 = (TextView) findViewById(R.id.ud1);

        ud.setTypeface(helper.droid(getApplicationContext()));
        ud1.setTypeface(helper.droid(getApplicationContext()));

        if (NetworkUtil.getConnectivityStatus(getApplicationContext()) == 0) {
            showAlert();
        } else {
            if(mApp.getPreference().getBoolean(Common.USRINFO,false)==true){
                new GetUsrInfo().execute();
            }else {
                check();
            }



        }
    }

    private void showAlert() {
        builder = new AlertDialog.Builder(Launcher.this)
                .setTitle("No Internet")
                .setCancelable(false)
                .setMessage("No Internet Connection Available.\n Do you want to try again?");

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                dialog.cancel();
            }
        }).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                checkConnection();
            }
        }).show();

    }

    private void checkConnection() {

        if (NetworkUtil.getConnectivityStatus(getApplicationContext()) == 0) {

            showAlert();

        } else {

            if(mApp.getPreference().getBoolean(Common.USRINFO,false)==true){
                new GetUsrInfo().execute();
            }else {
                check();
            }

        }

    }

    private void check() {

        if (mApp.getPreference().getBoolean(Common.Login, false) == false) {
            Intent i = new Intent(Launcher.this, LoginActivity.class);
            startActivity(i);
        } else {
            if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("3")) {

                if (mApp.getPreference().getBoolean(Common.COMPANY, false) == false) {
                    startActivity(new Intent(Launcher.this, CompanyDetails.class));
                } else {
                    startActivity(new Intent(Launcher.this, EmployerActivity.class));
                }
            } else {

                if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("1")) {
                    if (mApp.getPreference().getBoolean(Common.PAGE1, false) == false) {
                        startActivity(new Intent(Launcher.this, CSLActivity.class));
                    } else {
                        startActivity(new Intent(Launcher.this, JuniorActivity.class));
                    }

                } else {
                    startActivity(new Intent(Launcher.this, SeniorActivity.class));
                }


            }
        }

        finish();
    }

    public class GetUsrInfo extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            iData = new HashMap<String, String>();
            iData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            try {
                JSONObject json = Connection.UrlConnection(php.getUser, iData);
                int succ = json.getInt("success");

                if (succ == 0) {
                    s = "false";
                } else {
                    JSONArray jsonArray = json.getJSONArray("info");
                    JSONObject child = jsonArray.getJSONObject(0);

                    name = child.optString("full_name");
                    pPic = child.optString("pro_pic");
                    cPic = child.optString("cover_pic");
                    pass = child.optString("passwrd");
                    level = child.optString("level");
                    connect = child.optString("connection");
                    follow = child.optString("following");
                    follower = child.optString("follower");
                    alrt = child.optString("alert");
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
                mApp.getPreference().edit().putString(Common.FULLNAME, name).commit();
                mApp.getPreference().edit().putString(Common.PROPIC, pPic).commit();
                mApp.getPreference().edit().putString(Common.COVER, cPic).commit();
                mApp.getPreference().edit().putString(Common.PASSWRD, pass).commit();
                mApp.getPreference().edit().putString(Common.CONNECTION, connect).commit();
                mApp.getPreference().edit().putString(Common.FOLLOWER, follower).commit();
                mApp.getPreference().edit().putString(Common.FOLLOWING, follow).commit();
                mApp.getPreference().edit().putString(Common.ALERT, alrt).commit();
                mApp.getPreference().edit().putString(Common.LEVEL, level).commit();
                check();
            } else {
                Toast.makeText(getApplicationContext(), "Something Wrong. Please Login", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(Launcher.this, LoginActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
                finish();

            }
        }
    }
}
