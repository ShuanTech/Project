package com.shuan.project.asyncTasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
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
import com.shuan.project.Utils.Helper;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class PostView extends AsyncTask<String, String, String> {

    private Context mContext;
    private String j_id;
    private ImageView coverImg, cmpny_logo;
    private TextView jTitle, cmpny, created, viewd, applied, shared, skill, desc, type, cate, jId, sal;
    private RelativeLayout scroll;
    private ProgressBar progressBar;
    private HashMap<String, String> pData;
    private DisplayImageOptions options;
    private Helper help;
    private String pro_pic, cover_pic, cmpny_name, c_website, title, skll, tpe, category, pkg, level, location, description;
    private String vied, shred, appled, date_created, u_id;
    private LinearLayout jType, jSal, jCate, j_Id;


    public PostView(Context mContext, String u_id, String j_id, RelativeLayout scroll, ProgressBar progressBar, ImageView coverImg,
                    ImageView cmpny_logo, TextView jTitle, TextView cmpny, TextView created, TextView viewd, TextView applied,
                    TextView shared, TextView skill, TextView desc, TextView type, TextView cate, TextView jId, LinearLayout jType,
                    LinearLayout jSal, LinearLayout jCate, LinearLayout j_Id, TextView sal) {
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
        this.type = type;
        this.cate = cate;
        this.jId = jId;
        this.jType = jType;
        this.jSal = jSal;
        this.jCate = jCate;
        this.j_Id = j_Id;
        this.sal = sal;
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
                title = child.optString("title");
                skll = child.optString("skill");
                tpe = child.optString("type");
                category = child.optString("category");
                pkg = child.optString("package");
                level = child.optString("level");
                location = child.optString("location");
                description = child.optString("description");
                vied = child.optString("viewed");
                shred = child.optString("shared");
                appled = child.optString("applied");
                date_created = child.optString("date_created");
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
        setData(pro_pic, cover_pic, cmpny_name, c_website, title, skll, tpe, category, pkg, level, location, description,
                vied, shred, appled, date_created);

    }

    private void setData(String pro_pic, String cover_pic, String cmpny_name, String c_website, String title, String skll, String tpe, String category, String pkg, String level, String location, String description, String vied, String shred, String appled, String date_created) {

        setImage(pro_pic, cmpny_logo);
        setCover(cover_pic, coverImg);
        jTitle.setText(title);
        cmpny.setText(cmpny_name + ", " + location);
        created.setText("Posted on " + help.getTimeAgo(mContext, date_created));
        viewd.setText(vied + " Viewed");
        shared.setText(shred + " Shared");
        applied.setText(appled + " Applied");
        skill.setText("Skill: " + skll);
        desc.setText(description);

        if (tpe != null && !tpe.trim().isEmpty()) {
            jType.setVisibility(View.VISIBLE);
            type.setText(tpe);
        }
        if (pkg != null && pkg.trim().isEmpty()) {
            jSal.setVisibility(View.VISIBLE);
            sal.setText(pkg);
        }
        if (category != null && !category.trim().isEmpty()) {
            jCate.setVisibility(View.VISIBLE);
            cate.setText(category);
        }
        if (j_id != null && !j_id.trim().isEmpty()) {
            j_Id.setVisibility(View.VISIBLE);
            jId.setText(j_id);
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
