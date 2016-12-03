package com.shuan.Project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.shuan.Project.Utils.Common;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Android on 8/6/2016.
 */
public class AddCollege extends AsyncTask<String,String,String> {

    private Common mApp;
    private Context mContext;
    private String u_id,clgName,univ,loc,conCent,agrt,s="";
    private HashMap<String, String> aData;

    public AddCollege(Context mContext, String u_id, String clgName, String univ, String loc, String conCent, String agrt) {
        this.mContext = mContext;
        this.u_id = u_id;
        this.clgName = clgName;
        this.univ = univ;
        this.loc = loc;
        this.conCent = conCent;
        this.agrt = agrt;

    }

    @Override
    protected String doInBackground(String... params) {
        aData = new HashMap<String, String>();

        aData.put("u_id", u_id);
        aData.put("clgName", clgName);
        aData.put("univ", univ);
        aData.put("loc", loc);
        aData.put("coCent", conCent);
        aData.put("agrt", agrt);

        try {
            JSONObject json = Connection.UrlConnection(php.qualify, aData);
            int succ = json.getInt("success");
            if(succ==0){
                s="false";
            }else{
                s="true";
            }
        } catch (JSONException e) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s.equalsIgnoreCase("false")){
            Toast.makeText(mContext,"Error! Try Again.",Toast.LENGTH_SHORT).show();
        }
    }
}
