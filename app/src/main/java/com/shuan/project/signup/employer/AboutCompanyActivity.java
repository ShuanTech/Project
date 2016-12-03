package com.shuan.Project.signup.employer;

import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shuan.Project.R;
import com.shuan.Project.Utils.Common;
import com.shuan.Project.Utils.Helper;
import com.shuan.Project.asyncTasks.AboutCompany;
import com.shuan.Project.asyncTasks.GetInfo;
import com.shuan.Project.employer.EmployerActivity;
import com.shuan.Project.fragment.DateDialog;

public class AboutCompanyActivity extends AppCompatActivity implements View.OnClickListener {

    private Helper helper = new Helper();
    private Common mApp;

    private EditText yr_st, no_wrkr, c_des;
    private Button ft_skip, ft_next;
    private boolean exit = false;
    private TextInputLayout layout_yr_st,layout_no_wrkr,layout_c_des;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_company);

        layout_yr_st = (TextInputLayout) findViewById(R.id.layout_yr_st);
        layout_no_wrkr = (TextInputLayout) findViewById(R.id.layout_no_wrkr);
        layout_c_des = (TextInputLayout) findViewById(R.id.layout_c_des);

        yr_st = (EditText) findViewById(R.id.yr_st);
        no_wrkr = (EditText) findViewById(R.id.no_wrkr);
        c_des = (EditText) findViewById(R.id.c_des);
        ft_next = (Button) findViewById(R.id.ft_next);
        ft_skip = (Button) findViewById(R.id.ft_skip);


        yr_st.setOnTouchListener(new View.OnTouchListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    DateDialog dialog = new DateDialog(v);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "DataPicker");
                }
                return false;
            }
        });

        ft_skip.setOnClickListener(this);
        ft_next.setOnClickListener(this);


        yr_st.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (yr_st.getText().toString().length() == 0) {
                    ft_skip.setVisibility(View.VISIBLE);
                    ft_next.setVisibility(View.GONE);
                } else {
                    ft_skip.setVisibility(View.GONE);
                    ft_next.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ft_next:
                if (yr_st.getText().toString().length() == 0) {
                    yr_st.setError("Field Mandatory");
                    yr_st.requestFocus();
                } else if (no_wrkr.getText().toString().length() == 0) {
                    no_wrkr.setError("Field Mandatory");
                    no_wrkr.requestFocus();
                }  else {
                    ft_next.setEnabled(false);
                    new AboutCompany(AboutCompanyActivity.this, mApp.getPreference().getString(Common.u_id, ""),
                            yr_st.getText().toString(), no_wrkr.getText().toString(), c_des.getText().toString(),ft_next).execute();
                }
                break;
            case R.id.ft_skip:
                new GetInfo(getApplicationContext(),mApp.getPreference().getString(Common.u_id,"")).execute();
                startActivity(new Intent(getApplicationContext(), EmployerActivity.class));
                finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(this, "Press Back again to Cancel Signup Process.", Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 2000);
        }
    }
}
