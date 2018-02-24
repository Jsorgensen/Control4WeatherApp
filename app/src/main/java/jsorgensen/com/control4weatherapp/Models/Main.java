package jsorgensen.com.control4weatherapp.Models;


import org.json.JSONObject;

public class Main {

    public Main(JSONObject jsonObject){
        try{
            temp = jsonObject.getDouble("temp");
            tempMin = jsonObject.getDouble("temp_min");
            tempMax = jsonObject.getDouble("temp_max");
            pressure = jsonObject.getDouble("pressure");
            humidity = jsonObject.getInt("humidity");
        }catch (Exception e){}
    }

    double temp, tempMin, tempMax;
    double pressure;
    int humidity;
}
