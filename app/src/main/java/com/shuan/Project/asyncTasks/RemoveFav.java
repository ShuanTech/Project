package com.shuan.Project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.shuan.Project.Utils.Common;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;
import com.shuan.Project.profile.ProfileViewActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Android on 2/2/2017.
 */
public class RemoveFav  extends AsyncTask<String, String, String> {

    private Class<ProfileViewActivity> profile;
    private Common mApp;
    private Context mContext;
    private String u_id, frm_id, s="";
    private HashMap<String, String> sData;

    public RemoveFav(Context mContext, String u_id, String frm_id) {

       /* Toast.makeText(mContext,frm_id,Toast.LENGTH_SHORT).show();
        Toast.makeText(mContext,u_id, Toast.LENGTH_SHORT).show();*/
        this.mContext = mContext;
        this.u_id = u_id;
        this.frm_id = frm_id;

    }


    @Override
    protected String doInBackground(String... params) {

        sData = new HashMap<String, String>();
        sData.put("u_id",u_id );
        sData.put("frm_id", frm_id);

        try {
            JSONObject json = Connection.UrlConnection(php.rmvfav,sData);
            int succ = json.getInt("success");

            if (succ == 0){

            }else{
                s= "true";
            }

        } catch (JSONException e) {
        }
        return s;
    }
    @Override
    protected void onPostExecute(String s){
        super.onPostExecute(s);
        if (s.equalsIgnoreCase("true")) {
            Toast.makeText(mContext, "Removed from favourite list", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext, "Error! Try Again", Toast.LENGTH_SHORT).show();
        }
    }
}
