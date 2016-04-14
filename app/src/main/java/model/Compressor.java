package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by haopei on 2016/4/1.
 */
public class Compressor implements Parcelable{

    private String id;
    private String compressorName;
    private String compressorRemarks;
    private String compressorShop;

    protected Compressor(Parcel in) {
        id = in.readString();
        compressorName = in.readString();
        compressorRemarks = in.readString();
        compressorShop = in.readString();
    }

    public static final Creator<Compressor> CREATOR = new Creator<Compressor>() {
        @Override
        public Compressor createFromParcel(Parcel in) {
            return new Compressor(in);
        }

        @Override
        public Compressor[] newArray(int size) {
            return new Compressor[size];
        }
    };

    public String getCompressorName() {
        return compressorName;
    }

    public void setCompressorName(String compressorName) {
        this.compressorName = compressorName;
    }

    public String getCompressorRemarks() {
        return compressorRemarks;
    }

    public void setCompressorRemarks(String compressorRemarks) {
        this.compressorRemarks = compressorRemarks;
    }

    public String getCompressorShop() {
        return compressorShop;
    }

    public void setCompressorShop(String compressorShop) {
        this.compressorShop = compressorShop;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Compressor{" +
                "compressorName='" + compressorName + '\'' +
                ", id='" + id + '\'' +
                ", compressorRemarks='" + compressorRemarks + '\'' +
                ", compressorShop='" + compressorShop + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(compressorName);
        dest.writeString(compressorRemarks);
        dest.writeString(compressorShop);
    }
}
