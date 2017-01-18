package com.shuan.Project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shuan.Project.adapter.ServiceAdapter;
import com.shuan.Project.list.Sample;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Android on 12/13/2016.
 */

public class ServiceView extends AsyncTask<String, String, String> {


    private Context mContext;
    private String uId;
    private String s;
    private String ser_name;
    private TextView heading, desc;
    private String title, descrip;
    private ImageView image;
    private String img;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private ListView listView;
    private ArrayList<Sample> list;
    private ServiceAdapter adpter;
    private HashMap<String, String> sData;

    public ServiceView(Context mContext, String uId, String string, TextView heading, ImageView img, ProgressBar progressBar, TextView discr, Toolbar toolbar) {

        this.mContext = mContext;
        this.uId = uId;
        this.heading = heading;
        this.image = img;
        this.progressBar = progressBar;
        this.desc = discr;
        this.toolbar = toolbar;
        list = new ArrayList<Sample>();
    }


    @Override
    protected String doInBackground(String... params) {

        sData = new HashMap<String, String>();
        sData.put("ser_name", ser_name);
        sData.put("u_id", uId);

        try {

            JSONObject json = Connection.UrlConnection(php.serView, sData);
            int succ = json.getInt("success");
            if (succ == 0) {
                s = "false";
            } else {
                JSONArray jsonArray = json.getJSONArray("services");
                JSONObject child = jsonArray.getJSONObject(0);
                /*for (int i = 0; i < jsonArray.length() ;i++){
                    JSONObject child = jsonArray.getJSONObject(i);*/

                img = child.optString("img");
                title = child.optString("ser_name");
                descrip = child.optString("ser_desc");
            }
            s = "true";

        } catch (JSONException e) {

        }

        return s;
    }

    public void execute() {

    }
}
