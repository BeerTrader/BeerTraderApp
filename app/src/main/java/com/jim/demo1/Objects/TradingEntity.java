package com.jim.demo1.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by jasekurasz on 5/21/15.
 */
public class TradingEntity implements Parcelable {
    private long id;
    private String label;
    private String name;
    private List<TradingEntity> relations;

    public TradingEntity() {
    }

    public TradingEntity(long id, String label, String name, List<TradingEntity> relations) {
        this.id = id;
        this.label = label;
        this.name = name;
    }

    public String getLabel() {
        return this.label;
    }

    public String getName() {
        return this.name;
    }

    public List<TradingEntity> getRelations() {
        return this.relations;
    }

    @Override
    public String toString() {
        return "TradingEntity [id=" + id + ", label=" + label + ", name=" + name + ", relations=" + relations + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(label);
        dest.writeString(name);
        dest.writeList(relations);
    }

    private TradingEntity(Parcel in) {
        id = in.readLong();
        label = in.readString();
        name = in.readString();
        in.readList(relations, null);
    }

    public static final Parcelable.Creator<TradingEntity> CREATOR = new Parcelable.Creator<TradingEntity>() {
        public TradingEntity createFromParcel(Parcel in) {
            return new TradingEntity(in);
        }

        public TradingEntity[] newArray(int size) {
            return new TradingEntity[size];
        }
    };
}
