package com.shuan.project.asyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;
import com.shuan.project.resume.ResumeEditActivity;

import org.json.JSONObject;

import java.util.HashMap;


public class profileSummaryUpdate extends AsyncTask<String, String, String> {

    private Context mContext;
    private String uId;
    private String[] profile;
    private ProgressDialog pDialog;
    private HashMap<String, String> seniorData;


    public profileSummaryUpdate(Context mContext, String uId, String[] profile) {
        this.mContext = mContext;
        this.uId = uId;
        this.profile = profile;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Updating Profile Summary");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }


    @Override
    protected String doInBackground(String... params) {
        for (int i = 0; i < profile.length; i++) {
            seniorData = new HashMap<String, String>();
            seniorData.put("u_id", uId);
            seniorData.put("summary", profile[i]);
            try {
                JSONObject json = Connection.UrlConnection(php.profile_summary, seniorData);

            } catch (Exception e) {
            }
        }
        return null;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pDialog.cancel();
        Toast.makeText(mContext, "Successfully Update Profile Summary", Toast.LENGTH_SHORT).show();
        Intent in = new Intent(mContext, ResumeEditActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(in);
        ((AppCompatActivity) mContext).finish();
    }
}
