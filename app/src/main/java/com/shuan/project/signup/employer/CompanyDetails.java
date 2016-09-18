package com.shuan.project.signup.employer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.Utils.Helper;
import com.shuan.project.asyncTasks.CompanyDetail;
import com.shuan.project.asyncTasks.GetLocation;
import com.shuan.project.employer.EmployerActivity;

public class CompanyDetails extends AppCompatActivity implements View.OnClickListener {


    private Helper helper = new Helper();
    private Common mApp;
    private AutoCompleteTextView city;
    private EditText cmpname, district, state, country;
    private Button next;
    private ProgressBar progressBar;
    private ScrollView scroll;
    public boolean ins = false;
    private boolean exit = false;
    private Spinner cmpnyType, indusType;
    private String[] cType;
    private String[] iType;
    private String c, i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mApp = (Common) getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);


                /*Organization Details */

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        scroll = (ScrollView) findViewById(R.id.scroll);
        cmpname = (EditText) findViewById(R.id.cmpn_name);
        cmpnyType = (Spinner) findViewById(R.id.cmpny_type);
        indusType = (Spinner) findViewById(R.id.indus_type);
        city = (AutoCompleteTextView) findViewById(R.id.city);
        district = (EditText) findViewById(R.id.district);
        state = (EditText) findViewById(R.id.state);
        country = (EditText) findViewById(R.id.count);
        next = (Button) findViewById(R.id.next);

        cType = getResources().getStringArray(R.array.cmpny_type);
        iType = getResources().getStringArray(R.array.industry_type);
        new GetLocation(getApplicationContext(), scroll, city, progressBar).execute();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, cType);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, iType);

        cmpnyType.setAdapter(adapter);
        indusType.setAdapter(adapter1);
        cmpnyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                c = cmpnyType.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        indusType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                i = indusType.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txt1 = (TextView) view.findViewById(R.id.ins_name);
                TextView txt2 = (TextView) view.findViewById(R.id.univ);
                TextView txt3 = (TextView) view.findViewById(R.id.loc);
                TextView txt4 = (TextView) view.findViewById(R.id.txt1);

                city.setText(txt1.getText().toString());
                district.setText(txt2.getText().toString());
                state.setText(txt3.getText().toString());
                country.setText(txt4.getText().toString());
                ins = true;

            }
        });

        next.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                if (cmpname.getText().toString().length() == 0) {
                    cmpname.setError("Company Name Mandatory");
                } else if (c.equalsIgnoreCase("Select Company Type")) {
                    cmpnyType.requestFocus();
                    Toast.makeText(getApplicationContext(), "Select Company Type", Toast.LENGTH_SHORT).show();
                } else if (i.equalsIgnoreCase("Select an Industry")) {
                    indusType.requestFocus();
                    Toast.makeText(getApplicationContext(), "Select Industry Type", Toast.LENGTH_SHORT).show();
                } else if (city.getText().toString().length() == 0) {
                    city.setError("City Mandatory");
                    city.requestFocus();
                } else if (district.getText().toString().length() == 0) {
                    district.setError("District Mandatory");
                    district.requestFocus();
                } else if (state.getText().toString().length() == 0) {
                    state.setError("State Mandatory");
                    state.requestFocus();
                } else if (country.getText().toString().length() == 0) {
                    country.setError("Country Mandatory");
                    country.requestFocus();
                } else {
                    next.setEnabled(false);
                    new CompanyDetail(CompanyDetails.this, mApp.getPreference().getString(Common.u_id, ""), cmpname.getText().toString(),
                            c, i, city.getText().toString(), district.getText().toString(), state.getText().toString(),
                            country.getText().toString(), ins, next).execute();

                }
                break;


        }
    }


    @Override
    public void onBackPressed() {
        if (exit) {
            mApp.getPreference().edit().putBoolean(Common.COMPANY,false).commit();
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
