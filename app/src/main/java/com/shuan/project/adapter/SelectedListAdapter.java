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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.shuan.Project.R;
import com.shuan.Project.Utils.Common;
import com.shuan.Project.asyncTasks.RejectCandidate;
import com.shuan.Project.list.Sample;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;
import com.shuan.Project.profile.ProfileViewActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class SelectedListAdapter extends BaseAdapter {

    private Context mContext;
    private Common mApp;
    private HashMap<String, String> sData;
    private ArrayList<Sample> list;
    private LayoutInflater inflater;
    private DisplayImageOptions options;
    private EditText txt;
    private String usrname;
    private String usrId,lvl;
    private String a = "1";


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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Sample curr = list.get(position);

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

        usrname = curr.getProPic();
        usrImg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new Getuserid().execute();
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
                            }
                        }).setNegativeButton("Reject", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setCancelable(false);
                        builder.setTitle("Confirmation")
                                .setMessage("Are you sure ? Do you want to Reject the candidate ?")
                                .setPositiveButton("REJECT", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        getComments(usrId);
                                        dialog.cancel();
                                    }
                                }).setNegativeButton("NOT NOW", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
                    }
                }).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
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
    private void getComments(final String usr_id) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.row, null, false);
        txt = (EditText) v.findViewById(R.id.txt1);
        txt.setHint("Reason for Rejection (150 words)");

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(v);
        builder.setCancelable(false);
        builder.setTitle("Reject Reason");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (txt.getText().toString().length() == 0) {
                    txt.setError("Enter Comments");
                    txt.requestFocus();
                } else {
                    txt.setError("");
                    dialog.cancel();
                    new RejectCandidate(mContext, usr_id,txt.getText().toString()).execute();
                }
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }

    public class Getuserid extends AsyncTask<String, String, String>{

        private String name = usrname;

        @Override
        protected String doInBackground(String... params) {

            sData = new HashMap<String, String>();
            sData.put("usrname", name);

            try{
                JSONObject json = Connection.UrlConnection(php.Getuid,sData);
                int succ = json.getInt("success");

                if (succ == 0) {
                    Toast.makeText(mContext,"Please try again after sometime",Toast.LENGTH_SHORT).show();
                } else {
                    JSONArray jsonArray = json.getJSONArray("info");
                    JSONObject child = jsonArray.getJSONObject(0);

                    final String uId = child.optString("u_id");
                    final String level = child.optString("level");

                    usrId = uId;
                    lvl = level;
                }

            } catch (JSONException e) {
            }
            return null;
        }
    }
}
