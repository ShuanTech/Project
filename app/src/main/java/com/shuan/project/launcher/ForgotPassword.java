package com.shuan.project.launcher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.shuan.project.R;
import com.shuan.project.profile.ProfileViewActivity;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout linear0,linear,linear1;
    private EditText mail,otp,new_pass,con_new_pass;
    private Button button,confirm,get_code,submit,discard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        linear0 = (LinearLayout) findViewById(R.id.linear0);
        linear = (LinearLayout) findViewById(R.id.linear);
        linear1 = (LinearLayout) findViewById(R.id.linear1);

        mail = (EditText) findViewById(R.id.mail);
        otp = (EditText) findViewById(R.id.otp);
        new_pass = (EditText) findViewById(R.id.new_pass);
        con_new_pass = (EditText) findViewById(R.id.con_new_pass);

        button = (Button) findViewById(R.id.button);
        confirm = (Button) findViewById(R.id.confirm);
        get_code = (Button) findViewById(R.id.get_code);
        submit = (Button) findViewById(R.id.submit);
        discard = (Button) findViewById(R.id.discard);

        mail.addTextChangedListener(new TextWatcher() {
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

        button.setOnClickListener(this);
        confirm.setOnClickListener(this);
        get_code.setOnClickListener(this);
        submit.setOnClickListener(this);
        discard.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button:
                if(mail.getText().toString().length()==0){
                    mail.setError("Field Cannot be Empty");
                    mail.requestFocus();
                }else{
                    linear0.setVisibility(View.GONE);
                    linear1.setVisibility(View.GONE);
                    linear.setVisibility(View.VISIBLE);
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
                if (!new_pass.getText().toString().equals(con_new_pass.getText().toString())){
                    new_pass.setError("Password mismatch");
                    new_pass.requestFocus();
                }else{

                }
                break;
            case R.id.discard:
                startActivity(new Intent(getApplicationContext(), LoginActivity.Login.class));
                return;

        }
    }

}
