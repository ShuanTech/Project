package com.shuan.project.signup.employee;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
    private TextView tv, tv1;
    private Button s_skip, s_next;
    private HashMap<String, String> sData;
    private boolean exit = false;
    private TextInputLayout layout_skill,layout_area,layout_dev_evn,layout_others,layout_cer_name,layout_cer_centre,layout_cer_duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mApp = (Common) getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill);

        layout_skill = (TextInputLayout) findViewById(R.id.layout_skill);
        layout_area = (TextInputLayout) findViewById(R.id.layout_area);
        layout_dev_evn = (TextInputLayout) findViewById(R.id.layout_dev_env);
        layout_others = (TextInputLayout) findViewById(R.id.layout_others);

        skill = (MultiAutoCompleteTextView) findViewById(R.id.skill);
        workArea = (MultiAutoCompleteTextView) findViewById(R.id.area);
        dev_env = (EditText) findViewById(R.id.dev_env);
        others = (EditText) findViewById(R.id.others);
        s_next = (Button) findViewById(R.id.s_next);
        s_skip = (Button) findViewById(R.id.s_skip);
        tv = (TextView) findViewById(R.id.tv);


        layout_cer_name = (TextInputLayout) findViewById(R.id.layout_cer_name);
        layout_cer_centre = (TextInputLayout) findViewById(R.id.layout_cer_centre);
        layout_cer_duration = (TextInputLayout) findViewById(R.id.layout_cer_duration);

        course = (EditText) findViewById(R.id.cer_name);
        cerCentre = (EditText) findViewById(R.id.cer_centre);
        cerDur = (EditText) findViewById(R.id.cer_duration);
        tv1 = (TextView) findViewById(R.id.tv1);


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
                        new Skill().execute();
                    }
                }
                break;
            case R.id.s_skip:
                mApp.getPreference().edit().putBoolean(Common.SKILL, false).commit();
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
            sData.put("type", "add");
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