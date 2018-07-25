package com.example.android.music.presenters;

import com.example.android.music.database.models.LocationIqResponse;
import com.example.android.music.services.LocationIqApiCall;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;

public final class MapPresenter {

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
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }


}
