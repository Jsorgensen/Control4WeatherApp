package jsorgensen.com.control4weatherapp.Models;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Snow implements Parcelable {

    public Snow(JSONObject json){
        try{
            last3HoursSnowFall = json.getDouble("3h");
        }catch (Exception e){}
    }

    public Double last3HoursSnowFall;

    private void extractJSON(JSONObject json){
        try{
            last3HoursSnowFall = json.getDouble("3h");
        }catch (Exception e){}
    }

    JSONObject toJSON(){
        JSONObject json = new JSONObject();

        try{
            json.put("3h", last3HoursSnowFall);
        }catch (Exception e){}

        return json;
    }

    protected Snow(Parcel in) {
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

    public static final Creator<Snow> CREATOR = new Creator<Snow>() {
        @Override
        public Snow createFromParcel(Parcel in) {
            return new Snow(in);
        }

        @Override
        public Snow[] newArray(int size) {
            return new Snow[size];
        }
    };
}
