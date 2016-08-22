package com.shuan.project.profile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.shuan.project.R;
import com.shuan.project.Utils.CircleImageView;
import com.shuan.project.Utils.Common;
import com.shuan.project.asyncTasks.Following;
import com.shuan.project.list.Sample;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileViewActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Common mApp;
    private String u_id, level;
    private LinearLayout exp;
    private ImageView cover;
    private CircleImageView proPic;
    private TextView name, position, org, intro;
    private LinearLayout cmpny, expeince;
    private Button bu1, bu2;
    private TextView abt, url, cate, sze, found;
    private ProgressBar progressBar;
    private RelativeLayout scroll;
    private HashMap<String, String> pData;
    private DisplayImageOptions options;
    private ArrayList<Sample> list;
    private String pro_pic, cover_pic, cmpny_name, c_type, landmark, country, year_of_establish, num_wrkers, c_desc, c_website, follow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        mApp = (Common) getApplicationContext();
        u_id = getIntent().getStringExtra("u_id");
        level = getIntent().getStringExtra("level");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        toolbar.setBackgroundColor(Color.TRANSPARENT);


        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        scroll = (RelativeLayout) findViewById(R.id.scroll);
        cover = (ImageView) findViewById(R.id.cover_img);
        proPic = (CircleImageView) findViewById(R.id.pro_img);
        name = (TextView) findViewById(R.id.name);
        position = (TextView) findViewById(R.id.position);
        org = (TextView) findViewById(R.id.company_name);
        bu1 = (Button) findViewById(R.id.but1);
        bu2 = (Button) findViewById(R.id.but2);

        cmpny = (LinearLayout) findViewById(R.id.cmpny);

        expeince = (LinearLayout) findViewById(R.id.expeince);
        list = new ArrayList<Sample>();

        if (level.equalsIgnoreCase("3")) {
            cmpny.setVisibility(View.VISIBLE);
            abt = (TextView) findViewById(R.id.abt);
            url = (TextView) findViewById(R.id.url);
            cate = (TextView) findViewById(R.id.cate);
            sze = (TextView) findViewById(R.id.sze);
            found = (TextView) findViewById(R.id.found);
        } else if (level.equalsIgnoreCase("2")) {
            expeince.setVisibility(View.VISIBLE);
            exp = (LinearLayout) findViewById(R.id.exp);
        }


        new Profile().execute();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        bu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bu1.getText().toString().equalsIgnoreCase("Following")) {
                    new Following(ProfileViewActivity.this, u_id, mApp.getPreference().getString(Common.u_id, ""), bu1, bu2).execute();
                    bu1.setText("PENDING");
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public class Profile extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            pData = new HashMap<String, String>();
            pData.put("u_id", u_id);
            pData.put("level", level);
            pData.put("frm_id", mApp.getPreference().getString(Common.u_id, ""));

            try {

                JSONObject json = Connection.UrlConnection(php.profileView, pData);

                int succ = json.getInt("success");

                if (succ == 0) {

                } else {

                    if (level.equalsIgnoreCase("3")) {
                        JSONArray jsonArray = json.getJSONArray("pro_view");
                        JSONObject child = jsonArray.getJSONObject(0);
                        pro_pic = child.optString("pro_pic");
                        cover_pic = child.optString("cover_pic");
                        cmpny_name = child.optString("cmpny_name");
                        c_type = child.optString("c_type");
                        landmark = child.optString("landmark");
                        country = child.optString("country");
                        year_of_establish = child.optString("year_of_establish");
                        num_wrkers = child.optString("num_wrkers");
                        c_desc = child.optString("c_desc");
                        c_website = child.optString("c_website");
                        follow = child.optString("follow");
                    } else if (level.equalsIgnoreCase("2")) {
                        JSONArray jsonArray = json.getJSONArray("pro_view");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject child = jsonArray.getJSONObject(i);
                            cmpny_name = child.optString("full_name");
                            pro_pic = child.optString("pro_pic");
                            cover_pic = child.optString("cover_pic");
                            String position = child.optString("position");
                            String org_name = child.optString("org_name");
                            String location = child.optString("location");
                            String from_date = child.optString("from_date");
                            String to_date = child.optString("to_date");
                            follow = child.optString("follow");

                            list.add(new Sample(position, org_name, location, from_date, to_date));
                        }


                    }

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (level.equalsIgnoreCase("3")) {
                            setCompany(pro_pic, cover_pic, cmpny_name, c_type, landmark, country, year_of_establish, num_wrkers, c_desc, c_website, follow);
                        } else if (level.equalsIgnoreCase("2")) {
                            setExp(pro_pic, cover_pic, cmpny_name,follow,list);
                        }

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

    private void setExp(String pro_pic, String cover_pic, String cmpny_name, String follow,ArrayList<Sample> list) {

        setImage(pro_pic, proPic);
        setCover(cover_pic, cover);
        name.setText(cmpny_name);

        for(int i=0;i<list.size();i++){
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.wrk_lay, null);
            TextView txt = (TextView) v.findViewById(R.id.wrk);
            Sample curr=list.get(i);
            if(i==0){
                position.setText(curr.getDis());
                org.setText(curr.getCty() + ", " + curr.getDistrct());
                txt.setText(curr.getDis() + " at " + curr.getCty() + ", " + curr.getDistrct());
            }else{
                txt.setText("Former " + curr.getDis() + " at " + curr.getCty() + ", " + curr.getDistrct());
            }
            exp.addView(v);
        }

        if(getIntent().getStringExtra("who").equalsIgnoreCase("employer")){
            bu1.setVisibility(View.GONE);
            bu2.setVisibility(View.GONE);
        }else{
            if (follow.equalsIgnoreCase("1")) {
                bu1.setVisibility(View.VISIBLE);
                bu1.setText("Connect");
            } else {
                bu2.setVisibility(View.VISIBLE);
                bu2.setText("Connected");
            }
        }



    }

    private void setCompany(String pro_pic, String cover_pic, String cmpny_name, String c_type, String landmark, String country,
                            String year_of_establish, String num_wrkers, String c_desc, String c_website, String follow) {

        setImage(pro_pic, proPic);
        setCover(cover_pic, cover);
        name.setText(cmpny_name);
        position.setText(c_type);
        org.setText(landmark + "," + country);
        abt.setText(c_desc);
        url.setText(c_website);
        cate.setText(c_type);
        sze.setText(num_wrkers);
        found.setText(year_of_establish);
        if (follow.equalsIgnoreCase("1")) {
            bu1.setVisibility(View.VISIBLE);
            bu1.setText("Following");
        } else {
            bu2.setVisibility(View.VISIBLE);
            bu2.setText("Followed");
        }
    }


    private void setImage(String path, CircleImageView img) {

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


    private void setCover(String path, ImageView img) {
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showImageOnLoading(R.drawable.header)
                .showImageForEmptyUri(R.drawable.header)
                .showImageOnFail(R.drawable.header)
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
