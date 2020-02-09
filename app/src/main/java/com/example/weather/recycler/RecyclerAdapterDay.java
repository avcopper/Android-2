package com.example.weather.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.weather.DataHandler;
import com.example.weather.Parcel;
import com.example.weather.R;

public class RecyclerAdapterDay extends RecyclerView.Adapter<RecyclerAdapterDay.RecyclerViewHolder> {
    private String[] day;
    private int[] weather;

    public RecyclerAdapterDay(Parcel data){
        this.day = data.day;
        this.weather = data.weather_image_collection;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_day, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        int tempRandomDay = (new DataHandler()).generateNumber(-30, 30);
        String tempDay = ((tempRandomDay + 3) > 0 ? "+" : "") + (tempRandomDay + 3) + "\u00B0";
        String tempNight = ((tempRandomDay - 3) > 0 ? "+" : "") + (tempRandomDay - 3) + "\u00B0";
        String tempAverage = tempDay + " / " + tempNight;

        holder.setData(tempAverage, position, day, weather);
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView dayContainer;
        private ImageView weatherContainer;
        private TextView tempContainer;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            dayContainer = itemView.findViewById(R.id.recycler_item_day_day);
            weatherContainer = itemView.findViewById(R.id.recycler_item_day_weather);
            tempContainer = itemView.findViewById(R.id.recycler_item_day_temp);
        }

        void setData(String temp, int position, String[] day, int[] weather)
        {
            tempContainer.setText(temp);
            dayContainer.setText(day[position]);
            weatherContainer.setImageResource(weather[position]);
        }
    }
}
