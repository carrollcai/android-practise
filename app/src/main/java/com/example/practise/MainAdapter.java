package com.example.practise;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private List<BtnList> list;
    private OnItemClickListener onItemClickListener;

    public MainAdapter(List<BtnList> list, OnItemClickListener onItemClickListener) {
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                BtnList btnList = list.get(position);

                onItemClickListener.onClick(btnList.getCls());
            }
        });
        return viewHolder;
    }

    public interface OnItemClickListener {
        void onClick(Class<?> cls);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BtnList item = list.get(position);
        holder.mButton.setText(item.getText());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private Button mButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mButton = itemView.findViewById(R.id.main_btn);
        }
    }
}
