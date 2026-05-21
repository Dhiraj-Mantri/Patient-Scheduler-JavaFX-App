package de.buw.se;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import de.buw.se.Classes.Doctor;
import de.buw.se.Classes.Appointment;

import java.util.ArrayList;

public class AppGUI extends Application {

    // ── backend ──────────────────────────────────────────────────────────────
    private final ArrayList<Doctor> doctors = MasterList.Listdoctor;
    private ArrayList<Appointment> appointments;
    private AppointmentScheduler scheduler;

    // ── colour palette ────────────────────────────────────────────────────────
    private static final String C_BG = "#0f1923";
    private static final String C_CARD = "#162130";
    private static final String C_ACCENT = "#00c8a0";
    private static final String C_ACCENT2 = "#0096ff";
    private static final String C_TEXT = "#e8f0f7";
    private static final String C_MUTED = "#6b8099";
    private static final String C_BORDER = "#1e3048";
    private static final String C_ERROR = "#ff4d6d";
    private static final String C_SUCCESS = "#00c8a0";

    // ── shared state ──────────────────────────────────────────────────────────
    private BorderPane root;
    private Label statusBar;

    @Override
    public void start(Stage stage) {

        PersistanceCVS.ensureCsvExists();
        appointments = PersistanceCVS.readAppointments();
        scheduler = new AppointmentScheduler(doctors, appointments);

        root = new BorderPane();
        root.setStyle("-fx-background-color: " + C_BG + ";");

        root.setTop(buildHeader());
        root.setLeft(buildSidebar());
        root.setCenter(buildHomePanel());
        root.setBottom(buildStatusBar());

        Scene scene = new Scene(root, 900, 620);
        stage.setTitle("Patient Scheduler System");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(550);
        stage.show();
    }

    // ═════════════════════════════════════════════════════════════════════════
    // HEADER
    // ═════════════════════════════════════════════════════════════════════════
    private HBox buildHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(14, 24, 14, 24));
        header.setStyle("-fx-background-color: " + C_CARD + ";"
                + "-fx-border-color: " + C_BORDER + ";"
                + "-fx-border-width: 0 0 1 0;");

        Label cross = new Label("✚");
        cross.setStyle("-fx-text-fill: " + C_ACCENT + "; -fx-font-size: 20px; -fx-font-weight: bold;");

        Label title = new Label("  Patient Scheduler");
        title.setStyle("-fx-text-fill: " + C_TEXT + "; -fx-font-size: 18px; -fx-font-weight: bold;");

        Label sub = new Label("  —  Appointment Management System");
        sub.setStyle("-fx-text-fill: " + C_MUTED + "; -fx-font-size: 13px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label version = new Label("v2.0");
        version.setStyle("-fx-text-fill: " + C_MUTED + "; -fx-font-size: 11px;"
                + "-fx-background-color: " + C_BORDER + "; -fx-padding: 3 8 3 8;"
                + "-fx-background-radius: 10;");

        header.getChildren().addAll(cross, title, sub, spacer, version);
        return header;
    }

    // ═════════════════════════════════════════════════════════════════════════
    // SIDEBAR
    // ═════════════════════════════════════════════════════════════════════════
    private VBox buildSidebar() {
        VBox sidebar = new VBox(4);
        sidebar.setPadding(new Insets(20, 12, 20, 12));
        sidebar.setPrefWidth(190);
        sidebar.setStyle("-fx-background-color: " + C_CARD + ";"
                + "-fx-border-color: " + C_BORDER + ";"
                + "-fx-border-width: 0 1 0 0;");

        Label menuLabel = new Label("MENU");
        menuLabel.setStyle("-fx-text-fill: " + C_MUTED + "; -fx-font-size: 10px;"
                + "-fx-font-weight: bold; -fx-padding: 0 0 10 8;");

        Button btnHome = sidebarBtn("🏠  Home", true);
        Button btnDoctors = sidebarBtn("👨‍⚕️  Doctors", false);
        Button btnBook = sidebarBtn("📅  Book Appt.", false);
        Button btnAppts = sidebarBtn("📋  My Appointments", false);

        btnHome.setOnAction(e -> switchCenter(buildHomePanel(), btnHome, btnDoctors, btnBook, btnAppts));
        btnDoctors.setOnAction(e -> switchCenter(buildDoctorsPanel(), btnDoctors, btnHome, btnBook, btnAppts));
        btnBook.setOnAction(e -> switchCenter(buildBookingPanel(), btnBook, btnHome, btnDoctors, btnAppts));
        btnAppts.setOnAction(e -> switchCenter(buildAppointmentsPanel(), btnAppts, btnHome, btnDoctors, btnBook));

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Label footer = new Label("Bauhaus-Universität\nWeimar · SE 2026");
        footer.setStyle("-fx-text-fill: " + C_MUTED + "; -fx-font-size: 10px;"
                + "-fx-text-alignment: center; -fx-alignment: center;");
        footer.setMaxWidth(Double.MAX_VALUE);

        sidebar.getChildren().addAll(menuLabel, btnHome, btnDoctors, btnBook, btnAppts, spacer, footer);
        return sidebar;
    }

    private Button sidebarBtn(String text, boolean active) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setPadding(new Insets(10, 14, 10, 14));
        applySidebarStyle(btn, active);
        return btn;
    }

    private void applySidebarStyle(Button btn, boolean active) {
        if (active) {
            btn.setStyle("-fx-background-color: " + C_ACCENT + "22;"
                    + "-fx-text-fill: " + C_ACCENT + ";"
                    + "-fx-font-size: 13px; -fx-background-radius: 8;"
                    + "-fx-border-color: " + C_ACCENT + "44;"
                    + "-fx-border-radius: 8; -fx-font-weight: bold; -fx-cursor: hand;");
        } else {
            btn.setStyle("-fx-background-color: transparent;"
                    + "-fx-text-fill: " + C_TEXT + ";"
                    + "-fx-font-size: 13px; -fx-background-radius: 8;"
                    + "-fx-cursor: hand;");
        }
    }

    private void switchCenter(javafx.scene.Node panel, Button active, Button... others) {
        root.setCenter(panel);
        applySidebarStyle(active, true);
        for (Button b : others)
            applySidebarStyle(b, false);
    }

    // ═════════════════════════════════════════════════════════════════════════
    // STATUS BAR
    // ═════════════════════════════════════════════════════════════════════════
    private HBox buildStatusBar() {
        HBox bar = new HBox();
        bar.setPadding(new Insets(6, 16, 6, 16));
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.setStyle("-fx-background-color: " + C_CARD + ";"
                + "-fx-border-color: " + C_BORDER + ";"
                + "-fx-border-width: 1 0 0 0;");

        Label dot = new Label("●");
        dot.setStyle("-fx-text-fill: " + C_ACCENT + "; -fx-font-size: 10px;");

        statusBar = new Label("  System ready · " + doctors.size() + " doctors loaded");
        statusBar.setStyle("-fx-text-fill: " + C_MUTED + "; -fx-font-size: 11px;");

        bar.getChildren().addAll(dot, statusBar);
        return bar;
    }

    private void setStatus(String msg, boolean ok) {
        statusBar.setText("  " + msg);
        statusBar.setStyle("-fx-text-fill: " + (ok ? C_SUCCESS : C_ERROR) + "; -fx-font-size: 11px;");
    }

    // ═════════════════════════════════════════════════════════════════════════
    // HOME PANEL
    // ═════════════════════════════════════════════════════════════════════════
    private ScrollPane buildHomePanel() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(30));

        Label heading = new Label("Welcome to Patient Scheduler");
        heading.setStyle("-fx-text-fill: " + C_TEXT + "; -fx-font-size: 22px; -fx-font-weight: bold;");

        Label sub = new Label("Manage doctor appointments efficiently and easily.");
        sub.setStyle("-fx-text-fill: " + C_MUTED + "; -fx-font-size: 14px;");

        // Stat cards
        HBox stats = new HBox(16);
        stats.getChildren().addAll(
                statCard("👨‍⚕️", String.valueOf(doctors.size()), "Doctors"),
                statCard("📋", String.valueOf(appointments.size()), "Appointments"),
                statCard("🕐", countSlots(), "Available Slots"));

        // Quick actions
        Label qLabel = new Label("Quick Actions");
        qLabel.setStyle("-fx-text-fill: " + C_TEXT + "; -fx-font-size: 15px; -fx-font-weight: bold;");

        HBox actions = new HBox(12);
        Button qBook = accentBtn("📅  Book Appointment", C_ACCENT);
        Button qView = accentBtn("📋  View Appointments", C_ACCENT2);
        Button qDocs = accentBtn("👨‍⚕️  Browse Doctors", C_MUTED);

        qBook.setOnAction(e -> root.setCenter(buildBookingPanel()));
        qView.setOnAction(e -> root.setCenter(buildAppointmentsPanel()));
        qDocs.setOnAction(e -> root.setCenter(buildDoctorsPanel()));

        actions.getChildren().addAll(qBook, qView, qDocs);

        // Specialty summary
        Label specLabel = new Label("Specialties Available");
        specLabel.setStyle("-fx-text-fill: " + C_TEXT + "; -fx-font-size: 15px; -fx-font-weight: bold;");

        FlowPane chips = new FlowPane(8, 8);
        for (Doctor d : doctors) {
            Label chip = new Label(d.getEspeciality());
            chip.setStyle("-fx-background-color: " + C_ACCENT + "22;"
                    + "-fx-text-fill: " + C_ACCENT + ";"
                    + "-fx-padding: 5 12 5 12; -fx-background-radius: 20;"
                    + "-fx-font-size: 12px;");
            chips.getChildren().add(chip);
        }

        content.getChildren().addAll(heading, sub, stats, qLabel, actions, specLabel, chips);

        ScrollPane sp = new ScrollPane(content);
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background: " + C_BG + "; -fx-background-color: " + C_BG + ";");
        return sp;
    }

    private VBox statCard(String icon, String value, String label) {
        VBox card = new VBox(4);
        card.setPadding(new Insets(18, 24, 18, 24));
        card.setStyle("-fx-background-color: " + C_CARD + ";"
                + "-fx-background-radius: 12;"
                + "-fx-border-color: " + C_BORDER + ";"
                + "-fx-border-radius: 12;");
        card.setPrefWidth(160);

        Label ico = new Label(icon);
        ico.setStyle("-fx-font-size: 24px;");
        Label val = new Label(value);
        val.setStyle("-fx-text-fill: " + C_ACCENT + "; -fx-font-size: 28px; -fx-font-weight: bold;");
        Label lbl = new Label(label);
        lbl.setStyle("-fx-text-fill: " + C_MUTED + "; -fx-font-size: 12px;");

        card.getChildren().addAll(ico, val, lbl);
        return card;
    }

    private Button accentBtn(String text, String color) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: " + color + "22;"
                + "-fx-text-fill: " + color + ";"
                + "-fx-font-size: 13px; -fx-background-radius: 8;"
                + "-fx-border-color: " + color + "44;"
                + "-fx-border-radius: 8; -fx-padding: 10 20 10 20;"
                + "-fx-cursor: hand;");
        return btn;
    }

    private String countSlots() {
        int total = 0;
        for (Doctor d : doctors)
            total += d.getSchedules().size();
        return String.valueOf(total - appointments.size());
    }

    // ═════════════════════════════════════════════════════════════════════════
    // DOCTORS PANEL
    // ═════════════════════════════════════════════════════════════════════════
    private ScrollPane buildDoctorsPanel() {
        VBox content = new VBox(16);
        content.setPadding(new Insets(30));

        Label heading = new Label("Available Doctors");
        heading.setStyle("-fx-text-fill: " + C_TEXT + "; -fx-font-size: 20px; -fx-font-weight: bold;");

        for (Doctor d : doctors) {
            content.getChildren().add(doctorCard(d));
        }

        ScrollPane sp = new ScrollPane(content);
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background: " + C_BG + "; -fx-background-color: " + C_BG + ";");
        return sp;
    }

    private VBox doctorCard(Doctor d) {
        VBox card = new VBox(6);
        card.setPadding(new Insets(16, 20, 16, 20));
        card.setStyle("-fx-background-color: " + C_CARD + ";"
                + "-fx-background-radius: 10;"
                + "-fx-border-color: " + C_BORDER + ";"
                + "-fx-border-radius: 10;");

        HBox top = new HBox(12);
        top.setAlignment(Pos.CENTER_LEFT);

        Label avatar = new Label("👨‍⚕️");
        avatar.setStyle("-fx-font-size: 26px;");

        VBox info = new VBox(2);
        Label name = new Label(d.getName());
        name.setStyle("-fx-text-fill: " + C_TEXT + "; -fx-font-size: 15px; -fx-font-weight: bold;");
        Label spec = new Label(d.getEspeciality() + "  ·  ID: " + d.getID());
        spec.setStyle("-fx-text-fill: " + C_MUTED + "; -fx-font-size: 12px;");
        info.getChildren().addAll(name, spec);

        top.getChildren().addAll(avatar, info);

        HBox slots = new HBox(8);
        slots.setPadding(new Insets(6, 0, 0, 0));
        Label sLabel = new Label("Available slots:");
        sLabel.setStyle("-fx-text-fill: " + C_MUTED + "; -fx-font-size: 12px;");
        slots.getChildren().add(sLabel);

        for (String time : d.getSchedules()) {
            boolean taken = !scheduler.Available(d, time);
            Label chip = new Label(time + (taken ? " ✗" : " ✓"));
            chip.setStyle("-fx-background-color: " + (taken ? C_ERROR : C_ACCENT) + "22;"
                    + "-fx-text-fill: " + (taken ? C_ERROR : C_ACCENT) + ";"
                    + "-fx-padding: 3 10 3 10; -fx-background-radius: 12; -fx-font-size: 11px;");
            slots.getChildren().add(chip);
        }

        card.getChildren().addAll(top, slots);
        return card;
    }

    // ═════════════════════════════════════════════════════════════════════════
    // BOOKING PANEL
    // ═════════════════════════════════════════════════════════════════════════
    private ScrollPane buildBookingPanel() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(30));
        content.setMaxWidth(560);

        Label heading = new Label("Book an Appointment");
        heading.setStyle("-fx-text-fill: " + C_TEXT + "; -fx-font-size: 20px; -fx-font-weight: bold;");

        // Patient ID field
        Label idLabel = fieldLabel("Patient ID");
        TextField idField = styledField("e.g. P-12345");

        // Doctor selector
        Label docLabel = fieldLabel("Select Doctor");
        ComboBox<String> docBox = new ComboBox<>();
        docBox.setMaxWidth(Double.MAX_VALUE);
        for (Doctor d : doctors)
            docBox.getItems().add(d.getName() + " — " + d.getEspeciality());
        styleComboBox(docBox, "Choose a doctor...");

        // Time slot selector
        Label timeLabel = fieldLabel("Select Time Slot");
        ComboBox<String> timeBox = new ComboBox<>();
        timeBox.setMaxWidth(Double.MAX_VALUE);
        timeBox.setDisable(true);
        styleComboBox(timeBox, "First choose a doctor...");

        // Populate time slots when doctor is chosen
        docBox.setOnAction(e -> {
            int idx = docBox.getSelectionModel().getSelectedIndex();
            if (idx >= 0) {
                Doctor d = doctors.get(idx);
                timeBox.getItems().clear();
                for (String t : d.getSchedules()) {
                    boolean taken = !scheduler.Available(d, t);
                    timeBox.getItems().add(t + (taken ? "  (unavailable)" : "  (available)"));
                }
                timeBox.setDisable(false);
                timeBox.setPromptText("Choose a time slot...");
            }
        });

        // Result label
        Label resultLabel = new Label("");
        resultLabel.setWrapText(true);
        resultLabel.setStyle("-fx-font-size: 13px;");

        // Book button
        Button bookBtn = new Button("Confirm Appointment  →");
        bookBtn.setMaxWidth(Double.MAX_VALUE);
        bookBtn.setStyle("-fx-background-color: " + C_ACCENT + ";"
                + "-fx-text-fill: #0f1923; -fx-font-size: 14px; -fx-font-weight: bold;"
                + "-fx-background-radius: 8; -fx-padding: 12 0 12 0; -fx-cursor: hand;");

        bookBtn.setOnAction(e -> {
            String patId = idField.getText().trim();
            int docIdx = docBox.getSelectionModel().getSelectedIndex();
            int timeIdx = timeBox.getSelectionModel().getSelectedIndex();

            if (patId.isEmpty()) {
                resultLabel.setText("⚠  Please enter your Patient ID.");
                resultLabel.setStyle("-fx-text-fill: " + C_ERROR + "; -fx-font-size: 13px;");
                setStatus("Booking failed — missing patient ID", false);
                return;
            }
            if (docIdx < 0) {
                resultLabel.setText("⚠  Please select a doctor.");
                resultLabel.setStyle("-fx-text-fill: " + C_ERROR + "; -fx-font-size: 13px;");
                setStatus("Booking failed — no doctor selected", false);
                return;
            }
            if (timeIdx < 0) {
                resultLabel.setText("⚠  Please select a time slot.");
                resultLabel.setStyle("-fx-text-fill: " + C_ERROR + "; -fx-font-size: 13px;");
                setStatus("Booking failed — no time selected", false);
                return;
            }

            Doctor chosen = doctors.get(docIdx);
            String rawTime = chosen.getSchedules().get(timeIdx);

            if (!scheduler.Available(chosen, rawTime)) {
                resultLabel.setText("✗  This slot is already taken. Please choose another.");
                resultLabel.setStyle("-fx-text-fill: " + C_ERROR + "; -fx-font-size: 13px;");
                setStatus("Slot unavailable", false);
                return;
            }

            boolean ok = scheduler.MakeAppointment(patId, chosen, chosen.getEspeciality(), rawTime);
            if (ok) {
                resultLabel.setText("✔  Appointment booked!\n"
                        + "Doctor: " + chosen.getName() + "\n"
                        + "Time: " + rawTime + "\n"
                        + "Patient ID: " + patId);
                resultLabel.setStyle("-fx-text-fill: " + C_SUCCESS + "; -fx-font-size: 13px;");
                setStatus("Appointment booked successfully for patient " + patId, true);
                idField.clear();
                docBox.getSelectionModel().clearSelection();
                timeBox.getItems().clear();
                timeBox.setDisable(true);
            } else {
                resultLabel.setText("✗  Could not book appointment. Slot may be taken.");
                resultLabel.setStyle("-fx-text-fill: " + C_ERROR + "; -fx-font-size: 13px;");
                setStatus("Booking failed", false);
            }
        });

        content.getChildren().addAll(
                heading,
                idLabel, idField,
                docLabel, docBox,
                timeLabel, timeBox,
                bookBtn, resultLabel);

        ScrollPane sp = new ScrollPane(content);
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background: " + C_BG + "; -fx-background-color: " + C_BG + ";");
        return sp;
    }

    // ═════════════════════════════════════════════════════════════════════════
    // APPOINTMENTS PANEL
    // ═════════════════════════════════════════════════════════════════════════
    @SuppressWarnings("unchecked")
    private VBox buildAppointmentsPanel() {
        VBox content = new VBox(16);
        content.setPadding(new Insets(30));

        Label heading = new Label("All Appointments");
        heading.setStyle("-fx-text-fill: " + C_TEXT + "; -fx-font-size: 20px; -fx-font-weight: bold;");

        // Search bar
        HBox searchRow = new HBox(10);
        searchRow.setAlignment(Pos.CENTER_LEFT);
        TextField search = styledField("Search by Patient ID or Doctor...");
        search.setPrefWidth(300);
        Button refreshBtn = accentBtn("⟳  Refresh", C_ACCENT2);
        searchRow.getChildren().addAll(search, refreshBtn);

        // Table
        TableView<AppointmentRow> table = new TableView<>();
        table.setStyle("-fx-background-color: " + C_CARD + ";"
                + "-fx-border-color: " + C_BORDER + ";"
                + "-fx-table-cell-border-color: " + C_BORDER + ";");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(table, Priority.ALWAYS);

        TableColumn<AppointmentRow, String> colPat = tableCol("Patient ID", "patientId", 120);
        TableColumn<AppointmentRow, String> colDoc = tableCol("Doctor", "doctorName", 160);
        TableColumn<AppointmentRow, String> colSpec = tableCol("Specialty", "specialty", 160);
        TableColumn<AppointmentRow, String> colTime = tableCol("Time", "time", 100);

        table.getColumns().addAll(colPat, colDoc, colSpec, colTime);

        ObservableList<AppointmentRow> allRows = FXCollections.observableArrayList();
        for (Appointment a : appointments) {
            allRows.add(new AppointmentRow(
                    a.getIDpatient(),
                    a.getDoctor().getName(),
                    a.getDoctor().getEspeciality(),
                    a.getTime()));
        }
        table.setItems(allRows);

        // Live search filter
        search.textProperty().addListener((obs, old, nw) -> {
            String q = nw.toLowerCase();
            ObservableList<AppointmentRow> filtered = FXCollections.observableArrayList();
            for (AppointmentRow r : allRows) {
                if (r.getPatientId().toLowerCase().contains(q)
                        || r.getDoctorName().toLowerCase().contains(q)) {
                    filtered.add(r);
                }
            }
            table.setItems(filtered);
        });

        refreshBtn.setOnAction(e -> {
            allRows.clear();
            for (Appointment a : appointments) {
                allRows.add(new AppointmentRow(
                        a.getIDpatient(),
                        a.getDoctor().getName(),
                        a.getDoctor().getEspeciality(),
                        a.getTime()));
            }
            table.setItems(allRows);
            search.clear();
            setStatus("Appointments refreshed — " + appointments.size() + " total", true);
        });

        Label count = new Label(appointments.size() + " appointments total");
        count.setStyle("-fx-text-fill: " + C_MUTED + "; -fx-font-size: 12px;");

        content.getChildren().addAll(heading, searchRow, table, count);
        content.setStyle("-fx-background-color: " + C_BG + ";");
        return content;
    }

    // ═════════════════════════════════════════════════════════════════════════
    // HELPERS
    // ═════════════════════════════════════════════════════════════════════════
    private Label fieldLabel(String text) {
        Label l = new Label(text);
        l.setStyle("-fx-text-fill: " + C_MUTED + "; -fx-font-size: 12px; -fx-font-weight: bold;");
        return l;
    }

    private TextField styledField(String prompt) {
        TextField f = new TextField();
        f.setPromptText(prompt);
        f.setStyle("-fx-background-color: " + C_CARD + ";"
                + "-fx-text-fill: " + C_TEXT + ";"
                + "-fx-prompt-text-fill: " + C_MUTED + ";"
                + "-fx-border-color: " + C_BORDER + ";"
                + "-fx-border-radius: 6; -fx-background-radius: 6;"
                + "-fx-padding: 9 12 9 12; -fx-font-size: 13px;");
        return f;
    }

    private void styleComboBox(ComboBox<String> box, String prompt) {
        box.setPromptText(prompt);
        box.setStyle("-fx-background-color: " + C_CARD + ";"
                + "-fx-text-fill: " + C_TEXT + ";"
                + "-fx-border-color: " + C_BORDER + ";"
                + "-fx-border-radius: 6; -fx-background-radius: 6;"
                + "-fx-font-size: 13px;");
    }

    private <T> TableColumn<T, String> tableCol(String header, String prop, double minW) {
        TableColumn<T, String> col = new TableColumn<>(header);
        col.setCellValueFactory(new PropertyValueFactory<>(prop));
        col.setMinWidth(minW);
        col.setStyle("-fx-text-fill: " + C_BORDER + ";");
        return col;
    }

    // ─── TableView row model ──────────────────────────────────────────────────
    public static class AppointmentRow {
        private final javafx.beans.property.SimpleStringProperty patientId;
        private final javafx.beans.property.SimpleStringProperty doctorName;
        private final javafx.beans.property.SimpleStringProperty specialty;
        private final javafx.beans.property.SimpleStringProperty time;

        public AppointmentRow(String pid, String doc, String spec, String time) {
            this.patientId = new javafx.beans.property.SimpleStringProperty(pid);
            this.doctorName = new javafx.beans.property.SimpleStringProperty(doc);
            this.specialty = new javafx.beans.property.SimpleStringProperty(spec);
            this.time = new javafx.beans.property.SimpleStringProperty(time);
        }

        public String getPatientId() {
            return patientId.get();
        }

        public String getDoctorName() {
            return doctorName.get();
        }

        public String getSpecialty() {
            return specialty.get();
        }

        public String getTime() {
            return time.get();
        }

        public javafx.beans.property.SimpleStringProperty patientIdProperty() {
            return patientId;
        }

        public javafx.beans.property.SimpleStringProperty doctorNameProperty() {
            return doctorName;
        }

        public javafx.beans.property.SimpleStringProperty specialtyProperty() {
            return specialty;
        }

        public javafx.beans.property.SimpleStringProperty timeProperty() {
            return time;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}