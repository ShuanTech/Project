package com.shuan.Project.search;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.shuan.Project.R;
import com.shuan.Project.Utils.Common;
import com.shuan.Project.asyncTasks.EmployeeSerchResult;
import com.shuan.Project.asyncTasks.GetEmployeeSerach;
import com.shuan.Project.employer.PostViewActivity;

import java.util.List;
import java.util.Locale;

public class EmplyeeSearchActivity extends AppCompatActivity implements GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {

    private Toolbar toolbar;
    private Common mApp;
    private Boolean flag = false;
    private LocationClient mLocationClient;
    private ProgressDialog pDialog;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private ProgressBar progressBar;
    private ListView list;
    private AutoCompleteTextView preferSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApp = (Common) getApplicationContext();
        if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("1")) {
            setTheme(R.style.Junior);
        } else {
            setTheme(R.style.Senior);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emplyee_search);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("1")) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.junPrimary));
        } else {
            toolbar.setBackgroundColor(getResources().getColor(R.color.senPrimary));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mLocationClient = new LocationClient(this, this, this);
        mLocationClient.connect();

        mLocationRequest = new LocationRequest();

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        list = (ListView) findViewById(R.id.ser_res);
        preferSearch = (AutoCompleteTextView) findViewById(R.id.prefered_serach);

        new GetEmployeeSerach(EmplyeeSearchActivity.this, progressBar, preferSearch).execute();

        preferSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    preferSearch.dismissDropDown();
                    progressBar.setVisibility(View.VISIBLE);
                    new EmployeeSerchResult(EmplyeeSearchActivity.this, progressBar, list, preferSearch.getText().toString(),
                            "all").execute();
                }
                return false;
            }
        });

        preferSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
                TextView txt = (TextView) view.findViewById(R.id.display);
                TextView txt1 = (TextView) view.findViewById(R.id.ins_name);
                preferSearch.setText(txt.getText().toString());
                progressBar.setVisibility(View.VISIBLE);
                new EmployeeSerchResult(EmplyeeSearchActivity.this, progressBar, list, txt.getText().toString(),
                        txt1.getText().toString()).execute();
            }
        });

        preferSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                list.setAdapter(null);
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txt = (TextView) view.findViewById(R.id.jId);
                Intent in = new Intent(getApplicationContext(), PostViewActivity.class);
                in.putExtra("jId", txt.getText().toString());
                in.putExtra("apply", "no");
                startActivity(in);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.emp_search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.gps) {
            flag = gpsStatus();
            if (flag) {
                new displayCurrentLocation().execute();
            } else {
                showGpsAlert();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    public class displayCurrentLocation extends AsyncTask<String, String, String> {

        String location;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EmplyeeSearchActivity.this);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.setMessage("Searching");
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            Location currentLocation = mLocationClient.getLastLocation();

            Geocoder geocoder = new Geocoder(EmplyeeSearchActivity.this, Locale.getDefault());
            Location loc = currentLocation;
            List<android.location.Address> addresses;
            try {
                addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);

                if (addresses != null && addresses.size() > 0) {
                    final android.location.Address address = addresses.get(0);
                    location = address.getLocality();
                }

            } catch (Exception e) {
            }
            return location;
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            pDialog.cancel();
            Intent in = new Intent(getApplicationContext(), EmployeeSearchResultActivity.class);
            in.putExtra("loc", s);
            startActivity(in);


        }
    }

    private void showGpsAlert() {
        AlertDialog.Builder build = new AlertDialog.Builder(EmplyeeSearchActivity.this);
        build.setTitle("Alert")
                .setMessage("Turn On your GPS! Find the Jobs & companies")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent in = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(in);
                        dialog.cancel();
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(getApplicationContext(), "Can't Find Employer", Toast.LENGTH_SHORT).show();
            }
        }).show();
    }

    private Boolean gpsStatus() {
        ContentResolver contentResolver = getBaseContext().getContentResolver();
        boolean gpsStus = Settings.Secure.isLocationProviderEnabled(contentResolver, LocationManager.GPS_PROVIDER);
        if (gpsStus) {
            return true;
        } else {
            return false;
        }

    }

}
