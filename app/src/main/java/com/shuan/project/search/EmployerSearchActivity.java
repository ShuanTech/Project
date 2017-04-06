package com.shuan.Project.search;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.shuan.Project.R;
import com.shuan.Project.asyncTasks.EmployerSerchResult;
import com.shuan.Project.asyncTasks.GetEmployerSerach;
import com.shuan.Project.profile.ProfileViewActivity;

import java.util.List;
import java.util.Locale;

public class EmployerSearchActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private Toolbar toolbar;
    private Boolean flag = false;
    //private LocationClient mLocationClient;
    // google client for connecting to Google Api
    private GoogleApiClient googleApiClient;
    private ProgressDialog pDialog;
    private GoogleApiClient mGoogleApiClient;
    //for location request
    private LocationRequest locationRequest;
    private int locationupdate = 8000; //in milliseconds
    private int locationdistance = 10; // in meters
    //private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private ProgressBar progressBar;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private ListView list;
    private AutoCompleteTextView preferSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_search);

        //mLocationClient = new LocationClient(this, this, this);
        // mLocationClient.connect();
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //mLocationRequest = new LocationRequest();

        if (checkGooglePlayServices()) {
            // set up google client
            setUpGoogleClient();
            //set Location Request
            createLocationRequest();
        }

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        list = (ListView) findViewById(R.id.ser_res);
        preferSearch = (AutoCompleteTextView) findViewById(R.id.prefered_serach);

        new GetEmployerSerach(EmployerSearchActivity.this, progressBar, preferSearch).execute();

        preferSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txt = (TextView) view.findViewById(R.id.display);
                TextView txt1 = (TextView) view.findViewById(R.id.ins_name);

                preferSearch.setText(txt.getText().toString());
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                progressBar.setVisibility(View.VISIBLE);

                new EmployerSerchResult(EmployerSearchActivity.this, progressBar, list, txt.getText().toString(),
                        txt1.getText().toString()).execute();
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txt = (TextView) view.findViewById(R.id.u_id);
                TextView txt2 = (TextView) view.findViewById(R.id.level);
                //Toast.makeText(getActivity(),txt.getText().toString(),Toast.LENGTH_SHORT).show();
                Intent in = new Intent(getApplicationContext(), ProfileViewActivity.class);
                in.putExtra("u_id", txt.getText().toString());
                in.putExtra("level", txt2.getText().toString());
                startActivity(in);
            }
        });

    }

    public boolean checkGooglePlayServices() {
        GoogleApiAvailability gApi = GoogleApiAvailability.getInstance();
        int resultCode = gApi.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (gApi.isUserResolvableError(resultCode)) {
                gApi.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(this, "Google Play Services not Avaliable", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    public void setUpGoogleClient() {
        // Create an instance of GoogleAPIClient.
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    protected void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(locationupdate); //in milliseconds
        locationRequest.setFastestInterval(5000); //in milliseconds
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setSmallestDisplacement(locationdistance);
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
        new displayCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    protected void onStop() {
        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }
        super.onStop();
    }


    public class displayCurrentLocation extends AsyncTask<String, String, String> {

        String location;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EmployerSearchActivity.this);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.setMessage("Searching Details");
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            // Location currentLocation = mLocationClient.getLastLocation();
            if (ActivityCompat.checkSelfPermission(EmployerSearchActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EmployerSearchActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (currentLocation != null) {
                Geocoder geocoder = new Geocoder(EmployerSearchActivity.this, Locale.getDefault());
                Location loc = currentLocation;
                List<android.location.Address> addresses;
                try {
                    addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);

                    if (addresses != null && addresses.size() > 0) {
                        final android.location.Address address = addresses.get(0);
                        location = address.getLocality();
                        //Toast.makeText(getApplicationContext(),location,Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                }
            }
            return location;
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            pDialog.cancel();
            //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            Intent in = new Intent(getApplicationContext(), EmployerSearchResultActivity.class);
            in.putExtra("loc", s);
            startActivity(in);


        }
    }

    private void showGpsAlert() {
        AlertDialog.Builder build = new AlertDialog.Builder(EmployerSearchActivity.this);
        build.setTitle("Alert")
                .setMessage("Turn On your GPS! Find the Employee")
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
                Toast.makeText(getApplicationContext(), "Can't Find Employee", Toast.LENGTH_SHORT).show();
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
