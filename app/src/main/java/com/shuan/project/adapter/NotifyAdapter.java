package com.shuan.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shuan.project.R;
import com.shuan.project.list.Sample;

import org.w3c.dom.Text;

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

        TextView nId= (TextView) convertView.findViewById(R.id.n_id);
        TextView nFrmId = (TextView) convertView.findViewById(R.id.frm_id);
        TextView nToId = (TextView) convertView.findViewById(R.id.to_id);
        TextView nPost = (TextView) convertView.findViewById(R.id.post_id);
        TextView nContent = (TextView) convertView.findViewById(R.id.content);
        TextView nType = (TextView) convertView.findViewById(R.id.notify_type);
        TextView nLevel= (TextView) convertView.findViewById(R.id.n_levl);
        TextView nVwe= (TextView) convertView.findViewById(R.id.n_vwe);



        nId.setText(curr.getId());
        nFrmId.setText(curr.getFrmId());
        nToId.setText(curr.getToId());
        nPost.setText(curr.getPostId());
        nContent.setText(curr.getContent());
        nType.setText(curr.getType());
        nLevel.setText(curr.getLevel());
        nVwe.setText(curr.getVwed());

        if(curr.getVwed().equalsIgnoreCase("0")){
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.notify_color));
        }

        return convertView;
    }
}
