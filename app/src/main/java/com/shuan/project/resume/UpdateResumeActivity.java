package com.shuan.project.resume;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.Utils.Helper;
import com.shuan.project.asyncTasks.profileSummaryUpdate;

public class UpdateResumeActivity extends AppCompatActivity implements View.OnClickListener {

    private String what, which;
    private Common mApp;
    private RelativeLayout lay1, lay2, lay3, lay4, lay5, lay6, lay7, lay8, lay9, lay10;
    private int i = 0, j = 0, k = 0, l = 0;
    private Helper helper = new Helper();


    /* Profile Summary Field */
    private LinearLayout psContainer;
    private Button addPS, psUpdate;
    private String[] profile = new String[0];
    private TextView ps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        what = getIntent().getStringExtra("what");
        which = getIntent().getStringExtra("which");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_resume);

        lay1 = (RelativeLayout) findViewById(R.id.lay1);

        if (what.equalsIgnoreCase("add")) {

            if (which.equalsIgnoreCase("pro")) {
                lay1.setVisibility(View.VISIBLE);

                ps = (TextView) findViewById(R.id.ps);
                psContainer = (LinearLayout) findViewById(R.id.ps_container);
                addPS = (Button) findViewById(R.id.add_ps);
                psUpdate = (Button) findViewById(R.id.ps_update);

                ps.setTypeface(helper.droid(getApplicationContext()));
                psUpdate.setTypeface(helper.droid(getApplicationContext()));

                addPS.setOnClickListener(this);
                psUpdate.setOnClickListener(this);
            }

        } else {
            Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_ps:
                addProfileSummary();
                break;
            case R.id.ps_update:
                getProfileSummary(psContainer);
                break;
        }
    }


    private void addProfileSummary() {
        EditText et;
        if (i == 0) {
            et = new EditText(this);
            et.setHint("Profile Summary");
            et.setId(i);
            et.setTypeface(helper.droid(getApplicationContext()));
            et.requestFocus();
            psContainer.addView(et);
            i++;
        } else {
            int j = i - 1;
            et = (EditText) this.findViewById(j);
            if (et.getText().toString().length() == 0) {
                et.setError("Filed Mandatory");
            } else {
                et = new EditText(this);
                et.setHint("Profile Summary");
                et.setTypeface(helper.droid(getApplicationContext()));
                et.setId(i);
                psContainer.addView(et);
                i++;
            }

        }


    }

    private void getProfileSummary(ViewGroup psContainer) {

        for (int i = 0; i < psContainer.getChildCount(); i++) {
            View child = psContainer.getChildAt(i);
            EditText et = (EditText) child;
            if (et.getText().toString().length() == 0) {
            } else {
                profile = new String[]{et.getText().toString()};
            }

        }
        if (profile.length == 0) {
            Toast.makeText(getApplicationContext(), "Enter Profile Summary", Toast.LENGTH_SHORT).show();
        } else {
            new profileSummaryUpdate(UpdateResumeActivity.this, mApp.getPreference().getString(Common.u_id,""),profile).execute();
        }

    }


}
