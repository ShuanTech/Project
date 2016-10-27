package com.shuan.project.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.Utils.Helper;
import com.shuan.project.fragment.PostCommnts;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class CommentsActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private Common mApp;
    private ProgressBar progressBar;
    private ScrollView scroll;
    private LinearLayout cmnts;
    private EditText cmtEdt;
    private ImageButton cmdSnd;
    private DisplayImageOptions options;
    private HashMap<String, String> cData;
    private Helper help = new Helper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("1")) {
            setTheme(R.style.Junior);
        } else if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("2")) {
            setTheme(R.style.Senior);
        } else {
            setTheme(R.style.AppBaseTheme);
        }
        mApp = (Common) getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

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
        toolbar.setTitle("Comments");

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        scroll = (ScrollView) findViewById(R.id.scroll);
        cmnts = (LinearLayout) findViewById(R.id.comment);
        cmtEdt = (EditText) findViewById(R.id.entr_area);
        cmdSnd = (ImageButton) findViewById(R.id.cmt_snd);


        new GetComments().execute();

        cmdSnd.setOnClickListener(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        scroll.post(new Runnable() {
            @Override
            public void run() {
                scroll.fullScroll(View.FOCUS_DOWN);

            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.cmt_snd) {
            if (cmtEdt.getText().toString().length() == 0) {
            } else {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View vi = inflater.inflate(R.layout.cmt_item, null);
                TextView usrName = (TextView) vi.findViewById(R.id.usr_name);
                ImageView usrImg = (ImageView) vi.findViewById(R.id.usr_img);
                TextView usrCmts = (TextView) vi.findViewById(R.id.usr_cmts);
                TextView cmtdata = (TextView) vi.findViewById(R.id.cmt_created);

                setImage(mApp.getPreference().getString(Common.PROPIC, ""), usrImg);
                usrName.setText(mApp.getPreference().getString(Common.FULLNAME, ""));
                usrCmts.setText(cmtEdt.getText().toString());
                cmtdata.setText("now");
                cmnts.addView(vi);



                cmdSnd.setEnabled(false);
                new PostCommnts(CommentsActivity.this, mApp.getPreference().getString(Common.u_id, ""),
                        getIntent().getStringExtra("jId"), cmtEdt.getText().toString(),cmdSnd).execute();
                scroll.post(new Runnable() {
                    @Override
                    public void run() {
                        scroll.fullScroll(View.FOCUS_DOWN);

                    }
                });
                cmtEdt.setText("");



            }
        }

    }


    public class GetComments extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            cData = new HashMap<String, String>();
            cData.put("j_id", getIntent().getStringExtra("jId"));
            try {
                JSONObject json = Connection.UrlConnection(php.comments, cData);
                int succ = json.getInt("success");
                if (succ == 0) {
                } else {
                    JSONArray jsonArray = json.getJSONArray("comments");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject child = jsonArray.getJSONObject(i);
                        final String full_name = child.optString("full_name");
                        final String pro_pic = child.optString("pro_pic");
                        final String commnts = child.optString("commnts");
                        final String cmt_date = child.optString("cmt_date");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View v = inflater.inflate(R.layout.cmt_item, null);
                                TextView usrName = (TextView) v.findViewById(R.id.usr_name);
                                ImageView usrImg = (ImageView) v.findViewById(R.id.usr_img);
                                TextView usrCmts = (TextView) v.findViewById(R.id.usr_cmts);
                                TextView cmtdata = (TextView) v.findViewById(R.id.cmt_created);

                                setImage(pro_pic, usrImg);
                                usrName.setText(full_name);
                                usrCmts.setText(commnts);
                                cmtdata.setText(help.getTimeAgo(CommentsActivity.this, cmt_date));

                                cmnts.addView(v);

                            }
                        });
                    }
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
            scroll.post(new Runnable() {
                @Override
                public void run() {
                    scroll.fullScroll(View.FOCUS_DOWN);

                }
            });
        }
    }

    private void setImage(String path, ImageView img) {
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showImageOnLoading(R.drawable.user)
                .showImageForEmptyUri(R.drawable.user)
                .showImageOnFail(R.drawable.user)
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
