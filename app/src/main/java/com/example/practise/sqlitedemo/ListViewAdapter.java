package com.example.practise.sqlitedemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.practise.R;

/**
 * Copyright (C) 2020. All rights reserved.
 * <p>
 * Created by Peter on 2020/8/27.
 */


public class ListViewAdapter extends BaseAdapter {

    private List<History> listHistories = new ArrayList<History>();
    private LayoutInflater inflater;

    public ListViewAdapter(Context context, List<History> listHistories) {
        this.listHistories = listHistories;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return listHistories.size();
    }

    @Override
    public Object getItem(int position) {
        return listHistories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_item, null);

            holder = new ViewHolder();
            holder.title = convertView.findViewById(R.id.txt_title);
            holder.time = convertView.findViewById(R.id.txt_time);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(listHistories.get(position).title);
        holder.time.setText(DateUtil.getReadableDate(listHistories.get(position).time));
        return convertView;

    }

    public void addItem(final History history) {
        listHistories.add(history);
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        public TextView title;
        public TextView time;
    }
}
