package com.example.hikingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailHiking extends AppCompatActivity {
    private Button AddNewObservation;
    private TextView Name,Location, Date, Length,  Level, Parking, Participaints,
                    Description, CarryItem;
    private ArrayList<ObservationModal> ObservationModalArrayList;
    private ArrayList<HikeModel> HikeModalArrayList;
    private  DBHandler dbHandler;
    private ObservationAdapter ObservationAdapter;
    private RecyclerView ObservationRV;
    private RelativeLayout HikingEdit, HikingDelete;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_hiking);

        dbHandler = new DBHandler(DetailHiking.this);

        AddNewObservation = findViewById(R.id.AddObservation);
        Name = findViewById(R.id.Name);
        Location = findViewById(R.id.Location);
        Date = findViewById(R.id.Date);
        Length = findViewById(R.id.Length);
        Level = findViewById(R.id.Level);
        Parking = findViewById(R.id.Parking);
        Participaints = findViewById(R.id.Participaints);
        Description = findViewById(R.id.Description);
        CarryItem = findViewById(R.id.Items);
        HikingEdit = findViewById(R.id.DetailHikingEditbtn);
        HikingDelete = findViewById(R.id.DetailHikingDeletebtn);

        String GetId = getIntent().getStringExtra("id_hike");

        HikeModalArrayList = new ArrayList<>();
        HikeModalArrayList = dbHandler.readHikeDetail(GetId);
        HikeModel HikeDetailModal = HikeModalArrayList.get(0);
        String GetName = HikeDetailModal.getName();
        String GetLocation = HikeDetailModal.getLocation();
        String GetDate = HikeDetailModal.getDate();
        String GetLength = String.valueOf(HikeDetailModal.getLength());
        String GetLevel = HikeDetailModal.getLevel();
        String GetDescription = HikeDetailModal.getDescription();
        String GetParticipaints = String.valueOf(HikeDetailModal.getParticipaints());
        String GetParking = HikeDetailModal.getParking();
        String GetCarryItem = HikeDetailModal.getCarryItem();

        Name.setText(GetName);
        Location.setText(GetLocation);
        Date.setText(GetDate);
        Length.setText(GetLength);
        Level.setText(GetLevel);
        Description.setText(GetDescription);
        Participaints.setText(GetParticipaints);
        Parking.setText(GetParking);
        CarryItem.setText(GetCarryItem);

        searchView = findViewById(R.id.ObservationSearch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Do something when the user submits the search query.
                ObservationAdapter.getFilter().filter(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                // Do something whenever the user changes the search query.
                ObservationAdapter.getFilter().filter(newText);
                Log.d("Search", newText);
                return true;
            }
        });
        HikingEdit.setOnClickListener(view -> {
            Intent i = new Intent(this, EditHikePage.class);
            i.putExtra("id_hike",GetId);

            startActivity(i);
        });
        HikingDelete.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Do you want to Delete");
            builder.setMessage("This will delete data table");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Xử lý khi người dùng nhấn nút "Đồng ý"
                    // on below line we are calling an intent.
                    Intent i = new Intent(DetailHiking.this, ShowHike.class);

                    //Delete data in database
                    dbHandler.deleteHike(GetId);
                    // starting our activity.
                    startActivity(i);
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
        AddNewObservation.setOnClickListener(view -> {
            Intent i = new Intent(this, CreateNewObservation.class);
            i.putExtra("id_hike",GetId);

            startActivity(i);
        });
        //<<<<<<<<<<<<<<<<<< Create Recycle view for Observation
        // initializing our all variables.
        ObservationModalArrayList = new ArrayList<>();

        // getting our observation array
        // list from db handler class.
        ObservationModalArrayList = dbHandler.readObservation(GetId);

        // on below line passing our array list to our adapter class.
        ObservationAdapter = new ObservationAdapter(ObservationModalArrayList, DetailHiking.this);
        ObservationRV = findViewById(R.id.ShowObserve);

        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailHiking.this, RecyclerView.VERTICAL, false);
        ObservationRV.setLayoutManager(linearLayoutManager);

        // setting our adapter to recycler view.
        ObservationRV.setAdapter(ObservationAdapter);
        // Create Recycle view for Observation >>>>>>>>>>>>>>>>>>>>>>>>>>>>
    }
}