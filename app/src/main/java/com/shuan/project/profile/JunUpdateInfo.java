package com.shuan.project.profile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.asyncTasks.UpdateStatus;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class JunUpdateInfo extends AppCompatActivity implements View.OnClickListener {

    private Common mApp;
    private ImageView introEdit, infoEdit;
    private RelativeLayout lay2;
    private Toolbar toolbar;
    private AlertDialog.Builder builder;
    private EditText about;
    private TextView intro, fullName, dob, gender, relation, bld, addr, cty, ste, distct, contry;
    private ScrollView scroll;
    private HashMap<String, String> iData;
    private ProgressBar progressBar;
    private String name, dat, gen, rel, blood, address, city, district, state, pin, country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApp = (Common) getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Info");

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        lay2 = (RelativeLayout) findViewById(R.id.lay2);
        scroll = (ScrollView) findViewById(R.id.scroll);
        introEdit = (ImageView) findViewById(R.id.intro_edit);
        intro = (TextView) findViewById(R.id.intro);
        fullName = (TextView) findViewById(R.id.full_name);
        dob = (TextView) findViewById(R.id.dob);
        gender = (TextView) findViewById(R.id.gen);
        relation = (TextView) findViewById(R.id.rel);
        bld = (TextView) findViewById(R.id.bld);
        addr = (TextView) findViewById(R.id.addr);
        cty = (TextView) findViewById(R.id.cty);
        ste = (TextView) findViewById(R.id.ste);
        contry = (TextView) findViewById(R.id.country);
        infoEdit = (ImageView) findViewById(R.id.info_edit);


        chkIntro();


        introEdit.setOnClickListener(this);
        infoEdit.setOnClickListener(this);


        new UsrInfo().execute();

    }

    private void chkIntro() {
        if (mApp.getPreference().getString(Common.INTRO, "").equalsIgnoreCase("")) {
            lay2.setVisibility(View.GONE);
        } else {
            intro.setText(mApp.getPreference().getString(Common.INTRO, ""));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.intro_edit:
                getIntro();
                break;
            case R.id.info_edit:
                Intent in = new Intent(getApplicationContext(), InfoEditActivity.class);
                in.putExtra("name", name);
                in.putExtra("dat", dat);
                in.putExtra("gen", gen);
                in.putExtra("addr", address);
                in.putExtra("city", city);
                in.putExtra("district", district);
                in.putExtra("state", state);
                in.putExtra("country", country);
                in.putExtra("pincode", pin);
                in.putExtra("blood", blood);
                in.putExtra("rel", rel);
                startActivity(in);
                break;
        }
    }

    private void getIntro() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.intro_dialog, null, false);
        about = (EditText) v.findViewById(R.id.intro);
        builder = new AlertDialog.Builder(JunUpdateInfo.this)
                .setView(v);
        builder.setTitle("Introduction")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (about.getText().toString().length() == 0) {
                            about.setError("Invalid Enter About You");
                        } else {
                            mApp.getPreference().edit().putString(Common.INTRO, about.getText().toString()).commit();
                            new UpdateStatus(getApplicationContext(),mApp.getPreference().getString(Common.u_id,""),about.getText().toString()).execute();
                            dialog.cancel();
                            chkIntro();
                        }
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }

    public class UsrInfo extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            iData = new HashMap<String, String>();
            iData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            try {

                JSONObject json = Connection.UrlConnection(php.info, iData);
                int succ = json.getInt("success");
                if (succ == 0) {
                } else {
                    JSONArray jsonArray = json.getJSONArray("info");
                    JSONObject child = jsonArray.getJSONObject(0);

                    name = child.optString("full_name");
                    dat = child.optString("dob");
                    gen = child.optString("gender");
                    address = child.optString("address");
                    city = child.optString("city");
                    district = child.optString("district");
                    state = child.optString("state");
                    country = child.optString("country");
                    pin = child.optString("pincode");
                    blood = child.optString("blood_grp");
                    rel = child.optString("married_status");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fullName.setText(name);
                        dob.setText(dat);
                        gender.setText(gen);
                        addr.setText(address);
                        cty.setText(city);
                        ste.setText(state);
                        contry.setText(country);
                        relation.setText(rel);
                        bld.setText(blood);
                    }
                });

            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            scroll.setVisibility(View.VISIBLE);
        }
    }
}
