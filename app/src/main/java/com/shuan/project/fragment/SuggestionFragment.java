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
import com.shuan.Project.asyncTasks.GetSuggest;
import com.shuan.Project.list.Sample;

import java.util.ArrayList;
import java.util.HashMap;


public class SuggestionFragment extends Fragment {

    private ArrayList<Sample> list;
    private ConnectAdapter adapter;
    private ListView listView;
    private HashMap<String, String> cData;
    private Common mApp;
    private Context mContext;
    private ProgressBar progressBar;

    public SuggestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext=getActivity();
        mApp= (Common) mContext.getApplicationContext();
        View v= inflater.inflate(R.layout.fragment_suggestion, container, false);

        listView = (ListView) v.findViewById(R.id.connect);
        progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);


        new GetSuggest(getActivity(), listView, progressBar, mApp.getPreference().getString(Common.u_id,"")).execute();


        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txt = (TextView) view.findViewById(R.id.u_id);
                TextView txt2 = (TextView) view.findViewById(R.id.level);
                //Toast.makeText(getActivity(),txt.getText().toString(),Toast.LENGTH_SHORT).show();
                Intent in = new Intent(getActivity(), ProfileViewActivity.class);
                in.putExtra("u_id", txt.getText().toString());
                in.putExtra("level", txt2.getText().toString());
                in.putExtra("who","senior");
                startActivity(in);

            }
        });*/

        return v;
    }

}
