package com.shuan.project.employer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.asyncTasks.PostJob;

public class JobPostActivity extends AppCompatActivity implements View.OnClickListener {

    private Common mApp;
    private EditText title, skill, type, category, salary, location, level,descr;
    private LinearLayout jobLay, descLay;
    private Button cancel, save, post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApp = (Common) getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_post);

        jobLay = (LinearLayout) findViewById(R.id.job_lay);
        descLay = (LinearLayout) findViewById(R.id.desc_lay);

        title = (EditText) findViewById(R.id.title);
        skill = (EditText) findViewById(R.id.skill);
        type = (EditText) findViewById(R.id.type);
        category = (EditText) findViewById(R.id.category);
        salary = (EditText) findViewById(R.id.salary);
        location = (EditText) findViewById(R.id.location);
        level = (EditText) findViewById(R.id.level);
        descr= (EditText) findViewById(R.id.descr);

        cancel = (Button) findViewById(R.id.cancel);
        save = (Button) findViewById(R.id.save);
        post = (Button) findViewById(R.id.post);

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
                if (title.getText().toString().length() == 0) {
                    title.setError("Job Title Mandatory");
                    title.requestFocus();
                } else if (skill.getText().length() == 0) {
                    skill.setError("Job Skill Mandatory");
                    skill.requestFocus();
                } else if (type.getText().toString().length() == 0) {
                    type.setError("Job Type Mandatory");
                    type.requestFocus();
                } else if (category.getText().toString().length() == 0) {
                    category.setError("Job Category Mandatory");
                    category.requestFocus();
                } else if (location.getText().toString().length() == 0) {
                    location.setError("Job Location Mandatory");
                    location.requestFocus();
                } else if (level.getText().toString().length() == 0) {
                    level.setError("Job Level Mandatory");
                    level.requestFocus();
                }else{
                    jobLay.setVisibility(View.GONE);
                    descLay.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.post:
                if(descr.getText().toString().length()==0){
                    descr.setError("Job Description Mandatory");
                    descr.requestFocus();
                }else{
                    new PostJob(JobPostActivity.this,mApp.getPreference().getString(Common.u_id,""),
                            title.getText().toString(),skill.getText().toString(),type.getText().toString(),
                            category.getText().toString(),salary.getText().toString(),location.getText().toString(),
                            level.getText().toString(),descr.getText().toString()).execute();
                }
                break;
        }
    }
}
