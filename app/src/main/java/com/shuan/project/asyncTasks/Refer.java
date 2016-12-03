package com.shuan.Project.asyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Android on 9/1/2016.
 */
public class Refer extends AsyncTask<String, String, String> {

    private Context mContext;
    private String referId, uId, refer, s;
    private HashMap<String, String> rData;
    private ProgressDialog pDialog;

    public Refer(Context mContext, String referId, String uId, String refer) {
        this.mContext = mContext;
        this.referId = referId;
        this.uId = uId;
        this.refer = refer;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Updating");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {

        rData = new HashMap<String, String>();
        rData.put("refer_id", referId);
        rData.put("u_id", uId);
        rData.put("refer", refer);
        try {
            JSONObject json = Connection.UrlConnection(php.refer, rData);
            int succ = json.getInt("success");

            if(succ==0){
                s="false";
            }else{
                s="true";
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
            Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
            new GetInfo(mContext, referId).execute();
            AppCompatActivity activity = (AppCompatActivity) mContext;
            activity.finish();
            activity.startActivity(activity.getIntent());
        } else {
            Toast.makeText(mContext, "Failed Share.Try Again!...", Toast.LENGTH_SHORT).show();
        }
    }
}
