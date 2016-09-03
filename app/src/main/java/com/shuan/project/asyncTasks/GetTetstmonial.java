package com.shuan.project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.shuan.project.adapter.TestmonialAdapter;
import com.shuan.project.list.Sample;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Android on 9/3/2016.
 */
public class GetTetstmonial extends AsyncTask<String, String, String> {

    private Context mContext;
    private String uId, s;
    private ProgressBar progressBar;
    private ListView listView;
    private HashMap<String, String> sData;
    private TestmonialAdapter adapter;
    private ArrayList<Sample> list;

    public GetTetstmonial(Context mContext, String uId, ProgressBar progressBar, ListView listView) {
        this.mContext = mContext;
        this.uId = uId;
        this.progressBar = progressBar;
        this.listView = listView;
        list = new ArrayList<Sample>();
    }

    @Override
    protected String doInBackground(String... params) {
        sData = new HashMap<String, String>();
        sData.put("u_id", uId);
        try {

            JSONObject json = Connection.UrlConnection(php.cmpnyTestmpnial, sData);
            int succ = json.getInt("success");
            if (succ == 0) {
                s = "false";
            } else {

                JSONArray jsonArray = json.getJSONArray("testmonial");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject child = jsonArray.getJSONObject(i);

                    String pro_pic = child.optString("pro_pic");
                    String full_name = child.optString("full_name");
                    String content = child.optString("content");

                    list.add(new Sample(pro_pic, full_name, content));


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
            adapter = new TestmonialAdapter(mContext, list);
            listView.setAdapter(adapter);
        } else {
            Toast.makeText(mContext, "No Testimonial Available", Toast.LENGTH_SHORT).show();
        }

    }
}
