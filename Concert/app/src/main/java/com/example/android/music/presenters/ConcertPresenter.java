package com.example.android.music.presenters;

import com.example.android.music.database.models.Concert;
import com.example.android.music.database.models.Event;
import com.example.android.music.database.models.Image;
import com.example.android.music.database.models.ImageTracks;
import com.example.android.music.database.models.JamBaseResponse;
import com.example.android.music.database.models.LastFmResponse;
import com.example.android.music.database.models.Toptracks;
import com.example.android.music.database.models.Track;
import com.example.android.music.services.JamBaseApiCall;
import com.example.android.music.services.LastFmApiCall;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public final class ConcertPresenter {

    private static final String IMAGE_SIZE_XL = "extralarge";
    private static final String IMAGE_SIZE_L = "large";

    private ConcertPresenter() {
        // Default blank constructor
    }

    public static Observable<Event> getEventObservable(String zipCode) {
        return JamBaseApiCall.getJamBaseService().getConcerts(zipCode, JamBaseApiCall.getApiKey())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<JamBaseResponse, ObservableSource<Event>>() {
                    @Override
                    public ObservableSource<Event> apply(JamBaseResponse jamBaseResponse) throws Exception {
                        if (jamBaseResponse.getEvents().size() > 0) {
                            return Observable.fromIterable(jamBaseResponse.getEvents());
                        } else {
                            return null;
                        }

                    }
                });
    }

    public static Observable<LastFmResponse> getTracksObservable(String artistName) {
        return LastFmApiCall.getLastFmService().getTracks(artistName, LastFmApiCall.getApiKey())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static String getImageURL(List<Image> images) {
        for (int i = 0; i < images.size(); i++) {
            Image currImage = images.get(i);
            if (currImage.getSize().equals(IMAGE_SIZE_L)) {
                return currImage.getText();
            }
        }
        return null;
    }

    public static boolean populateConcert(ArrayList<Concert> concerts,
                                          Object receivedObject,
                                          String[] artists,
                                          Event[] events) {
        LastFmResponse lastFmResponse = null;
        if (receivedObject != null) {
            if (receivedObject instanceof LastFmResponse) {
                lastFmResponse = (LastFmResponse) receivedObject;
            }
        }
        Toptracks toptracks = lastFmResponse.getToptracks();
        if (toptracks != null) {
            if (toptracks.getTrack() != null && toptracks.getTrack().size() > 0) {
                if (toptracks.getTrack().get(0).getImage() != null) {
                    if (!events[0].getTicketUrl().isEmpty()) {
                        List<Track> tracks = toptracks.getTrack();
                        List<Image> images = tracks.get(0).getImage();
                        String imageUrl = getImageURL(images);
                        ImageTracks imageTracks = new ImageTracks(imageUrl, tracks);
                        concerts.add(new Concert(artists[0],
                                imageTracks.getImageUrl(),
                                imageTracks.getTrack(),
                                events[0].getVenue().getName(),
                                events[0].getVenue().getAddress(),
                                events[0].getVenue().getCity(),
                                events[0].getVenue().getState(),
                                events[0].getVenue().getCountry(),
                                events[0].getVenue().getZipCode(),
                                events[0].getId().toString(),
                                events[0].getDate(),
                                events[0].getDate(),
                                events[0].getTicketUrl()));
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

