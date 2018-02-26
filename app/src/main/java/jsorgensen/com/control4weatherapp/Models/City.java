package jsorgensen.com.control4weatherapp.Models;


import org.json.JSONObject;

public class City {

    public City(JSONObject json){
        try{
            id = json.getInt("id");
            name = json.getString("name");
            country = json.getString("country");
            coordinates = new Coordinates(json.getJSONObject("coord"));
        }catch (Exception e){}
    }

    public int id;
    public String name;
    public String country;
    public Coordinates coordinates;
}
