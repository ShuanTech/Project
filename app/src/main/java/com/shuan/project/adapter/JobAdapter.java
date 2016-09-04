package com.shuan.project.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.asyncTasks.ClosePost;
import com.shuan.project.asyncTasks.ReopenPost;
import com.shuan.project.employer.ShortListActivity;
import com.shuan.project.list.Sample;

import java.util.ArrayList;


public class JobAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Sample> list;
    private LayoutInflater inflater;
    private Common mApp;


    public JobAdapter(Context mContext, ArrayList<Sample> list) {
        this.mContext = mContext;
        this.list = list;
        inflater = LayoutInflater.from(mContext);
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
        convertView = inflater.inflate(R.layout.job_item, null);
        TextView jId = (TextView) convertView.findViewById(R.id.j_id);
        TextView jTitle = (TextView) convertView.findViewById(R.id.title);
        TextView viewd = (TextView) convertView.findViewById(R.id.viewd);
        TextView applied = (TextView) convertView.findViewById(R.id.applied);
        TextView shared = (TextView) convertView.findViewById(R.id.shared);
        final Button close = (Button) convertView.findViewById(R.id.close);

        jId.setText(curr.getU_id());
        jTitle.setText(curr.getProPic());
        viewd.setText(curr.getName() + " Viewed");
        shared.setText(curr.getLevel() + " Shared");
        applied.setText(curr.getPos() + " Applied");

        if(curr.getCompanyName().equalsIgnoreCase("0")){
            close.setText("Close");
        }else{
            close.setText("Reopen");
        }
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(close.getText().toString().equalsIgnoreCase("close")){
                    AlertDialog.Builder build = new AlertDialog.Builder(mContext)
                            .setTitle("Confirmation")
                            .setMessage("Are you sure want to close Job Post?");
                    build.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new ClosePost(mContext, curr.getU_id()).execute();
                            dialog.cancel();
                        }
                    }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();

                }else{
                    AlertDialog.Builder build = new AlertDialog.Builder(mContext)
                            .setTitle("Confirmation")
                            .setMessage("Are you sure want to close Job Post?");
                    build.setPositiveButton("REOPEN", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new ReopenPost(mContext, curr.getU_id()).execute();
                            dialog.cancel();
                        }
                    }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();

                }



            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mContext, ShortListActivity.class);
                //in.putExtra("title", curr.getCty());
                //in.putExtra("jId", curr.getDis());
                mApp.getPreference().edit().putString("title", curr.getProPic()).commit();
                mApp.getPreference().edit().putString("jId", curr.getU_id()).commit();
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(in);
            }
        });

        return convertView;
    }
}
