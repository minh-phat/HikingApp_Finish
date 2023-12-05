package com.example.hikingapp;

public class ObservationModal {
    private int Id_Observation;
    private int Id_Hike;
    private String Name;
    private String Date;
    private String Comment;

    public int getId_Observation() {
        return Id_Observation;
    }

    public void setId_Observation(int id_Observation) {
        Id_Observation = id_Observation;
    }

    public int getId_Hike() {
        return Id_Hike;
    }

    public void setId_Hike(int id_Hike) {
        Id_Hike = id_Hike;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public ObservationModal(int id_Observation, int id_Hike, String name, String date, String comment) {
        Id_Observation = id_Observation;
        Id_Hike = id_Hike;
        Name = name;
        Date = date;
        Comment = comment;
    }
}
