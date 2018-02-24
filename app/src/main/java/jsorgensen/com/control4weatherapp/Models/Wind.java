package jsorgensen.com.control4weatherapp.Models;


import org.json.JSONObject;

public class Wind {

    public Wind(JSONObject json){
        try {
            speed = json.getDouble("speed");
            degrees = json.getDouble("degrees");
        }catch (Exception e){}
    }

    double speed;
    double degrees;
}
