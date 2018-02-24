package jsorgensen.com.control4weatherapp.Models;


import org.json.JSONObject;

public class WeatherDescription {

    public WeatherDescription(JSONObject json){
        try{
            id = json.getInt("id");
            description = json.getString("description");
            icon = json.getString("icon");
        }catch (Exception e){}
    }

    int id;
    String description;
    String icon;
}
