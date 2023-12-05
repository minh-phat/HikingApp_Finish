package com.example.hikingapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements Filterable {
    // variable for our array list and context
    private ArrayList<HikeModel> HikeModalArrayList ,HikeModalArrayListOld;
    private Context context;
    private DBHandler dbHandler;
    // constructor
    public Adapter(ArrayList<HikeModel> HikeModalArrayList, Context context)
    {
        this.HikeModalArrayListOld = HikeModalArrayList;
        this.HikeModalArrayList = HikeModalArrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        dbHandler = new DBHandler(context);

        // on below line we are inflating our layout
        // file for our recycler view items.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hike_rv_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // on below line we are setting data
        // to our views of recycler view item.
        HikeModel modal = HikeModalArrayList.get(position);
        holder.NameTV.setText(modal.getName());
        holder.LocationTV.setText(modal.getLocation());
        holder.DateTV.setText(modal.getDate());
        holder.LevelTV.setText(modal.getLevel());
        holder.LengthTV.setText(String.valueOf(modal.getLength()));
        holder.DescriptionTV.setText(modal.getDescription());
        holder.ParticipaintsTV.setText(String.valueOf(modal.getParticipaints()));
        // below line is to add on click listener for our recycler view item.
        holder.EditRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are calling an intent.
                Intent i = new Intent(context, EditHikePage.class);

                // below we are passing all our values.
                i.putExtra("name", modal.getName());
                i.putExtra("location", modal.getLocation());
                i.putExtra("date", modal.getDate());
                i.putExtra("level", modal.getLevel());
                //if have integer variable , use String.valueOf to convert it to String;
                i.putExtra("length", String.valueOf(modal.getLength()));
                i.putExtra("description", modal.getDescription());
                i.putExtra("participaints", String.valueOf(modal.getParticipaints()));
                i.putExtra("id_hike", String.valueOf(modal.getId()));
                i.putExtra("parking", modal.getParking());
                i.putExtra("carryitem", modal.getCarryItem());
                // starting our activity.
                context.startActivity(i);
            }
        });
        holder.Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are calling an intent.
                Intent i = new Intent(context, DetailHiking.class);

                i.putExtra("id_hike", String.valueOf(modal.getId()));
                // below we are passing all our values.
                i.putExtra("name", modal.getName());
                i.putExtra("location", modal.getLocation());
                i.putExtra("date", modal.getDate());
                i.putExtra("level", modal.getLevel());
                //if have integer variable , use String.valueOf to convert it to String;
                i.putExtra("length", String.valueOf(modal.getLength()));
                i.putExtra("description", modal.getDescription());
                i.putExtra("participaints", String.valueOf(modal.getParticipaints()));
                i.putExtra("parking", modal.getParking());
                i.putExtra("carryitem", modal.getCarryItem());
                Log.d("Tag", "231");
                // starting our activity.
                context.startActivity(i);
            }
        });
        holder.DeleteRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo đối tượng AlertDialog.Builder
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Do you want to Delete");
                builder.setMessage("This will delete data table");


                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xử lý khi người dùng nhấn nút "Đồng ý"
                        // on below line we are calling an intent.
                        Intent i = new Intent(context, ShowHike.class);

                        //Delete data in database
                        dbHandler.deleteHike(String.valueOf(modal.getId()));
                        // starting our activity.
                        context.startActivity(i);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xử lý khi người dùng nhấn nút "Từ chối"
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
    @Override
    public int getItemCount() {
        // returning the size of our array list
        return HikeModalArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
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
    @Override
    public Filter getFilter() {
       return new Filter() {
           @Override
           protected FilterResults performFiltering(CharSequence charSequence) {
               String strSearch = charSequence.toString();
               if (strSearch.isEmpty()){
                   HikeModalArrayList = HikeModalArrayListOld;
               }else {
                   ArrayList<HikeModel> HikeFilter = new ArrayList<>();
                   for (HikeModel hikeModel : HikeModalArrayListOld){
                       if (hikeModel.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            HikeFilter.add(hikeModel);
                       }
                   }
                   HikeModalArrayList = HikeFilter;
               }
               FilterResults filterResults = new FilterResults();
               filterResults.values = HikeModalArrayList;
               return filterResults;
           }
           @Override
           protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
               HikeModalArrayList = (ArrayList<HikeModel>) filterResults.values;
               notifyDataSetChanged();
           }
       };
    }
}
