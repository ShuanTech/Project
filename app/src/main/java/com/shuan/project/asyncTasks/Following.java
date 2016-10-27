package com.shuan.project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONObject;

import java.util.HashMap;


public class Following extends AsyncTask<String, String, String> {

    private Context mContext;
    private String u_id, frm_d, s = "", level;
    private RelativeLayout flow,unflow;
    private HashMap<String, String> fData;

    public Following(Context mContext, String u_id, String frm_d, RelativeLayout flow, RelativeLayout unflow,String level) {
        this.mContext = mContext;
        this.u_id = u_id;
        this.frm_d = frm_d;
        this.flow = flow;
        this.unflow=unflow;
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
            } else if(succ==1){
                s = "true";
            }else if(succ==2){
                s="unflow";
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
            Toast.makeText(mContext, "You have successfully following", Toast.LENGTH_SHORT).show();
            flow.setVisibility(View.GONE);
            unflow.setVisibility(View.VISIBLE);
        } else if(s.equalsIgnoreCase("false")){
            Toast.makeText(mContext, "Error! Try Again", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(mContext, "You have successfully un follow", Toast.LENGTH_SHORT).show();
            flow.setVisibility(View.VISIBLE);
            unflow.setVisibility(View.GONE);
        }
    }
}
