package jsorgensen.com.control4weatherapp.Models;


import org.json.JSONObject;

public class Clouds {

    public Clouds(JSONObject json){
        try{
            coverage = json.getInt("all");
        }catch (Exception e){}
    }

    int coverage;
}
