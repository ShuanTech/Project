package com.shuan.project.asyncTasks;


import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.shuan.project.R;
import com.shuan.project.fragment.ShortlistFragment;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONObject;

import java.util.HashMap;

public class ClosePost extends AsyncTask<String, String, String> {
    private Context mContext;
    private String jId, s;
    private HashMap<String, String> cData;


    public ClosePost(Context mContext, String jId) {
        this.mContext = mContext;
        this.jId = jId;
    }

    @Override
    protected String doInBackground(String... params) {
        cData = new HashMap<String, String>();
        cData.put("jId", jId);
        try {

            JSONObject json = Connection.UrlConnection(php.close_post, cData);
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
            Toast.makeText(mContext, "Successfully Shared", Toast.LENGTH_SHORT).show();
            AppCompatActivity activity = (AppCompatActivity) mContext;
            Fragment f = new ShortlistFragment();
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, f);
            ft.detach(f);
            ft.attach(f);
            ft.commit();
        } else {
            Toast.makeText(mContext, "Something Wrong.Try Agin", Toast.LENGTH_SHORT).show();
        }
    }
}
