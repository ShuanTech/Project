package com.shuan.Project.asyncTasks;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.Toast;

import com.shuan.Project.Utils.Common;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Android on 9/4/2016.
 */
public class AddFavorite extends AsyncTask<String, String, String> {
    private Common mApp;
    private Context mContext;
    private String u_id, frm_id, s="",fId;
    private HashMap<String, String> sData;

    public AddFavorite(Context mContext, String u_id, String frm_id) {
        this.mContext = mContext;
        this.u_id = u_id;
        this.frm_id = frm_id;

        fId = frm_id;
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
            Toast.makeText(mContext, "Successfully Added to Favorite", Toast.LENGTH_SHORT).show();
        } else if(s.equalsIgnoreCase("add")){
            //Toast.makeText(mContext, "Already in Favorite List.", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder build = new AlertDialog.Builder(mContext);
            build.setCancelable(false);
            build.setTitle("Do you really want to remove from favourite list ?");
            build.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //new RemoveFav(mContext,frm_id,mApp.getPreference().getString(Common.u_id, "")).execute();
                   /* Toast.makeText(mContext,u_id,Toast.LENGTH_SHORT).show();
                    Toast.makeText(mContext,frm_id,Toast.LENGTH_SHORT).show();*/
                    new RemoveFav(mContext,u_id,frm_id).execute();

                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).show();
        }else {
            Toast.makeText(mContext, "Error! Try Again", Toast.LENGTH_SHORT).show();
        }
    }
}
