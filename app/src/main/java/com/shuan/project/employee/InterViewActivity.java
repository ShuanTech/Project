package com.shuan.project.employee;

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
import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class InterViewActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView title, loc, dat, tme, tpe, note, cmpnyName;
    private Common mApp;
    private HashMap<String, String> iData;
    private LinearLayout scroll;
    private ProgressBar progressBar;
    private DisplayImageOptions options;
    private ImageView cmpnyImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApp = (Common) getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inter_view);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        scroll = (LinearLayout) findViewById(R.id.scroll);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        cmpnyName = (TextView) findViewById(R.id.cmpny_name);
        cmpnyImg = (ImageView) findViewById(R.id.cmpny_img);
        title = (TextView) findViewById(R.id.inter_title);
        loc = (TextView) findViewById(R.id.inter_loc);
        dat = (TextView) findViewById(R.id.inter_dat);
        tme = (TextView) findViewById(R.id.inter_time);
        tpe = (TextView) findViewById(R.id.inter_type);
        note = (TextView) findViewById(R.id.note);

        new GetInterViewDetail().execute();
    }


    public class GetInterViewDetail extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            iData = new HashMap<String, String>();
            iData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            iData.put("j_id", getIntent().getStringExtra("post"));

            try {

                JSONObject json = Connection.UrlConnection(php.interView, iData);
                int succ = json.getInt("success");
                if (succ == 0) {
                } else {
                    JSONArray jsonArray = json.getJSONArray("interview");

                    JSONObject child = jsonArray.getJSONObject(0);
                    final String cName = child.optString("cmpny_name");
                    final String cImg = child.optString("pro_pic");
                    final String venue = child.optString("venue");
                    final String intervew_date = child.optString("intervew_date");
                    final String intervew_time = child.optString("intervew_time");
                    final String type = child.optString("type");
                    final String commnts = child.optString("commnts");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            cmpnyName.setText(cName);
                            title.setText(getIntent().getStringExtra("title"));

                            loc.setText(venue);
                            dat.setText(intervew_date);
                            tme.setText(intervew_time);
                            if (type.equalsIgnoreCase("1")) {
                                tpe.setText("Face to Face");
                            }

                            note.setText(commnts);
                            setImage(cImg,cmpnyImg);
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
}
