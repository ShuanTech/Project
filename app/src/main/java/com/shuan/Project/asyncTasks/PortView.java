package com.shuan.Project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
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
 * Created by Android on 12/12/2016.
 */

public class PortView extends AsyncTask<String, String, String> {

    private Context mContext;
    private String u_id;
    private String s;
    private String p_title;
    private String title, desc, image;
    private TextView heading, discr;
    private ProgressBar progressBar;
    private DisplayImageOptions options;
    private ImageView img;
    private Toolbar toolbar;
    private ListView listView;
    private ArrayList<Sample> list;
    private ServiceAdapter adpter;
    private HashMap<String, String> pData;


    public PortView(Context mContext,  String p_title, String u_id, TextView heading, ImageView img, ProgressBar progressBar, TextView discr) {
        this.mContext = mContext;
        this.u_id = u_id;
        this.p_title = p_title;
        this.heading = heading;
        this.img = img;
        this.progressBar = progressBar;
        this.discr = discr;
        list = new ArrayList<Sample>();


    }

    @Override
    protected String doInBackground(String... params) {

        pData = new HashMap<String, String>();
        pData.put("p_title", p_title);
        pData.put("u_id", u_id);

        try {

            JSONObject json = Connection.UrlConnection(php.portView, pData);
            int succ = json.getInt("success");
            if (succ == 0) {
                s = "false";
            } else {
                JSONArray jsonArray = json.getJSONArray("port");
                JSONObject child = jsonArray.getJSONObject(0);
                /*for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject child = jsonArray.getJSONObject(i);*/
                    image = child.optString("img");
                    title = child.optString("p_title");
                    desc = child.optString("p_description");


                    //  list.add(new Sample(img, title, desc));
               // }
                s = "true";
            }

        } catch (JSONException e) {
        }
        return s;
    }


    public void onPostExecute() {
        super.onPostExecute(s);
        if (s.equalsIgnoreCase("true")) {
            progressBar.setVisibility(View.GONE);
            // scroll.setVisibility(View.VISIBLE);
            setData(image, title, desc);
        }
        else{
            return ;
        }
    }

    private void setData(String image, String title, String desc) {

        heading.setText(title);
        discr.setText(Html.fromHtml(desc));
    }

}