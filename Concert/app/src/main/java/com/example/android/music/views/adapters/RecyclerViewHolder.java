package com.example.android.music.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.music.R;
import com.example.android.music.database.models.Concert;
import com.example.android.music.views.activities.DescriptionActivity;
import com.example.android.music.utilities.DateUtils;
import com.squareup.picasso.Picasso;

public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public static final String CONCERT_INFO = "CONCERT_INFO";
    public static final String PLACEHOLDER_IMAGE_URL =
            "https://res.cloudinary.com/jlaja/image/upload/v1532342872/ph_artist_image.jpg";

    private final ImageView artistImageView;
    private final TextView artistTextView;
    private final TextView venueTextView;
    private final TextView dateTextView;
    private final TextView dayTextView;

    private Concert concert;
    private Context context;

    public RecyclerViewHolder(Context context, View itemView) {
        super(itemView);

        this.context = context;
        this.artistImageView = itemView.findViewById(R.id.iv_artist_picture);
        this.artistTextView = itemView.findViewById(R.id.tv_artist_name);
        this.venueTextView = itemView.findViewById(R.id.tv_venue_location);
        this.dateTextView = itemView.findViewById(R.id.tv_date);
        this.dayTextView = itemView.findViewById(R.id.tv_day);

        itemView.setOnClickListener(this);
    }

    public void bindConcert(Concert concert) {
        this.concert = concert;
        artistTextView.setText(concert.getArtistName());
        venueTextView.setText(concert.getVenueName());
        dateTextView.setText(DateUtils.makeDate(concert.getConcertDate()));
        dayTextView.setText(DateUtils.makeDay(concert.getConcertDay()));
        Picasso.get().cancelRequest(artistImageView);
        if (!concert.getArtistImageURL().isEmpty()) {
            Picasso.get().load(concert.getArtistImageURL()).into(artistImageView);
        } else {
            Picasso.get().load(PLACEHOLDER_IMAGE_URL).into(artistImageView);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, DescriptionActivity.class);
        intent.putExtra(CONCERT_INFO, concert);
        context.startActivity(intent);
    }


}
