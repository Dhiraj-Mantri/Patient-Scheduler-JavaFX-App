package de.buw.se;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;


import de.buw.se.Classes.Appointment;
import de.buw.se.Classes.Doctor;
import java.nio.file.Path;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;



public class PersistanceCVS {
    private static final String FILE_NAME = "src/main/resources/Appointments.csv";

  /**
   * Read appointments from the CSV file
   * 
   * @return list of appointments
    */
     public static ArrayList<Appointment> readAppointments() {
        ArrayList<Appointment> appointments = new ArrayList<>();
        // open CSV file and read appointments of each entry
            try (Reader reader = Files.newBufferedReader(Paths.get(FILE_NAME));
                    @SuppressWarnings("deprecation")
                    CSVParser csvParser = new CSVParser(reader,
                            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
                for (CSVRecord csvRecord : csvParser) {
                    String patientId = csvRecord.get("PatientId");
                    String doctorName = csvRecord.get("DoctorName");
                    Doctor doctor = MasterList.Listdoctor.stream()
                            .filter(d -> d.getName().equals(doctorName))
                            .findFirst()
                            .orElse(null);
                    String speciality = csvRecord.get("Speciality");
                    String timeSlot = csvRecord.get("TimeSlot");

                    Appointment appointment = new Appointment(patientId, doctor, speciality, timeSlot);
                    appointments.add(appointment);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        return appointments;

    }



    public static void ensureCsvExists() {
    Path path = Paths.get(FILE_NAME);

    if (!Files.exists(path)) {
        try (BufferedWriter writer = Files.newBufferedWriter(path);
             CSVPrinter csvPrinter = new CSVPrinter(writer,
                     CSVFormat.DEFAULT.withHeader("PatientId", "DoctorName", "Speciality", "TimeSlot"))) {

            csvPrinter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    /**
   * Add a new appointment to the CSV file
   * 
   * @param patientId ID of the patient
   * @param doctor doctor for the appointment
   * @param speciality speciality for the appointment
   * @param timeSlot time slot for the appointment
   */
  public static void addAppointment(String patientId, Doctor doctor, String speciality, String timeSlot) {
    // add a new appointment to the CSV file
    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_NAME), 
        StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);) {
      csvPrinter.printRecord(patientId, doctor.getName(), speciality, timeSlot);
      csvPrinter.flush();
    } catch (IOException e) {
      e.printStackTrace();      
    }
    Appointment appointment = new Appointment(patientId, doctor, speciality, timeSlot);
    MasterList.Listappointments.add(appointment);
  }
  
  
}  

