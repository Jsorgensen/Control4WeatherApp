package jsorgensen.com.control4weatherapp.Models;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Wind implements Parcelable{

    public Wind(JSONObject json){
        extractJSON(json);
    }

    double speed;
    double degrees;

    protected Wind(Parcel in) {
        try{
            String jsonString = in.readString();
            JSONObject json = new JSONObject(jsonString);
            extractJSON(json);
        }catch(Exception e){}
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(toJSON().toString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Wind> CREATOR = new Creator<Wind>() {
        @Override
        public Wind createFromParcel(Parcel in) {
            return new Wind(in);
        }

        @Override
        public Wind[] newArray(int size) {
            return new Wind[size];
        }
    };

    private void extractJSON(JSONObject json){
        try {
            speed = json.getDouble("speed");
            degrees = json.getDouble("degrees");
        }catch (Exception e){}
    }

    JSONObject toJSON(){
        JSONObject json = new JSONObject();

        try{
            json.put("speed", speed);
            json.put("degrees", degrees);
        }catch (Exception e){}

        return json;
    }
}
