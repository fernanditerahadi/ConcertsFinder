package com.example.android.music.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.music.R;
import com.example.android.music.activities.ConcertActivity;
import com.example.android.music.adapters.RecyclerViewAdapter;
import com.example.android.music.database.models.Concert;
import com.example.android.music.database.models.Event;
import com.example.android.music.presenters.ConcertPresenter;

import java.util.ArrayList;

import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


public class ConcertFragment extends Fragment implements ConcertActivity.RefreshListener {

    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Concert> concerts = new ArrayList<>();
    private Disposable disposable;
    private String zipCode;
    private ConcertActivity concertActivity;
    private View emptyView;
    private RecyclerView recyclerView;

    private ProgressBar mLoadingIndicator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        concertActivity = (ConcertActivity) getActivity();
        concertActivity.setRefreshListener(this);
        onRefresh();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressLint("CheckResult")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view_list, container, false);

        recyclerViewAdapter =
                new RecyclerViewAdapter(getContext(), R.layout.list_items, concerts);

        emptyView = rootView.findViewById(R.id.empty_view);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_list);
        recyclerView.setAdapter(recyclerViewAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        mLoadingIndicator = (ProgressBar) rootView.findViewById(R.id.pb_loading);
        showLoadingIndicator();
        return rootView;
    }

    @Override
    public void onRefresh() {
        Toast.makeText(getContext(), getString(R.string.toast_retrieve_concert), Toast.LENGTH_SHORT).show();

        if (mLoadingIndicator != null) {
            showLoadingIndicator();
        }

        zipCode = concertActivity.getRequestZipCode();
        final Event[] currEvent = {null};
        final String[] currArtistName = {null};

        if (disposable != null) {
            disposable.dispose();
            disposable = null;
            concerts.clear();
            recyclerViewAdapter.notifyDataSetChanged();
        }

        disposable = ConcertPresenter.getEventObservable(zipCode)
                .concatMap(new Function<Event, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Event event) throws Exception {
                        String artistName = event.getArtists().get(0).getName();
                        currEvent[0] = event;
                        currArtistName[0] = artistName;
                        if (artistName != null && event.getTicketUrl() != null) {
                            return ConcertPresenter
                                    .getTracksObservable(event.getArtists().get(0).getName());
                        } else {
                            if (mLoadingIndicator != null) {
                                hideLoadingIndicator();
                            }
                            return null;
                        }
                    }
                }).subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        ConcertPresenter.populateConcert(concerts, o, currArtistName, currEvent);
                        recyclerViewAdapter.notifyDataSetChanged();
                        if (mLoadingIndicator != null) {
                            hideLoadingIndicator();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override

                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(
                                getContext(),
                                getString(R.string.toast_failed_retrieve),
                                Toast.LENGTH_SHORT).show();
                        if (mLoadingIndicator != null) {
                            hideLoadingIndicator();

                        }
                    }
                });
    }

    private void hideLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.GONE);
        if (concerts.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    private void showLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
    }
}

