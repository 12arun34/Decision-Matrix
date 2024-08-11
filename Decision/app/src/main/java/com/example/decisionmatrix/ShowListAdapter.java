package com.example.decisionmatrix;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ShowListAdapter extends RecyclerView.Adapter<ShowListAdapter.ViewHolder> {
    private List<String> stringList;

    public ShowListAdapter(List<String> stringList) {
        this.stringList = stringList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String string = stringList.get(position);
        if(position==0){
            holder.textView.setText(string+" (1st Priority)");
        }
        else if(position==1){
            holder.textView.setText(string+" (2nd Priority)");
        }
        else{
            holder.textView.setText(string);
        }

    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
            textView.setTextSize(20);
//            textView.setTextColor(Color.parseColor("#8692f7"));
        }
    }
}
