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
import com.shuan.Project.asyncTasks.GetPortfolio;

/**
 * A simple {@link Fragment} subclass.
 */
public class PortfolioFragment extends Fragment {

    private Context mContext;
    private Common mApp;
    private ListView list;
    private ProgressBar progressBar;

    public PortfolioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        mApp = (Common) mContext.getApplicationContext();
        View v = inflater.inflate(R.layout.fragment_services, container, false);

        progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        list = (ListView) v.findViewById(R.id.services);

       // Toast.makeText(getActivity(), "Use website for adding more Portfolio", Toast.LENGTH_SHORT).show();
        new GetPortfolio(getActivity(), mApp.getPreference().getString(Common.u_id, ""), progressBar, list).execute();

        return v;
    }

}
