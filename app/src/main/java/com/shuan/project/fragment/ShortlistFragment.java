package com.shuan.project.fragment;


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
import android.widget.Toast;

import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.asyncTasks.GetJobDetail;
import com.shuan.project.employer.ShortListActivity;


public class ShortlistFragment extends Fragment {


    public ListView list;
    public Common mApp;
    public Context mContext;
    public ProgressBar progressBar;

    public ShortlistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mContext = getActivity();
        mApp = (Common) mContext.getApplicationContext();
        View v = inflater.inflate(R.layout.fragment_shortlist, container, false);

        progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        list = (ListView) v.findViewById(R.id.shrt_list);

        new GetJobDetail(getActivity(), mApp.getPreference().getString(Common.u_id, ""), list, progressBar).execute();

        return v;
    }

}
