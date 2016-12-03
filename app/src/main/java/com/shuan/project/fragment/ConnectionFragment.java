package com.shuan.Project.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.shuan.Project.R;
import com.shuan.Project.Utils.Common;
import com.shuan.Project.adapter.ConnectAdapter;
import com.shuan.Project.asyncTasks.GetConnection;
import com.shuan.Project.list.Sample;

import java.util.ArrayList;
import java.util.HashMap;


public class ConnectionFragment extends Fragment {

    private ArrayList<Sample> list;
    private ConnectAdapter adapter;
    private ListView listView;
    private HashMap<String, String> cData;
    private Common mApp;
    private Context mContext;
    private ProgressBar progressBar;


    public ConnectionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContext=getActivity();
        mApp= (Common) mContext.getApplicationContext();
        View view = inflater.inflate(R.layout.fragment_connection, container, false);

        listView = (ListView) view.findViewById(R.id.connect);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        list = new ArrayList<Sample>();

        new GetConnection(getActivity(), listView, progressBar, mApp.getPreference().getString(Common.u_id,"")).execute();

        return view;
    }


}
