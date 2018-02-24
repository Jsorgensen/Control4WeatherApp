package jsorgensen.com.control4weatherapp.Models;


import org.json.JSONObject;

public class Snow {

    public Snow(JSONObject json){
        try{
            last3HoursSnowFall = json.getDouble("3h");
        }catch (Exception e){}
    }

    Double last3HoursSnowFall;
}
