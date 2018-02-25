package jsorgensen.com.control4weatherapp.Models;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Clouds implements Parcelable{

    public Clouds(JSONObject json){
        extractJSON(json);
    }

    int coverage;

    private void extractJSON(JSONObject json){
        try{
            coverage = json.getInt("all");
        }catch (Exception e){}
    }

    JSONObject toJSON(){
        JSONObject json = new JSONObject();

        try{
            json.put("all", coverage);
        }catch (Exception e){}

        return json;
    }

    protected Clouds(Parcel in) {
        try{
            String jsonString = in.readString();
            JSONObject json = new JSONObject(jsonString);
            extractJSON(json);
        }catch(Exception e){}
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(toJSON().toString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Clouds> CREATOR = new Creator<Clouds>() {
        @Override
        public Clouds createFromParcel(Parcel in) {
            return new Clouds(in);
        }

        @Override
        public Clouds[] newArray(int size) {
            return new Clouds[size];
        }
    };
}
