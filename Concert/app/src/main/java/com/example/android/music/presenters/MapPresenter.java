package com.example.android.music.presenters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.android.music.R;
import com.example.android.music.database.models.LocationIqResponse;
import com.example.android.music.services.LocationIqApiCall;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;

public final class MapPresenter {

    private static final String LOG_TAG = MapPresenter.class.getSimpleName();

    public static final LatLngBounds UNITED_STATES = new LatLngBounds(
            new LatLng(25, -125), new LatLng(49, -66));

    public static final LatLng DEFAULT_LATLNG = new LatLng(34.0, -118.0);
    public static final String DEFAULT_ZIP_CODE = "90001";
    public static final String DEFAULT_SUBURB_NAME = "East Oak Canyon Drive";

    private MapPresenter() {
        // Default blank constructor
    }

    public static Call<LocationIqResponse> getLocationIqResponse(String latitude, String longitude) {
        return LocationIqApiCall
                .getLastFmService()
                .getZipCode(latitude, longitude, LocationIqApiCall.getApiKey());

    }

    public static void setMarkerAndCamera(GoogleMap map, LatLng latLng, String road) {
        map.clear();
        Marker marker = map.addMarker(new MarkerOptions().position(latLng));
        marker.setTitle(road);
        marker.showInfoWindow();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
        map.addCircle(new CircleOptions()
                .center(latLng)
                .radius(80500)
                .strokeWidth(1)
                .strokeColor(0x80FFFFFF)
                .fillColor(0x50DDDDDD));
    }

    public static void setMapStyleLayout(GoogleMap googleMap, Context context) {
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            context, R.raw.map_style));

            if (!success) {
                Log.e(LOG_TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(LOG_TAG, "Can't find style. Error: ", e);
        }
    }

    public static void setupPlaceAutocomplete(PlaceAutocompleteFragment autocompleteFragment, Context context) {
        autocompleteFragment.setBoundsBias(UNITED_STATES);
        autocompleteFragment.setHint(context.getString(R.string.place_auto_complete_hint));
        autocompleteFragment.getView();
        autocompleteFragment.setFilter(new AutocompleteFilter
                .Builder()
                .setCountry("US")
                .build());

        EditText autocompleteText = (EditText) autocompleteFragment
                .getView()
                .findViewById(R.id.place_autocomplete_search_input);
        autocompleteText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        autocompleteText.setTextColor(context.getResources().getColor(R.color.white));
        autocompleteText.setHintTextColor(context.getResources().getColor(R.color.lightGray));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            autocompleteText.setBackgroundTintList(ColorStateList.valueOf(
                    context.getResources().getColor(R.color.blueGrayLight)));
        }

        ImageView searchIcon = autocompleteFragment.getView().findViewById(
                R.id.place_autocomplete_search_button);
        searchIcon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        int paddingLeft = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 8, context.getResources().getDisplayMetrics());
        int paddingTop = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 0, context.getResources().getDisplayMetrics());
        int paddingRight = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 0, context.getResources().getDisplayMetrics());
        int paddingBottom = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 0, context.getResources().getDisplayMetrics());

        searchIcon.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }


}
