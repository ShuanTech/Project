package com.shuan.Project.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.shuan.Project.R;
import com.shuan.Project.list.Sample;
import com.shuan.Project.profile.ProfileViewActivity;

import java.util.ArrayList;

/**
 * Created by Android on 10/27/2016.
 */

public class FollowAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Sample> list;
    private LayoutInflater inflater;
    private DisplayImageOptions options;


    public FollowAdapter(Context mContext, ArrayList<Sample> list) {
        this.mContext = mContext;
        this.list = list;
        inflater = LayoutInflater.from(mContext);
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

        convertView = inflater.inflate(R.layout.recycler_list_item, parent, false);

        ImageView usrImg = (ImageView) convertView.findViewById(R.id.usr_img);
        TextView uId = (TextView) convertView.findViewById(R.id.u_id);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView pos = (TextView) convertView.findViewById(R.id.sec);
        /*TextView companyName = (TextView) convertView.findViewById(R.id.place);*/
        TextView level = (TextView) convertView.findViewById(R.id.level);
        ImageButton chat = (ImageButton) convertView.findViewById(R.id.chat);
        uId.setText(curr.getDis());
        name.setText(curr.getDistrct());
        pos.setText(curr.getContry());
       /* companyName.setText(curr.getCompanyName());*/
        level.setText(curr.getState());

        chat.setVisibility(View.GONE);

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
                Intent in = new Intent(mContext, ProfileViewActivity.class);
                in.putExtra("u_id", curr.getDis());
                in.putExtra("level", curr.getState());
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(in);
            }
        });

        return convertView;
    }
}
