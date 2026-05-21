////////****Patient Scheduler System:

A JavaFX desktop application for managing patient appointments. The system allows patients to browse available doctors, check time-slot availability, and book appointments - with all data persisted to a CSV file.

Features
    - View a roster of doctors across multiple specialities (Cardiology, Neurology, Gastroenterology, Pediatrics, Dermatology) 
    - Book appointments by selecting a doctor and an available time slot 
    - Automatic availability check - a slot already taken cannot be double-booked 
    - Appointment data is read from and written to a CSV file (Appointments.csv) for persistence between sessions 
    - Clean JavaFX GUI with a dark theme and a sidebar for navigation 

Project Structure

patient-scheduler-system/
├── src/
│   └── main/
│       ├── java/de/buw/se/
│       │   ├── AppGUI.java               # JavaFX entry point & UI
│       │   ├── AppointmentScheduler.java # Availability & booking logic
│       │   ├── MasterList.java           # In-memory doctor & appointment lists
│       │   ├── PersistanceCVS.java       # CSV read/write (Apache Commons CSV)
│       │   └── Classes/
│       │       ├── Appointment.java      # Appointment data model
│       │       └── Doctor.java           # Doctor data model
│       └── resources/
│           └── Appointments.csv          # Persistent appointment store
├── build.gradle
├── settings.gradle
└── gradlew / gradlew.bat

Tech Stack
Layer	Technology
Language	Java 21
UI Framework	JavaFX 21.0.5
Build Tool	Gradle
CSV Persistence	Apache Commons CSV 1.10.0
Database (optional)	H2 (embedded SQL)
Testing	JUnit Jupiter 5.10.0

Prerequisites
    - JDK 21 or higher 
    - Git (to clone the repository) 
    - An IDE: Eclipse or Visual Studio Code (see setup below) 

Getting Started
1. Clone the repository
git clone git@https://gitlab.uni-weimar.de/software-engineering/se-lectures/se-sose-2026/patient-scheduler-system.git
cd patient-scheduler-system
2. Open in your IDE
Eclipse No additional plugins required. Import the project as an existing Gradle project: File ? Import ? Gradle ? Existing Gradle Project
Visual Studio Code Install the following extensions before importing:
    - Extension Pack for Java (Microsoft) 
    - Gradle for Java (Microsoft) 
3. Run the application
Eclipse: Open the Gradle Tasks tab ? application ? run (If the tab isn't visible: Window ? Show View ? Other... ? search for Gradle)
VS Code: Click the Gradle icon (?) in the sidebar ? unfold your project ? application ? run (?)
Terminal:
./gradlew run        # macOS / Linux
gradlew.bat run      # Windows

CSV Data Format
Appointments are stored in src/main/resources/Appointments.csv:
PatientId,DoctorName,Speciality,TimeSlot
444,Dr. Johnson,Neurology,10:30
666,Dr. Smith,Cardiology,10:00
The file is created automatically if it does not exist when the application starts.

Available Doctors
ID	Name	Speciality	Available Slots
1	Dr. Smith	Cardiology	10:00, 10:30, 11:00
2	Dr. Johnson	Neurology	9:00, 10:30, 12:00
3	Dr. Martin	Gastroenterology	1:00, 2:30, 3:00
4	Dr. Hernandez	Pediatrics	7:00, 7:30, 8:00
5	Dr. Garcia	Dermatology	5:30, 6:00, 6:30

Key Classes
AppointmentScheduler - Core business logic. Checks whether a doctor is available at a given time and creates new appointments.
MasterList - Static in-memory store for doctors and appointments, initialised at startup.
PersistanceCVS - Handles all CSV I/O: reading existing appointments on launch and appending new ones on booking.
AppGUI - JavaFX application class that wires the UI to the scheduler backend.

References
    - JavaFX + Gradle setup: https://openjfx.io/openjfx-docs/#gradle 
    - Apache Commons CSV tutorial: https://www.callicoder.com/java-read-write-csv-file-apache-commons-csv/ 
    - Git documentation: https://git-scm.com/ 

Transparent Documentation of AI implementation
•	For coding generation:
•	AI-generated using ChatGPT 4.0, OpenAI, May. 2026, chatgpt.com
•	AI-generated using Microsoft Copilot, Microsoft, May. 2026, copilot.microsoft.com

