package com.shuan.Project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Android on 9/4/2016.
 */
public class AddFavorite extends AsyncTask<String, String, String> {
    private Context mContext;
    private String u_id, frm_id, s="";
    private HashMap<String, String> sData;

    public AddFavorite(Context mContext, String u_id, String frm_id) {
        this.mContext = mContext;
        this.u_id = u_id;
        this.frm_id = frm_id;
    }

    @Override
    protected String doInBackground(String... params) {
        sData = new HashMap<String, String>();
        sData.put("u_id", u_id);
        sData.put("frm_id", frm_id);
        try {
            JSONObject json = Connection.UrlConnection(php.fav, sData);
            int succ = json.getInt("success");
            if (succ == 0) {
                s = "false";
            } else if(succ==2){
                s="add";
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
        if (s.equalsIgnoreCase("true")) {
            Toast.makeText(mContext, "Successfully Add to Favorite", Toast.LENGTH_SHORT).show();
        } else if(s.equalsIgnoreCase("add")){
            Toast.makeText(mContext, "Already in Favorite List.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext, "Error! Try Again", Toast.LENGTH_SHORT).show();
        }
    }
}
