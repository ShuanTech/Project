package com.shuan.project.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.shuan.project.R;
import com.shuan.project.parser.Connection;
import com.shuan.project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class GetCourse extends AsyncTask<String, String, String> {

    private Context mContext;
    private AutoCompleteTextView txt;
    private HashMap<String, String> seniorData;
    private String[] cours;

    public GetCourse(Context mContext, AutoCompleteTextView txt) {
        this.mContext = mContext;
        this.txt = txt;
    }

    @Override
    protected String doInBackground(String... params) {
        seniorData = new HashMap<String, String>();
        seniorData.put("id", "course");
        try {

            JSONObject json = Connection.UrlConnection(php.getCourse, seniorData);
            int succ = json.getInt("success");

            if (succ == 0) {
            } else {
                JSONArray jsonArray = json.getJSONArray("course");
                cours = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject child = jsonArray.getJSONObject(i);
                    String course = child.optString("course");
                    cours[i] = course;

                }
            }

        } catch (Exception e) {}

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.custom_auto_complete_item, R.id.display, cours);
        txt.setThreshold(1);
        txt.setAdapter(adapter);

    }
}
