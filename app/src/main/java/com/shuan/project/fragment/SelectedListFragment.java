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
import com.shuan.Project.asyncTasks.GetSelectedCandidate;


public class SelectedListFragment extends Fragment {

    private ListView list;
    private ProgressBar progressBar;
    private Common mApp;
    private Context mContext;


    public SelectedListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext = getActivity();
        mApp = (Common) mContext.getApplicationContext();
        View v = inflater.inflate(R.layout.fragment_selected_list, container, false);
        list = (ListView) v.findViewById(R.id.slct_list);
        progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);

        new GetSelectedCandidate(getActivity(), mApp.getPreference().getString("jId", ""), list, progressBar).execute();

        return v;
    }

}
