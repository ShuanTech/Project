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
import com.shuan.project.adapter.ConnectAdapter;
import com.shuan.project.asyncTasks.GetPost;
import com.shuan.project.employer.PostViewActivity;
import com.shuan.project.list.Sample;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppliedFragment extends Fragment {

    private ArrayList<Sample> list;
    private ConnectAdapter adapter;
    private ListView listView;
    private HashMap<String, String> cData;
    private Common mApp;
    private Context mContext;
    private ProgressBar progressBar;

    public AppliedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mContext=getActivity();
        mApp= (Common) mContext.getApplicationContext();
        View view = inflater.inflate(R.layout.fragment_employer_home, container, false);

        listView = (ListView) view.findViewById(R.id.post);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        list = new ArrayList<Sample>();

        new GetPost(getActivity(), listView, progressBar, mApp.getPreference().getString(Common.u_id,""),"applied").execute();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txt = (TextView) view.findViewById(R.id.jId);
                TextView txt1= (TextView) view.findViewById(R.id.frm_id);
                Intent in=new Intent(getActivity(),PostViewActivity.class);
                in.putExtra("jId",txt.getText().toString());
                in.putExtra("frmId",txt1.getText().toString());
                in.putExtra("apply","yes");
                startActivity(in);
            }
        });

        return view;
    }

}
