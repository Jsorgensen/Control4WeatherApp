package jsorgensen.com.control4weatherapp.Models;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Sys implements Parcelable {

    public Sys(JSONObject json){
        extractJSON(json);
    }

    String country;

    private void extractJSON(JSONObject json){
        try{
            country = json.getString("country");
        }catch (Exception e){

        }
    }

    JSONObject toJSON(){
        JSONObject json = new JSONObject();

        try{
            json.put("country", country);
        }catch (Exception e){

        }

        return json;
    }

    protected Sys(Parcel in) {
        try {
            String jsonString = in.readString();
            JSONObject json = new JSONObject(jsonString);
            extractJSON(json);
        }catch (Exception e){}
    }

    public static final Creator<Sys> CREATOR = new Creator<Sys>() {
        @Override
        public Sys createFromParcel(Parcel in) {
            return new Sys(in);
        }

        @Override
        public Sys[] newArray(int size) {
            return new Sys[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(toJSON().toString());
    }
}
