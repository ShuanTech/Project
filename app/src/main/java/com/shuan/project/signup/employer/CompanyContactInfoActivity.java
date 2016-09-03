package com.shuan.project.signup.employer;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.Utils.EmailValidator;
import com.shuan.project.Utils.Helper;
import com.shuan.project.asyncTasks.CompanyContactInfo;

public class CompanyContactInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private Helper helper = new Helper();
    private Common mApp;
    private boolean exit = false;
    private EditText cnt_prsn, cnt_mail, cnt_phn, avl_time, frm_time, to_time;
    private EmailValidator emailValidator;
    private Button cp_skip, cp_next;
    private String contctTime;
    private TextInputLayout layout_cnt_prsn,layout_cnt_mail,layout_cnt_phn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_contact_info);

         /*Contact Details */

        layout_cnt_mail = (TextInputLayout) findViewById(R.id.layout_cnt_mail);
        layout_cnt_phn = (TextInputLayout) findViewById(R.id.layout_cnt_phn);
        layout_cnt_prsn = (TextInputLayout) findViewById(R.id.layout_cnt_prsn);

        cnt_prsn = (EditText) findViewById(R.id.cnt_prsn);
        cnt_mail = (EditText) findViewById(R.id.cnt_mail);
        cnt_phn = (EditText) findViewById(R.id.cnt_phn);
        frm_time = (EditText) findViewById(R.id.frm_time);
        to_time = (EditText) findViewById(R.id.to_time);
        cp_skip = (Button) findViewById(R.id.cp_skip);
        cp_next = (Button) findViewById(R.id.cp_next);
        emailValidator=new EmailValidator();

        frm_time.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showTimePicker("frm");
                }
                return false;
            }
        });
        to_time.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showTimePicker("to");
                }
                return false;
            }
        });

        /*layout_cnt_prsn.setTypeface(helper.droid(getApplicationContext()));
        layout_cnt_mail.setTypeface(helper.droid(getApplicationContext()));
        layout_cnt_phn.setTypeface(helper.droid(getApplicationContext()));

        cnt_prsn.setTypeface(helper.droid(getApplicationContext()));
        cnt_mail.setTypeface(helper.droid(getApplicationContext()));
        cnt_phn.setTypeface(helper.droid(getApplicationContext()));

        cp_next.setTypeface(helper.droid(getApplicationContext()));
        cp_skip.setTypeface(helper.droid(getApplicationContext()));*/

        cp_skip.setOnClickListener(this);
        cp_next.setOnClickListener(this);

        cnt_prsn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (cnt_prsn.getText().toString().length() == 0) {
                    cp_next.setVisibility(View.GONE);
                    cp_skip.setVisibility(View.VISIBLE);
                } else {
                    cp_next.setVisibility(View.VISIBLE);
                    cp_skip.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cp_next:
                if (cnt_mail.getText().toString().length() == 0) {
                    cnt_mail.setError("Mail Id Mandatory");
                    cnt_mail.requestFocus();
                } else if (!emailValidator.validate(cnt_mail.getText().toString())) {
                    cnt_mail.setError("Invalid Email Id");
                    cnt_mail.requestFocus();
                } else if (cnt_phn.getText().toString().length() == 0) {
                    cnt_phn.setError("Phone Number Mandatory");
                    cnt_phn.requestFocus();
                } else if(cnt_phn.getText().toString().length()<10){
                    cnt_phn.setError("Enter Valid Phone Number");
                    cnt_phn.requestFocus();
                }else if (frm_time.getText().toString().length() == 0) {
                    frm_time.setError("Field Mandatory");
                    frm_time.requestFocus();
                } else if (to_time.getText().toString().length() == 0) {
                    to_time.setError("Field Mandatory");
                    to_time.requestFocus();
                } else {
                    contctTime = frm_time.getText().toString() + " - " + to_time.getText().toString();

                    new CompanyContactInfo(CompanyContactInfoActivity.this,mApp.getPreference().getString(Common.u_id,""),
                            cnt_prsn.getText().toString(),cnt_mail.getText().toString(),cnt_phn.getText().toString(),
                            contctTime).execute();
                }
                break;
            case R.id.cp_skip:
                mApp.getPreference().edit().putBoolean(Common.CONTACTDETAILS, false).commit();
                mApp.getPreference().edit().putBoolean(Common.COMPANY2, true).commit();
                startActivity(new Intent(getApplicationContext(),AboutCompanyActivity.class));
                finish();
                break;
        }
    }


    private void showTimePicker(final String where) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.time_picker, null, false);

        // the time picker on the alert dialog, this is how to get the value
        final TimePicker myTimePicker = (TimePicker) view
                .findViewById(R.id.myTimePicker);


        new AlertDialog.Builder(CompanyContactInfoActivity.this).setView(view)
                .setTitle("Set Time")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.M)
                    public void onClick(DialogInterface dialog, int id) {

                        int currentHourText = myTimePicker.getCurrentHour();


                        int currentMinuteText = myTimePicker
                                .getCurrentMinute();
                        updateTime(currentHourText, currentMinuteText, where);
                        dialog.cancel();

                    }

                }).show();
    }

    private void updateTime(int hours, int mins, String where) {
        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";
        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();
        if (where.equalsIgnoreCase("frm")) {
            frm_time.setText(aTime);
        } else {
            to_time.setText(aTime);
        }
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            mApp.getPreference().edit().putBoolean(Common.COMPANY2, false).commit();
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
