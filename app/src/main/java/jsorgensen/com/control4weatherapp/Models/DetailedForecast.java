package jsorgensen.com.control4weatherapp.Models;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.util.Date;


public class DetailedForecast implements Parcelable {

    public DetailedForecast(JSONObject json){
        extractJSON(json);
    }

    public int id;
    public int timeStamp;
    public Date localTimeStamp;
    public String cityName;
    public Sys sys;
    public Coordinates coordinates;
    public Weather weather;
    public Main main;
    public Wind wind;
    public Clouds clouds;
    public Rain rain;
    public Snow snow;

    @Override
    public String toString() {
        return cityName + ", " + sys.country;
    }

    private void extractJSON(JSONObject json){
        try{
            id = json.getInt("id");
            timeStamp = json.getInt("dt");
            localTimeStamp = new Date();
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
        }catch(Exception e){}
    }

    public JSONObject toJSON(){
        JSONObject json = new JSONObject();

        try{
            json.put("id", id);
            json.put("dt", timeStamp);
            json.put("name", cityName);
            json.put("sys", sys.toJSON());
            json.put("coord", coordinates.toJSON());
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
