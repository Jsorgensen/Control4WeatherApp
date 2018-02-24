package jsorgensen.com.control4weatherapp.Models;


import org.json.JSONObject;

public class Coordinates {

    public Coordinates(JSONObject json){
        try{
            lattitude = json.getDouble("lat");
            longitude = json.getDouble("lon");
        }catch(Exception e){}
    }

    double lattitude;
    double longitude;
}
