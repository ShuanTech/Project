package com.shuan.project.asyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;
import com.shuan.project.resume.ExpResumeGenerate;
import com.shuan.project.resume.JuniorResumeGenerate;
import com.shuan.project.resume.ResumeEditActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class CheckEligible extends AsyncTask<String, String, String> {

    private Context mContext;
    private String uId, jId, level, s="";
    private HashMap<String, String> cData;
    private ProgressDialog pDialog;
    private AlertDialog.Builder builder;
    private String[] referName = new String[0];
    private String[] referId = new String[0];
    private String name = "";

    public CheckEligible(Context mContext, String uId, String jId, String level) {
        this.mContext = mContext;
        this.uId = uId;
        this.jId = jId;
        this.level = level;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Check Eligibility...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }


    @Override
    protected String doInBackground(String... params) {
        cData = new HashMap<String, String>();
        cData.put("u_id", uId);
        cData.put("j_id", jId);
        cData.put("level", level);

        try {

            JSONObject json = Connection.UrlConnection(php.chkEligible, cData);
            int succ = json.getInt("success");
            if (succ == 1) {
                s = "same";
            } else if (succ == 2) {
                s = "meet";
            } else {
                JSONArray jsonArray = json.getJSONArray("refer");
                referName = new String[jsonArray.length()];
                referId = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject child = jsonArray.getJSONObject(i);
                    String full_name = child.optString("full_name");
                    String uId = child.optString("u_id");
                    referName[i] = full_name;
                    referId[i] = uId;
                }
                s = "ok";
            }

        } catch (Exception e) {
        }

        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pDialog.cancel();
        if (s.equalsIgnoreCase("same")) {
            Toast.makeText(mContext, "You are Working in the Same Company", Toast.LENGTH_SHORT).show();
        } else if (s.equalsIgnoreCase("meet")) {
            Toast.makeText(mContext, "You are not eligible for the job", Toast.LENGTH_SHORT).show();
        } else {

            final ArrayList<Integer> mSelectedItems = new ArrayList<Integer>();
            builder = new AlertDialog.Builder(mContext);
            builder.setTitle("select the Reference");
            builder.setMultiChoiceItems(referName, null, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    if (isChecked) {
                        mSelectedItems.add(which);
                    }

                }
            }).setPositiveButton("Refer", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    name = "";
                    for (int i = 0; i < mSelectedItems.size(); i++) {
                        if (i == mSelectedItems.size() - 1) {
                            name += referId[i];
                        } else {
                            name += referId[i] + ",";
                        }

                    }
                    conformDialog(name);
                    dialog.cancel();
                }
            }).setNegativeButton("Direct", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    conformDialog("refer");
                    dialog.cancel();
                }
            }).show();
        }
    }

    private void conformDialog(final String refer) {
        AlertDialog.Builder build = new AlertDialog.Builder(mContext);
        build.setTitle("CONFIRMATION");
        build.setMessage("Are You Sure Apply the Post or Edit the resume Content")
                .setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Intent in = new Intent(mContext, ResumeEditActivity.class);
                        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(in);
                    }
                }).setNegativeButton("APPLY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                if (level.equalsIgnoreCase("1")) {
                    Intent in = new Intent(mContext, JuniorResumeGenerate.class);
                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    in.putExtra("job_id", jId);
                    in.putExtra("refer", refer);
                    mContext.startActivity(in);
                } else {
                    Intent in = new Intent(mContext, ExpResumeGenerate.class);
                    in.putExtra("job_id", jId);
                    in.putExtra("refer", refer);
                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(in);
                }

            }
        }).show();
    }
}
