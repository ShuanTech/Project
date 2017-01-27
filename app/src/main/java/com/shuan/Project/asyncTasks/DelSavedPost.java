package com.shuan.Project.asyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

import com.shuan.Project.R;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Android on 1/27/2017.
 */

public class DelSavedPost extends AsyncTask<String, String, String> {

    private ProgressDialog pDialog;
    private Context mContext;
    private String uId, jId, s;
    private ImageView img;
    private HashMap<String, String> sData;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Deleting from saved...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public DelSavedPost(Context mContext, String uId, String jId,ImageView img) {
        this.mContext = mContext;
        this.uId = uId;
        this.jId = jId;
        this.img = img;

    }

    @Override
    protected String doInBackground(String... params) {

        try {

            sData = new HashMap<String, String>();
            sData.put("u_id", uId);
            sData.put("j_id", jId);

            try {
                JSONObject json = Connection.UrlConnection(php.del_savedpost, sData);
                int succ = json.getInt("success");
                if (succ == 0) {
                    s = "false";
                }else {
                    s = "true";
                }

            } catch (Exception e) {
            }

        } catch (Exception e) {
        }
        return s;
    }

    @Override
    protected void onPostExecute(String s){
        super.onPostExecute(s);
        pDialog.cancel();
        if (s.equalsIgnoreCase("true")) {
            img.setImageResource(R.drawable.ic_important);
            Toast.makeText(mContext, "Removed from Important list", Toast.LENGTH_SHORT).show();
        } else if(s.equalsIgnoreCase("false")){
            Toast.makeText(mContext, "Failed Share.Try Again!...", Toast.LENGTH_SHORT).show();
        }

    }
}
