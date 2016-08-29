package com.shuan.project.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.asyncTasks.GetAppliedDetail;


public class ActionFragment extends Fragment {

    private Common mApp;
    private Context mContext;
    private ListView list;
    private ProgressBar progressBar;


    public ActionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        mApp = (Common) mContext.getApplicationContext();
        View v = inflater.inflate(R.layout.fragment_action, container, false);

        list = (ListView) v.findViewById(R.id.shrt);
        progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        LayoutInflater inflater1 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vi = inflater1.inflate(R.layout.list_heder, null);
        list.addHeaderView(vi, null, false);
        progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);

        new GetAppliedDetail(getActivity(), mApp.getPreference().getString("jId", ""), list, progressBar).execute();

        return v;
    }

}
