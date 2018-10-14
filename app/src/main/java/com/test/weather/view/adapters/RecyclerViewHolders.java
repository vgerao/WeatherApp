package com.test.weather.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.weather.R;

public class RecyclerViewHolders extends RecyclerView.ViewHolder{

    public TextView dayOfWeekTv;
    public ImageView weatherIconImgView;
    public TextView weatherResultTv;
    public TextView weatherResultSmallTv;

    public RecyclerViewHolders(final View itemView) {
        super(itemView);
        dayOfWeekTv = (TextView)itemView.findViewById(R.id.day_of_the_week);
        weatherIconImgView = (ImageView)itemView.findViewById(R.id.weather_img_icon);
        weatherResultTv = (TextView) itemView.findViewById(R.id.weather_result_Tv);
        weatherResultSmallTv = (TextView)itemView.findViewById(R.id.weather_small_Tv);
    }
}
