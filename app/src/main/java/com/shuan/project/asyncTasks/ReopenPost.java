package com.shuan.Project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.shuan.Project.R;
import com.shuan.Project.fragment.ReOpenPostFragment;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Android on 9/4/2016.
 */
public class ReopenPost extends AsyncTask<String, String, String> {

    private Context mContext;
    private String jId, s;
    private HashMap<String, String> cData;


    public ReopenPost(Context mContext, String jId) {
        this.mContext = mContext;
        this.jId = jId;
    }

    @Override
    protected String doInBackground(String... params) {
        cData = new HashMap<String, String>();
        cData.put("jId", jId);
        try {

            JSONObject json = Connection.UrlConnection(php.reOpen, cData);
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
            Toast.makeText(mContext, "Successfully Reopened", Toast.LENGTH_SHORT).show();
            AppCompatActivity activity = (AppCompatActivity) mContext;
            Fragment f = new ReOpenPostFragment();
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, f);
            ft.detach(f);
            ft.attach(f);
            ft.commit();
        } else {
            Toast.makeText(mContext, "Something Wrong.Try Agin", Toast.LENGTH_SHORT).show();
        }
    }
}
