package com.shuan.Project.employer;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.shuan.Project.R;
import com.shuan.Project.Utils.Common;
import com.shuan.Project.asyncTasks.SelectCandidate;

import java.util.Calendar;

public class InterviewPanelActivity extends AppCompatActivity implements View.OnClickListener {

    private Common mApp;
    private Toolbar toolbar;
    private String[] mnth = new String[0];
    private String[] date = new String[0];
    private EditText loc, yr, mnt, dat, frm_time, to_time,cmmts;
    private Button set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApp = (Common) getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview_panel);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Interview Panel");

        loc = (EditText) findViewById(R.id.loc);
        yr = (EditText) findViewById(R.id.yr);
        mnt = (EditText) findViewById(R.id.mnth);
        dat = (EditText) findViewById(R.id.dat);
        frm_time = (EditText) findViewById(R.id.frm_time);
        to_time = (EditText) findViewById(R.id.to_time);
        set = (Button) findViewById(R.id.set);
        cmmts= (EditText) findViewById(R.id.cmmnts);

        mnth = getResources().getStringArray(R.array.month);
        date = getResources().getStringArray(R.array.date);
        Calendar cal = Calendar.getInstance();
        yr.setText(String.valueOf(cal.get(Calendar.YEAR)));

        mnt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showMnth();
                }
                return false;
            }
        });

        dat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDat();
                }
                return false;
            }
        });

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


        set.setOnClickListener(this);


    }

    private void showTimePicker(final String where) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.time_picker, null, false);

        // the time picker on the alert dialog, this is how to get the value
        final TimePicker myTimePicker = (TimePicker) view
                .findViewById(R.id.myTimePicker);


        new AlertDialog.Builder(InterviewPanelActivity.this).setView(view)
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


    private void showDat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(InterviewPanelActivity.this);
        builder.setTitle("Select Date")
                .setSingleChoiceItems(date, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dat.setText(date[which]);
                        dialog.cancel();
                    }
                }).show();
    }

    private void showMnth() {
        AlertDialog.Builder builder = new AlertDialog.Builder(InterviewPanelActivity.this);
        builder.setTitle("Select Month")
                .setSingleChoiceItems(mnth, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mnt.setText(mnth[which]);
                        dialog.cancel();
                    }
                }).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set:
                if (loc.getText().toString().length() == 0) {
                    loc.setError("Field Mandatory");
                    loc.requestFocus();
                } else if (yr.getText().toString().length() == 0) {
                    yr.setError("Field Mandatory");
                    yr.requestFocus();
                } else if (mnt.getText().toString().length() == 0) {
                    mnt.setError("Field Mandatory");
                    mnt.requestFocus();
                } else if (dat.getText().toString().length() == 0) {
                    dat.setError("Field Mandatory");
                    dat.requestFocus();
                } else if (frm_time.getText().toString().length() == 0) {
                    frm_time.setError("Field Mandatory");
                    frm_time.requestFocus();
                } else if (to_time.getText().toString().length() == 0) {
                    to_time.setError("Field Mandatory");
                    to_time.requestFocus();
                } else {

                    String dt = yr.getText().toString() + "-" + mnt.getText().toString() + "-" + dat.getText().toString();
                    String tme = frm_time.getText().toString() + "-" + to_time.getText().toString();
                    set.setEnabled(false);
                    new SelectCandidate(InterviewPanelActivity.this,mApp.getPreference().getString("jId", ""), getIntent().getStringExtra("a_id"),
                            getIntent().getStringExtra("r_id"), loc.getText().toString(),
                            dt,tme,cmmts.getText().toString(),set).execute();
                }
                break;
        }
    }
}
