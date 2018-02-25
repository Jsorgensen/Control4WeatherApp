package jsorgensen.com.control4weatherapp.Models;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Coordinates implements Parcelable{

    public Coordinates(JSONObject json){
        extractJSON(json);
    }

    double lattitude;
    double longitude;

    private void extractJSON(JSONObject json){
        try{
            lattitude = json.getDouble("lat");
            longitude = json.getDouble("lon");
        }catch(Exception e){}
    }

    protected Coordinates(Parcel in) {
        try {
            String jsonString = in.readString();
            JSONObject json = new JSONObject(jsonString);
            extractJSON(json);
        }catch (Exception e){}
    }

    public static final Creator<Coordinates> CREATOR = new Creator<Coordinates>() {
        @Override
        public Coordinates createFromParcel(Parcel in) {
            return new Coordinates(in);
        }

        @Override
        public Coordinates[] newArray(int size) {
            return new Coordinates[size];
        }
    };

    JSONObject toJSON(){
        JSONObject json = new JSONObject();

        try{
            json.put("lat", lattitude);
            json.put("lon", longitude);
        }catch (Exception e){

        }

        return json;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(toJSON().toString());
    }
}
