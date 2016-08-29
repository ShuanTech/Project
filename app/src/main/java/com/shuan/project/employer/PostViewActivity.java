package com.shuan.project.employer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.asyncTasks.PostView;
import com.shuan.project.resume.ExpResumeGenerate;
import com.shuan.project.resume.JuniorResumeGenerate;
import com.shuan.project.resume.ResumeEditActivity;

public class PostViewActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Common mApp;
    private ImageView coverImg, cmpny_logo;
    private TextView jTitle, cmpny, created, viewd, applied, shared, skill, desc, type, cate, jId,sal;
    private Button apply;
    private RelativeLayout scroll;
    private ProgressBar progressBar;
    private LinearLayout jType,jSal,jCate,j_Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApp = (Common) getApplicationContext();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        scroll= (RelativeLayout) findViewById(R.id.scroll);
        progressBar= (ProgressBar) findViewById(R.id.progress_bar);
        jType= (LinearLayout) findViewById(R.id.j_type);
        jSal= (LinearLayout) findViewById(R.id.j_sal);
        jCate= (LinearLayout) findViewById(R.id.j_cate);
        j_Id= (LinearLayout) findViewById(R.id.j_id);
        coverImg = (ImageView) findViewById(R.id.cover_img);
        cmpny_logo = (ImageView) findViewById(R.id.cmpny_logo);
        jTitle = (TextView) findViewById(R.id.jTitle);
        cmpny = (TextView) findViewById(R.id.cmpny);
        created = (TextView) findViewById(R.id.created);
        viewd = (TextView) findViewById(R.id.viewd);
        applied = (TextView) findViewById(R.id.applied);
        shared = (TextView) findViewById(R.id.shared);
        skill = (TextView) findViewById(R.id.skill);
        desc = (TextView) findViewById(R.id.desc);
        type = (TextView) findViewById(R.id.type);
        cate = (TextView) findViewById(R.id.cate);
        jId = (TextView) findViewById(R.id.jId);
        apply= (Button) findViewById(R.id.apply);
        sal= (TextView) findViewById(R.id.sal);

        if(mApp.getPreference().getString(Common.LEVEL,"").equalsIgnoreCase("3")){
            apply.setVisibility(View.GONE);
        }

        new PostView(PostViewActivity.this,mApp.getPreference().getString(Common.u_id,""),getIntent().getStringExtra("jId"),scroll,progressBar,coverImg,cmpny_logo,jTitle,cmpny,
                created,viewd,applied,shared,skill,desc,type,cate,jId,jType,jSal,jCate,j_Id,sal).execute();


        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mApp.getPreference().edit().putBoolean(Common.APPLY, true).commit();

                    CallResumeData(getIntent().getStringExtra("jId"),getIntent().getStringExtra("frmId"));

                } else {

                    AlertDialog.Builder build = new AlertDialog.Builder(PostViewActivity.this);
                    build.setTitle("CONFIRMATION");
                    build.setMessage("Are You Sure Apply the Post or Edit the resume Content")
                            .setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    Intent in=new Intent(getApplicationContext(), ResumeEditActivity.class);
                                    startActivity(in);
                                }
                            }).setNegativeButton("APPLY", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.cancel();
                            CallResumeData(getIntent().getStringExtra("jId"),getIntent().getStringExtra("frmId"));
                        }
                    }).show();
                }

            }
        });
    }

    private void CallResumeData(String jId,String frmId) {

        if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("1")) {
            Intent in = new Intent(getApplicationContext(), JuniorResumeGenerate.class);
            in.putExtra("job_id", jId);
            in.putExtra("frm_id", frmId);
            startActivity(in);
        } else {
            Intent in = new Intent(getApplicationContext(), ExpResumeGenerate.class);
            in.putExtra("job_id", jId);
            in.putExtra("frm_id", frmId);
            startActivity(in);
        }
    }
}
