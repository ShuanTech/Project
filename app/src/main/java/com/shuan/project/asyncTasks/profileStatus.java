package com.shuan.Project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Android on 10/24/2016.
 */

public class profileStatus extends AsyncTask<String,String,String> {
    public Context mContext;
    public String uId,stus,s;
    public HashMap<String,String> uData;

    public profileStatus(Context mContext, String uId,String stus) {
        this.mContext = mContext;
        this.uId = uId;
        this.stus=stus;
    }

    @Override
    protected String doInBackground(String... params) {
        uData=new HashMap<String, String>();
        uData.put("u_id",uId);
        uData.put("stus",stus);
        try{
            JSONObject json= Connection.UrlConnection(php.profileUpdate,uData);
            int succ=json.getInt("success");
            if(succ==0){
                s="false";
            }else{
                s="true";
            }
        }catch (Exception e){}
        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s.equalsIgnoreCase("true")){
            Toast.makeText(mContext,"Successfully status updated",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(mContext,"Status couldn't update! Try again.",Toast.LENGTH_SHORT).show();
        }
    }
}
