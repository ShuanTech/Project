package com.shuan.project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONObject;

import java.util.HashMap;


public class Feedback extends AsyncTask<String, String, String> {

    private Context mContext;
    private String uId, feed, s;
    private HashMap<String, String> fData;

    public Feedback(Context mContext, String uId, String feed) {
        this.mContext = mContext;
        this.uId = uId;
        this.feed = feed;
    }

    @Override
    protected String doInBackground(String... params) {
        fData = new HashMap<String, String>();
        fData.put("u_id", uId);
        fData.put("feed", feed);
        try {
            JSONObject json = Connection.UrlConnection(php.feedBack, fData);
            int succ = json.getInt("success");
            if (succ == 0) {
                s = "false";
            } else {
                s = "true";
            }
        } catch (Exception e) {
        }
        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s.equalsIgnoreCase("true")) {
            Toast.makeText(mContext, "successfully Feedback Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Something went Wrong.Feedback Not Updated", Toast.LENGTH_SHORT).show();
        }
    }
}
