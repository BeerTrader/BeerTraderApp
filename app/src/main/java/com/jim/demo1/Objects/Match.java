package com.jim.demo1.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

/**
 * Created by jasekurasz on 5/21/15.
 */
public class Match implements Parcelable {
    private long id;
    private User offerer;
    private User desirer;
    private TradingEntity offerable;
    private TradingEntity desirable;

    public Match() {
    }

    public Match(long id, User offerer, User desirer, TradingEntity offerable, TradingEntity desirable) {
        this.id = id;
        this.offerer = offerer;
        this.desirer = desirer;
        this.offerable = offerable;
        this.desirable = desirable;
    }

    public long getId() {
        return id;
    }

    public User getOfferer() {
        return offerer;
    }

    public User getDesirer() {
        return desirer;
    }

    public TradingEntity getOfferable() {
        return offerable;
    }

    public TradingEntity getDesirable() {
        return desirable;
    }

    @Override
    public String toString() {
        return "Match [id=" + id + "offerer=" + offerer + ", desirer=" + desirer + ", offerable=" + offerable + ", desirable=" + desirable + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(offerer, flags);
        dest.writeParcelable(desirer, flags);
        dest.writeParcelable(offerable, flags);
        dest.writeParcelable(desirable, flags);
        dest.writeLong(id);
    }

    private Match(Parcel in) {
        offerer = (User) in.readParcelable(User.class.getClassLoader());
        desirer = (User) in.readParcelable(User.class.getClassLoader());
        offerable = (TradingEntity) in.readParcelable(TradingEntity.class.getClassLoader());
        desirable = (TradingEntity) in.readParcelable(TradingEntity.class.getClassLoader());
        id = in.readLong();
        //offerer = in.readParcelable(getClass().getClassLoader());
        //desirer = in.readParcelable(getClass().getClassLoader());
        //offerable = in.readParcelable(getClass().getClassLoader());
        //desirable = in.readParcelable(getClass().getClassLoader());
    }

    public static final Parcelable.Creator<Match> CREATOR = new Parcelable.Creator<Match>() {
        public Match createFromParcel(Parcel in) {
            return new Match(in);
        }

        public Match[] newArray(int size) {
            return new Match[size];
        }
    };
}
