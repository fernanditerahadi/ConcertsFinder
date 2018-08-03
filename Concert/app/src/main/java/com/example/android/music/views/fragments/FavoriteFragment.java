package com.example.android.music.views.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.android.music.R;
import com.example.android.music.views.adapters.RecyclerViewAdapter;
import com.example.android.music.database.models.Concert;
import com.example.android.music.viewmodels.ConcertListViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private RecyclerViewAdapter recyclerViewAdapter;
    private ProgressBar mLoadingIndicator;
    private ArrayList<Concert> concerts = new ArrayList<>();
    private View emptyView;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrieveConcerts();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view_list, container, false);

        emptyView = rootView.findViewById(R.id.empty_view);

        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), R.layout.list_items, concerts);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_list);
        recyclerView.setAdapter(recyclerViewAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        mLoadingIndicator = (ProgressBar) rootView.findViewById(R.id.pb_loading);
        mLoadingIndicator.setVisibility(View.VISIBLE);

        return rootView;
    }


    private void retrieveConcerts() {
        ConcertListViewModel concertListViewModel = ViewModelProviders.of(this).get(ConcertListViewModel.class);
        concertListViewModel.getConcertList().observe(getActivity(), new Observer<List<Concert>>() {
            @Override
            public void onChanged(@Nullable List<Concert> concerts) {
                if (concerts.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                }
                recyclerViewAdapter.setTasks(concerts);
                mLoadingIndicator.setVisibility(View.GONE);
            }
        });
    }
}
