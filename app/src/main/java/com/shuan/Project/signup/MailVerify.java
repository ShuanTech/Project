package com.shuan.Project.signup;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shuan.Project.R;
import com.shuan.Project.Utils.Common;
import com.shuan.Project.asyncTasks.mail_verify;

import java.util.HashMap;

public class MailVerify extends AppCompatActivity implements View.OnClickListener {

    private Common mApp;
    private EditText code;
    private Button verify;
    private ProgressDialog pDialog;
    private HashMap<String, String> mData;
    private String s,vercode;
    private boolean exit=false;
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
            verify.setEnabled(true);
            vercode = code.getText().toString();
            new mail_verify(MailVerify.this,mApp.getPreference().getString(Common.u_id, ""),vercode).execute();
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

}
