package jsorgensen.com.control4weatherapp.Models;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class WeatherDescription implements Parcelable{

    public WeatherDescription(JSONObject json){
        extractJSON(json);
    }

    public int id;
    public String description;
    public String icon;

    protected WeatherDescription(Parcel in) {
        try{
            String jsonString = in.readString();
            JSONObject json = new JSONObject(jsonString);
            extractJSON(json);
        }catch (Exception e){}
    }

    public static final Creator<WeatherDescription> CREATOR = new Creator<WeatherDescription>() {
        @Override
        public WeatherDescription createFromParcel(Parcel in) {
            return new WeatherDescription(in);
        }

        @Override
        public WeatherDescription[] newArray(int size) {
            return new WeatherDescription[size];
        }
    };

    private void extractJSON(JSONObject json){
        try{
            id = json.getInt("id");
            description = json.getString("description");
            icon = json.getString("icon");
        }catch (Exception e){}
    }

    JSONObject toJSON(){
        JSONObject json = new JSONObject();

        try{
            json.put("id", id);
            json.put("description", description);
            json.put("icon", icon);
        }catch (Exception e){}

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
