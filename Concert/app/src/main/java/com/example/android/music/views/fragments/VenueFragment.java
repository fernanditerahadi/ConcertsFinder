package com.example.android.music.views.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.music.R;
import com.example.android.music.views.adapters.RecyclerViewHolder;
import com.example.android.music.database.models.Concert;
import com.example.android.music.utilities.DateUtils;

public class VenueFragment extends Fragment implements View.OnClickListener {

    private Concert concert;


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
        View rootView = inflater.inflate(R.layout.fragment_venue, container, false);

        TextView venueArtist = (TextView) rootView.findViewById(R.id.venue_artist);
        venueArtist.setText(concert.getArtistName());

        TextView venueName = (TextView) rootView.findViewById(R.id.venue_name);
        venueName.setText(concert.getVenueName());

        TextView venueAddress = (TextView) rootView.findViewById(R.id.venue_address);
        venueAddress.setText(concert.getVenueAddress());

        TextView venueCityState = (TextView) rootView.findViewById(R.id.venue_city_state);
        String cityState = concert.getVenueCity() + ", " + concert.getVenueState();
        venueCityState.setText(cityState);

        TextView venueCountryZipCode = (TextView) rootView.findViewById(R.id.venue_country_zipcode);
        String countryZipCode = concert.getVenueCountry() + " " + concert.getVenueZipCode();
        venueCountryZipCode.setText(countryZipCode);

        TextView venueDate = (TextView) rootView.findViewById(R.id.venue_date);
        venueDate.setText(DateUtils.makeDate(concert.getConcertDate()));

        TextView venueDay = (TextView) rootView.findViewById(R.id.venue_day);
        venueDay.setText(DateUtils.makeDay(concert.getConcertDay()));

        Button viewLocation = (Button) rootView.findViewById(R.id.button_view_location);
        viewLocation.setOnClickListener(this);

        Button getTicket = (Button) rootView.findViewById(R.id.button_get_ticket);
        if (!concert.getConcertTicketUrl().isEmpty()) {
            getTicket.setVisibility(View.VISIBLE);
            getTicket.setOnClickListener(this);
        } else {
            getTicket.setVisibility(View.GONE);
        }
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_view_location) {
            viewLocation();
        } else if (v.getId() == R.id.button_get_ticket) {
            getTicket();
        }
    }

    public boolean viewLocation() {
        String venueAddress = concert.getVenueAddress();
        String venueCity = concert.getVenueCity();
        String venueState = concert.getVenueState();
        String addressString = venueAddress + ", " + venueCity + ", " + venueState;
        Uri.Builder uriBuilder = new Uri.Builder()
                .scheme("geo")
                .appendPath("0,0")
                .appendQueryParameter("q", addressString);
        Uri gmmIntentUri = uriBuilder.build();
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
        return true;
    }

    public boolean getTicket() {
        String ticketUrl = concert.getConcertTicketUrl();
        if (!ticketUrl.isEmpty()) {
            Uri ticketUri = Uri.parse(ticketUrl);
            Intent intent = new Intent(Intent.ACTION_VIEW, ticketUri);
            startActivity(intent);
            return true;
        } else {
            return false;
        }

    }
}
