package com.example.hikingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;

public class CreateNewTracking extends AppCompatActivity  {

    private EditText ETextName, ETextLocation, ETextLength, ETextDescription, ETextCarryItem;
    private Button GetDate_btn, Save_btn;
    private TextView TViewDate;
    public int yearCurrent, monthCurrent,dayCurrent;
    private Spinner spinner;
    private String GetName,GetLocation ,Level , ParkingAvailable,NumberParticipantsStr;
    private Integer NumberParticipants,GetLength;
    private DBHandler dbHandler;
    private RadioGroup Parking_available;
    private RadioButton Yesbtn,Nobtn, radioButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_tracking);

        Calendar calendar = Calendar.getInstance();
        yearCurrent = calendar.get(Calendar.YEAR);
        monthCurrent = calendar.get(Calendar.MONTH) + 1;
        dayCurrent = calendar.get(Calendar.DAY_OF_MONTH);

        TViewDate = findViewById(R.id.textViewDate);
        GetDate_btn = findViewById(R.id.GetDate);
        spinner = (Spinner) findViewById(R.id.spinnerLevel);
        ETextName = findViewById(R.id.eTextName);
        ETextLocation = findViewById(R.id.eTextLocation);
        ETextDescription = findViewById(R.id.editTextDescription);
        ETextLength = findViewById(R.id.eTextLength);
        Save_btn = findViewById(R.id.Savebtn);
        Parking_available = findViewById(R.id.ParkingGroup);
        ETextCarryItem = findViewById(R.id.ETCarryItem);

        ParkingAvailable = null;

        TViewDate.setText(String.valueOf(yearCurrent)+"/"+String.valueOf(monthCurrent)+"/"+String.valueOf(dayCurrent));
        GetDate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenCalendar();
            }
        });

        CreateLevelSpinner();
        CreateNumberParticipantsSpinner();

        // creating a new dbhandler class
        // and passing our context to it.
        dbHandler = new DBHandler(CreateNewTracking.this);
        Save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Get Data
                // below line is to get data from all edit text fields.
                GetName = ETextName.getText().toString();
                GetLocation = ETextLocation.getText().toString();
                String GetDate = TViewDate.getText().toString();

                //Convert from String To integer
                String GetLengthSt = ETextLength.getText().toString();
                GetLength = null;
                if (!GetLengthSt.isEmpty()) {
                    GetLength = Integer.parseInt(GetLengthSt);
                } else {

                }
                String GetLevel = Level;
                String GetDescription = ETextDescription.getText().toString();
                String GetCarryItem = ETextCarryItem.getText().toString();
                Integer GetNumberParticipants =  NumberParticipants;

                int radioId = Parking_available.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
                //Check if radioGroup is clicked
                if (radioId != -1) {
                    //Parking_available.getCheckedRadioButtonId() is different from -1 , it return radio group is clicked
                    ParkingAvailable = radioButton.getText().toString();
                }
                //Get Data>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                boolean checkValidate = validateHikeInput(
                        GetName,
                        GetLocation,
                        GetDate,
                        GetLengthSt,
                        Level,
                        GetCarryItem,
                        NumberParticipantsStr,
                        ParkingAvailable);
                if (checkValidate == true){
                    dbHandler.addNewHike(GetName, GetLocation, GetDate,
                            GetLength,
                            GetLevel,
                            GetDescription,
                            GetCarryItem,
                            GetNumberParticipants,
                            ParkingAvailable
                    );
                    Intent i = new Intent(CreateNewTracking.this, ShowHike.class);
                    startActivity(i);
                }
            }
        });
    }
    private boolean validateHikeInput(String Name, String Location, String Date, String Length , String Level ,
                                      String CarryItem , String Participaints, String Parking){
        boolean error = true;
        if (Name.isEmpty()){
            ETextName.setError("Name can't be empty");
            error = false;
        }
        if (Location.isEmpty()){
            ETextLocation.setError("Location can't be empty");
            error = false;
        }
        if (Date.isEmpty()){
            TViewDate.setError("Date can't be empty");
            error = false;
        }
        if (Length.isEmpty()){
            ETextLength.setError("Length can't be empty");
            error = false;
        }
        if(!Length.matches("^[0-9]+$")){
            ETextLength.setError("Length can only be numbers");
            error = false;
        }
        if (Level.isEmpty()){
            ETextLocation.setError("Level can't be empty");
            error = false;
        }
        if (CarryItem.isEmpty()){
            ETextCarryItem.setError("Carry Item can't be empty");
            error = false;
        }
        if (Participaints.isEmpty()){
            ETextLocation.setError("Participaints can't be empty");
            error = false;
        }
        if (Parking == null){
            Toast.makeText(this, "Parking avaliable can't be empty",
                    Toast.LENGTH_SHORT).show();
            error = false;
        }
        return  error;
    }
    private void CreateLevelSpinner(){

        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.Level_array,
                android.R.layout.simple_spinner_item
        );
        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                //pos that mean position of variable in spinner
                //Get value of spinner
                if (pos >= 0) {
                    // An item is selected. You can retrieve the selected item using
                    // parent.getItemAtPosition(pos).
                    Level = adapterView.getItemAtPosition(pos).toString();
                    Toast.makeText(adapterView.getContext(), Level, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void CreateNumberParticipantsSpinner(){
        List<String> spinnerItems = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            spinnerItems.add(String.valueOf(i));
        }
        // Create a SpinnerAdapter to bind the list of strings to the spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerItems);
        // Set the adapter for the spinner
        Spinner spinner = findViewById(R.id.NumberParticipantsSpinner);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                //pos that mean position of variable in spinner
                //Get value of spinner
                if (pos >= 0) {
                    // An item is selected. You can retrieve the selected item using
                    // parent.getItemAtPosition(pos).
                    NumberParticipantsStr = adapterView.getItemAtPosition(pos).toString();
                    NumberParticipants = Integer.parseInt(NumberParticipantsStr);
//                    Toast.makeText(adapterView.getContext(), NumberParticipants, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
    public void CheckButton(View view) {
        int radioId = Parking_available.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);

    }
}