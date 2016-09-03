package com.shuan.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.shuan.project.R;
import com.shuan.project.list.Sample;

import java.util.ArrayList;


public class OrgAdapter extends ArrayAdapter<Sample> implements Filterable {
    private Context mContext;
    private int resource, txtId;
    private ArrayList<Sample> list, temp;
    private LayoutInflater inflater;
    private ArrayFilter filter;

    public OrgAdapter(Context mContext, int resource, int txtId, ArrayList<Sample> list) {
        super(mContext, resource);
        this.mContext = mContext;
        this.resource = resource;
        this.txtId = txtId;
        this.list = list;
        this.temp = list;
        inflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Sample getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Sample curr = list.get(position);

        convertView = inflater.inflate(resource, null);

        TextView display = (TextView) convertView.findViewById(R.id.display);
        TextView insName = (TextView) convertView.findViewById(R.id.ins_name);
        TextView univ = (TextView) convertView.findViewById(R.id.univ);
        TextView loc = (TextView) convertView.findViewById(R.id.loc);
        TextView txt1 = (TextView) convertView.findViewById(R.id.txt1);
        TextView txt2 = (TextView) convertView.findViewById(R.id.txt2);
        TextView txt3 = (TextView) convertView.findViewById(R.id.txt3);
        TextView txt4 = (TextView) convertView.findViewById(R.id.txt4);
        TextView txt5 = (TextView) convertView.findViewById(R.id.txt5);
        TextView txt6 = (TextView) convertView.findViewById(R.id.txt6);

        display.setText(curr.getDis());
        insName.setText(curr.getOrgName());
        univ.setText(curr.getType());
        loc.setText(curr.getAddr());
        txt1.setText(curr.getLand());
        txt2.setText(curr.getCty());
        txt3.setText(curr.getState());
        txt4.setText(curr.getContry());
        txt5.setText(curr.getPin());
        txt6.setText(curr.getDistrct());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new ArrayFilter();
        }
        return filter;
    }

    public class ArrayFilter extends Filter {

        private Sample sam;

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults result = new FilterResults();
            if (temp == null) {
                synchronized (sam) {
                    temp = new ArrayList<Sample>(list);
                }
            }
            if (constraint == null || constraint.length() == 0) {
                synchronized (sam) {
                    ArrayList<Sample> s_list = new ArrayList<Sample>(temp);
                    result.values = s_list;
                    result.count = s_list.size();
                }
            } else {
                String prefixString = constraint.toString().toLowerCase();
                ArrayList<Sample> val = temp;

                int count = val.size();

                ArrayList<Sample> newValues = new ArrayList<Sample>(count);
                for (int i = 0; i < count; i++) {
                    Sample item = val.get(i);
                    if (item.getDis().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        newValues.add(item);
                    }

                }
                result.values = newValues;
                result.count = newValues.size();

            }
            return result;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
                list = (ArrayList<Sample>) results.values;
            } else {
                list = new ArrayList<Sample>();
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

}
