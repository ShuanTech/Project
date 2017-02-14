package com.shuan.Project.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.shuan.Project.R;
import com.shuan.Project.employer.InterviewPanelActivity;
import com.shuan.Project.list.Sample;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;
import com.shuan.Project.profile.ProfileViewActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class RejectedAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Sample> list;
    private LayoutInflater inflater;
    private HashMap<String, String> sData;
    private String usrname;
    private String usrId, lvl,rfrId;
    private DisplayImageOptions options;

    public RejectedAdapter(Context mContext, ArrayList<Sample> list) {
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

        convertView = inflater.inflate(R.layout.reject_list_item, null);

        ImageView usrImg = (ImageView) convertView.findViewById(R.id.usr_img);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView reason = (TextView) convertView.findViewById(R.id.reason);
        LinearLayout line = (LinearLayout) convertView.findViewById(R.id.lay1);

        name.setText(curr.getName());
        reason.setText("Reason : " + curr.getLevel());

        //line.setOnClickListener(this);

        usrname = curr.getName();
        usrImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new Getuserid1().execute();
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setCancelable(false);
                builder.setTitle("What to do ?")
                        .setPositiveButton("View", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent in = new Intent(mContext.getApplicationContext(), ProfileViewActivity.class);
                                in.putExtra("u_id", usrId);
                                in.putExtra("level", lvl);
                                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(in);
                                dialog.cancel();
                                /*Toast.makeText(mContext,lvl,Toast.LENGTH_SHORT).show();*/
                            }
                        }).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setNegativeButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setCancelable(false);
                        builder.setTitle("Confirmation");
                        builder.setMessage("Select the candidate?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent in = new Intent(mContext, InterviewPanelActivity.class);
                                in.putExtra("a_id",usrId);
                                in.putExtra("r_id", rfrId);
                                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(in);
                                dialog.cancel();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();

                    }
                }).show();
            }
        });

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

    public class Getuserid1 extends AsyncTask<String, String, String> {

        private String name = usrname;

        @Override
        protected String doInBackground(String... params) {

            sData = new HashMap<String, String>();
            sData.put("usrname", name);

            try {
                JSONObject json = Connection.UrlConnection(php.Getuid1, sData);
                int succ = json.getInt("success");

                if (succ == 0) {
                    Toast.makeText(mContext, "Please try again after sometime", Toast.LENGTH_SHORT).show();
                } else {
                    JSONArray jsonArray = json.getJSONArray("info");
                    JSONObject child = jsonArray.getJSONObject(0);

                    final String uId = child.optString("u_id");
                    final String level = child.optString("level");
                    final String refer = child.optString("r_id");

                    usrId = uId;
                    lvl = level;
                    rfrId = refer;
                }

            } catch (JSONException e) {
            }
            return null;
        }
    }

    /*@Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(false);
        builder.setTitle("Confirmation")
                .setMessage("Are you sure ? Do you want to move from Reject list ?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Removefromreject();
                    }
                }).setNegativeButton("NOT NOW", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }

    private class Removefromreject {

    }*/
}
