package com.example.android.music.views.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.music.R;
import com.example.android.music.views.adapters.RecyclerViewHolder;
import com.example.android.music.views.adapters.TracksArrayAdapter;
import com.example.android.music.database.models.Concert;
import com.example.android.music.database.models.Track;

import java.util.List;

public class TracksFragment extends Fragment implements AdapterView.OnItemClickListener {

    private Concert concert;
    private List<Track> tracks;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(RecyclerViewHolder.CONCERT_INFO)) {
            concert = intent.getExtras().getParcelable(RecyclerViewHolder.CONCERT_INFO);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_view_list, container, false);

        tracks = concert.getArtistTrack();

        TracksArrayAdapter tracksArrayAdapter = new TracksArrayAdapter(getActivity(), tracks);
        ListView listView = (ListView) rootView.findViewById(R.id.list_view_list);
        listView.setAdapter(tracksArrayAdapter);
        listView.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String currTrackUrl = tracks.get(position).getUrl();
        if (!currTrackUrl.isEmpty()) {
            Uri currTrackUri = Uri.parse(currTrackUrl);
            Intent intent = new Intent(Intent.ACTION_VIEW, currTrackUri);
            startActivity(intent);
        }
    }
}
