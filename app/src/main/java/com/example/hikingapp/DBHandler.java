package com.example.hikingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "hike";

    // below int is our database version
    private static final int DB_VERSION = 1;


    // below variable is for our table name.
    private static final String TABLE_NAME = "hike_infor";

    // below variable is for our id column.
    private static final String ID_HIKE = "hike_id";

    // below variable is for our course name column
    private static final String NAME_COL = "name";

    // below variable id for our course duration column.
    private static final String LOCATION_COL = "location";

    // below variable for our course description column.
    private static final String DATE_COL = "date";

    // below variable is for our course tracks column.
    private static final String LENGTH_COL = "length";
    private static final String LEVEL_COL = "level";
    private static final String  DESCRIPTION_COL = "description";
    private static final String  CARRYITEM_COL = "carry_items";
    private static final String  PARKINGAVAILABLE_COL = "parking_available";

    private static final String NUMBER_PARTICIPAINTS_COL = "participants";


    private static final String TABLE_OBSERVATION = "observation";
    private static final String ID_OBSERVATION = "id";
    private static final String NAME_OBSERVATION = "name";
    private static final String DATE_OBSERVATION = "date";
    private static final String COMMENT_OBSERVATION = "comment";
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_HIKE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT ,"
                + LOCATION_COL + " TEXT,"
                + DATE_COL + " TEXT,"
                + LENGTH_COL + " INTEGER,"
                + LEVEL_COL + " TEXT,"
                + DESCRIPTION_COL + " TEXT,"
                + CARRYITEM_COL + " TEXT,"
                + NUMBER_PARTICIPAINTS_COL + " INTEGER,"
                + PARKINGAVAILABLE_COL + " TEXT)";

        String queryObservation = "CREATE TABLE " + TABLE_OBSERVATION + " ("
                + ID_OBSERVATION + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ID_HIKE + " INTEGER,"
                + NAME_OBSERVATION + " TEXT,"
                + DATE_OBSERVATION + " TEXT,"
                + COMMENT_OBSERVATION + " TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
//        db.execSQL(query);
        db.execSQL(query);
        db.execSQL(queryObservation);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public ArrayList<HikeModel> readHike()
    {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to
        // read data from database.
        Cursor cursorHike
                = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY "+ ID_HIKE + " DESC", null);

        // on below line we are creating a new array list.
        ArrayList<HikeModel> HikeModalArrayList
                = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorHike.moveToFirst()) {
            do {
                // on below line we are adding the data from
                // cursor to our array list.
                HikeModalArrayList.add(new HikeModel(
                        cursorHike.getInt(0),
                        cursorHike.getString(1),
                        cursorHike.getString(2),
                        cursorHike.getString(3),
                        cursorHike.getInt(4),
                        cursorHike.getString(5),
                        cursorHike.getString(6),
                        cursorHike.getString(7),
                        cursorHike.getInt(8),
                        cursorHike.getString(9)


                ));
            } while (cursorHike.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorHike.close();
        return HikeModalArrayList;
    }

    public void addNewHike(String Name, String Location, String Date,
                           Integer Length,
                           String Level,
                           String Description,
                           String CarryItem,
                           Integer Number_Of_Participaints,
                           String ParkingAvailable
    ) {
        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_COL, Name);
        values.put(LOCATION_COL, Location);
        values.put(DATE_COL, Date);
        values.put(LENGTH_COL, Length);
        values.put(LEVEL_COL, Level);
        values.put(DESCRIPTION_COL,Description);
        values.put(CARRYITEM_COL, CarryItem);
        values.put(NUMBER_PARTICIPAINTS_COL, Number_Of_Participaints);
        values.put(PARKINGAVAILABLE_COL, ParkingAvailable);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }
    // we have created a new method for reading all the courses.
    public ArrayList<HikeModel> readHikeDetail(String Id)
    {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        String[] selectionArgs = { "1" }; // Replace 1 with the ID of the data you want to read.
        // on below line we are creating a cursor with query to
        // read data from database.
        Cursor cursorHike
                = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + ID_HIKE + " = " + Id, null);

        // on below line we are creating a new array list.
        ArrayList<HikeModel> HikeModalArrayList
                = new ArrayList<>();


                // on below line we are adding the data from
        if (cursorHike.moveToFirst()) {
            HikeModalArrayList.add(new HikeModel(
                    cursorHike.getInt(0),
                    cursorHike.getString(1),
                    cursorHike.getString(2),
                    cursorHike.getString(3),
                    cursorHike.getInt(4),
                    cursorHike.getString(5),
                    cursorHike.getString(6),
                    cursorHike.getString(7),
                    cursorHike.getInt(8),
                    cursorHike.getString(9)
            ));
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorHike.close();
        return HikeModalArrayList;
    }
    public void resetHike(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
    }
    public void updateHike(String Id ,String Name, String Location, String Date,
                             String Length, String Level, String Description,
                           String CarryItem,
                           Integer Participiants, String Parking) {

        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_COL, Name);
        values.put(LOCATION_COL, Location);
        values.put(DESCRIPTION_COL, Description);
        values.put(CARRYITEM_COL, CarryItem);
        values.put(DATE_COL, Date);
        values.put(LENGTH_COL, Length);
        values.put(LEVEL_COL, Level);
        values.put(NUMBER_PARTICIPAINTS_COL, Participiants);
        values.put(PARKINGAVAILABLE_COL, Parking);


        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        db.update(TABLE_NAME, values, "hike_id=?", new String[]{Id});
        db.close();
    }
    public void deleteHike(String Id) {

        // on below line we are creating
        // a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(TABLE_NAME, "hike_id=?", new String[]{Id});
        db.close();
    }
    public void addNewObservation(String Id_Hike,String Name, String Date, String Comment) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_OBSERVATION, Name);
        values.put(COMMENT_OBSERVATION, Comment);
        values.put(DATE_OBSERVATION, Date);
        values.put(ID_HIKE, Id_Hike);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_OBSERVATION, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }
    public ArrayList<ObservationModal> readObservation(String Id)
    {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to
        // read data from database.
        Cursor cursorHike
                = db.rawQuery("SELECT * FROM " + TABLE_OBSERVATION + " WHERE " + ID_HIKE + " = " + Id , null);

        // on below line we are creating a new array list.
        ArrayList<ObservationModal> ObservationModalArrayList
                = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorHike.moveToFirst()) {
            do {
                // on below line we are adding the data from
                // cursor to our array list.
                ObservationModalArrayList.add(new ObservationModal(
                        cursorHike.getInt(0),
                        cursorHike.getInt(1),
                        cursorHike.getString(2),
                        cursorHike.getString(3),
                        cursorHike.getString(4)
                ));
            } while (cursorHike.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorHike.close();
        return ObservationModalArrayList;
    }
    public ArrayList<ObservationModal> readObservationDetail(String Id)
    {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to
        // read data from database.
        Cursor cursorHike
                = db.rawQuery("SELECT * FROM " + TABLE_OBSERVATION + " WHERE " + ID_OBSERVATION + " = " + Id, null);

        // on below line we are creating a new array list.
        ArrayList<ObservationModal> ObservationModalArrayList
                = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorHike.moveToFirst()) {

            // on below line we are adding the data from
            // cursor to our array list.
            ObservationModalArrayList.add(new ObservationModal(
                    cursorHike.getInt(0),
                    cursorHike.getInt(1),
                    cursorHike.getString(2),
                    cursorHike.getString(3),
                    cursorHike.getString(4)
            ));

        // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorHike.close();
        return ObservationModalArrayList;
    }
    public void updateObservation(String ID_Observation ,String Name,  String Date, String Comment) {

        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_OBSERVATION, Name);
        values.put(DATE_OBSERVATION, Date);
        values.put(COMMENT_OBSERVATION, Comment);


        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        db.update(TABLE_OBSERVATION, values, "id=?", new String[]{ID_Observation});
        db.close();
    }
    public void deleteObservation(String Id) {

        // on below line we are creating
        // a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(TABLE_OBSERVATION, "id=?", new String[]{Id});
        db.close();
    }
}
