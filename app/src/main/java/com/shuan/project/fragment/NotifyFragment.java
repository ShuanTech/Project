package com.shuan.Project.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shuan.Project.R;
import com.shuan.Project.Utils.Common;
import com.shuan.Project.asyncTasks.GetNotifyDetail;
import com.shuan.Project.asyncTasks.Refer;
import com.shuan.Project.asyncTasks.UpdateNotify;
import com.shuan.Project.employee.EventViewActivity;
import com.shuan.Project.employee.InterViewActivity;
import com.shuan.Project.employee.InviteActivity;
import com.shuan.Project.employer.PostViewActivity;
import com.shuan.Project.employer.ShortListActivity;
import com.shuan.Project.profile.ProfileViewActivity;


public class NotifyFragment extends Fragment implements AbsListView.OnScrollListener {

    private Common mApp;
    private Context mContext;
    private ProgressBar progressBar;
    private ListView listView;
    private SwipeRefreshLayout swipe;
    private int preLast;


    public NotifyFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext = getActivity();
        mApp = (Common) mContext.getApplicationContext();
        final View v = inflater.inflate(R.layout.fragment_notify, container, false);

        swipe = (SwipeRefreshLayout) v.findViewById(R.id.swipe);
        progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        listView = (ListView) v.findViewById(R.id.notify_list);

        new GetNotifyDetail(getActivity(), mApp.getPreference().getString(Common.u_id, ""), listView, progressBar, swipe).execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView nId = (TextView) view.findViewById(R.id.n_id);
                TextView type = (TextView) view.findViewById(R.id.notify_type);
                TextView txt = (TextView) view.findViewById(R.id.title);
                TextView txt1 = (TextView) view.findViewById(R.id.post_id);
                TextView txt2 = (TextView) view.findViewById(R.id.frm_id);
                TextView levl = (TextView) view.findViewById(R.id.n_levl);
                TextView txt3 = (TextView) view.findViewById(R.id.to_id);
                TextView vewd = (TextView) view.findViewById(R.id.n_vwe);
                if (type.getText().toString().equalsIgnoreCase("2")) {
                    if (vewd.getText().toString().equalsIgnoreCase("0")) {
                        showDialog(txt2.getText().toString());
                    } else {
                        Fragment frg = new InviteFragment();
                        FragmentManager frgmg = getActivity().getSupportFragmentManager();
                        FragmentTransaction frgtra = frgmg.beginTransaction();
                        frgtra.replace(R.id.container, frg);
                        frgtra.addToBackStack(null);
                        frgtra.commit();
                        getActivity().setTitle("Invitations");
                    }

                } else if (type.getText().toString().equalsIgnoreCase("1")) {

                    Intent in = new Intent(getActivity(), InterViewActivity.class);
                    /*in.putExtra("title", txt.getText().toString());*/
                    in.putExtra("post", txt1.getText().toString());
                    new UpdateNotify(getActivity(), mApp.getPreference().getString(Common.u_id, ""),
                            nId.getText().toString()).execute();
                    startActivity(in);
                } else if (type.getText().toString().equalsIgnoreCase("4")) {
                    Intent in = new Intent(getActivity(), InviteActivity.class);
                    in.putExtra("frm", txt2.getText().toString());
                    new UpdateNotify(getActivity(), mApp.getPreference().getString(Common.u_id, ""),
                            nId.getText().toString()).execute();
                    startActivity(in);
                } else if (type.getText().toString().equalsIgnoreCase("3")) {
                    /*mApp.getPreference().edit().putString("title", txt.getText().toString()).commit();
                    mApp.getPreference().edit().putString("jId", txt1.getText().toString()).commit();*/
                    //in.putExtra("level",levl.getText().toString());
                    new UpdateNotify(getActivity(), mApp.getPreference().getString(Common.u_id, ""),
                            nId.getText().toString()).execute();
                    startActivity(new Intent(getActivity(), ShortListActivity.class));
                } else if (type.getText().toString().equalsIgnoreCase("5")) {
                    Intent in = new Intent(mContext, PostViewActivity.class);
                    in.putExtra("jId", txt1.getText().toString());
                    new UpdateNotify(getActivity(), mApp.getPreference().getString(Common.u_id, ""),
                            nId.getText().toString()).execute();
                    startActivity(in);
                } else if (type.getText().toString().equalsIgnoreCase("7")) {
                    // Toast.makeText(mContext,txt2.getText().toString(),Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(mContext, ProfileViewActivity.class);
                    in.putExtra("u_id", txt2.getText().toString());
                    in.putExtra("level", levl.getText().toString());
                    new UpdateNotify(getActivity(), mApp.getPreference().getString(Common.u_id, ""),
                            nId.getText().toString()).execute();
                    startActivity(in);
                } else if (type.getText().toString().equalsIgnoreCase("8")) {
                    Intent in = new Intent(mContext, EventViewActivity.class);
                    in.putExtra("evnt_id", txt1.getText().toString());
                  /*  in.putExtra("u_id"),txt2.getText().toString();*/
                    in.putExtra("level", levl.getText().toString());
                    new UpdateNotify(getActivity(), mApp.getPreference().getString(Common.u_id, ""),
                            nId.getText().toString()).execute();
                    startActivity(in);
                } else if (type.getText().toString().equalsIgnoreCase("9")) {
                    Toast.makeText(getActivity(), "Verification Successful", Toast.LENGTH_SHORT).show();
                    new UpdateNotify(getActivity(), mApp.getPreference().getString(Common.u_id, ""),
                            nId.getText().toString()).execute();
                } else {
                    if (txt1.getText() == "") {
                        Toast.makeText(getActivity(), "Does not exists what are you Looking for", Toast.LENGTH_SHORT).show();
                        new UpdateNotify(getActivity(), mApp.getPreference().getString(Common.u_id, ""),
                                nId.getText().toString()).execute();
                    } else {
                        Intent in = new Intent(mContext, PostViewActivity.class);
                        in.putExtra("jId", txt1.getText().toString());
                        new UpdateNotify(getActivity(), mApp.getPreference().getString(Common.u_id, ""),
                                nId.getText().toString()).execute();
                        startActivity(in);
                    }
                }


            }
        });
        listView.setOnScrollListener(this);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetNotifyDetail(getActivity(), mApp.getPreference().getString(Common.u_id, ""), listView, progressBar, swipe).execute();
                swipe.setRefreshing(false);
            }
        });
        return v;
    }

    private void showDialog(final String s) {
        AlertDialog.Builder build = new AlertDialog.Builder(getActivity());
        build.setCancelable(false);
        build.setTitle("Reference")
                .setMessage("Are You Interested to Refer this Person?");
        build.setPositiveButton("Refer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                new Refer(getActivity(), mApp.getPreference().getString(Common.u_id, ""), s, "refer").execute();
            }
        });
        build.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Refer(getActivity(), mApp.getPreference().getString(Common.u_id, ""), s, "not").execute();
                dialog.cancel();
            }
        });
        build.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (view.getId() == R.id.notify_list) {
            if (firstVisibleItem == 0) {
                swipe.setEnabled(true);
                int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount) {
                    if (preLast != lastItem) {
                        preLast = lastItem;
                        //Toast.makeText(getActivity(), "In Last", Toast.LENGTH_SHORT).show();
                    }

                } else {

                }
            } else {
                swipe.setEnabled(false);
            }
        }

    }
}