package com.shuan.project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.shuan.project.Utils.Common;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Android on 9/4/2016.
 */
public class FrsherDefault extends AsyncTask<String,String,String> {

    private Common mApp;
    private Context mContext;
    private String u_id, level;
    private HashMap<String, String> cData;

    public FrsherDefault(Context mContext, String u_id, String level) {
        this.mContext = mContext;
        this.u_id = u_id;
        this.level = level;
    }

    @Override
    protected String doInBackground(String... params) {
        cData = new HashMap<String, String>();

        cData.put("u_id", u_id);
        cData.put("cDistrict",level);

        try {
            JSONObject json = Connection.UrlConnection(php.frherDefault, cData);
            int succ = json.getInt("success");
        } catch (Exception e) {
        }
        return null;
    }
}
