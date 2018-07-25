package com.example.android.music.database.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.android.music.database.converters.TrackConverter;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "concert")
public class Concert implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "artist_name")
    private String mArtistName;
    @ColumnInfo(name = "artist_imageurl")
    private String mArtistImageURL;
    @TypeConverters(TrackConverter.class)
    @ColumnInfo(name = "artist_track")
    private List<Track> mArtistTrack;
    @ColumnInfo(name = "venue_name")
    private String mVenueName;
    @ColumnInfo(name = "venue_address")
    private String mVenueAddress;
    @ColumnInfo(name = "venue_city")
    private String mVenueCity;
    @ColumnInfo(name = "venue_state")
    private String mVenueState;
    @ColumnInfo(name = "venue_country")
    private String mVenueCountry;
    @ColumnInfo(name = "venue_zipcode")
    private String mVenueZipCode;
    @ColumnInfo(name = "concert_id")
    private String mConcertId;
    @ColumnInfo(name = "concert_date")
    private String mConcertDate;
    @ColumnInfo(name = "concert_day")
    private String mConcertDay;
    @ColumnInfo(name = "concert_ticketurl")
    private String mConcertTicketUrl;

    public Concert(int id,
                   String mArtistName,
                   String mArtistImageURL,
                   List<Track> mArtistTrack,
                   String mVenueName,
                   String mVenueAddress,
                   String mVenueCity,
                   String mVenueState,
                   String mVenueCountry,
                   String mVenueZipCode,
                   String mConcertId,
                   String mConcertDate,
                   String mConcertDay,
                   String mConcertTicketUrl) {
        this.id = id;
        this.mArtistName = mArtistName;
        this.mArtistImageURL = mArtistImageURL;
        this.mArtistTrack = mArtistTrack;
        this.mVenueName = mVenueName;
        this.mVenueAddress = mVenueAddress;
        this.mVenueCity = mVenueCity;
        this.mVenueState = mVenueState;
        this.mVenueCountry = mVenueCountry;
        this.mVenueZipCode = mVenueZipCode;
        this.mConcertId = mConcertId;
        this.mConcertDate = mConcertDate;
        this.mConcertDay = mConcertDay;
        this.mConcertTicketUrl = mConcertTicketUrl;
    }

    @Ignore
    public Concert(String mArtistName,
                   String mArtistImageURL,
                   List<Track> mArtistTrack,
                   String mVenueName,
                   String mVenueAddress,
                   String mVenueCity,
                   String mVenueState,
                   String mVenueCountry,
                   String mVenueZipCode,
                   String mConcertId,
                   String mConcertDate,
                   String mConcertDay,
                   String mConcertTicketUrl) {
        this.mArtistName = mArtistName;
        this.mArtistImageURL = mArtistImageURL;
        this.mArtistTrack = mArtistTrack;
        this.mVenueName = mVenueName;
        this.mVenueAddress = mVenueAddress;
        this.mVenueCity = mVenueCity;
        this.mVenueState = mVenueState;
        this.mVenueCountry = mVenueCountry;
        this.mVenueZipCode = mVenueZipCode;
        this.mConcertId = mConcertId;
        this.mConcertDate = mConcertDate;
        this.mConcertDay = mConcertDay;
        this.mConcertTicketUrl = mConcertTicketUrl;
    }

    protected Concert(Parcel in) {
        mArtistName = in.readString();
        mArtistImageURL = in.readString();
        mVenueName = in.readString();
        mVenueAddress = in.readString();
        mVenueCity = in.readString();
        mVenueState = in.readString();
        mVenueCountry = in.readString();
        mVenueZipCode = in.readString();
        mConcertId = in.readString();
        mConcertDate = in.readString();
        mConcertDay = in.readString();
        mConcertTicketUrl = in.readString();
        mArtistTrack = new ArrayList<Track>();
        in.readTypedList(mArtistTrack, Track.CREATOR);
    }

    public static final Creator<Concert> CREATOR = new Creator<Concert>() {
        @Override
        public Concert createFromParcel(Parcel in) {
            return new Concert(in);
        }

        @Override
        public Concert[] newArray(int size) {
            return new Concert[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getArtistName() {
        return mArtistName;
    }

    public String getArtistImageURL() {
        return mArtistImageURL;
    }

    public List<Track> getArtistTrack() {
        return mArtistTrack;
    }

    public String getVenueName() {
        return mVenueName;
    }

    public String getVenueAddress() {
        return mVenueAddress;
    }

    public String getVenueCity() {
        return mVenueCity;
    }

    public String getVenueState() {
        return mVenueState;
    }

    public String getVenueCountry() {
        return mVenueCountry;
    }

    public String getVenueZipCode() {
        return mVenueZipCode;
    }

    public String getConcertId() {
        return mConcertId;
    }

    public String getConcertDate() {
        return mConcertDate;
    }

    public String getConcertDay() {
        return mConcertDay;
    }

    public String getConcertTicketUrl() {
        return mConcertTicketUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mArtistName);
        dest.writeString(mArtistImageURL);
        dest.writeString(mVenueName);
        dest.writeString(mVenueAddress);
        dest.writeString(mVenueCity);
        dest.writeString(mVenueState);
        dest.writeString(mVenueCountry);
        dest.writeString(mVenueZipCode);
        dest.writeString(mConcertId);
        dest.writeString(mConcertDate);
        dest.writeString(mConcertDay);
        dest.writeString(mConcertTicketUrl);
        dest.writeTypedList(mArtistTrack);
    }
}
