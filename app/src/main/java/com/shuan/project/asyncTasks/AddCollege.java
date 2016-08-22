package com.shuan.project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.shuan.project.Utils.Common;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Android on 8/6/2016.
 */
public class AddCollege extends AsyncTask<String,String,String> {

    private Common mApp;
    private Context mContext;
    private String u_id,clgName,univ,loc,conCent,agrt;
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
        } catch (JSONException e) {
        }
        return null;
    }
}
