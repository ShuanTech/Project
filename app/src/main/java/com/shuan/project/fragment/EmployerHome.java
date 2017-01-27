package com.shuan.Project.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuan.Project.R;
import com.shuan.Project.Utils.Common;
import com.shuan.Project.adapter.ConnectAdapter;
import com.shuan.Project.asyncTasks.GetHome;
import com.shuan.Project.employer.PostViewActivity;
import com.shuan.Project.list.Sample;

import java.util.ArrayList;
import java.util.HashMap;


public class EmployerHome extends Fragment implements AbsListView.OnScrollListener {


    private ArrayList<Sample> list;
    private ConnectAdapter adapter;
    private ListView listView;
    private HashMap<String, String> cData;
    private CardView cv;
    private Common mApp;
    private Context mContext;
    private RelativeLayout udyowel;
    private ProgressBar progressBar;

    private int preLast;
    private SwipeRefreshLayout swipe;

    public EmployerHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContext = getActivity();
        mApp = (Common) mContext.getApplicationContext();
        View view = inflater.inflate(R.layout.fragment_employer_home, container, false);

        udyowel = (RelativeLayout) view.findViewById(R.id.udyopro_post);
        listView = (ListView) view.findViewById(R.id.post);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        list = new ArrayList<Sample>();

        new GetHome(getActivity(), listView, progressBar, mApp.getPreference().getString(Common.u_id, ""), "all", swipe).execute();


       /* if (listView.getCount()==0){
            udyowel.setVisibility(View.VISIBLE);
        }else {*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView txt = (TextView) view.findViewById(R.id.jId);
                Intent in = new Intent(getActivity(), PostViewActivity.class);
                in.putExtra("jId", txt.getText().toString());
                startActivity(in);
            }
        });
        // }

        listView.setOnScrollListener(this);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetHome(getActivity(), listView, progressBar, mApp.getPreference().getString(Common.u_id, ""), "all", swipe).execute();
            }
        });
        return view;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (view.getId() == R.id.post) {
            if (firstVisibleItem == 0) {
                swipe.setEnabled(true);
                int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount) {
                    if (preLast != lastItem) {
                        preLast = lastItem;
                        //Toast.makeText(getActivity(), "In Last", Toast.LENGTH_SHORT).show();
                    }

                }else{

                }
            } else {
                swipe.setEnabled(false);
            }
        }
    }
}
