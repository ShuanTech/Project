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
import com.shuan.Project.asyncTasks.GetFollowing;


public class FollowingFragment extends Fragment {

    private Context mContext;
    private Common mApp;
    private ListView listView;
    private ProgressBar progressBar;

    public FollowingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_following, container, false);

        listView = (ListView) v.findViewById(R.id.followeing);
        progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);

        new GetFollowing(getActivity(), listView, progressBar, mApp.getPreference().getString(Common.u_id,"")).execute();

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txt = (TextView) view.findViewById(R.id.u_id);
                TextView txt2 = (TextView) view.findViewById(R.id.level);
                Intent in = new Intent(getActivity(), ProfileViewActivity.class);
                in.putExtra("u_id", txt.getText().toString());
                in.putExtra("level", txt2.getText().toString());
                startActivity(in);

            }
        });*/

        return v;
    }


}
