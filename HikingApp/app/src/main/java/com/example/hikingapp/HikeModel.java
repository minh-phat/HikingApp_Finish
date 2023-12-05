package com.example.hikingapp;

public class HikeModel {
    private String Name;
    private String Location;
    private String Date;
    private String Level;
    private int Length;
    private String Description;
    private String CarryItem;
    private int Participaints;
    private String Parking;
    private int Id;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public int getLength() {
        return Length;
    }

    public void setLength(int length) {
        Length = length;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCarryItem() {
        return CarryItem;
    }

    public void setCarryItem(String carryItem) {
        CarryItem = carryItem;
    }

    public int getParticipaints() {
        return Participaints;
    }

    public void setParticipaints(int participaints) {
        Participaints = participaints;
    }

    public String getParking() {
        return Parking;
    }

    public void setParking(String parking) {
        Parking = parking;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public HikeModel( int id, String name, String location, String date, int length, String level,  String description, String carryItem, int participaints, String parking) {
        Name = name;
        Location = location;
        Date = date;
        Level = level;
        Length = length;
        Description = description;
        CarryItem = carryItem;
        Participaints = participaints;
        Parking = parking;
        Id = id;
    }
}

