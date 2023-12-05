package com.example.hikingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;

public class ShowHike extends AppCompatActivity {

    // creating variables for our array list,
    // dbhandler, adapter and recycler view.
    private ArrayList<HikeModel> HikeModalArrayList;
    private DBHandler dbHandler;
    private Adapter Adapter;
    private RecyclerView HikeRV;
    private RelativeLayout Edit;
    private  SearchView searchView;
    private EditText FindHikeSearch;
    private Button CreateNewTracking,ResetHike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_hike);

        CreateNewTracking = findViewById(R.id.CreateNewTracking_btn);
        ResetHike = findViewById(R.id.ResetHike_btn);

        // initializing our all variables.
        HikeModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(ShowHike.this);

        // getting our course array
        // list from db handler class.
        HikeModalArrayList = dbHandler.readHike();
        // on below line passing our array list to our adapter class.
        Adapter = new Adapter(HikeModalArrayList, ShowHike.this);
        HikeRV = findViewById(R.id.idRVHike);

        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ShowHike.this, RecyclerView.VERTICAL, false);
        HikeRV.setLayoutManager(linearLayoutManager);

        // setting our adapter to recycler view.
        HikeRV.setAdapter(Adapter);

        searchView = findViewById(R.id.HikeSearch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Do something when the user submits the search query.
                Adapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Do something whenever the user changes the search query.
                Adapter.getFilter().filter(newText);
                Log.d("Search", newText);
                return true;
            }
        });

        CreateNewTracking.setOnClickListener(view -> {
            Intent i = new Intent(ShowHike.this, CreateNewTracking.class);
            startActivity(i);
        });
        ResetHike.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Do you want to reset data");
            builder.setMessage("This will reset data table");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dbHandler.resetHike();
                    Intent i = new Intent(ShowHike.this, ShowHike.class);
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
    }
}