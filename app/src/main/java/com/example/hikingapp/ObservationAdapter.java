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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ObservationAdapter extends RecyclerView.Adapter<ObservationAdapter.ViewHolder> implements Filterable {
    private ArrayList<ObservationModal> ObservationModalArrayList, ObservationModalArrayListOld;
    private Context context;
    private DBHandler dbHandler;

    public ObservationAdapter(ArrayList<ObservationModal> ObservationModalArrayList, Context context) {
        this.ObservationModalArrayList = ObservationModalArrayList;
        this.ObservationModalArrayListOld = ObservationModalArrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public ObservationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        dbHandler = new DBHandler(context);

        // on below line we are inflating our layout
        // file for our recycler view items.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.observation_rv_item, parent, false);
        return new ObservationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ObservationAdapter.ViewHolder holder, int position) {
        ObservationModal modal = ObservationModalArrayList.get(position);
        holder.ObservationName.setText(modal.getName());
        holder.ObservationDate.setText(modal.getDate());

        holder.ShowObservationDetail.setOnClickListener(view -> {
            Intent i = new Intent(context, ShowObservationDetail.class);
            i.putExtra("id_observation",String.valueOf(modal.getId_Observation()));
            Log.d("tag",String.valueOf(modal.getId_Observation()));
            context.startActivity(i);
        });
        holder.ObservationEditbtn.setOnClickListener(view -> {
            Intent i = new Intent(context, ObservationEditPage.class);
            i.putExtra("id_observation",String.valueOf(modal.getId_Observation()));
            context.startActivity(i);
        });
        holder.ObservationDeletebtn.setOnClickListener(view -> {
            // Tạo đối tượng AlertDialog.Builder
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("Do you want to Delete");
            builder.setMessage("This will delete data table");


            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Xử lý khi người dùng nhấn nút "Đồng ý"
                    // on below line we are calling an intent.
                    //Delete data in database
                    dbHandler.deleteObservation(String.valueOf(modal.getId_Observation()));
                    Intent i = new Intent(context, DetailHiking.class);
                    i.putExtra("id_hike",String.valueOf(modal.getId_Hike()));
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

        });

    }

    @Override
    public int getItemCount() {
        return ObservationModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView ObservationName,ObservationDate;
        private RelativeLayout ObservationEditbtn, ObservationDeletebtn, ShowObservationDetail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            ObservationName = itemView.findViewById(R.id.idTVNameObservation);
            ObservationDate = itemView.findViewById(R.id.idTVDateObservation);
            ObservationEditbtn = itemView.findViewById(R.id.EditObservationbtn);
            ObservationDeletebtn = itemView.findViewById(R.id.DeleteObservationbtn);
            ShowObservationDetail = itemView.findViewById(R.id.RLObservationDetail);
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()){
                    ObservationModalArrayList = ObservationModalArrayListOld;
                }else {
                    ArrayList<ObservationModal> HikeFilter = new ArrayList<>();
                    for (ObservationModal ObservationModal : ObservationModalArrayListOld){
                        if (ObservationModal.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            HikeFilter.add(ObservationModal);
                        }
                    }
                    ObservationModalArrayList = HikeFilter;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = ObservationModalArrayList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                ObservationModalArrayList = (ArrayList<ObservationModal>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
