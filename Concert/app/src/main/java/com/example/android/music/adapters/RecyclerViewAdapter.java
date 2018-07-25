package com.example.android.music.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.music.database.models.Concert;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private ArrayList<Concert> concerts;
    private Context context;
    private int itemResource;


    public RecyclerViewAdapter(Context context, int itemResource, ArrayList<Concert> concerts) {
        this.concerts = concerts;
        this.context = context;
        this.itemResource = itemResource;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(this.itemResource, parent, false);

        return new RecyclerViewHolder(this.context, view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Concert concert = this.concerts.get(position);
        holder.bindConcert(concert);
    }

    @Override
    public int getItemCount() {
        return this.concerts.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setTasks(List<Concert> concertList){
        concerts = new ArrayList<>(concertList);
        notifyDataSetChanged();
    }
}
