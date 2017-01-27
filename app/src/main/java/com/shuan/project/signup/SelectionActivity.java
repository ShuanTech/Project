package com.shuan.Project.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shuan.Project.R;
import com.shuan.Project.Utils.Common;
import com.shuan.Project.Utils.Helper;
import com.shuan.Project.launcher.LoginActivity;

public class SelectionActivity extends AppCompatActivity implements View.OnClickListener {

    public Helper helper = new Helper();
    public Common mApp;
    public TextView txt1, txt2, txt3;
    public ImageButton employee, employer;
    public String select;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mApp = (Common) getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);


        employee = (ImageButton) findViewById(R.id.employee);
        employer = (ImageButton) findViewById(R.id.employer);
        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);
        txt3 = (TextView) findViewById(R.id.txt3);

        txt1.setTypeface(helper.droid(getApplicationContext()));
        txt2.setTypeface(helper.droid(getApplicationContext()));
        txt3.setTypeface(helper.droid(getApplicationContext()));

        employee.setOnClickListener(this);
        employer.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        Intent in = null;
        switch (v.getId()) {
            case R.id.employee:
                in=new Intent(getApplicationContext(),SignupActivity.class);
                in.putExtra("select","employee");
                break;
            case R.id.employer:
                in=new Intent(getApplicationContext(),SignupActivity.class);
                in.putExtra("select","employer");
                break;
        }
        startActivity(in);
        finish();
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }


}
