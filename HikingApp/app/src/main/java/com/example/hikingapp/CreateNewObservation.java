package com.example.hikingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class CreateNewObservation extends AppCompatActivity {
    private int yearCurrent, monthCurrent,dayCurrent;
    private Button GetDate, Savebtn;
    private TextView TViewDate, ETName, FindETComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_observation);

        Calendar calendar = Calendar.getInstance();
        yearCurrent = calendar.get(Calendar.YEAR);
        monthCurrent = calendar.get(Calendar.MONTH) + 1;
        dayCurrent = calendar.get(Calendar.DAY_OF_MONTH);

        GetDate = findViewById(R.id.GetDate);
        TViewDate = findViewById(R.id.idTVDate);
        Savebtn = findViewById(R.id.SaveObservation);
        ETName = findViewById(R.id.eTextNameObservation);
        FindETComment = findViewById(R.id.ETComment);

        TViewDate.setText(String.valueOf(yearCurrent)+"/"+String.valueOf(monthCurrent)+"/"+String.valueOf(dayCurrent));

        GetDate.setOnClickListener(view -> {
            OpenCalendar();
        });

        DBHandler dbHandler = new DBHandler(CreateNewObservation.this);

        Savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // below line is to get data from all edit text fields.
                String GetName = ETName.getText().toString();
                String GetComment = FindETComment.getText().toString();
                String GetDate = TViewDate.getText().toString();
                String GetID = getIntent().getStringExtra("id_hike");
                boolean checkValidate = validateObservationInput(GetName);
                if(checkValidate == true){
                    dbHandler.addNewObservation(GetID,GetName, GetDate,GetComment);

                    Intent i = new Intent(CreateNewObservation.this, DetailHiking.class);

                    String Id = getIntent().getStringExtra("id_hike");

                    i.putExtra("id_hike", Id);

                    startActivity(i);
                }
            }
        });
    }
    private boolean validateObservationInput(String Name){
        boolean error = true;
        if (Name.isEmpty()){
            ETName.setError("Name can't be empty");
            error = false;
        }
        return  error;
    }

    private void OpenCalendar(){
        DatePickerDialog dialog= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                TViewDate.setText(String.valueOf(year)+"/"+String.valueOf(month)+"/"+String.valueOf(day));
            }
        },yearCurrent, monthCurrent, dayCurrent);
        //Show Calendar
        dialog.show();
    }
}