package com.shuan.Project.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.shuan.Project.R;
import com.shuan.Project.list.Sample;

import java.util.ArrayList;


public class SelectedListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Sample> list;
    private LayoutInflater inflater;
    private DisplayImageOptions options;


    public SelectedListAdapter(Context mContext, ArrayList<Sample> list) {
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
        Sample curr = list.get(position);

        convertView = inflater.inflate(R.layout.select_list_item, null);
        ImageView usrImg = (ImageView) convertView.findViewById(R.id.usr_img);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView dt = (TextView) convertView.findViewById(R.id.intrvew_d_t);
        TextView venue = (TextView) convertView.findViewById(R.id.intrvew_venue);
        TextView type = (TextView) convertView.findViewById(R.id.intrvew_type);

       // String[] getdt = curr.getLevel().split("-", 3);
        //Toast.makeText(mContext,curr.getLevel().toString(),Toast.LENGTH_SHORT).show();
        /*String yr = getdt[0];
        String mnth = getdt[1];
        String dat = getdt[2];*/


        name.setText(curr.getProPic());
        dt.setText("Date & Time : " + curr.getLevel() + " ," + curr.getPos());
        venue.setText("Venue : " + curr.getName());

        if (curr.getCompanyName().equalsIgnoreCase("1")) {
            type.setText("Interview Type : Face to Face");
        } else if (curr.getCompanyName().equalsIgnoreCase("2")) {
            type.setText("Interview Type : Video Chat");
        } else {
            type.setText("Interview Type : Voice Chat");
        }


        ImageLoader.getInstance().displayImage(curr.getU_id(), usrImg, options, new SimpleImageLoadingListener() {

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

        return convertView;
    }
}
