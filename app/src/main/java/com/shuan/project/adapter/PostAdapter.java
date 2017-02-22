package com.shuan.Project.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.shuan.Project.R;
import com.shuan.Project.Utils.Common;
import com.shuan.Project.Utils.Helper;
import com.shuan.Project.activities.CommentsActivity;
import com.shuan.Project.asyncTasks.CheckEligible;
import com.shuan.Project.asyncTasks.SavePost;
import com.shuan.Project.asyncTasks.SharePost;
import com.shuan.Project.list.Sample;
import com.shuan.Project.profile.ProfileViewActivity;

import java.util.ArrayList;

public class PostAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Sample> list;
    private LayoutInflater inflater;
    private DisplayImageOptions options,options1;
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
        options1 = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(R.drawable.user)
                .showImageForEmptyUri(R.drawable.user)
                .showImageOnFail(R.drawable.user)
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

        LinearLayout shred= (LinearLayout) convertView.findViewById(R.id.shred);
        RelativeLayout comments = (RelativeLayout) convertView.findViewById(R.id.comment);
        RelativeLayout apply = (RelativeLayout) convertView.findViewById(R.id.apply);
        RelativeLayout share = (RelativeLayout) convertView.findViewById(R.id.share);
        final RelativeLayout imp = (RelativeLayout) convertView.findViewById(R.id.imp);
        final ImageView cImg = (ImageView) convertView.findViewById(R.id.cmpny_logo);
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
        final ImageView cImpt = (ImageView) convertView.findViewById(R.id.imprtnt);
        ImageView fp= (ImageView) convertView.findViewById(R.id.frm_img);
        TextView fs= (TextView) convertView.findViewById(R.id.shr_txt);
        TextView fl= (TextView) convertView.findViewById(R.id.fl);


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

        if(curr.getFs()!=null && !curr.getFs().trim().isEmpty()){
            shred.setVisibility(View.VISIBLE);
            fs.setText(curr.getFs() +" \t" + "Shared a post");
            fl.setText(curr.getFl());
            ImageLoader.getInstance().displayImage(curr.getFp(), fp, options1, new SimpleImageLoadingListener() {
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

            shred.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent in=new Intent(mContext, ProfileViewActivity.class);
                    in.putExtra("u_id",curr.getjFrmId());
                    in.putExtra("level",curr.getFl());
                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    mContext.startActivity(in);
                }
            });
        }

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
                final AlertDialog.Builder build = new AlertDialog.Builder(v.getRootView().getContext());
                build.setCancelable(false);
                build.setTitle("Do you want to Apply ?");

                build.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new CheckEligible(mContext, mApp.getPreference().getString(Common.u_id, ""), curr.getjId(),
                                mApp.getPreference().getString(Common.LEVEL, "")).execute();
                        mApp.getPreference().edit().putBoolean(Common.APPLY, true).commit();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();




            }


        });

        share.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final AlertDialog.Builder build = new AlertDialog.Builder(v.getRootView().getContext());
                build.setCancelable(false);
                build.setTitle("Do you want to share this post on your Timeline ?");

                build.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new SharePost(mContext, mApp.getPreference().getString(Common.u_id, ""), curr.getjId()).execute();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();


            }
        });

        imp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SavePost(mContext, mApp.getPreference().getString(Common.u_id, ""), curr.getjId(),cImpt).execute();
            }
        });
      /*  cImpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DelSavedPost(mContext,mApp.getPreference().getString(Common.u_id, ""), curr.getjId(),cImpt).execute();
            }
        });*/

        return convertView;
    }
}


