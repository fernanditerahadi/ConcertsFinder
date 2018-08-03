package com.example.android.music.views.activities;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.music.R;
import com.example.android.music.views.adapters.ConcertViewPagerAdapter;
import com.example.android.music.database.models.LocationIqResponse;
import com.example.android.music.presenters.MapPresenter;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConcertActivity extends AppCompatActivity
        implements OnMapReadyCallback,
        PlaceSelectionListener,
        GoogleMap.OnMapClickListener {

    private static final String LOG_TAG = ConcertActivity.class.getSimpleName();
    public static final LatLngBounds UNITED_STATES = new LatLngBounds(
            new LatLng(25, -125), new LatLng(49, -66));

    public RefreshListener refreshListener;

    PlaceAutocompleteFragment autocompleteFragment;
    private GoogleMap mMap;
    private Call<LocationIqResponse> mCall;
    private String mRequestZipCode = MapPresenter.DEFAULT_ZIP_CODE;


    public interface RefreshListener {
        void onRefresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concert);


        // Instantiate the Map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.concert_map);
        mapFragment.getMapAsync(this);

        // Instantiate the Place Autocomplete
        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(this);
        MapPresenter.setupPlaceAutocomplete(autocompleteFragment, getApplicationContext());


        // Instantiate the View Pager
        ViewPager viewPager = (ViewPager) findViewById(R.id.concert_view_pager);
        ConcertViewPagerAdapter concertViewPagerAdapter = new ConcertViewPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(concertViewPagerAdapter);

        // Instantiate the Tab Layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.concert_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.setLatLngBoundsForCameraTarget(MapPresenter.UNITED_STATES);
        MapPresenter.setMapStyleLayout(mMap, getApplicationContext());
        MapPresenter.setMarkerAndCamera(mMap, MapPresenter.DEFAULT_LATLNG, MapPresenter.DEFAULT_SUBURB_NAME);

    }

    @Override
    public void onMapClick(final LatLng latLng) {
        String latitude = String.valueOf(latLng.latitude);
        String longitude = String.valueOf(latLng.longitude);
        final String[] zipCode = {MapPresenter.DEFAULT_ZIP_CODE};

        if (mCall != null) {
            mCall.cancel();
            mCall = null;
        }

        mCall = MapPresenter.getLocationIqResponse(latitude, longitude);
        mCall.enqueue(new Callback<LocationIqResponse>() {

            @Override
            public void onResponse(Call<LocationIqResponse> call, Response<LocationIqResponse> response) {
                LocationIqResponse locationIqResponse = response.body();
                if (locationIqResponse != null) {
                    zipCode[0] = locationIqResponse.getAddress().getPostcode();
                    String road = locationIqResponse.getAddress().getRoad();
                    if (zipCode[0] != null && road != null) {
                        mRequestZipCode = zipCode[0];
                        MapPresenter.setMarkerAndCamera(mMap, latLng, road);
                        autocompleteFragment.setText(road);
                        if (getRefreshListener() != null) {
                            getRefreshListener().onRefresh();
                        }
                    } else {
                        Toast.makeText(ConcertActivity.this,
                                getString(R.string.toast_failed_zip_code),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LocationIqResponse> call, Throwable t) {
                Toast.makeText(ConcertActivity.this,
                        getString(R.string.toast_failed_retrieve),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPlaceSelected(Place place) {
        if (place != null && place.getLatLng() != null) {
            onMapClick(place.getLatLng());
        }

    }

    @Override
    public void onError(Status status) {
        Log.i(LOG_TAG, "An error occurred: " + status);
    }

    public String getRequestZipCode() {
        return mRequestZipCode;
    }

    public RefreshListener getRefreshListener() {
        return refreshListener;
    }

    public void setRefreshListener(RefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }


}
