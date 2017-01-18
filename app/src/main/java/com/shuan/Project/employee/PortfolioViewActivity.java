package com.shuan.Project.employee;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shuan.Project.R;
import com.shuan.Project.Utils.Common;
import com.shuan.Project.asyncTasks.PortView;

public class PortfolioViewActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private ImageView img;
    private TextView discr, heading;
    private Toolbar toolbar;
    private Common mApp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApp = (Common) getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_view);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        img = (ImageView) findViewById(R.id.img);
        heading = (TextView) findViewById(R.id.heading);
        discr = (TextView) findViewById(R.id.description);



      new PortView(PortfolioViewActivity.this, mApp.getPreference().getString(Common.u_id, ""), getIntent().getStringExtra("p_title"), heading, img, progressBar, discr).execute();



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
