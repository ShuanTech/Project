package com.shuan.Project.asyncTasks;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.text.Html;
import android.view.View;
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
import com.shuan.Project.R;
import com.shuan.Project.Utils.Helper;
import com.shuan.Project.activities.CommentsActivity;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;
import com.shuan.Project.profile.ProfileViewActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class PostView extends AsyncTask<String, String, String> {

    private Context mContext;
    private String j_id;
    private ImageView coverImg, cmpny_logo;
    private TextView jTitle, cmpny, created, viewd, applied, shared, skill, desc, type, cate, jId, sal, loc, exp, qua,vac;
    private RelativeLayout scroll;
    private ProgressBar progressBar;
    private HashMap<String, String> pData;
    private DisplayImageOptions options;
    private Helper help;
    private String pro_pic, cover_pic, cmpny_name, c_website, title, skll, tpe, category, pkg, level, location, description, apply, qulify;
    private String vied, shred, appled, date_created, u_id, cLoc,vacancy;
    private LinearLayout jType, jSal, jCate, j_Id, jLoc, jExp, jQua,jVac,lay4;
    private String cmpnyId;
    private Button but;


    public PostView(Context mContext, String u_id, String j_id, RelativeLayout scroll, ProgressBar progressBar, ImageView coverImg,
                    ImageView cmpny_logo, TextView jTitle, TextView cmpny, TextView created, TextView viewd, TextView applied,
                    TextView shared, TextView skill, TextView desc, TextView type, TextView cate, TextView jId, LinearLayout jType,
                    LinearLayout jSal, LinearLayout jCate, LinearLayout j_Id, TextView sal, Button but, LinearLayout jVac, LinearLayout jLoc, LinearLayout jExp,
                    LinearLayout jQua, TextView loc, TextView exp, TextView qua,TextView vac, LinearLayout lay4) {
        this.mContext = mContext;
        this.u_id = u_id;
        this.j_id = j_id;
        this.scroll = scroll;
        this.progressBar = progressBar;
        this.coverImg = coverImg;
        this.cmpny_logo = cmpny_logo;
        this.jTitle = jTitle;
        this.cmpny = cmpny;
        this.created = created;
        this.viewd = viewd;
        this.applied = applied;
        this.shared = shared;
        this.skill = skill;
        this.desc = desc;
        this.vac = vac;
        this.type = type;
        this.cate = cate;
        this.jId = jId;
        this.jType = jType;
        this.jSal = jSal;
        this.jCate = jCate;
        this.j_Id = j_Id;
        this.sal = sal;
        this.but = but;
        this.jLoc = jLoc;
        this.jExp = jExp;
        this.jQua = jQua;
        this.loc = loc;
        this.exp = exp;
        this.qua = qua;
        this.lay4 = lay4;
        help = new Helper();
    }

    @Override
    protected String doInBackground(String... params) {

        pData = new HashMap<String, String>();
        pData.put("jId", j_id);
        pData.put("u_id", u_id);

        try {

            JSONObject json = Connection.UrlConnection(php.post_view, pData);

            int succ = json.getInt("success");
            if (succ == 0) {
            } else {
                JSONArray jsonArray = json.getJSONArray("post");
                JSONObject child = jsonArray.getJSONObject(0);

                pro_pic = child.optString("pro_pic");
                cover_pic = child.optString("cover_pic");
                cmpny_name = child.optString("cmpny_name");
                c_website = child.optString("c_website");
                cLoc = child.optString("city");
                cmpnyId = child.optString("u_id");
                title = child.optString("title");
                skll = child.optString("skill");
                tpe = child.optString("type");
                category = child.optString("category");
                pkg = child.optString("package");
                level = child.optString("level");
                qulify = child.optString("qualification");
                vacancy = child.optString("vacancy");
                location = child.optString("location");
                description = child.optString("description");
                vied = child.optString("viewed");
                shred = child.optString("shared");
                appled = child.optString("applied");
                date_created = child.optString("date_created");
                apply = child.optString("apply");
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
        setData(pro_pic, cover_pic, cmpny_name, c_website, cLoc, title, skll, tpe, category, pkg, level, location, description,
                vied, shred, appled, date_created, qulify,vacancy);

    }

    private void setData(String pro_pic, String cover_pic, String cmpny_name, String c_website, String cLoc, String title, String skll, String tpe, String category, String pkg, String level, String location, String description, String vied, String shred, String appled, String date_created, String qualify, String vacancy) {

        setImage(pro_pic, cmpny_logo);
        setCover(cover_pic, coverImg);
        jTitle.setText(title);
        cmpny.setText(cmpny_name + " \n " + cLoc);
        created.setText("Posted " + help.getTimeAgo(mContext, date_created));
        viewd.setText(vied + " Viewed");
        shared.setText(shred );
        applied.setText(appled + " Applied");
        skill.setText( skll);
        desc.setText(Html.fromHtml(description));
        loc.setText(location);
        vac.setText(vacancy);

        if (tpe != null && !tpe.trim().isEmpty()) {
            jType.setVisibility(View.VISIBLE);
            type.setText(tpe);
        }
        if (pkg != null && !pkg.trim().isEmpty()) {
            jSal.setVisibility(View.VISIBLE);
            sal.setText(mContext.getResources().getString(R.string.rs)+" "+pkg+" /Year");
        }
        if (category != null && !category.trim().isEmpty()) {
            jCate.setVisibility(View.VISIBLE);
            cate.setText(category);
        }
        if (j_id != null && !j_id.trim().isEmpty()) {
            j_Id.setVisibility(View.VISIBLE);
            jId.setText(j_id);
        }

      /*  if (location != null && !location.trim().isEmpty()) {
            jLoc.setVisibility(View.VISIBLE);
            loc.setText(location);
        }*/

        if (level != null && !level.trim().isEmpty()) {
            jExp.setVisibility(View.VISIBLE);
            exp.setText(level);
        }

        if (qualify != null && !qualify.trim().isEmpty()) {
            jQua.setVisibility(View.VISIBLE);
            qua.setText(qualify);
        }
        cmpny_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mContext, ProfileViewActivity.class);
                in.putExtra("u_id", cmpnyId);
                in.putExtra("level", "3");
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(in);
            }
        });
        cmpny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mContext, ProfileViewActivity.class);
                in.putExtra("u_id", cmpnyId);
                in.putExtra("level","3");
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(in);
            }
        });
        lay4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mContext, CommentsActivity.class);
                in.putExtra("j_id",j_id);
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(in);
            }
        });

        if (apply.equalsIgnoreCase("1")) {
            but.setText("Applied");
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
