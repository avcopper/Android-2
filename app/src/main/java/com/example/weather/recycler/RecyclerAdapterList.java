package com.example.weather.recycler;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.HistoryActivity;
import com.example.weather.R;
import com.example.weather.ui.history.HistoryFragment;

import java.util.ArrayList;

public class RecyclerAdapterList extends RecyclerView.Adapter<RecyclerAdapterList.ViewHolder> {
    private ArrayList<Integer> data;
    private HistoryActivity activity;

    public RecyclerAdapterList(ArrayList<Integer> data, HistoryActivity activity) {
        this.activity = activity;
        if(data != null) {
            this.data = data;
        } else {
            this.data = new ArrayList<>();
        }
    }

    public void addItem() {
        int num = data.size() + 1;
        data.add(num);
        notifyItemInserted(data.size() - 1);
    }

    public void editItem(int newNum) {
        if(data.size() > 0) {
            int latestElement = data.size() - 1;
            data.set(latestElement, newNum);
            notifyItemChanged(latestElement);
        }
    }

    public void removeElement() {
        if(data.size() > 0) {
            int latestElement = data.size() - 1;
            data.remove(latestElement);
            notifyItemRemoved(latestElement);
        }
    }

    public void clearList() {
        data.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item_list,
                viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String text = activity.getString(R.string.element_number) + data.get(position);
        holder.textView.setText(text);
        activity.registerForContextMenu(holder.textView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
