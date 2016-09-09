package com.shuan.project.employer;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.asyncTasks.PostJob;
import com.shuan.project.list.Sample;

import java.util.ArrayList;

public class JobPostActivity extends AppCompatActivity implements View.OnClickListener {

    private Common mApp;
    private EditText title, category, salary, descr;
    private AutoCompleteTextView location;
    private MultiAutoCompleteTextView skill;
    private LinearLayout jobLay, descLay;
    private Button cancel, save, post;
    private Spinner job_level;
    private String j;
    private RadioGroup job_radio;
    private RadioButton job,part,full,contr;
    private String[] exprnc = new String[]{"< 1 year", "1-2 years", "2-3 years", "3-4 years", "4-5 years","5-6 years","6-7 years","7-8 years","8-9 years","9-10 years","above 10 years"};
    private ArrayList<Sample> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApp = (Common) getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_post);

        jobLay = (LinearLayout) findViewById(R.id.job_lay);
        descLay = (LinearLayout) findViewById(R.id.desc_lay);

        list = new ArrayList<Sample>();

        job_level = (Spinner) findViewById(R.id.job_level);
        job_radio = (RadioGroup) findViewById(R.id.job_radio);
        part = (RadioButton) findViewById(R.id.part);
        full = (RadioButton) findViewById(R.id.full);
        contr = (RadioButton) findViewById(R.id.part);

        title = (EditText) findViewById(R.id.title);
        skill = (MultiAutoCompleteTextView) findViewById(R.id.skill);
        category = (EditText) findViewById(R.id.category);
        salary = (EditText) findViewById(R.id.salary);
        location = (AutoCompleteTextView) findViewById(R.id.location);
        descr = (EditText) findViewById(R.id.descr);

        cancel = (Button) findViewById(R.id.cancel);
        save = (Button) findViewById(R.id.save);
        post = (Button) findViewById(R.id.post);



        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, exprnc);

        job_level.setAdapter(adapter1);
        job_level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String get = job_level.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        cancel.setOnClickListener(this);
        save.setOnClickListener(this);
        post.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                startActivity(new Intent(getApplicationContext(), EmployerActivity.class));
                finish();
                break;
            case R.id.save:
                int getjob_radio = job_radio.getCheckedRadioButtonId();
                job = (RadioButton) findViewById(getjob_radio);
                if (title.getText().toString().length() == 0) {
                    title.setError("Job Title Mandatory");
                    title.requestFocus();
                } else if (skill.getText().length() == 0) {
                    skill.setError("Job Skill Mandatory");
                    skill.requestFocus();
                } else if (category.getText().toString().length() == 0) {
                    category.setError("Job Category Mandatory");
                    category.requestFocus();
                } else if (location.getText().toString().length() == 0) {
                    location.setError("Job Location Mandatory");
                    location.requestFocus();
                } else {
                    jobLay.setVisibility(View.GONE);
                    descLay.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.post:
                if (descr.getText().toString().length() == 0) {
                    descr.setError("Job Description Mandatory");
                    descr.requestFocus();
                } else {
                    new PostJob(JobPostActivity.this, mApp.getPreference().getString(Common.u_id, ""),
                            title.getText().toString(), skill.getText().toString(), job.getText().toString(),
                            category.getText().toString(), salary.getText().toString(), location.getText().toString(),
                            job_level.getSelectedItem().toString(), descr.getText().toString()).execute();
                }
                break;
        }
    }
}
