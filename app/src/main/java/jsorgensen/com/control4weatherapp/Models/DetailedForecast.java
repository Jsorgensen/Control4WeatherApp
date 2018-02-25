package jsorgensen.com.control4weatherapp.Models;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;


public class DetailedForecast implements Parcelable {

    public DetailedForecast(JSONObject json){
        extractJSON(json);
    }

    int id;
    int timeStamp;
    String cityName;
    Sys sys;
    Coordinates coordinates;
    Weather weather;
    Main main;
    Wind wind;
    Clouds clouds;
    Rain rain;
    Snow snow;

    private void extractJSON(JSONObject json){
        try{
            id = json.getInt("id");
            timeStamp = json.getInt("dt");
            cityName = json.getString("name");
            sys = new Sys(json.getJSONObject("sys"));
            coordinates = new Coordinates(json.getJSONObject("coord"));
            weather = new Weather(json.getJSONArray("weather"));
            main = new Main(json.getJSONObject("main"));
            wind = new Wind(json.getJSONObject("wind"));
            JSONObject jsonClouds = json.optJSONObject("clouds");
            if(jsonClouds != null)
                clouds = new Clouds(jsonClouds);
            JSONObject jsonRain = json.optJSONObject("rain");
            if(jsonRain != null)
                rain = new Rain(jsonRain);
            JSONObject jsonSnow = json.optJSONObject("snow");
            if(jsonSnow != null)
                snow = new Snow(jsonSnow);
        }catch(Exception e){

        }
    }

    JSONObject toJSON(){
        JSONObject json = new JSONObject();

        try{
            json.put("id", id);
            json.put("dt", timeStamp);
            json.put("name", cityName);
            json.put("sys", sys.toJSON());
            json.put("coordinates", coordinates.toJSON());
            json.put("weather", weather.toJSON());
            json.put("main", main.toJSON());
            json.put("wind", wind.toJSON());
            json.put("clouds", clouds.toJSON());
            json.put("rain", rain.toJSON());
            json.put("snow", snow);
        }catch (Exception e){}

        return json;
    }

    protected DetailedForecast(Parcel in) {
        try{
            String jsonString = in.readString();
            JSONObject json = new JSONObject(jsonString);
            extractJSON(json);
        }catch(Exception e){}
    }

    public static final Creator<DetailedForecast> CREATOR = new Creator<DetailedForecast>() {
        @Override
        public DetailedForecast createFromParcel(Parcel in) {
            return new DetailedForecast(in);
        }

        @Override
        public DetailedForecast[] newArray(int size) {
            return new DetailedForecast[size];
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
