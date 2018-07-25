package com.example.android.music.activities;

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
import com.example.android.music.adapters.ConcertViewPagerAdapter;
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
        setupPlaceAutocomplete(autocompleteFragment);


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
        setMapStyleLayout(mMap);
        mMap.setOnMapClickListener(this);
        mMap.setLatLngBoundsForCameraTarget(MapPresenter.UNITED_STATES);
        Marker marker = mMap.addMarker(new MarkerOptions().position(MapPresenter.DEFAULT_LATLNG));
        marker.setTitle(MapPresenter.DEFAULT_SUBURB_NAME);
        marker.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory
                .newLatLngZoom(MapPresenter.DEFAULT_LATLNG, 5));

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

    private void setMapStyleLayout(GoogleMap googleMap) {
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style));

            if (!success) {
                Log.e(LOG_TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(LOG_TAG, "Can't find style. Error: ", e);
        }
    }

    private void setupPlaceAutocomplete(PlaceAutocompleteFragment autocompleteFragment) {
        autocompleteFragment.setOnPlaceSelectedListener(this);
        autocompleteFragment.setBoundsBias(UNITED_STATES);
        autocompleteFragment.setHint(getString(R.string.place_auto_complete_hint));
        autocompleteFragment.getView();
        autocompleteFragment.setFilter(new AutocompleteFilter
                .Builder()
                .setCountry("US")
                .build());
        EditText autocompleteText = (EditText) autocompleteFragment
                .getView()
                .findViewById(R.id.place_autocomplete_search_input);
        autocompleteText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        autocompleteText.setTextColor(getResources().getColor(R.color.white));
        autocompleteText.setHintTextColor(getResources().getColor(R.color.lightGray));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            autocompleteText.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.blueGrayLight)));
        }
        ImageView searchIcon = autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_button);
        searchIcon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        int paddingLeft = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
        int paddingTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics());
        int paddingRight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics());
        int paddingBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics());
        searchIcon.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

}
