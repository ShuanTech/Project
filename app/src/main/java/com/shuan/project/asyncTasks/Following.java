package com.shuan.project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.Toast;

import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONObject;

import java.util.HashMap;


public class Following extends AsyncTask<String, String, String> {

    private Context mContext;
    private String u_id, frm_d, s = "", level;
    private Button but1;
    private HashMap<String, String> fData;

    public Following(Context mContext, String u_id, String frm_d, Button but1, String level) {
        this.mContext = mContext;
        this.u_id = u_id;
        this.frm_d = frm_d;
        this.but1 = but1;
        this.level = level;
    }

    @Override
    protected String doInBackground(String... params) {
        fData = new HashMap<String, String>();
        fData.put("u_id", u_id);
        fData.put("frm_id", frm_d);
        fData.put("level", level);
        try {

            JSONObject json = Connection.UrlConnection(php.following, fData);
            int succ = json.getInt("success");
            if (succ == 0) {
                s = "false";
            } else {
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
            Toast.makeText(mContext, "You have succesfully followed", Toast.LENGTH_SHORT).show();
            but1.setText("Following");
        } else {
            Toast.makeText(mContext, "Error! Try Again", Toast.LENGTH_SHORT).show();
            but1.setText("Follow");
        }
    }
}
