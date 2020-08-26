package com.example.practise.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.practise.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 * <p>
 * Created by jackwang on 2020/8/22.
 */


public class LinearAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private OnItemClickListener mListener;
    private OnItemLongClickListener mLongListener;

    public LinearAdapter(Context context,OnItemClickListener listener, OnItemLongClickListener longListener) {
        this.context = context;
        this.mListener = listener;
        this.mLongListener = longListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 0) {
            return new LinearViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_linear_item, parent, false));
        } else {
            return new LinearViewHolder2(LayoutInflater.from(context).inflate(R.layout.activity_linear_item2, parent, false));
        }

//        return new LinearViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_linear_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (getItemViewType(position) == 0) {
            ((LinearViewHolder)holder).textView.setText("以梦为马，即刻出发");
        } else {
            ((LinearViewHolder2)holder).textView.setText("面朝大海，春暖花开");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(position);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mLongListener.onLongClick(position);
                return mLongListener.isStop(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public interface OnItemClickListener {
        void onClick(int pos);
    }

    public interface OnItemLongClickListener {
        void onLongClick(int pos);
        boolean isStop(int pos);
    }

    class LinearViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_title);

        }
    }

    class LinearViewHolder2 extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;

        public LinearViewHolder2(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tv_title2);
            imageView = itemView.findViewById(R.id.iv);
        }

    }
}
