package com.shuan.project.profile;

import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
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
import com.shuan.project.adapter.LocationAdapter;
import com.shuan.project.asyncTasks.GetLocation;
import com.shuan.project.asyncTasks.UpdateUsrInfo;
import com.shuan.project.fragment.DateDialog;
import com.shuan.project.list.Sample;

import java.util.ArrayList;
import java.util.HashMap;

public class InfoEditActivity extends AppCompatActivity implements View.OnClickListener {

    private Common mApp;
    private String name, dat, gen, rel, blood, address, city, district, state, country, pin;
    private HashMap<String, String> iData;
    public EditText fullName, dob, relation, bld, addr, pinNo;
    public AutoCompleteTextView loc, distct, ste, coutry;
    public RadioButton radio, r1, r2;
    public ScrollView scrollView;
    public ProgressBar progressBar;
    public ArrayList<Sample> list;
    public Toolbar toolbar;
    public RadioGroup sex;
    public Button save, cancel;
    public boolean ins = false;
    private HashMap<String, String> pData;
    public LocationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApp = (Common) getApplicationContext();
        name = getIntent().getStringExtra("name");
        dat = getIntent().getStringExtra("dat");
        gen = getIntent().getStringExtra("gen");
        rel = getIntent().getStringExtra("rel");
        blood = getIntent().getStringExtra("blood");
        address = getIntent().getStringExtra("addr");
        city = getIntent().getStringExtra("city");
        district = getIntent().getStringExtra("district");
        state = getIntent().getStringExtra("state");
        country = getIntent().getStringExtra("country");
        pin = getIntent().getStringExtra("pincode");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_edit);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Info");

        scrollView = (ScrollView) findViewById(R.id.scroll);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);


        fullName = (EditText) findViewById(R.id.full_name);
        dob = (EditText) findViewById(R.id.dob);
        r1 = (RadioButton) findViewById(R.id.male);
        r2 = (RadioButton) findViewById(R.id.female);
        relation = (EditText) findViewById(R.id.relation);
        bld = (EditText) findViewById(R.id.bld);
        addr = (EditText) findViewById(R.id.door);
        loc = (AutoCompleteTextView) findViewById(R.id.location);
        distct = (AutoCompleteTextView) findViewById(R.id.district);
        ste = (AutoCompleteTextView) findViewById(R.id.state);
        coutry = (AutoCompleteTextView) findViewById(R.id.cntry);
        pinNo = (EditText) findViewById(R.id.pin);
        sex = (RadioGroup) findViewById(R.id.sex);
        save = (Button) findViewById(R.id.save);
        cancel = (Button) findViewById(R.id.cancel);
        new GetLocation(getApplicationContext(), scrollView, loc, progressBar).execute();
        fullName.setText(name);
        dob.setText(dat);
        relation.setText(rel);
        bld.setText(blood);
        addr.setText(address);
        loc.setText(city);
        distct.setText(district);
        ste.setText(state);
        coutry.setText(country);
        pinNo.setText(pin);
        Toast.makeText(getApplicationContext(), gen, Toast.LENGTH_SHORT).show();

        dob.setOnTouchListener(new View.OnTouchListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                   /* InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);*/
                    DateDialog dialog = new DateDialog(v);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "DataPicker");
                }
                return false;
            }
        });

        loc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView txt1 = (TextView) view.findViewById(R.id.ins_name);
                TextView txt2 = (TextView) view.findViewById(R.id.univ);
                TextView txt3 = (TextView) view.findViewById(R.id.loc);
                TextView txt4 = (TextView) view.findViewById(R.id.txt1);

                loc.setText(txt1.getText().toString());
                distct.setText(txt2.getText().toString());
                ste.setText(txt3.getText().toString());
                coutry.setText(txt4.getText().toString());
                ins = true;
                pinNo.requestFocus();
            }
        });

        save.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.cancel:
                startActivity(new Intent(getApplicationContext(), JunUpdateInfo.class));
                finish();
                break;
            case R.id.save:
                if (fullName.getText().toString().length() == 0) {
                    fullName.setError("Field Mandatory");
                } else if (dob.getText().toString().length() == 0) {
                    dob.setError("Field Mandatory");
                } else if (relation.getText().toString().length() == 0) {
                    relation.setError("Field Mandatory");
                } else if (bld.getText().toString().length() == 0) {
                    bld.setError("Field Mandatory");
                } else if (addr.getText().toString().length() == 0) {
                    addr.setError("Field Mandatory");
                } else if (loc.getText().toString().length() == 0) {
                    loc.setError("Field Mandatory");
                } else if (distct.getText().toString().length() == 0) {
                    distct.setError("Field Mandatory");
                } else if (ste.getText().toString().length() == 0) {
                    ste.setError("Field Mandatory");
                } else if (coutry.getText().toString().length() == 0) {
                    coutry.setError("Field Mandatory");
                } else if (pinNo.getText().toString().length() == 0) {
                    pinNo.setError("Field Mandatory");
                } else {
                    int getSex = sex.getCheckedRadioButtonId();
                    radio = (RadioButton) findViewById(getSex);
                    new UpdateUsrInfo(InfoEditActivity.this, mApp.getPreference().getString(Common.u_id, ""), fullName.getText().toString(),
                            dob.getText().toString(), radio.getText().toString(), relation.getText().toString(), bld.getText().toString(), addr.getText().toString(),
                            loc.getText().toString(), distct.getText().toString(), ste.getText().toString(),
                            coutry.getText().toString(), pinNo.getText().toString(), ins).execute();
                }
                break;
        }

    }


}
