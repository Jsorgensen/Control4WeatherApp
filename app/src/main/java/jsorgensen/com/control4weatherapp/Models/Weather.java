package jsorgensen.com.control4weatherapp.Models;


import org.json.JSONArray;

import java.util.ArrayList;

public class Weather {

    public Weather(JSONArray json){
        try{
            descriptions = new ArrayList<>();

            for(int i=0; i<json.length(); i++){
                WeatherDescription description = new WeatherDescription(json.getJSONObject(i));
                descriptions.add(description);
            }
        }catch (Exception e){}
    }

    ArrayList<WeatherDescription> descriptions;
}
