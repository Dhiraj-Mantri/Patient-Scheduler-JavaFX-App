package de.buw.se.Classes;
import java.util.ArrayList;

public class Doctor {

    String name;
    String especiality;
    String ID;
    ArrayList<String> schedules;
    

    public Doctor(String name, String especiality, String ID, ArrayList<String> schedules) {
        this.name = name;
        this.especiality = especiality;
        this.ID = ID;
        this.schedules = schedules;
    }

    public String getName() {
        return name;
    }

    public String getEspeciality() {
        return especiality;
    }
    public String getID() {
        return ID;
    }

    public ArrayList<String> getSchedules() {
        return schedules;
    }
}
