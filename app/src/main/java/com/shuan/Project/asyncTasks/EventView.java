package com.shuan.Project.asyncTasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
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
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import static com.shuan.Project.Utils.Common.u_id;

/**
 * Created by Android on 12/9/2016.
 */

public class EventView extends AsyncTask<String, String, String> {

    private Context mContext;
    private String evntId;
    private ImageView coverImg, cmpny_logo;
    private TextView evntname, evntdate, evntime, created, evntdesc, evntloc;
    private String companyId;
    private RelativeLayout scroll;
    private ProgressBar progressBar;
    private HashMap<String, String> eData;
    private DisplayImageOptions options;
    private String pro_pic, covr_pic,cmpny_name, c_website, cloc,cmpnyId,evnttile,location,description,cmpny,attend,date_created;
    private Helper help;

    public EventView(Context mContext, String evnt_id, RelativeLayout scroll, ProgressBar progressBar, ImageView coverImg, ImageView cmpny_logo, TextView evntname, TextView created, TextView evntdesc, TextView evntloc, TextView evntdate, TextView evntime) {

        this.mContext = mContext;
        this.evntId = evnt_id;
        this.scroll = scroll;
        this.progressBar = progressBar;
        this.coverImg = coverImg;
        this.cmpny_logo = cmpny_logo;
        this.evntname = evntname;
        this.created = created;
        this.evntdesc = evntdesc;
        this.evntloc = evntloc;
        this.evntdate = evntdate;
        this.evntime = evntime;
        help = new Helper();
    }

    @Override
    protected String doInBackground(String... params) {

        eData = new HashMap<String, String>();
        eData.put("evnt_id", evntId);
        eData.put("u_id", u_id);

        try {
            JSONObject json = Connection.UrlConnection(php.evntView, eData);

            int succ = json.getInt("success");
            if (succ == 0) {
            } else {

                JSONArray jsonArray = json.getJSONArray("evnt");
                JSONObject child = jsonArray.getJSONObject(0);

                pro_pic = child.optString("pro_pic");
                covr_pic = child.optString("cover_pic");
                cmpny_name = child.optString("cmpny_name");
                c_website = child.optString("c_website");
                cloc = child.optString("city");
                cmpnyId = child.optString("u_id");
                evnttile = child.optString("name");
                location = child.optString("location");
                description = child.optString("desc");
               /* attend = child.optString("attend");
                date_created = child.optString("date_created");*/
            }


        } catch (Exception e) {

        }
        return null;
    }

    @Override
    protected void onPostExecute(String s){

        super.onPostExecute(s);
        progressBar.setVisibility(View.GONE);
        scroll.setVisibility(View.VISIBLE);

        setData(pro_pic, covr_pic, evnttile, location, description );
    }

    private void setData(String pro_pic, String covr_pic, String evnttile, String location, String description) {

        setImage(pro_pic, cmpny_logo);
        setCover(covr_pic, coverImg);

        evntname.setText(evnttile);
        /*created.setText("Posted on " + help.getTimeAgo(mContext, date_created));*/

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
