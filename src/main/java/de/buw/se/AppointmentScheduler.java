package de.buw.se;

import java.util.ArrayList;
import de.buw.se.Classes.Appointment;
import de.buw.se.Classes.Doctor;

public class AppointmentScheduler {
    ArrayList<Doctor> Listdoctors;
    ArrayList<Appointment> Listappointments;

    public AppointmentScheduler(ArrayList<Doctor> doctors, ArrayList<Appointment> appointments) {
        this.Listdoctors = doctors;
        this.Listappointments = appointments;
    }

    public boolean Available(Doctor doctor, String time) {

        for (Appointment i : Listappointments) {

            if (i.getDoctor() != null
                    && i.getDoctor().getID().equals(doctor.getID())
                    && i.getTime() != null
                    && i.getTime().equals(time)
            ) {
                return false;
            }
        }

        return true;
    }

    public boolean MakeAppointment(
            String IDpatient,
            Doctor doctor,
            String speciality,
            String time

    ) {

        if (Available(doctor, time)) {
            Appointment appointment = new Appointment(IDpatient, doctor, speciality, time);

            // 1. saving object in the list
            Listappointments.add(appointment);

            // 2. saving information in the CSV file

            // Listappointments.add(new Appointment(IDpatient, doctor, time, ""));
            return true;
        }

        return false;
    }
}
