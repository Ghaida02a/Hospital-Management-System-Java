package service;

import Interface.Appointable;
import Utils.HelperUtils;
import Utils.InputHandler;
import entity.Appointment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Interface.Manageable;
import Interface.Searchable;
import entity.Doctor;
import entity.Patient;

public class AppointmentService implements Manageable, Searchable, Appointable {
    public static List<Appointment> appointmentList = new ArrayList<>();

    public static Appointment addAppointment() {
        Appointment appointment = new Appointment();
        System.out.println("--- Add Appointment ---");
        String generatedId = HelperUtils.generateId("Appt");
        appointment.setAppointmentId(generatedId);
        System.out.println("Appointment ID: " + appointment.getAppointmentId());


        System.out.println("Patient List:");
        PatientService.displayPatientNamesAndIds();
        String patientId = InputHandler.getStringInput("Enter Patient ID: ");
        while (HelperUtils.isNull(PatientService.getPatientById(patientId))) {
            System.out.println("Patient not found. Please try again.");
            patientId = InputHandler.getStringInput("Enter Patient ID: ");
        }
        appointment.setPatientId(patientId);

        System.out.println("Doctor List:");
        DoctorService.displayDoctorNamesAndIds();
        String doctorId = InputHandler.getStringInput("Enter Doctor ID: ");
        while (HelperUtils.isNull(DoctorService.getDoctorById(doctorId))) {
            System.out.println("Doctor not found. Please try again.");
            doctorId = InputHandler.getStringInput("Enter Doctor ID: ");
        }
        appointment.setDoctorId(doctorId);

        while (true) {
            LocalDate date = InputHandler.getDateInput("Enter Appointment Date ");
            if (HelperUtils.isNull(date)) {
                System.out.println("Invalid date format. Please try again.");
            } else if (date.isBefore(LocalDate.now())) {
                System.out.println("Appointment date cannot be in the past. Please enter a future date.");
            } else {
                appointment.setAppointmentDate(date);
                break;
            }
        }

        appointment.setAppointmentTime(InputHandler.getTimeInput("Enter Appointment Time").toString());

        System.out.println("Appointment set for " + appointment.getAppointmentDate() + " at " + appointment.getAppointmentTime());

        appointment.setReason(InputHandler.getStringInput("Enter Reason: "));

        appointment.setNotes(InputHandler.getStringInput("Enter Notes: "));

        appointment.setStatus("Scheduled");

        return appointment;
    }

    public static void save(Appointment appointment) {
        if (HelperUtils.isNotNull(appointment)) {
            appointmentList.add(appointment);
            System.out.println("Appointment saved successfully.");

            // Assign patient to doctor
            Doctor doctor = DoctorService.getDoctorById(appointment.getDoctorId());
            Patient patient = PatientService.getPatientById(appointment.getPatientId());
            if (HelperUtils.isNotNull(doctor) && HelperUtils.isNotNull(patient)) {
                doctor.assignPatient(patient);
            }
        }
    }

    public static Appointment getAppointmentById(String appointmentId) {
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentId().equals(appointmentId)) {
                return appointment;
            }
        }
        return null;
    }

    public static List<Appointment> getAppointmentsByPatient(String patientId) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment a : appointmentList) {
            if (HelperUtils.isNotNull(a.getPatientId()) && a.getPatientId().equals(patientId)) result.add(a);
        }
        if (result.isEmpty()) {
            System.out.println("No appointments found for patient ID: " + patientId);
        }
        return result;
    }

    public static List<Appointment> getAppointmentsByDoctor(String doctorId) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment appointment : appointmentList) {
            if (HelperUtils.isNotNull(appointment.getDoctorId()) && appointment.getDoctorId().equals(doctorId))
                result.add(appointment);
        }
        if (result.isEmpty()) System.out.println("No appointments found for doctor ID: " + doctorId);
        return result;
    }

    public static List<Appointment> getAppointmentsByDate(LocalDate date) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment appointment : appointmentList) {
            if (HelperUtils.isNotNull(appointment.getAppointmentDate()) && appointment.getAppointmentDate().equals(date))
                result.add(appointment);
        }
        if (result.isEmpty()) System.out.println("No appointments found for date: " + date);
        return result;
    }

    public static boolean cancelAppointmentById(String appointmentId) {
        Appointment appointment = getAppointmentById(appointmentId);
        if (HelperUtils.isNotNull(appointment)) {
            return appointment.cancel();
        }
        System.out.println("Appointment not found.");
        return false;
    }

    public static boolean completeAppointment(String appointmentId) {
        Appointment appointment = getAppointmentById(appointmentId);
        if (HelperUtils.isNotNull(appointment)) {
            return appointment.complete();
        }
        System.out.println("Appointment not found.");
        return false;
    }

    public static List<Appointment> viewUpcomingAppointments() {
        LocalDate today = LocalDate.now();
        System.out.println("===== Upcoming Appointments =====");
        for (Appointment a : appointmentList) {
            if (HelperUtils.isNotNull(a.getAppointmentDate()) && !a.getAppointmentDate().isBefore(today)
                    && a.getStatus().equals("Scheduled")) {
                a.displayInfo("");
                System.out.println("------------------------");
            }
        }
        return null;
    }

    public static void displayAllAppointments() {
        if (appointmentList.isEmpty()) {
            System.out.println("No appointments found.\n");
            return;
        }
        System.out.println("===== Appointments =====");
        for (Appointment a : appointmentList) {
            a.displayInfo("");
            System.out.println("------------------------");
        }
    }

    //overloaded methods
    public static Appointment createAppointment(String patientId, String doctorId, LocalDate date) {
        Appointment appointment = new Appointment();
        String generatedId = HelperUtils.generateId("Appt");
        appointment.setAppointmentId(generatedId);
        appointment.setPatientId(patientId);
        appointment.setDoctorId(doctorId);
        appointment.setAppointmentDate(date);
        appointment.setAppointmentTime("09:00");
        appointment.setReason("General Consultation");
        appointment.setStatus("Scheduled");
        save(appointment);
        return appointment;
    }

    public static Appointment createAppointment(String patientId, String doctorId, LocalDate date, String time) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(HelperUtils.generateId("Appt"));
        appointment.setPatientId(patientId);
        appointment.setDoctorId(doctorId);
        appointment.setAppointmentDate(date);
        appointment.setAppointmentTime(time);
        appointment.setStatus("Scheduled");
        save(appointment);
        return appointment;
    }

    public static Appointment createAppointment(Appointment appointment) {
        if (HelperUtils.isNull(appointment.getAppointmentId()) || appointment.getAppointmentId().isEmpty()) {
            appointment.setAppointmentId(HelperUtils.generateId("Appt"));
        }
        save(appointment);
        return appointment;
    }

    public static boolean rescheduleAppointment(String appointmentId, LocalDate newDate, String newTime) {
        Appointment appointment = getAppointmentById(appointmentId);
        if (HelperUtils.isNull(appointment)) {
            System.out.println("Appointment with ID " + appointmentId + " not found.");
            return false;
        }
        if (HelperUtils.isNull(newDate)) {
            System.out.println("New date cannot be null.");
            return false;
        }
        if (HelperUtils.isNull(newTime) || !newTime.matches("^([01]?\\d|2[0-3]):[0-5]\\d$")) {
            System.out.println("New time is invalid. Expected HH:mm (24-hour).");
            return false;
        }
        return appointment.reschedule(newDate, newTime); // delegate to entity
    }

    public static boolean rescheduleAppointment(Appointment appointment, LocalDate newDate, String newTime, String reason) {
        if (HelperUtils.isNull(appointment)) {
            System.out.println("Appointment object is null.");
            return false;
        }
        if (HelperUtils.isNull(newDate)) {
            System.out.println("New date cannot be null.");
            return false;
        }
        if (HelperUtils.isNull(newTime) || !newTime.matches("^([01]?\\d|2[0-3]):[0-5]\\d$")) {
            System.out.println("Invalid time format.");
            return false;
        }

        appointment.setAppointmentDate(newDate);
        appointment.setAppointmentTime(newTime);
        appointment.setReason(reason);
        appointment.setStatus("Rescheduled");
        System.out.println("Appointment " + appointment.getAppointmentId() +
                " rescheduled to " + newDate + " at " + newTime + ". Reason: " + reason);
        return true;
    }

    public static boolean rescheduleAppointmentById(String appointmentId, LocalDate newDate) {
        return rescheduleAppointmentById(appointmentId, newDate); // default time
    }

    public static void displayAppointments(LocalDate date) {
        List<Appointment> list = getAppointmentsByDate(date);
        if (list.isEmpty()) {
            System.out.println("No appointments found for " + date);
            return;
        }
        System.out.println("=== Appointments on " + date + " ===");
        for (Appointment a : list) {
            a.displayInfo("");
            System.out.println("------------------------");
        }
    }

    public static void displayAppointments(String doctorId, LocalDate startDate, LocalDate endDate) {
        System.out.println("=== Appointments for Doctor " + doctorId +
                " between " + startDate + " and " + endDate + " ===");
        boolean found = false;
        for (Appointment a : appointmentList) {
            if (HelperUtils.isNotNull(a.getDoctorId()) && a.getDoctorId().equals(doctorId)
                    && (a.getAppointmentDate().isEqual(startDate) || a.getAppointmentDate().isAfter(startDate))
                    && (a.getAppointmentDate().isEqual(endDate) || a.getAppointmentDate().isBefore(endDate))) {
                a.displayInfo("");
                System.out.println("------------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No appointments found for this range.");
        }
    }

    @Override
    public String add(Object entity) {
        if (entity instanceof Appointment) {
            appointmentList.add((Appointment) entity);
            return "Appointment added successfully!";
        }
        return "Invalid entity type. Expected Appointment.";
    }

    @Override
    public String remove(String id) {
        Appointment a = getAppointmentById(id);
        if (HelperUtils.isNotNull(a)) {
            appointmentList.remove(a);
            return "Appointment deleted: " + id;
        }
        return "Appointment not found: " + id;
    }

    @Override
    public String getAll() {
        if (appointmentList.isEmpty()) {
            return "No appointments available.";
        }
        StringBuilder sb = new StringBuilder();
        for (Appointment a : appointmentList) {
            sb.append(a.displayInfo(""));
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    @Override
    public String search(String keyword) {
        StringBuilder sb = new StringBuilder();
        for (Appointment a : appointmentList) {
            if (HelperUtils.isNotNull(a.getPatientId()) && a.getPatientId().toLowerCase().contains(keyword.toLowerCase())
                    || HelperUtils.isNotNull(a.getDoctorId()) && a.getDoctorId().toLowerCase().contains(keyword.toLowerCase())
                    || HelperUtils.isNotNull(a.getAppointmentDate()) && a.getAppointmentDate().toString().toLowerCase().contains(keyword.toLowerCase())
                    || HelperUtils.isNotNull(a.getAppointmentId()) && a.getAppointmentId().toLowerCase().contains(keyword.toLowerCase())) {
                sb.append(a.displayInfo(""));
                sb.append(System.lineSeparator());
            }
        }
        return sb.length() > 0 ? sb.toString() : "No appointments found for keyword: " + keyword;
    }

    @Override
    public String searchById(String id) {
        Appointment a = getAppointmentById(id);
        return HelperUtils.isNotNull(a) ? a.displayInfo("") : "Appointment not found: " + id;
    }

    @Override
    public void scheduleAppointment(Appointment appointment) {
        if (HelperUtils.isNull(appointment)) {
            System.out.println("Cannot schedule a null appointment.");
            return;
        }

        if (HelperUtils.isNull(appointment.getAppointmentId()) || appointment.getAppointmentId().isEmpty()) {
            appointment.setAppointmentId(HelperUtils.generateId("Appt"));
        }

        if (HelperUtils.isNull(appointment.getPatientId()) || HelperUtils.isNull(appointment.getDoctorId())) {
            System.out.println("Patient ID and Doctor ID are required to schedule an appointment.");
            return;
        }

        if (HelperUtils.isNull(appointment.getAppointmentDate())) {
            System.out.println("Appointment date is required.");
            return;
        }

        appointment.setStatus("Scheduled");
        save(appointment);
    }

    @Override
    public boolean cancelAppointment(String appointmentId) {
        if (HelperUtils.isNotNull(appointmentId)) {
            return AppointmentService.cancelAppointmentById(appointmentId);
        }
        return false;
    }

    @Override
    public void rescheduleAppointment(String appointmentId, LocalDate newDate) {
        AppointmentService.rescheduleAppointmentById(appointmentId, newDate);
    }

    public static void addSampleAppointments() {
       String[] appointmentDates = {"2026-04-01", "2026-04-02", "2026-04-03", "2026-04-04", "2026-04-05", "2026-04-06", "2026-04-07", "2026-04-08", "2026-04-09", "2026-04-10"};
        String[] appointmentTimes = {"09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"};
        String[] reasons = {"General Consultation", "Follow-up", "Surgery", "Lab Test", "Medication Review", "Dental Checkup", "Eye Exam", "Physical Therapy", "Psychological Counseling", "Nutrition Consultation"};
        for (int i = 0; i < 15; i++) {
            Appointment appointment = new Appointment();
            appointment.setAppointmentId("APP-123" + i);
            appointment.setPatientId("PAT-123" + i);
            appointment.setDoctorId("DR-123" + i);
            appointment.setAppointmentDate(LocalDate.parse(appointmentDates[i % appointmentDates.length]));
            appointment.setAppointmentTime(appointmentTimes[i % appointmentTimes.length]);
            appointment.setReason(reasons[i % reasons.length]);
            appointment.setStatus("Scheduled");
            appointment.setNotes("Notes for appointment " + i);

            save(appointment);
        }
    }
}
