package com.shuan.Project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.shuan.Project.adapter.ServiceAdapter;
import com.shuan.Project.list.Sample;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Android on 9/3/2016.
 */
public class GetPortfolio extends AsyncTask<String, String, String> {

    private Context mContext;
    private String uId, s;
    private ProgressBar progressBar;
    private ListView listView;
    private ArrayList<Sample> list;
    private ServiceAdapter adpter;
    private HashMap<String, String> sData;

    public GetPortfolio(Context mContext, String uId, ProgressBar progressBar, ListView listView) {
        this.mContext = mContext;
        this.uId = uId;
        this.progressBar = progressBar;
        this.listView = listView;
        list=new ArrayList<Sample>();
    }

    @Override
    protected String doInBackground(String... params) {

        sData = new HashMap<String, String>();
        sData.put("u_id", uId);
        try {

            JSONObject json = Connection.UrlConnection(php.cmpnyPortfolio, sData);
            int succ = json.getInt("success");
            if (succ == 0) {
                s = "false";
            } else {
                JSONArray jsonArray = json.getJSONArray("portfolio");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject child = jsonArray.getJSONObject(i);
                    String img = child.optString("img");
                    String ser_name = child.optString("p_title");
                    String ser_desc = child.optString("p_description");

                    list.add(new Sample(img, ser_name, ser_desc));

                }
                s = "true";
            }

        } catch (Exception e) {
        }
        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressBar.setVisibility(View.GONE);

        if (s.equalsIgnoreCase("true")) {
            listView.setVisibility(View.VISIBLE);
            adpter = new ServiceAdapter(mContext, list);
            listView.setAdapter(adpter);
        } else {
            Toast.makeText(mContext, "No services Available.", Toast.LENGTH_SHORT).show();
        }
    }
}
