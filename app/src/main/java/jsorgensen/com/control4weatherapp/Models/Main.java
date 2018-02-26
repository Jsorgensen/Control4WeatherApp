package jsorgensen.com.control4weatherapp.Models;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Main implements Parcelable{

    public Main(JSONObject json){
        extractJSON(json);
    }

    public double temp, tempMin, tempMax;
    public double pressure;
    public int humidity;

    protected Main(Parcel in) {
        try{
            String jsonString = in.readString();
            JSONObject json = new JSONObject(jsonString);
            extractJSON(json);
        }catch (Exception e){}
    }

    public static final Creator<Main> CREATOR = new Creator<Main>() {
        @Override
        public Main createFromParcel(Parcel in) {
            return new Main(in);
        }

        @Override
        public Main[] newArray(int size) {
            return new Main[size];
        }
    };

    private void extractJSON(JSONObject json){
        try{
            temp = json.getDouble("temp");
            tempMin = json.getDouble("temp_min");
            tempMax = json.getDouble("temp_max");
            pressure = json.getDouble("pressure");
            humidity = json.getInt("humidity");
        }catch (Exception e){}
    }

    JSONObject toJSON(){
        JSONObject json = new JSONObject();

        try{
            json.put("temp", temp);
            json.put("temp_min", tempMin);
            json.put("temp_max", tempMax);
            json.put("pressure", pressure);
            json.put("humidity", humidity);
        }catch (Exception e){}

        return json;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(toJSON().toString());
    }
}
