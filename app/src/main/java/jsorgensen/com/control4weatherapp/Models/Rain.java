package jsorgensen.com.control4weatherapp.Models;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Rain implements Parcelable{

    public Rain(JSONObject json){
        extractJSON(json);
    }

    public Double last3HoursRainFall;

    private void extractJSON(JSONObject json){
        try{
            last3HoursRainFall = json.getDouble("3h");
        }catch (Exception e){}
    }

    JSONObject toJSON(){
        JSONObject json = new JSONObject();

        try{
            json.put("3h", last3HoursRainFall);
        }catch (Exception e){}

        return json;
    }

    protected Rain(Parcel in) {
        try{
            String jsonString = in.readString();
            JSONObject json = new JSONObject(jsonString);
            extractJSON(json);
        }catch (Exception e){}
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(toJSON().toString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Rain> CREATOR = new Creator<Rain>() {
        @Override
        public Rain createFromParcel(Parcel in) {
            return new Rain(in);
        }

        @Override
        public Rain[] newArray(int size) {
            return new Rain[size];
        }
    };
}
