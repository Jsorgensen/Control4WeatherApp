package jsorgensen.com.control4weatherapp.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class CityForecastViewHolder extends RecyclerView.ViewHolder {

    public CityForecastViewHolder(View itemView){
        super(itemView);


    }

    TextView temperatureTextView;
    TextView cityTextView;
    ImageView iconImageView;
}
