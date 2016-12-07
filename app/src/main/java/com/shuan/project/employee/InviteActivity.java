package com.shuan.Project.employee;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.shuan.Project.R;
import com.shuan.Project.Utils.Common;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class InviteActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView title, note, cmpnyName, cntPer, cntMail, cntPh, cntTme;
    private Common mApp;
    private HashMap<String, String> iData;
    private LinearLayout scroll;
    private ProgressBar progressBar;
    private DisplayImageOptions options;
    private ImageView cmpnyImg;

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
        setContentView(R.layout.activity_invite);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("1")) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.junPrimary));
        } else if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("2")) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.senPrimary));
        } else {
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        scroll = (LinearLayout) findViewById(R.id.scroll);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        cmpnyName = (TextView) findViewById(R.id.cmpny_name);
        cmpnyImg = (ImageView) findViewById(R.id.cmpny_img);
        title = (TextView) findViewById(R.id.title);
        cntPer = (TextView) findViewById(R.id.cnt_per);
        cntMail = (TextView) findViewById(R.id.cnt_mail);
        cntPh = (TextView) findViewById(R.id.cnt_no);
        cntTme = (TextView) findViewById(R.id.cnt_tme);
        note = (TextView) findViewById(R.id.note);

        new GetInvite().execute();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public class GetInvite extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            iData = new HashMap<String, String>();
            iData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            iData.put("frm_id", getIntent().getStringExtra("frm"));

            try {

                JSONObject json = Connection.UrlConnection(php.inviteDetail, iData);
                int succ = json.getInt("success");
                if (succ == 0) {
                } else {
                    JSONArray jsonArray = json.getJSONArray("invite");

                    JSONObject child = jsonArray.getJSONObject(0);
                    final String cName = child.optString("cmpny_name");
                    final String cImg = child.optString("pro_pic");
                    final String cPer = child.optString("contact_person");
                    final String cMail = child.optString("contact_mail");
                    final String cPh = child.optString("contact_ph");
                    final String cTme = child.optString("contact_time");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            cmpnyName.setText(cName);
                            title.setText("Hello " + mApp.getPreference().getString(Common.FULLNAME, "") + ",");

                            cntPer.setText(cPer);
                            cntMail.setText(cMail);
                            cntPh.setText(cPh);
                            cntTme.setText(cTme);
                            note.setText(" I found your profile on Udyomita. If You are interested, contact for a discussion .");
                            setImage(cImg, cmpnyImg);
                        }
                    });

                }
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

    private void setImage(String path, ImageView img) {
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showImageOnLoading(R.drawable.logo)
                .showImageForEmptyUri(R.drawable.logo)
                .showImageOnFail(R.drawable.logo)
                .build();

        ImageLoader.getInstance().displayImage(path, img, options, new SimpleImageLoadingListener() {

            @Override
            public void onLoadingStarted(String imageUri, View view) {
                super.onLoadingStarted(imageUri, view);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                super.onLoadingFailed(imageUri, view, failReason);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                super.onLoadingCancelled(imageUri, view);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String s, View view, int i, int i1) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
