package com.example.trackingappliaction2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class carInfoAdapter extends RecyclerView.Adapter<carInfoAdapter.ViewHolder>{

    private ArrayList <busmodel> busList;
    private Context context;

    public carInfoAdapter(ArrayList<busmodel> busList, Context context) {
        this.busList = busList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //inlate layout of car item
        View view= LayoutInflater.from(context).inflate(R.layout.activity_buslayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        busmodel busmodel=busList.get(position);
        holder.name.setText(busmodel.getBusname());
       // holder.location.setText(busmodel.getRealaddress());
    }

    @Override
    public int getItemCount() {
        return busList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //private ImageView carImageview;
        private TextView name,location,open,start;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            name=itemView.findViewById(R.id.txtName);
            location=itemView.findViewById(R.id.txtLocation);
            open=itemView.findViewById(R.id.txtopen);
            start=itemView.findViewById(R.id.txtStart);
        }
    }
}
