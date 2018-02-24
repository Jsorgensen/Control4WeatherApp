package jsorgensen.com.control4weatherapp.Models;


import org.json.JSONObject;

public class Rain {

    public Rain(JSONObject json){
        try{
            last3HoursRainFall = json.getDouble("3h");
        }catch (Exception e){}
    }

    Double last3HoursRainFall;
}
