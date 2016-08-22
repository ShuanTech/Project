package com.shuan.project.asyncTasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.shuan.project.R;
import com.shuan.project.Utils.CircleImageView;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;


public class ProfileView extends AsyncTask<String, String, String> {

    private Context mContext;
    private String uId,level;
    private ImageView coverImg;
    private CircleImageView proPic;
    private TextView name, postition, org, intro;
    private LinearLayout lay;
    private HashMap<String, String> pData;
    private DisplayImageOptions options;
    private String full_name,pro_pic,cover_pic,pos ,org_name,status ,location;


    public ProfileView(Context mContext, String uId, String level,ImageView coverImg, CircleImageView proPic, TextView name, TextView postition, TextView org, TextView intro, LinearLayout lay) {
        this.mContext = mContext;
        this.uId = uId;
        this.level=level;
        this.coverImg = coverImg;
        this.proPic = proPic;
        this.name = name;
        this.postition = postition;
        this.org = org;
        this.intro = intro;
        this.lay = lay;
    }

    @Override
    protected String doInBackground(String... params) {

        pData = new HashMap<String, String>();
        pData.put("u_id", uId);
        pData.put("level",level);

        try {

            JSONObject json = Connection.UrlConnection(php.profileView, pData);
            int succ = json.getInt("success");

            if (succ == 0) {
            } else {
                JSONArray jsonArray = json.getJSONArray("pro_view");
                JSONObject chlid = jsonArray.getJSONObject(0);

                full_name = chlid.optString("full_name");
                pro_pic = chlid.optString("pro_pic");
                cover_pic = chlid.optString("cover_pic");
                pos = chlid.optString("level");
                org_name = chlid.optString("locate");
               /* status = chlid.optString("status");
                location = chlid.optString("location");*/


            }

        } catch (Exception e) {
        }

        return null;


    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        setData(full_name, pro_pic, cover_pic, pos, org_name);
    }


    private void setData(String full_name, String pro_pic, String cover_pic, String pos, String org_name) {

        setImage(pro_pic, proPic);
        setCover(cover_pic, coverImg);
        name.setText(full_name);
        postition.setText(pos);
        org.setText(org_name);
       // intro.setText(status);

       /* LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.wrk_lay, null);
        TextView txt = (TextView) v.findViewById(R.id.wrk);
        txt.setText(pos + " at " + org_name + ", " + loc);
        lay.addView(v);*/

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
