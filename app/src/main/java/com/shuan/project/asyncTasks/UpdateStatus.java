package com.shuan.project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONObject;

import java.util.HashMap;


public class UpdateStatus extends AsyncTask<String, String, String> {

    private Context mContext;
    private String u_id, status, s;
    private HashMap<String, String> uData;

    public UpdateStatus(Context mContext, String u_id, String status) {
        this.mContext = mContext;
        this.u_id = u_id;
        this.status = status;
    }

    @Override
    protected String doInBackground(String... params) {

        uData = new HashMap<String, String>();
        uData.put("u_id", u_id);
        uData.put("status", status);

        try {
            JSONObject json= Connection.UrlConnection(php.update_status,uData);

            int succ=json.getInt("success");
            if(succ==0){
                s="false";
            }else{
                s="true";
            }

        } catch (Exception e) {}

        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s.equalsIgnoreCase("true")){
            Toast.makeText(mContext,"Status Updated Successfully",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(mContext,"Something Went Wrong! Status couldn't update",Toast.LENGTH_SHORT).show();
        }
    }
}
