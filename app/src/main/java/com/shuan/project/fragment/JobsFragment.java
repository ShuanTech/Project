package com.shuan.project.fragment;


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
import com.shuan.project.asyncTasks.GetEmployeeResult;
import com.shuan.project.employer.PostViewActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobsFragment extends Fragment {

    private ProgressBar progressBar;
    private ListView listView;

    public JobsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jobs, container, false);

        listView = (ListView) view.findViewById(R.id.exprinced);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);


        new GetEmployeeResult(getActivity(), listView, progressBar, getArguments().getString("loc"), "jobs").execute();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txt = (TextView) view.findViewById(R.id.jId);

                Intent in = new Intent(getActivity(), PostViewActivity.class);
                in.putExtra("jId", txt.getText().toString());

                in.putExtra("apply", "no");
                startActivity(in);

            }
        });

        return view;
    }

}
