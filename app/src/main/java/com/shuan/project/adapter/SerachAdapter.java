package com.shuan.project.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shuan.project.R;
import com.shuan.project.Utils.Common;
import com.shuan.project.list.Sample;
import com.shuan.project.profile.ProfileViewActivity;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Android on 8/19/2016.
 */
public class SerachAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Sample> list, temp;
    private Common mApp;

    public SerachAdapter(Context mContext, ArrayList<Sample> list) {
        this.mContext = mContext;
        this.list = list;
        inflater = LayoutInflater.from(mContext);
        this.temp = new ArrayList<Sample>();
        temp.addAll(list);
        mApp= (Common) mContext.getApplicationContext();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Sample curr = list.get(position);

        convertView = inflater.inflate(R.layout.search_list_item, null);

        TextView searc = (TextView) convertView.findViewById(R.id.saerch);
        TextView u_id = (TextView) convertView.findViewById(R.id.u_id);
        TextView level = (TextView) convertView.findViewById(R.id.level);

        searc.setText(curr.getName());
        u_id.setText(curr.getU_id());
        level.setText(curr.getLevel());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mContext, ProfileViewActivity.class);
                in.putExtra("u_id", curr.getU_id());
                in.putExtra("level", curr.getLevel());
                in.putExtra("who",mApp.getPreference().getString(Common.LEVEL,""));
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(in);
            }
        });

        return convertView;
    }

    public void filter(String str) {
        str = str.toLowerCase(Locale.getDefault());
        list.clear();
        if (str.length() == 0) {
            list.addAll(temp);
        } else {
            for (Sample wp : temp) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(str)) {
                    list.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}


