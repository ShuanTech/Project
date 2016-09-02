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

/**
 * Created by Android on 9/1/2016.
 */
public class GetReadyAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Sample> list;
    private LayoutInflater inflater;

    public GetReadyAdapter(Context mContext, ArrayList<Sample> list) {
        this.mContext = mContext;
        this.list = list;
        this.inflater = LayoutInflater.from(mContext);
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
        convertView = inflater.inflate(R.layout.get_ready_item, null);
        TextView title = (TextView) convertView.findViewById(R.id.j_title);
        TextView jobId = (TextView) convertView.findViewById(R.id.job_id);
        TextView jType = (TextView) convertView.findViewById(R.id.j_type);

        title.setText(curr.getName());
        jobId.setText(curr.getU_id());
        if(curr.getLevel().equalsIgnoreCase("1")){
            jType.setText("Interview Type: Face to Face");
        }


        return convertView;
    }
}
