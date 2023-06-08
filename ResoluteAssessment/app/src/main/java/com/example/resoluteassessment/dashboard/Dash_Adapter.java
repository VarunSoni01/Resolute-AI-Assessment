package com.example.resoluteassessment.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resoluteassessment.R;

import java.util.List;

public class Dash_Adapter extends RecyclerView.Adapter<Dash_Adapter.ViewHolder> {
    Context context;
    List<Dash_Model> list;

    public Dash_Adapter(Context context, List<Dash_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Dash_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_rows,parent,false);
        return new Dash_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Dash_Adapter.ViewHolder holder, int position) {
        holder.data.setText(list.get(position).getData());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView data;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            data = itemView.findViewById(R.id.res_row);
        }
    }
}
