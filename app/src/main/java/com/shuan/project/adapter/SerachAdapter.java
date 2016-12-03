package com.shuan.Project.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.shuan.Project.R;
import com.shuan.Project.Utils.CircleImageView;
import com.shuan.Project.Utils.Common;
import com.shuan.Project.list.Sample;
import com.shuan.Project.profile.ProfileActivity;
import com.shuan.Project.profile.ProfileViewActivity;

import java.util.ArrayList;
import java.util.Locale;

public class SerachAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Sample> list, temp;
    private Common mApp;
    private DisplayImageOptions options;

    public SerachAdapter(Context mContext, ArrayList<Sample> list) {
        this.mContext = mContext;
        this.list = list;
        inflater = LayoutInflater.from(mContext);
        this.temp = new ArrayList<Sample>();
        temp.addAll(list);
        mApp = (Common) mContext.getApplicationContext();
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(R.drawable.user)
                .showImageForEmptyUri(R.drawable.user)
                .showImageOnFail(R.drawable.user)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Sample curr = list.get(position);

        convertView = inflater.inflate(R.layout.search_list_item, null);
        CircleImageView usrImg = (CircleImageView) convertView.findViewById(R.id.usr_img);
        TextView searc = (TextView) convertView.findViewById(R.id.saerch);
        TextView u_id = (TextView) convertView.findViewById(R.id.u_id);
        TextView level = (TextView) convertView.findViewById(R.id.level);
        TextView sec= (TextView) convertView.findViewById(R.id.sec);

        searc.setText(curr.getDistrct());
        u_id.setText(curr.getDis());
        level.setText(curr.getState());
        sec.setText(curr.getContry());


        ImageLoader.getInstance().displayImage(curr.getCty(), usrImg, options, new SimpleImageLoadingListener() {

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


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = null;
                if(mApp.getPreference().getString(Common.u_id,"").equalsIgnoreCase(curr.getDis())){
                    in = new Intent(mContext, ProfileActivity.class);
                }else{
                    in = new Intent(mContext, ProfileViewActivity.class);
                    in.putExtra("u_id", curr.getDis());
                    in.putExtra("level", curr.getState());
                }

                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(in);
            }
        });


        return convertView;
    }

    public void filter(String str) {
        str = str.toLowerCase(Locale.getDefault());
        list.clear();
        if (str.length() == 0) {
            list.addAll(temp);
        } else {
            for (Sample wp : temp) {
                if (wp.getDistrct().toLowerCase(Locale.getDefault()).contains(str)) {
                    list.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}


