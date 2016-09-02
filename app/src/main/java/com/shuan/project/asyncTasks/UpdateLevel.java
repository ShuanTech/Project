package com.shuan.project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONObject;

import java.util.HashMap;


public class UpdateLevel extends AsyncTask<String, String, String> {

    private Context mContext;
    private String uId, s;
    private HashMap<String, String> uData;


    public UpdateLevel(Context mContext, String uId) {
        this.mContext = mContext;
        this.uId = uId;
    }

    @Override
    protected String doInBackground(String... params) {
        uData = new HashMap<String, String>();
        uData.put("u_id", uId);
        try {

            JSONObject json = Connection.UrlConnection(php.updateLevel, uData);
            int succ=json.getInt("success");
            if(succ==0){
                s="false";
            }else{
                s="true";
            }

        } catch (Exception e) {
        }
        return s;
    }


}
