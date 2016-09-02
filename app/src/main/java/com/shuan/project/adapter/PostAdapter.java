package com.shuan.project.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.Utils.Helper;
import com.shuan.project.activities.CommentsActivity;
import com.shuan.project.asyncTasks.CheckEligible;
import com.shuan.project.asyncTasks.SavePost;
import com.shuan.project.asyncTasks.SharePost;
import com.shuan.project.list.Sample;

import java.util.ArrayList;

public class PostAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Sample> list;
    private LayoutInflater inflater;
    private DisplayImageOptions options;
    private Helper help = new Helper();
    private Common mApp;

    public PostAdapter(Context mContext, ArrayList<Sample> list) {
        this.mContext = mContext;
        this.list = list;
        inflater = LayoutInflater.from(mContext);
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(R.drawable.logo)
                .showImageForEmptyUri(R.drawable.logo)
                .showImageOnFail(R.drawable.logo)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        mApp = (Common) mContext.getApplicationContext();
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
        convertView = inflater.inflate(R.layout.job_post_view, null);

        RelativeLayout comments = (RelativeLayout) convertView.findViewById(R.id.comment);
        RelativeLayout apply = (RelativeLayout) convertView.findViewById(R.id.apply);
        RelativeLayout share = (RelativeLayout) convertView.findViewById(R.id.share);
        RelativeLayout imp = (RelativeLayout) convertView.findViewById(R.id.imp);
        ImageView cImg = (ImageView) convertView.findViewById(R.id.cmpny_logo);
        final TextView jId = (TextView) convertView.findViewById(R.id.jId);
        TextView cName = (TextView) convertView.findViewById(R.id.cmpny_name);
        TextView cDated = (TextView) convertView.findViewById(R.id.created);
        TextView cTitle = (TextView) convertView.findViewById(R.id.title);
        TextView cSkill = (TextView) convertView.findViewById(R.id.skill);
        TextView cLevel = (TextView) convertView.findViewById(R.id.level);
        TextView cLocate = (TextView) convertView.findViewById(R.id.locate);
        TextView cApplied = (TextView) convertView.findViewById(R.id.applied);
        TextView cShared = (TextView) convertView.findViewById(R.id.shared);
        TextView cFrmId = (TextView) convertView.findViewById(R.id.frm_id);
        ImageView cImpt = (ImageView) convertView.findViewById(R.id.imprtnt);

        cName.setText(curr.getCompanyName());
        jId.setText(curr.getjId());
        cDated.setText(help.getTimeAgo(mContext, curr.getjCreate()));
        cTitle.setText(curr.getjTitle());
        cSkill.setText(curr.getjSkill());
        cLevel.setText(curr.getjLevel());
        cLocate.setText(curr.getjLoc());
        cApplied.setText(curr.getjApply());
        cShared.setText(curr.getjShare());
        cFrmId.setText(curr.getjFrmId());

        if (curr.getjImp().equalsIgnoreCase("1")) {
            //Drawable getDraw=mContext.getResources().getDrawable(R.drawable.ic_important);
            // getDraw.setColorFilter(new PorterDuffColorFilter(mContext.getResources().getColor(R.color.junPrimary), PorterDuff.Mode.MULTIPLY));
            cImpt.setImageResource(R.drawable.ic_important_clr);
            //Toast.makeText(mContext,"wrk",Toast.LENGTH_SHORT).show();
        }

        ImageLoader.getInstance().displayImage(curr.getProPic(), cImg, options, new SimpleImageLoadingListener() {

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

        if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("3")) {
            apply.setVisibility(View.GONE);
            imp.setVisibility(View.GONE);
        }

        comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mContext, CommentsActivity.class);
                in.putExtra("jId", curr.getjId());
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(in);
            }
        });


        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new CheckEligible(mContext, mApp.getPreference().getString(Common.u_id, ""), curr.getjId(),
                        mApp.getPreference().getString(Common.LEVEL, "")).execute();
                mApp.getPreference().edit().putBoolean(Common.APPLY, true).commit();


            }


        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SharePost(mContext, mApp.getPreference().getString(Common.u_id, ""), curr.getjId()).execute();

            }
        });

        imp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SavePost(mContext, mApp.getPreference().getString(Common.u_id, ""), curr.getjId()).execute();
            }
        });

        return convertView;
    }
}


