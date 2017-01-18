package com.shuan.Project.employee;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuan.Project.R;
import com.shuan.Project.Utils.Common;
import com.shuan.Project.asyncTasks.EventView;
import com.shuan.Project.asyncTasks.eventAttend;
import com.shuan.Project.asyncTasks.eventCancel;

public class EventViewActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Common mApp;
    private ImageView coverImg, cmpny_logo;
    private RelativeLayout scroll;
    private ProgressBar progressBar;
    private Button attend,cancel;
    private LinearLayout cmpany,lay2,lay3,lay4,lay5;
    private TextView evntname,evntdesc,evntloc,evntdate,evntime,created,cmpny;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mApp = (Common) getApplicationContext();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        scroll = (RelativeLayout) findViewById(R.id.scroll);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        cmpany = (LinearLayout) findViewById(R.id.cmpany);
        lay2 = (LinearLayout) findViewById(R.id.lay2);
        lay3 = (LinearLayout) findViewById(R.id.lay3);
        lay4 = (LinearLayout) findViewById(R.id.lay4);
        lay5 = (LinearLayout) findViewById(R.id.lay5);

        attend = (Button) findViewById(R.id.apply);
        coverImg = (ImageView) findViewById(R.id.cover_img);
        cmpny_logo = (ImageView) findViewById(R.id.cmpny_logo);

        cmpny = (TextView) findViewById(R.id.cmpny);
        evntname = (TextView) findViewById(R.id.evntname);
        evntdesc = (TextView) findViewById(R.id.evntdesc);
        evntloc = (TextView) findViewById(R.id.evntloc);
        evntdate = (TextView) findViewById(R.id.evntdate);
        evntime = (TextView) findViewById(R.id.evntime);
        created = (TextView) findViewById(R.id.created);

        if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("3")){
            attend.setVisibility(View.GONE);
        }

        new EventView(EventViewActivity.this,mApp.getPreference().getString(Common.u_id, ""), getIntent().getStringExtra("evnt_id"),scroll,progressBar,coverImg, cmpny_logo,cmpny,evntname,
                evntdesc,evntloc,evntdate,evntime).execute();

        attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!attend.getText().toString().equalsIgnoreCase("Attending")){
                    new eventAttend(EventViewActivity.this,mApp.getPreference().getString(Common.u_id, ""),getIntent().getStringExtra("evnt_id")).execute();
                    mApp.getPreference().edit().putBoolean(Common.ATTEND, true).commit();
                    attend.setText("Attending");

                }else {
                    new eventCancel(EventViewActivity.this,mApp.getPreference().getString(Common.u_id, ""),getIntent().getStringExtra("evnt_id")).execute();
                    mApp.getPreference().edit().putBoolean(Common.ATTEND, false).commit();
                    attend.setText("Attend");
                }
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
}
