package com.shuan.project.signup.employer;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.Utils.Helper;
import com.shuan.project.asyncTasks.CompanyDetail;
import com.shuan.project.asyncTasks.Follower;
import com.shuan.project.asyncTasks.GetOrg;

import java.util.HashMap;

public class CompanyDetails extends AppCompatActivity implements TextWatcher, View.OnClickListener {


    private Helper helper = new Helper();
    private Common mApp;
    private AutoCompleteTextView cmpname;
    private HashMap<String, String> cData;
    private EditText cmpnyType, doorno, lctn, cntry, city, state, pin;
    private Button cd_skip, cd_next;
    private ProgressBar progressBar;
    private ScrollView scroll;
    public boolean ins = false;
    private boolean exit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApp = (Common) getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);


                /*Organization Details */

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        scroll = (ScrollView) findViewById(R.id.scroll);
        cmpname = (AutoCompleteTextView) findViewById(R.id.cmpn_name);
        cmpnyType = (EditText) findViewById(R.id.cmpny_type);
        doorno = (EditText) findViewById(R.id.door_num);
        lctn = (EditText) findViewById(R.id.loc);
        cntry = (EditText) findViewById(R.id.count);
        city = (AutoCompleteTextView) findViewById(R.id.city);
        state = (AutoCompleteTextView) findViewById(R.id.state);
        pin = (EditText) findViewById(R.id.pin);
        cd_skip = (Button) findViewById(R.id.cd_skip);
        cd_next = (Button) findViewById(R.id.cd_next);

        cmpname.setTypeface(helper.droid(getApplicationContext()));
        cmpnyType.setTypeface(helper.droid(getApplicationContext()));
        doorno.setTypeface(helper.droid(getApplicationContext()));
        lctn.setTypeface(helper.droid(getApplicationContext()));
        cntry.setTypeface(helper.droid(getApplicationContext()));
        city.setTypeface(helper.droid(getApplicationContext()));
        state.setTypeface(helper.droid(getApplicationContext()));
        pin.setTypeface(helper.droid(getApplicationContext()));
        cd_next.setTypeface(helper.droid(getApplicationContext()));
        cd_skip.setTypeface(helper.droid(getApplicationContext()));

        new GetOrg(getApplicationContext(), progressBar, scroll, cmpname).execute();


        cmpname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView txt1 = (TextView) view.findViewById(R.id.ins_name);
                TextView txt2 = (TextView) view.findViewById(R.id.univ);
                TextView txt3 = (TextView) view.findViewById(R.id.loc);
                TextView txt4 = (TextView) view.findViewById(R.id.txt1);
                TextView txt5 = (TextView) view.findViewById(R.id.txt2);
                TextView txt6 = (TextView) view.findViewById(R.id.txt3);
                TextView txt7 = (TextView) view.findViewById(R.id.txt4);
                TextView txt8= (TextView) view.findViewById(R.id.txt5);

                cmpname.setText(txt1.getText().toString());
                cmpnyType.setText(txt2.getText().toString());
                doorno.setText(txt3.getText().toString());
                lctn.setText(txt4.getText().toString());
                city.setText(txt7.getText().toString());
                state.setText(txt6.getText().toString());
                cntry.setText(txt5.getText().toString());
                pin.setText(txt8.getText().toString());
                ins = true;
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });





        cd_skip.setOnClickListener(this);
        cd_next.setOnClickListener(this);

        cmpname.addTextChangedListener(this);



    }




    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if (cmpname.getText().toString().length() == 0) {
            cd_skip.setVisibility(View.VISIBLE);
            cd_next.setVisibility(View.GONE);
        } else {

            cd_skip.setVisibility(View.GONE);
            cd_next.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.cd_next:
                if (doorno.getText().toString().length() == 0) {
                    doorno.setError("Address Mandatory");
                    doorno.requestFocus();
                } else if (lctn.getText().toString().length() == 0) {
                    lctn.setError("Location Mandatory");
                    lctn.requestFocus();
                } else if (cntry.getText().toString().length() == 0) {
                    cntry.setError("Country Mandatory");
                    cntry.requestFocus();
                } else if (state.getText().toString().length() == 0) {
                    state.setError("State Mandatory");
                    state.requestFocus();
                } else if (city.getText().toString().length() == 0) {
                    city.setError("City Mandatory");
                    city.requestFocus();
                } else if (pin.getText().toString().length() == 0) {
                    pin.setError("Pincode Mandatory");
                    pin.requestFocus();
                } else {
                    mApp.getPreference().edit().putString(Common.COMPANY,cmpname.getText().toString()).commit();
                    new CompanyDetail(CompanyDetails.this, mApp.getPreference().getString(Common.u_id, ""), cmpname.getText().toString(),
                            cmpnyType.getText().toString(), doorno.getText().toString(), lctn.getText().toString(), city.getText().toString(), state.getText().toString(),
                            cntry.getText().toString(), pin.getText().toString(), ins).execute();



                }
                break;
            case R.id.cd_skip:
                mApp.getPreference().edit().putBoolean(Common.BASICDETAILS, false).commit();
                mApp.getPreference().edit().putBoolean(Common.COMPANY1, true).commit();
                startActivity(new Intent(getApplicationContext(),CompanyContactInfoActivity.class));
                finish();
                break;

        }
    }




    @Override
    public void onBackPressed() {
        if (exit) {
            mApp.getPreference().edit().putBoolean(Common.COMPANY1, false).commit();
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
