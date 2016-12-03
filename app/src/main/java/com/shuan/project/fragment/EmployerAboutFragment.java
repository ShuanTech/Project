package com.shuan.Project.fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.shuan.Project.R;
import com.shuan.Project.Utils.Common;
import com.shuan.Project.parser.Connection;
import com.shuan.Project.parser.php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;


public class EmployerAboutFragment extends Fragment {


    private ProgressBar progressBar;
    private TextView abt;
    private ScrollView scroll;
    private Context mContext;
    private Common mApp;
    private HashMap<String, String> aData;

    public EmployerAboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mContext = getActivity();
        mApp = (Common) mContext.getApplicationContext();

        View v = inflater.inflate(R.layout.fragment_employer_about, container, false);
        progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        scroll = (ScrollView) v.findViewById(R.id.scroll);
        abt = (TextView) v.findViewById(R.id.abt);

        new GetCmpnyDesc().execute();

        return v;
    }


    public class GetCmpnyDesc extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            aData = new HashMap<String, String>();
            aData.put("u_id", mApp.getPreference().getString(Common.u_id, ""));
            try {

                JSONObject json = Connection.UrlConnection(php.get_cmpny, aData);
                int succ = json.getInt("success");
                if (succ == 0) {
                } else {
                    JSONArray jsonArray = json.getJSONArray("abt");
                    JSONObject child = jsonArray.getJSONObject(0);
                    final String abut = child.optString("c_desc");

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            abt.setText(abut);
                        }
                    });

                }

            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            scroll.setVisibility(View.VISIBLE);
        }
    }

}
