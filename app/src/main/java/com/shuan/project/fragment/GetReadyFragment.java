package com.shuan.Project.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shuan.Project.R;
import com.shuan.Project.Utils.Common;
import com.shuan.Project.adapter.ConnectAdapter;
import com.shuan.Project.asyncTasks.GetReadyList;
import com.shuan.Project.employee.InterViewActivity;
import com.shuan.Project.list.Sample;

import java.util.ArrayList;
import java.util.HashMap;


public class GetReadyFragment extends Fragment {

    private ArrayList<Sample> list;
    private ConnectAdapter adapter;
    private ListView listView;
    private HashMap<String, String> cData;
    private Common mApp;
    private Context mContext;
    private ProgressBar progressBar;


    public GetReadyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext=getActivity();
        mApp= (Common) mContext.getApplicationContext();
        View v= inflater.inflate(R.layout.fragment_get_ready, container, false);

        listView = (ListView) v.findViewById(R.id.connect);
        progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);

        list = new ArrayList<Sample>();

        new GetReadyList(getActivity(), listView, progressBar, mApp.getPreference().getString(Common.u_id,"")).execute();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txt = (TextView) view.findViewById(R.id.j_title);
                TextView txt1 = (TextView) view.findViewById(R.id.job_id);
               // Toast.makeText(getActivity(),txt1.getText().toString(),Toast.LENGTH_SHORT).show();
                Intent in = new Intent(getActivity(), InterViewActivity.class);
                in.putExtra("title", txt.getText().toString());
                in.putExtra("post", txt1.getText().toString());
                //Toast.makeText(getActivity(),txt1.getText().toString(),Toast.LENGTH_SHORT).show();
                startActivity(in);

            }
        });

        return v;
    }

}
