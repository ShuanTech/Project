package com.shuan.project.signup.employee;

import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.Utils.Helper;
import com.shuan.project.adapter.LocationAdapter;
import com.shuan.project.asyncTasks.FrsherDefault;
import com.shuan.project.asyncTasks.GetInfo;
import com.shuan.project.employee.JuniorActivity;
import com.shuan.project.employee.SeniorActivity;
import com.shuan.project.fragment.DateDialog;
import com.shuan.project.list.Sample;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PersonalActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    public Common mApp;
    public EditText dob, fName, mName, addr, pinNo;
    public AutoCompleteTextView loc, district, state, country;
    public RadioButton radio, r1, r2;
    public Button po_next, po_skip;
    public RadioGroup sex;
    public HashMap<String, String> pData;
    public ScrollView scrollView;
    public ProgressBar progressBar;
    public LocationAdapter adapter;
    private TextView prsnl;
    public ArrayList<Sample> list;
    private Helper helper = new Helper();
    public boolean ins = false;
    public boolean exit = false;
    private TextInputLayout layout_f_name,layout_m_name,layout_door,layout_location,layout_district,layout_state,layout_cntry,layout_pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mApp = (Common) getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        scrollView = (ScrollView) findViewById(R.id.scroll_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        list = new ArrayList<Sample>();

        new getLocation().execute();

        layout_f_name = (TextInputLayout) findViewById(R.id.layout_f_name);
        layout_m_name = (TextInputLayout) findViewById(R.id.layout_m_name);
        layout_door = (TextInputLayout) findViewById(R.id.layout_door);
        layout_location = (TextInputLayout) findViewById(R.id.layout_location);
        layout_district = (TextInputLayout) findViewById(R.id.layout_district);
        layout_state = (TextInputLayout) findViewById(R.id.layout_state);
        layout_cntry = (TextInputLayout) findViewById(R.id.layout_cntry);
        layout_pin = (TextInputLayout) findViewById(R.id.layout_pin);


        dob = (EditText) findViewById(R.id.dob);
        r1 = (RadioButton) findViewById(R.id.male);
        r2 = (RadioButton) findViewById(R.id.female);
        fName = (EditText) findViewById(R.id.f_name);
        mName = (EditText) findViewById(R.id.m_name);
        addr = (EditText) findViewById(R.id.door);
        loc = (AutoCompleteTextView) findViewById(R.id.location);
        district = (AutoCompleteTextView) findViewById(R.id.district);
        state = (AutoCompleteTextView) findViewById(R.id.state);
        country = (AutoCompleteTextView) findViewById(R.id.cntry);
        prsnl = (TextView) findViewById(R.id.prsnl);


        pinNo = (EditText) findViewById(R.id.pin);
        po_next = (Button) findViewById(R.id.po_next);
        po_skip = (Button) findViewById(R.id.po_skip);
        sex = (RadioGroup) findViewById(R.id.sex);



        dob.setOnTouchListener(new View.OnTouchListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    DateDialog dialog = new DateDialog(v);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "DataPicker");
                }
                return false;
            }
        });


        dob.addTextChangedListener(this);
        po_next.setOnClickListener(this);
        po_skip.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.po_next:
                int getSex = sex.getCheckedRadioButtonId();
                radio = (RadioButton) findViewById(getSex);
                if (fName.getText().toString().length() == 0) {
                    fName.setError("Father Name Mandatory");
                    fName.requestFocus();
                } else if (mName.getText().toString().length() == 0) {
                    mName.setError("Mother Name Mandatory");
                    mName.requestFocus();
                } else if (addr.getText().toString().length() == 0) {
                    addr.setError("Address Mandatory");
                    addr.requestFocus();
                } else if (loc.getText().toString().length() == 0) {
                    loc.setError("City / Town / Location Mandatory");
                    loc.requestFocus();
                } else if (district.getText().toString().length() == 0) {
                    district.setError("District Mandatory");
                    district.requestFocus();
                } else if (state.getText().toString().length() == 0) {
                    state.setError("State Mandatory");
                    state.requestFocus();
                } else if (country.getText().toString().length() == 0) {
                    country.setError("Country Mandatory");
                    country.requestFocus();
                } else if (pinNo.getText().toString().length() == 0) {
                    pinNo.setError("Pincode Mandatory");
                    pinNo.requestFocus();
                } else {
                    new Personal().execute();
                }
                break;
            case R.id.po_skip:
                mApp.getPreference().edit().putBoolean(Common.HOBBIES, false).commit();
                mApp.getPreference().edit().putBoolean(Common.PROJECT, false).commit();
                mApp.getPreference().edit().putBoolean(Common.PERSONALINFO, false).commit();
                mApp.getPreference().edit().putInt(Common.PROFILESTRENGTH, 25).commit();

                if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("1")) {
                    mApp.getPreference().edit().putString(Common.RESUME, "junior").commit();
                    startActivity(new Intent(getApplicationContext(), JuniorActivity.class));
                } else {
                    mApp.getPreference().edit().putString(Common.RESUME, "senior").commit();
                    mApp.getPreference().edit().putBoolean(Common.PROFILESUMMARY, false).commit();
                    mApp.getPreference().edit().putBoolean(Common.WORKEXPERIENCE, false).commit();
                    startActivity(new Intent(getApplicationContext(), SeniorActivity.class));
                }
                new GetInfo(getApplicationContext(), mApp.getPreference().getString(Common.u_id, "")).execute();
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

        if (dob.getText().toString().length() == 0) {
            po_next.setVisibility(View.GONE);
            po_skip.setVisibility(View.VISIBLE);
        } else {
            po_next.setVisibility(View.VISIBLE);
            po_skip.setVisibility(View.GONE);
        }
    }

    public class getLocation extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            pData = new HashMap<String, String>();
            pData.put("id", "city");
            try {

                JSONObject json = Connection.UrlConnection(php.getCity, pData);
                int succ = json.getInt("success");

                if (succ == 0) {
                } else {

                    JSONArray jsonArray = json.getJSONArray("city");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject child = jsonArray.getJSONObject(i);
                        String xcty = child.optString("city");
                        String distrt = child.optString("district");
                        String stea = child.optString("state");
                        String con = child.optString("country");


                        list.add(new Sample(xcty + "," + distrt, xcty, distrt, stea, con));
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter = new LocationAdapter(getApplicationContext(), R.layout.custom_auto_complete_item, R.id.display, list);
                            loc.setThreshold(1);
                            loc.setAdapter(adapter);
                            loc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    TextView txt1 = (TextView) view.findViewById(R.id.ins_name);
                                    TextView txt2 = (TextView) view.findViewById(R.id.univ);
                                    TextView txt3 = (TextView) view.findViewById(R.id.loc);
                                    TextView txt4 = (TextView) view.findViewById(R.id.txt1);

                                    loc.setText(txt1.getText().toString());
                                    district.setText(txt2.getText().toString());
                                    state.setText(txt3.getText().toString());
                                    country.setText(txt4.getText().toString());
                                    ins = true;
                                    pinNo.requestFocus();

                                }
                            });


                        }
                    });


                }

            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    public class Personal extends AsyncTask<String, String, String> {

        String uDob = dob.getText().toString();
        String usex = radio.getText().toString();
        String ufName = fName.getText().toString();
        String umName = mName.getText().toString();
        String udno = addr.getText().toString();
        String uct = loc.getText().toString();
        String udistirct = district.getText().toString();
        String ustate = state.getText().toString();
        String ucnt = country.getText().toString();
        String upin = pinNo.getText().toString();


        @Override
        protected String doInBackground(String... params) {
            pData = new HashMap<String, String>();
            pData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            pData.put("dob", uDob);
            pData.put("gender", usex);
            pData.put("address", udno);
            pData.put("city", uct);
            pData.put("district", udistirct);
            pData.put("state", ustate);
            pData.put("country", ucnt);
            pData.put("pincode", upin);
            pData.put("father_name", ufName);
            pData.put("mother_name", umName);
            if (ins == true) {
                pData.put("insrt", "false");
            } else {
                pData.put("insrt", "true");
            }
            try {
                JSONObject json = Connection.UrlConnection(php.persnal_info, pData);
                int succ = json.getInt("success");
                if (succ == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Error... Try Again!", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mApp.getPreference().edit().putBoolean(Common.PERSONALINFO, true).commit();
                            mApp.getPreference().edit().putBoolean(Common.HOBBIES, false).commit();
                            mApp.getPreference().edit().putBoolean(Common.PROJECT, false).commit();
                            mApp.getPreference().edit().putBoolean(Common.USRINFO, true).commit();
                            mApp.getPreference().edit().putInt(Common.PROFILESTRENGTH, 25).commit();
                            new GetInfo(getApplicationContext(), mApp.getPreference().getString(Common.u_id, "")).execute();

                            if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("1")) {
                                mApp.getPreference().edit().putString(Common.RESUME, "junior").commit();
                                new FrsherDefault(PersonalActivity.this,mApp.getPreference().getString(Common.u_id,""),uct).execute();
                                startActivity(new Intent(getApplicationContext(), JuniorActivity.class));
                            } else {
                                mApp.getPreference().edit().putString(Common.RESUME, "senior").commit();
                                mApp.getPreference().edit().putBoolean(Common.PROFILESUMMARY, false).commit();
                                mApp.getPreference().edit().putBoolean(Common.WORKEXPERIENCE, false).commit();
                                startActivity(new Intent(getApplicationContext(), SeniorActivity.class));
                            }
                            Toast.makeText(getApplicationContext(), "Successfully Completed Signup...", Toast.LENGTH_LONG).show();


                            finish();
                        }
                    });
                }
            } catch (Exception e) {
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
