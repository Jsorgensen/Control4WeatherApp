package jsorgensen.com.control4weatherapp.Models;


import org.json.JSONObject;


public class DetailedForecast {

    public DetailedForecast(JSONObject json){
        try{
            id = json.getInt("id");
            timeStamp = json.getInt("dt");
            cityName = json.getString("name");
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
        }catch(Exception e){

        }
    }

    int id;
    int timeStamp;
    String cityName;
    Coordinates coordinates;
    Weather weather;
    Main main;
    Wind wind;
    Clouds clouds;
    Rain rain;
}
