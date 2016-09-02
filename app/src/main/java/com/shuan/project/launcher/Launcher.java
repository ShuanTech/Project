package com.shuan.project.launcher;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.Utils.Helper;
import com.shuan.project.Utils.NetworkUtil;
import com.shuan.project.employee.JuniorActivity;
import com.shuan.project.employee.SeniorActivity;
import com.shuan.project.employer.EmployerActivity;
import com.shuan.project.signup.employer.AboutCompanyActivity;
import com.shuan.project.signup.employer.CompanyContactInfoActivity;
import com.shuan.project.signup.employer.CompanyDetails;

public class Launcher extends AppCompatActivity {

    private Common mApp;
    private Context context;
    private TextView ud, ud1;
    private Helper helper = new Helper();
    private static int SPLASH_TIME_OUT = 2000;
    private AlertDialog.Builder builder;

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
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    check();

                }
            }, SPLASH_TIME_OUT);
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
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    check();
                }
            }, SPLASH_TIME_OUT);
        }

    }

    private void check() {

        if (mApp.getPreference().getBoolean(Common.Login, false) == false) {
            Intent i = new Intent(Launcher.this, LoginActivity.class);
            startActivity(i);
        } else {
            if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("3")) {
                if (mApp.getPreference().getBoolean(Common.COMPANY1, false) == false) {
                    startActivity(new Intent(getApplicationContext(), CompanyDetails.class));
                } else if (mApp.getPreference().getBoolean(Common.COMPANY2, false) == false) {
                    startActivity(new Intent(getApplicationContext(), CompanyContactInfoActivity.class));
                } else if (mApp.getPreference().getBoolean(Common.COMPANY3, false) == false) {
                    startActivity(new Intent(getApplicationContext(), AboutCompanyActivity.class));
                } else {
                    startActivity(new Intent(Launcher.this, EmployerActivity.class));
                }


            } else {

                if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("1")) {
                    startActivity(new Intent(Launcher.this, JuniorActivity.class));
                } else {
                    startActivity(new Intent(Launcher.this, SeniorActivity.class));
                }

                
            }
        }

        finish();
    }
}
