package com.example.hikingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class EditHikePage extends AppCompatActivity {

    private EditText ETextName, ETextLocation, ETextLength, ETextDescription, ETextCarryItem;
    private Button GetDate_btn, Update_btn;
    private TextView TViewDate;
    public int yearCurrent, monthCurrent,dayCurrent;
    private Spinner spinner;
    private Integer NumberParticipants;
    private DBHandler dbHandler;
    private String Name,Location, Date, Length, Level , Description,CarryItem, Participaints, Id,Parking;
    private RadioGroup Parking_available;
    private RadioButton radioButton, yesbtn, nobtn;
    private ArrayList<HikeModel> HikeModalArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hike_page);

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
        Update_btn = findViewById(R.id.Updatebtn);
        Parking_available = findViewById(R.id.ParkingGroup);
        yesbtn = findViewById(R.id.YesButton);
        nobtn = findViewById(R.id.NoButton);
        ETextCarryItem = findViewById(R.id.ETCarryItem);

        Id = getIntent().getStringExtra("id_hike");

        HikeModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(EditHikePage.this);
        HikeModalArrayList = dbHandler.readHikeDetail(Id);
        HikeModel HikeDetailModal = HikeModalArrayList.get(0);
        String Name = HikeDetailModal.getName();
        String Location = HikeDetailModal.getLocation();
        String Date = HikeDetailModal.getDate();
        String Length = String.valueOf(HikeDetailModal.getLength());
        String Level = HikeDetailModal.getLevel();
        String Description = HikeDetailModal.getDescription();
        Integer Participaints = HikeDetailModal.getParticipaints();
        String Parking = HikeDetailModal.getParking();
        String CarryItem = HikeDetailModal.getCarryItem();

        if (Parking.equals("Yes")){
            yesbtn.setChecked(true);
        }else {nobtn.setChecked(true);}

        ETextName.setText(Name);
        ETextLocation.setText(Location);
        TViewDate.setText(Date);
        ETextLength.setText(Length);
        ETextDescription.setText(Description);
        ETextCarryItem.setText(CarryItem);

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

        Update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int radioId = Parking_available.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);

                String ParkingAvailable = radioButton.getText().toString();

                boolean checkValidate = validateHikeInput(
                        ETextName.getText().toString(),
                        ETextLocation.getText().toString(),
                        TViewDate.getText().toString(),
                        ETextLength.getText().toString(),
                        Level,
                        ETextCarryItem.getText().toString());
                if(checkValidate == true){
                    dbHandler.updateHike(Id ,ETextName.getText().toString(),
                            ETextLocation.getText().toString(), TViewDate.getText().toString(),
                            ETextLength.getText().toString(), Level, ETextDescription.getText().toString(),
                            ETextCarryItem.getText().toString(),Participaints,
                            ParkingAvailable);

                    Intent i = new Intent(EditHikePage.this, ShowHike.class);
                    startActivity(i);
                }
            }
        });
    }
    private boolean validateHikeInput(String Name, String Location, String Date, String Length , String Level ,
                                      String CarryItem ){
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
            TViewDate.setError("Location can't be empty");
            error = false;
        }
        if (Length.isEmpty()){
            ETextLength.setError("Location can't be empty");
            error = false;
        }
        if (Level.isEmpty()){
            ETextLocation.setError("Location can't be empty");
            error = false;
        }
        if (CarryItem.isEmpty()){
            ETextCarryItem.setError("Location can't be empty");
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
        //Set default value in spinner
        int spinnerPosition = adapter.getPosition(Level);
        spinner.setSelection(spinnerPosition);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                //pos that mean position of variable in spinner
                //Get value of spinner
                if (pos >= 0) {
                    // An item is selected. You can retrieve the selected item using
                    // parent.getItemAtPosition(pos).
                    Level = adapterView.getItemAtPosition(pos).toString();
//                    Toast.makeText(adapterView.getContext(), Level, Toast.LENGTH_SHORT).show();

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

        //Set default value in spinner
        int spinnerPosition = spinnerAdapter.getPosition(Participaints);
        spinner.setSelection(spinnerPosition);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                //pos that mean position of variable in spinner
                //Get value of spinner
                if (pos >= 0) {
                    // An item is selected. You can retrieve the selected item using
                    // parent.getItemAtPosition(pos).
                    String NumberParticipantsStr = adapterView.getItemAtPosition(pos).toString();
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