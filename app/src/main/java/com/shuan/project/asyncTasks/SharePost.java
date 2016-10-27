package com.shuan.project.asyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONObject;

import java.util.HashMap;


public class SharePost extends AsyncTask<String, String, String> {

    private ProgressDialog pDialog;
    private Context mContext;
    private String uId, jId, s;
    private HashMap<String, String> sData;

    public SharePost(Context mContext, String uId, String jId) {
        this.mContext = mContext;
        this.uId = uId;
        this.jId = jId;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Sharing ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {

        sData = new HashMap<String, String>();
        sData.put("u_id", uId);
        sData.put("j_id", jId);

        try {
            JSONObject json = Connection.UrlConnection(php.share_post, sData);
            int succ = json.getInt("success");

            if (succ == 0) {
                s = "false";
            } else if (succ == 2) {
                s = "shared";
            } else if(succ==3){
                s="not";
            }else {
                s = "true";
            }

        } catch (Exception e) {
        }

        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pDialog.cancel();
        if (s.equalsIgnoreCase("true")) {
            AppCompatActivity activity = (AppCompatActivity) mContext;
            Intent in = activity.getIntent();
            activity.finish();
            activity.startActivity(in);
            Toast.makeText(mContext, "Successfully shared", Toast.LENGTH_SHORT).show();
        } else if (s.equalsIgnoreCase("shared")) {
            Toast.makeText(mContext, "You have Already share this post.", Toast.LENGTH_SHORT).show();
        } else if(s.equalsIgnoreCase("not")){
            Toast.makeText(mContext,"You Cannot share this post right now. Still You don't have followers",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext, "Failed Share.Try Again!...", Toast.LENGTH_SHORT).show();
        }
    }


}
