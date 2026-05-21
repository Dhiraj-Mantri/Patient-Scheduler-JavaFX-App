package de.buw.se.Classes;

public class Appointment {
 
    String IDpatient;
    Doctor doctor;
    String time;
    String speciality;
    String Confirmation;

    public Appointment(String IDpatient, Doctor doctor, String speciality, String time) {
        this.IDpatient = IDpatient;
        this.doctor = doctor;
        this.speciality = speciality;
        this.time = time;
    }

    public String getIDpatient() {
        return IDpatient;
    }

    public Doctor getDoctor() {
        return doctor;
    }
    public String getSpeciality() {
        return speciality;
    }

    public String getTime() {
        return time;
    }

    public String toString() {
        return "Appointment with "+ doctor.getName() + " at " + time + ". Patient ID: " + IDpatient;
    }

}