package com.shuan.project.asyncTasks;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;
import com.shuan.project.resume.ResumeEditActivity;

import org.json.JSONObject;

import java.util.HashMap;

public class DeleteDetail extends AsyncTask<String, String, String> {

    private Context mContext;
    private String pId, table, s;
    private ProgressDialog pDialog;
    private HashMap<String, String> pData;


    public DeleteDetail(Context mContext, String pId, String table) {
        this.mContext = mContext;
        this.pId = pId;
        this.table = table;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Deleting Detail");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {

        pData = new HashMap<String, String>();
        pData.put("p_id", pId);
        pData.put("table", table);

        try {

            JSONObject json = Connection.UrlConnection(php.delData, pData);
            int succ=json.getInt("success");
            if(succ==0){
                s="false";
            }else{
                s="true";
            }

        } catch (Exception e) {
        }

        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pDialog.cancel();
        if (s.equalsIgnoreCase("true")) {
            Toast.makeText(mContext, "Successfully Deleted", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(mContext, ResumeEditActivity.class);
            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(in);
            ((AppCompatActivity) mContext).finish();
        } else {
            Toast.makeText(mContext, "Something went wrong!... Try Again", Toast.LENGTH_SHORT).show();
        }
    }
}
