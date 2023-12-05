package com.example.hikingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class ShowObservationDetail extends AppCompatActivity {

    private DBHandler dbHandler;
    private ArrayList<ObservationModal> ObservationModalArrayList;
    private TextView FindObservationName, FindObservationDate, FindObservationComment;
    private RelativeLayout FindEditbtn,FindDeletebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_observation_detail);

        String GetIdObservation = getIntent().getStringExtra("id_observation");

        FindObservationName = findViewById(R.id.ObservationNameDetail);
        FindObservationDate = findViewById(R.id.ObservationDateDetail);
        FindObservationComment = findViewById(R.id.ObservationCommentDetail);
        FindEditbtn = findViewById(R.id.DetailObservationEditbtn);
        FindDeletebtn = findViewById(R.id.DetailObservationDeletebtn);

        ObservationModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(ShowObservationDetail.this);
        ObservationModalArrayList = dbHandler.readObservationDetail(GetIdObservation);
        ObservationModal ObservationModal = ObservationModalArrayList.get(0);

        String GetObservationName = ObservationModal.getName();
        String GetObservationDate = ObservationModal.getDate();
        String GetObservationComment = ObservationModal.getComment();
        String GetObservationIdHike = String.valueOf(ObservationModal.getId_Hike());

        FindObservationName.setText(GetObservationName);
        FindObservationDate.setText(GetObservationDate);
        FindObservationComment.setText(GetObservationComment);

        FindEditbtn.setOnClickListener(view -> {
            Intent i = new Intent(ShowObservationDetail.this, ObservationEditPage.class);

            i.putExtra("id_observation",GetIdObservation);
            // starting our activity.
            startActivity(i);
        });
        FindDeletebtn.setOnClickListener(view -> {
            // Tạo đối tượng AlertDialog.Builder
            AlertDialog.Builder builder = new AlertDialog.Builder(ShowObservationDetail.this);

            builder.setTitle("Do you want to Delete");
            builder.setMessage("This will delete data table");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Xử lý khi người dùng nhấn nút "Đồng ý"
                    // on below line we are calling an intent.
                    dbHandler.deleteObservation(GetIdObservation);
                    Intent i = new Intent(ShowObservationDetail.this, DetailHiking.class);
                    i.putExtra("id_hike",GetObservationIdHike);
                    //Delete data in database

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
    }
}