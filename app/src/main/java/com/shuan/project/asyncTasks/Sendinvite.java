package com.shuan.project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONObject;

import java.util.HashMap;


public class Sendinvite extends AsyncTask<String, String, String> {
    private Context mContext;
    private String u_id, frm_id, name, s;
    private HashMap<String, String> sData;

    public Sendinvite(Context mContext, String u_id, String frm_id, String name) {
        this.mContext = mContext;
        this.name = name;
        this.u_id = u_id;
        this.frm_id = frm_id;
    }

    @Override
    protected String doInBackground(String... params) {
        sData = new HashMap<String, String>();
        sData.put("u_id", u_id);
        sData.put("frm_id", frm_id);
        sData.put("name", name);
        try {
            JSONObject json = Connection.UrlConnection(php.invitation, sData);
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
            Toast.makeText(mContext, "Invitation Send Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Error! Try Again", Toast.LENGTH_SHORT).show();
        }
    }
}
