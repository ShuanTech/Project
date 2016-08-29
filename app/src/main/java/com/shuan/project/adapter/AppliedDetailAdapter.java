package com.shuan.project.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shuan.project.R;
import com.shuan.project.asyncTasks.RejectCandidate;
import com.shuan.project.employer.InterviewPanelActivity;
import com.shuan.project.employer.PdfViewActivity;
import com.shuan.project.list.Sample;

import java.util.ArrayList;


public class AppliedDetailAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Sample> list;
    private LayoutInflater inflater;
    private EditText txt;


    public AppliedDetailAdapter(Context mContext, ArrayList<Sample> list) {
        this.mContext = mContext;
        this.list = list;
        inflater = LayoutInflater.from(mContext);
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

        convertView = inflater.inflate(R.layout.shrt_list_item, null);
        TextView applied = (TextView) convertView.findViewById(R.id.candidate);
        TextView refer = (TextView) convertView.findViewById(R.id.refer);
        ImageButton view = (ImageButton) convertView.findViewById(R.id.view);
        ImageButton select = (ImageButton) convertView.findViewById(R.id.select);
        ImageButton reject = (ImageButton) convertView.findViewById(R.id.reject);

        applied.setText(curr.getDis());
        refer.setText(curr.getCty());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mContext, PdfViewActivity.class);
                in.putExtra("path", curr.getContry());
                in.putExtra("name", curr.getDis());
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(in);

            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Confirmation")
                        .setMessage("Are you sure want to Select the candidate")
                        .setPositiveButton("SELECT", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent in = new Intent(mContext, InterviewPanelActivity.class);
                                in.putExtra("a_id", curr.getDistrct());
                                in.putExtra("r_id", curr.getState());
                                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(in);
                                dialog.cancel();
                            }
                        }).setNegativeButton("NOT NOW", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Confirmation")
                        .setMessage("Are you sure want to Reject the candidate")
                        .setPositiveButton("REJECT", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getComments(curr.getDistrct());

                                dialog.cancel();
                            }
                        }).setNegativeButton("NOT NOW", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
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
                new RejectCandidate(mContext, usr_id,txt.getText().toString()).execute();
            }
        }).show();
    }


}
