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
    private String usr, frm, s="",abc;
    private HashMap<String, String> sData;

    public RemoveFav(Context mContext, String uId, String frmId) {

        /*Toast.makeText(mContext,uId,Toast.LENGTH_SHORT).show();
        Toast.makeText(mContext,frmId, Toast.LENGTH_SHORT).show();*/
        this.mContext = mContext;
        this.usr = frmId;  //here frmId is u_id
        this.frm = uId;    //here uId is frm_id

    }


    @Override
    protected String doInBackground(String... params) {

        sData = new HashMap<String, String>();
        sData.put("u_id",usr );
        sData.put("frm_id", frm);

        try {
            JSONObject json = Connection.UrlConnection(php.rmvfav,sData);
            int succ = json.getInt("success");
            abc = String.valueOf(succ);

            if (succ == 1){
                s= "true";

            }else{
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
