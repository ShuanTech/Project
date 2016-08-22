package com.shuan.project.signup.employee;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.Utils.Helper;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SkillActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private Helper helper = new Helper();
    private Common mApp;
    private EditText course, cerCentre, cerDur, dev_env, others;
    private MultiAutoCompleteTextView skill, workArea;
    private TextView tv,tv1;
    private Button s_skip, s_next;
    private HashMap<String, String> sData;
    private boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApp = (Common) getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill);

        skill = (MultiAutoCompleteTextView) findViewById(R.id.skill);
        workArea = (MultiAutoCompleteTextView) findViewById(R.id.area);
        dev_env = (EditText) findViewById(R.id.dev_env);
        others = (EditText) findViewById(R.id.others);
        s_next = (Button) findViewById(R.id.s_next);
        s_skip = (Button) findViewById(R.id.s_skip);
        tv = (TextView) findViewById(R.id.tv);

        skill.setTypeface(helper.droid(getApplicationContext()));
        workArea.setTypeface(helper.droid(getApplicationContext()));
        dev_env.setTypeface(helper.droid(getApplicationContext()));
        others.setTypeface(helper.droid(getApplicationContext()));
        s_next.setTypeface(helper.droid(getApplicationContext()));
        s_skip.setTypeface(helper.droid(getApplicationContext()));
        tv.setTypeface(helper.droid(getApplicationContext()));

        course = (EditText) findViewById(R.id.cer_name);
        cerCentre = (EditText) findViewById(R.id.cer_centre);
        cerDur = (EditText) findViewById(R.id.cer_duration);
        tv1 = (TextView) findViewById(R.id.tv1);

        tv1.setTypeface(helper.droid(getApplicationContext()));
        course.setTypeface(helper.droid(getApplicationContext()));
        cerCentre.setTypeface(helper.droid(getApplicationContext()));
        cerDur.setTypeface(helper.droid(getApplicationContext()));

        skill.addTextChangedListener(this);

        s_next.setOnClickListener(this);
        s_skip.setOnClickListener(this);

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.s_next:
                if (workArea.getText().toString().length() == 0) {
                    workArea.setError("Interest Area Mandatory");
                    workArea.requestFocus();
                } else {
                    if (course.getText().toString().length() != 0) {

                        if (cerCentre.getText().toString().length() == 0) {
                            cerCentre.setError("Center Name Mandatory");
                            cerCentre.requestFocus();
                        } else if (cerDur.getText().toString().length() == 0) {
                            cerDur.setError("Course Duration Mandatory");
                            cerDur.requestFocus();
                        } else {
                            new Skill().execute();
                            new Certificate().execute();
                        }
                    } else {
                        mApp.getPreference().edit().putBoolean(Common.CERITIFY, false).commit();
                        new Skill().execute();
                    }
                }
                break;
            case R.id.s_skip:
                mApp.getPreference().edit().putBoolean(Common.SKILL, false).commit();
                mApp.getPreference().edit().putBoolean(Common.CERITIFY, false).commit();
                mApp.getPreference().edit().putBoolean(Common.ACTIVITY3, true).commit();
                mApp.getPreference().edit().putBoolean(Common.ACTIVITY2, true).commit();
                startActivity(new Intent(getApplicationContext(), PersonalActivity.class));
                finish();
                break;
        }


    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (skill.getText().toString().length() == 0) {
            s_skip.setVisibility(View.VISIBLE);
            s_next.setVisibility(View.GONE);
        } else {
            s_skip.setVisibility(View.GONE);
            s_next.setVisibility(View.VISIBLE);
        }

    }

    public class Skill extends AsyncTask<String, String, String> {

        String sSkill = skill.getText().toString();
        String sWorkArea = workArea.getText().toString();
        String sDevEnv = dev_env.getText().toString();
        String sOther = others.getText().toString();

        @Override
        protected String doInBackground(String... params) {
            sData = new HashMap<String, String>();
            sData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            sData.put("skill", sSkill);
            sData.put("area", sWorkArea);
            sData.put("devEnv", sDevEnv);
            sData.put("other", sOther);
            try {
                JSONObject json = Connection.UrlConnection(php.skill, sData);
                int succ = json.getInt("success");
                if (succ == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Error...Try Again!...", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mApp.getPreference().edit().putBoolean(Common.SKILL, true).commit();
                            Toast.makeText(getApplicationContext(), "Your Skill Saved", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), PersonalActivity.class));
                            finish();

                        }
                    });
                }
            } catch (JSONException e) {

            }

            return null;
        }
    }

    public class Certificate extends AsyncTask<String, String, String> {

        String cName = course.getText().toString();
        String cCentre = cerCentre.getText().toString();
        String cDuration = cerDur.getText().toString();

        @Override
        protected String doInBackground(String... params) {

            sData = new HashMap<String, String>();
            sData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            sData.put("cer_name", cName);
            sData.put("cer_centre", cCentre);
            sData.put("cer_dur", cDuration);
            try {
                JSONObject json = Connection.UrlConnection(php.certification, sData);
                int succ = json.getInt("success");
                if (succ == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Error...Try Again!...", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mApp.getPreference().edit().putBoolean(Common.CERITIFY, true).commit();
                            mApp.getPreference().edit().putBoolean(Common.ACTIVITY3, true).commit();
                            mApp.getPreference().edit().putBoolean(Common.ACTIVITY2, true).commit();
                            Toast.makeText(getApplicationContext(), "Certification Details Saved", Toast.LENGTH_SHORT).show();


                        }
                    });
                }
            } catch (JSONException e) {

            }

            return null;
        }
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            mApp.getPreference().edit().putBoolean(Common.ACTIVITY3, false).commit();
            mApp.getPreference().edit().putBoolean(Common.ACTIVITY2, true).commit();
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