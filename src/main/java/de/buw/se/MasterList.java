package de.buw.se;

import de.buw.se.Classes.Appointment;
import de.buw.se.Classes.Doctor;
import java.util.ArrayList;


public class MasterList {
   
    public static ArrayList<Doctor> Listdoctor = new ArrayList<>();
    //public static ArrayList<Doctor> doctors = new ArrayList<>();
    public static ArrayList<Appointment> Listappointments = new ArrayList<>();
  
    static{

        //Reading appointments from the CSV file and adding them to the list
        
        //Listappointments = PersistanceCVS.readAppointments();


        //Schedules of the doctors
          ArrayList<String>ScheduleD1 = new ArrayList<>();
            ScheduleD1.add("10:00");
            ScheduleD1.add("10:30");
            ScheduleD1.add("11:00");

            //Schedules Second doctor
        ArrayList<String>ScheduleD2 = new ArrayList<>();
            ScheduleD2.add("9:00");
            ScheduleD2.add("10:30");
            ScheduleD2.add("12:00");

            //Schedules Third doctor
        ArrayList<String>ScheduleD3 = new ArrayList<>();
            ScheduleD3.add("1:00");
            ScheduleD3.add("2:30");
            ScheduleD3.add("3:00");

            //Schedules Fourth doctor
        ArrayList<String>ScheduleD4 = new ArrayList<>();
            ScheduleD4.add("7:00");
            ScheduleD4.add("7:30");
            ScheduleD4.add("8:00");

            //Schedules Fifth doctor
        ArrayList<String>ScheduleD5 = new ArrayList<>();
            ScheduleD5.add("5:30");
            ScheduleD5.add("6:00");
            ScheduleD5.add("6:30");

    //Creating doctors with their name, speciality, ID and schedule
            //Firts Doctor
        Doctor D1=new Doctor("Dr. Smith",
            "Cardiology", 
            "1", 
            ScheduleD1);
            //Second doctor
        Doctor D2=new Doctor("Dr. Johnson",
            "Neurology", 
            "2", 
            ScheduleD2);
            //Third doctor
        Doctor D3=new Doctor("Dr. Martin",
            "Gastroenterology", 
            "3", 
            ScheduleD3);  
            //Fourth doctor
        Doctor D4=new Doctor("Dr. Hernandez",
            "Pediatrics", 
            "4", 
            ScheduleD4);   
        
            //Fifth doctor
        Doctor D5=new Doctor("Dr. Garcia",
            "dermatology", 
            "5", 
            ScheduleD5);   


        //Adding doctors to the list
        Listdoctor.add(D1);
        Listdoctor.add(D2);
        Listdoctor.add(D3);
        Listdoctor.add(D4);
        Listdoctor.add(D5);





  }
    
}