package com.shuan.Project.fragment;


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
import com.shuan.Project.asyncTasks.GetExperienced;
import com.shuan.Project.profile.ProfileViewActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExperiencedFragment extends Fragment {

    private ProgressBar progressBar;
    private ListView listView;

    public ExperiencedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_experienced, container, false);

        listView = (ListView) view.findViewById(R.id.exprinced);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);


        new GetExperienced(getActivity(), listView, progressBar, getArguments().getString("loc"),"2").execute();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txt = (TextView) view.findViewById(R.id.u_id);
                TextView txt2 = (TextView) view.findViewById(R.id.level);
                //Toast.makeText(getActivity(),txt.getText().toString(),Toast.LENGTH_SHORT).show();
                Intent in = new Intent(getActivity(), ProfileViewActivity.class);
                in.putExtra("u_id", txt.getText().toString());
                in.putExtra("level", txt2.getText().toString());
                startActivity(in);

            }
        });

        return view;
    }

}
