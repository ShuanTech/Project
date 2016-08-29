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

import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.employer.ShortListActivity;


public class NotifyFragment extends Fragment {

    private Common mApp;
    private Context mContext;
    private ProgressBar progressBar;
    private ListView list;


    public NotifyFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext = getActivity();
        mApp = (Common) mContext.getApplicationContext();
        final View v = inflater.inflate(R.layout.fragment_notify, container, false);

        progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        list = (ListView) v.findViewById(R.id.notify_list);
        new GetNotifyDetail(getActivity(), mApp.getPreference().getString(Common.u_id, ""), list, progressBar).execute();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView txt = (TextView) view.findViewById(R.id.title);
                TextView txt1 = (TextView) view.findViewById(R.id.post_id);

                mApp.getPreference().edit().putString("title", txt.getText().toString()).commit();
                mApp.getPreference().edit().putString("jId", txt1.getText().toString()).commit();

                startActivity(new Intent(getActivity(), ShortListActivity.class));
            }
        });
        return v;
    }

}
