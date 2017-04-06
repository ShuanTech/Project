package com.shuan.Project.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.shuan.Project.R;
import com.shuan.Project.Utils.Common;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private LinearLayout chngePass,invitefrnd,privacy;
    private Common mApp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApp = (Common) getApplicationContext();

        if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("1")) {
            setTheme(R.style.Junior);
        } else if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("2")) {
            setTheme(R.style.Senior);
        } else {
            setTheme(R.style.AppBaseTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("1")) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.junPrimary));
        } else if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("2")) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.senPrimary));
        } else {
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Settings");

        chngePass = (LinearLayout) findViewById(R.id.chng_pass);
        invitefrnd = (LinearLayout) findViewById(R.id.invite_frnd);
        privacy = (LinearLayout) findViewById(R.id.privacy);

        chngePass.setOnClickListener(this);
        invitefrnd.setOnClickListener(this);
        privacy.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chng_pass:
                startActivity(new Intent(getApplicationContext(),ChangePasswrd.class));
                break;
            /*case R.id.privacy:
                startActivity(new Intent(getApplicationContext(),Privacy.class));
                break;*/
            case  R.id.invite_frnd:
                invite();
                break;
        }
    }
    private void invite() {
//sharing implementation here

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);

        sharingIntent.setType("text/plain");
        String shareBody = "Udyomitra \n The ultimate professional network. \n Create your account today. \n Download the app from the link. " + "\n https://goo.gl/uXxF7H";

        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Udyomitra invitation");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);


        startActivity(Intent.createChooser(sharingIntent, "Invite via"));
    }

}
