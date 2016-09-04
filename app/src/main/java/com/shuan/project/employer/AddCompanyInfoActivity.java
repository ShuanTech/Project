package com.shuan.project.employer;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.Utils.Helper;
import com.shuan.project.asyncTasks.CompanyDetail;
import com.shuan.project.asyncTasks.GetOrg;

import java.util.HashMap;

public class AddCompanyInfoActivity extends AppCompatActivity {

    private Helper helper = new Helper();
    private Common mApp;
    private AutoCompleteTextView cmpname;
    private HashMap<String, String> cData;
    private EditText cmpnyType, doorno, lctn, cntry, city, state, pin;
    private Button cUpt;
    private ProgressBar progressBar;
    private ScrollView scroll;
    public boolean ins = false;
    private String district;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mApp = (Common) getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company_info);

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

        cUpt = (Button) findViewById(R.id.c_update);


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
                TextView txt8 = (TextView) view.findViewById(R.id.txt5);
                TextView txt9 = (TextView) view.findViewById(R.id.txt6);

                cmpname.setText(txt1.getText().toString());
                cmpnyType.setText(txt2.getText().toString());
                doorno.setText(txt3.getText().toString());
                lctn.setText(txt4.getText().toString());
                city.setText(txt7.getText().toString());
                state.setText(txt6.getText().toString());
                cntry.setText(txt5.getText().toString());
                pin.setText(txt8.getText().toString());
                district = txt9.getText().toString();
                ins = true;
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        cUpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    mApp.getPreference().edit().putBoolean("frm", false).commit();
                    new CompanyDetail(AddCompanyInfoActivity.this, mApp.getPreference().getString(Common.u_id, ""), cmpname.getText().toString(),
                            cmpnyType.getText().toString(), doorno.getText().toString(), lctn.getText().toString(), city.getText().toString(), state.getText().toString(),
                            cntry.getText().toString(), pin.getText().toString(), ins).execute();


                }
            }
        });

    }
}
