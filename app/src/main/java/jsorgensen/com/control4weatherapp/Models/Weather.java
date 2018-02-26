package jsorgensen.com.control4weatherapp.Models;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;

import java.util.ArrayList;

public class Weather implements Parcelable {

    public Weather(JSONArray json){
        extractJSON(json);
    }

    public ArrayList<WeatherDescription> descriptions;

    protected Weather(Parcel in) {
        try{
            String jsonString = in.readString();
            JSONArray json = new JSONArray(jsonString);
            extractJSON(json);
        }catch (Exception e){}
    }

    public static final Creator<Weather> CREATOR = new Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };

    private void extractJSON(JSONArray json){
        try{
            descriptions = new ArrayList<>();

            for(int i=0; i<json.length(); i++){
                WeatherDescription description = new WeatherDescription(json.getJSONObject(i));
                descriptions.add(description);
            }
        }catch (Exception e){}
    }

    JSONArray toJSON(){
        JSONArray json = new JSONArray();

        try{
            for(WeatherDescription description: descriptions){
                json.put(description.toJSON());
            }
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
