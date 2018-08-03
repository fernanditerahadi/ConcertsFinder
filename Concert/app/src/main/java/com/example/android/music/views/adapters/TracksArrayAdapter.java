package com.example.android.music.views.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.music.R;
import com.example.android.music.database.models.Track;

import java.util.List;

public class TracksArrayAdapter extends ArrayAdapter<Track> {

    public TracksArrayAdapter(Activity context, List<Track> tracks) {
        super(context, 0, tracks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.track_items, parent, false);
        }

        Track currentTrack = getItem(position);

        TextView trackRankTextView = (TextView) listItemView.findViewById(R.id.tv_track_rank);
        trackRankTextView.setText(String.valueOf(position + 1) + ".");

        TextView trackNameTextView = (TextView) listItemView.findViewById(R.id.tv_track_name);
        trackNameTextView.setText(currentTrack.getName());

        ImageView trackImageView = (ImageView) listItemView.findViewById(R.id.iv_play_button);
        if (currentTrack.getUrl() == null) {
            trackImageView.setVisibility(View.INVISIBLE);
        } else {
            trackImageView.setVisibility(View.VISIBLE);
        }

        return listItemView;
    }
}
