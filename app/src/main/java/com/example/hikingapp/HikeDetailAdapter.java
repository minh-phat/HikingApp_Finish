package com.example.hikingapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HikeDetailAdapter extends RecyclerView.Adapter<HikeDetailAdapter.ViewHolder>{
    private ArrayList<HikeModel> HikeModalArrayList;
    private Context context;
    private DBHandler dbHandler;

    // constructor
    public HikeDetailAdapter(ArrayList<HikeModel> HikeModalArrayList, Context context) {
        this.HikeModalArrayList = HikeModalArrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView NameTV, LocationTV, DateTV, LevelTV, LengthTV, DescriptionTV, ParticipaintsTV;
        private RelativeLayout EditRL, Layout, DeleteRL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            NameTV = itemView.findViewById(R.id.idTVName);
            LocationTV = itemView.findViewById(R.id.idTVLocation);
            DateTV = itemView.findViewById(R.id.idTVDate);
            LevelTV = itemView.findViewById(R.id.idTVLevel);
            LengthTV = itemView.findViewById(R.id.idTVLength);
            DescriptionTV = itemView.findViewById(R.id.idTVDescription);
            ParticipaintsTV = itemView.findViewById(R.id.idTVParticipaints);
            EditRL = itemView.findViewById(R.id.Editbtn);
            DeleteRL = itemView.findViewById(R.id.Deletebtn);
            Layout = itemView.findViewById(R.id.layout);
        }
    }
}
