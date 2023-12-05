package com.example.hikingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class ObservationEditPage extends AppCompatActivity {
    private Button FindUpdatebtn, FindGetDate;

    private int yearCurrent, monthCurrent,dayCurrent;
    private EditText FindObservationNameUpdate,FindObservationCommentUpdate;
    private TextView FindObservationDateUpdate;
    private ArrayList<ObservationModal> ObservationModalArrayList;
    private DBHandler dbHandler;
    private String GetObservationName,GetObservationDate,GetObservationComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation_edit_page);

        String GetIdObservation = getIntent().getStringExtra("id_observation");

        Calendar calendar = Calendar.getInstance();
        yearCurrent = calendar.get(Calendar.YEAR);
        monthCurrent = calendar.get(Calendar.MONTH) + 1;
        dayCurrent = calendar.get(Calendar.DAY_OF_MONTH);

        FindUpdatebtn = findViewById(R.id.UpDateObservation);
        FindGetDate = findViewById(R.id.GetDateObservationEditPage);
        FindObservationNameUpdate = findViewById(R.id.GetObservationNameUpdate);
        FindObservationCommentUpdate = findViewById(R.id.GetObservationCommentUpdate);
        FindObservationDateUpdate = findViewById(R.id.GetObservationDateUpdate);

        FindGetDate.setOnClickListener(view -> {
            OpenCalendar();
        });

        ObservationModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(ObservationEditPage.this);
        ObservationModalArrayList = dbHandler.readObservationDetail(GetIdObservation);
        ObservationModal ObservationModal = ObservationModalArrayList.get(0);

        GetObservationName = ObservationModal.getName();
        GetObservationDate = ObservationModal.getDate();
        GetObservationComment = ObservationModal.getComment();
        String GetHikeId = String.valueOf(ObservationModal.getId_Hike()) ;

        FindObservationNameUpdate.setText(GetObservationName);
        FindObservationCommentUpdate.setText(GetObservationComment);
        FindObservationDateUpdate.setText(GetObservationDate);

        FindUpdatebtn.setOnClickListener(view -> {
            boolean checkValidate = validateObservationInput(FindObservationNameUpdate.getText().toString());
            if(checkValidate == true){
                dbHandler.updateObservation(
                        GetIdObservation,
                        FindObservationNameUpdate.getText().toString(),
                        FindObservationDateUpdate.getText().toString(),
                        FindObservationCommentUpdate.getText().toString());
                Intent i = new Intent(ObservationEditPage.this, ShowObservationDetail.class);

                i.putExtra("id_observation",GetIdObservation);
                i.putExtra("id_hike",GetHikeId);
                // starting our activity.
                startActivity(i);
            }
        });
    }
    private boolean validateObservationInput(String Name){
        boolean error = true;
        if (Name.isEmpty()){
            FindObservationNameUpdate.setError("Name can't be empty");
            error = false;
        }
        return  error;
    }
    private void OpenCalendar(){
        DatePickerDialog dialog= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                FindObservationDateUpdate.setText(String.valueOf(year)+"/"+String.valueOf(month)+"/"+String.valueOf(day));
            }
        },yearCurrent, monthCurrent, dayCurrent);
        //Show Calendar
        dialog.show();
    }
}