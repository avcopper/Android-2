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

public class RecyclerAdapterTime extends RecyclerView.Adapter<RecyclerAdapterTime.RecyclerViewHolder> {
    private String[] time;
    private int[] weather;

    public RecyclerAdapterTime(Parcel data){
        this.time = data.time;
        this.weather = data.weather_image_collection;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_time, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        int tempRandomTime;
        String[] tempCollection = new String[6];
        DataHandler dataHandler = new DataHandler();

        for (int i = 0; i < 6; i++) {
            tempRandomTime = dataHandler.generateNumber(-2, 2);
            tempCollection[i] = (-11 + tempRandomTime) + "\u00B0";
        }

        holder.setData(position, tempCollection, time, weather);
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView timeContainer;
        private ImageView weatherContainer;
        private TextView tempContainer;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            timeContainer = itemView.findViewById(R.id.recycler_item_time_time);
            weatherContainer = itemView.findViewById(R.id.recycler_item_time_weather);
            tempContainer = itemView.findViewById(R.id.recycler_item_time_temp);
        }

        void setData(int position, String[] tempCollection, String[] time, int[] weather)
        {
            tempContainer.setText(tempCollection[position]);
            timeContainer.setText(time[position]);
            weatherContainer.setImageResource(weather[position + 6]);
        }
    }
}
