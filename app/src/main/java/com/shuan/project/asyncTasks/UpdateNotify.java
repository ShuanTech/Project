package com.shuan.Project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONObject;

import java.util.HashMap;


public class UpdateNotify extends AsyncTask<String, String, String> {

    private Context mContext;
    private String u_id, post_id, s;
    private HashMap<String, String> rData;

    public UpdateNotify(Context mContext, String u_id, String post_id) {
        this.mContext = mContext;
        this.u_id = u_id;
        this.post_id = post_id;
    }


    @Override
    protected String doInBackground(String... params) {

        rData = new HashMap<String, String>();
        rData.put("u_id", u_id);
        rData.put("postId", post_id);

        try {
            JSONObject json = Connection.UrlConnection(php.updateNotify, rData);
            int succ = json.getInt("success");
            if (succ == 0) {
                s = "false";
            } else {
                s = "true";
            }

        } catch (Exception e) {}

        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s.equalsIgnoreCase("true")){
            new GetInfo(mContext,u_id).execute();
        }else{
            Log.d("Notification","Notify not update");
        }
    }
}
