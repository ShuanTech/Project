package com.shuan.project.employer;

import android.os.Bundle;
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

public class AddCompanyInfoActivity extends AppCompatActivity {
    private Helper helper = new Helper();
    private Common mApp;
    private AutoCompleteTextView city;
    private EditText cmpname, doorno, lctn, cntry, state, pin;
    private ProgressBar progressBar;
    private ScrollView scroll;
    public boolean ins = false;
    private Button cUpt;
    private Spinner cmpnyType, indusType;
    private String[] cType;
    private String[] iType ;
    private String c, i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mApp = (Common) getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company_info);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        scroll = (ScrollView) findViewById(R.id.scroll);
        cmpname = (EditText) findViewById(R.id.cmpn_name);
        cmpnyType = (Spinner) findViewById(R.id.cmpny_type);
        indusType = (Spinner) findViewById(R.id.indus_type);
        doorno = (EditText) findViewById(R.id.door_num);
        lctn = (EditText) findViewById(R.id.loc);
        city = (AutoCompleteTextView) findViewById(R.id.city);
        state = (EditText) findViewById(R.id.state);
        cntry = (EditText) findViewById(R.id.count);
        pin = (EditText) findViewById(R.id.pin);

        cType=getResources().getStringArray(R.array.cmpny_type);
        iType=getResources().getStringArray(R.array.industry_type);
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
                state.setText(txt3.getText().toString());
                cntry.setText(txt4.getText().toString());
                ins = true;
                pin.requestFocus();
            }
        });


        cUpt = (Button) findViewById(R.id.c_update);

        cUpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cmpname.getText().toString().length() == 0) {
                    cmpname.setError("Company Name Mandatory");
                } else if (c.equalsIgnoreCase("Select Company Type")) {
                    cmpnyType.requestFocus();
                    Toast.makeText(getApplicationContext(), "Select Company Type", Toast.LENGTH_SHORT).show();
                } else if (i.equalsIgnoreCase("Select an Industry")) {
                    indusType.requestFocus();
                    Toast.makeText(getApplicationContext(), "Select Industry Type", Toast.LENGTH_SHORT).show();
                } else if (doorno.getText().toString().length() == 0) {
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
                    mApp.getPreference().edit().putBoolean("frm", false).commit();
                    cUpt.setEnabled(false);
                    new CompanyDetail(AddCompanyInfoActivity.this, mApp.getPreference().getString(Common.u_id, ""), cmpname.getText().toString(),
                            c, i, doorno.getText().toString(), lctn.getText().toString(), city.getText().toString(), state.getText().toString(),
                            cntry.getText().toString(), pin.getText().toString(), ins, cUpt).execute();


                }
            }
        });

    }
}
