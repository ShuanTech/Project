package com.shuan.Project.asyncTasks;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.shuan.Project.R;
import com.shuan.Project.Utils.Common;
import com.shuan.Project.fragment.ShortlistFragment;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;
import com.shuan.Project.resume.ResumeEditActivity;

import org.json.JSONObject;

import java.util.HashMap;

public class DeleteDetail extends AsyncTask<String, String, String> {

    private Context mContext;
    private String pId, table, s;
    private ProgressDialog pDialog;
    private HashMap<String, String> pData;

    private Common mApp;

    public DeleteDetail(Context mContext, String pId, String table) {
        this.mContext = mContext;
        this.pId = pId;
        this.table = table;
        mApp = (Common) mContext.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Deleting Details");
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
        pDialog.cancel();
        if (s.equalsIgnoreCase("true")) {
            if (table.equalsIgnoreCase("job")) {
                Toast.makeText(mContext, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                AppCompatActivity activity = (AppCompatActivity) mContext;
                Fragment f = new ShortlistFragment();
                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, f);
                ft.detach(f);
                ft.attach(f);
                ft.commit();
            } else {
                int val = mApp.getPreference().getInt(Common.PROFILESTRENGTH, 0);
                mApp.getPreference().edit().putInt(Common.PROFILESTRENGTH, val - 2).commit();
                Toast.makeText(mContext, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(mContext, ResumeEditActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(in);
                ((AppCompatActivity) mContext).finish();
            }

        } else {
            Toast.makeText(mContext, "Something went wrong!... Try Again", Toast.LENGTH_SHORT).show();
        }
    }
}
