package com.shuan.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shuan.project.R;
import com.shuan.project.list.Sample;

import java.util.ArrayList;


public class NotifyAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Sample> list;
    private LayoutInflater inflater;

    public NotifyAdapter(Context mContext, ArrayList<Sample> list) {
        this.mContext = mContext;
        this.list = list;
        inflater = LayoutInflater.from(mContext);
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

        Sample curr = list.get(position);
        convertView = inflater.inflate(R.layout.notify_list_item, null);

        TextView nContent = (TextView) convertView.findViewById(R.id.content);
        TextView nType = (TextView) convertView.findViewById(R.id.notify_type);
        TextView nFrmId = (TextView) convertView.findViewById(R.id.frm_id);
        TextView nTitle = (TextView) convertView.findViewById(R.id.title);
        TextView nPost = (TextView) convertView.findViewById(R.id.post_id);

        nContent.setText(curr.getState());
        nType.setText(curr.getContry());
        nFrmId.setText(curr.getCty());
        nTitle.setText(curr.getDis());
        nPost.setText(curr.getDistrct());

        return convertView;
    }
}
