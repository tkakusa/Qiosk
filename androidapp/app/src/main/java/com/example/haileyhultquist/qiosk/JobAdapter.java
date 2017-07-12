package com.example.haileyhultquist.qiosk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by haileyhultquist on 7/7/17.
 */

public class JobAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Job> mDataSource;

    public JobAdapter(Context context, ArrayList<Job> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.job_listview_layout, parent, false);

        TextView titleTextView = (TextView) rowView.findViewById(R.id.job_list_title);
        TextView subtitleTextView = (TextView) rowView.findViewById(R.id.job_list_subtitle);
        TextView payTextView = (TextView) rowView.findViewById(R.id.job_list_pay);

        Job job = (Job) getItem(position);

        titleTextView.setText(job.getTitle());
        subtitleTextView.setText(job.getAddress());
        payTextView.setText(job.getPay());

        return rowView;
    }
}
