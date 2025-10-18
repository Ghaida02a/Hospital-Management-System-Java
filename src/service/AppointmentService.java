package service;

import entity.Appointment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppointmentService {
    public static List<Appointment> appointmentList = new ArrayList<>();
    public static Scanner scanner = new Scanner(System.in);

    public static Appointment addAppointment() {
        System.out.println("--- Add Appointment ---");
        System.out.print("Enter Appointment ID: ");
        String appointmentId = scanner.nextLine().trim();

        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine().trim();

        System.out.print("Enter Doctor ID: ");
        String doctorId = scanner.nextLine().trim();

        LocalDate date = null;
        boolean validDate = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (!validDate) {
            System.out.print("Enter Appointment Date (yyyy-MM-dd): ");
            String dateInput = scanner.nextLine();

            if (dateInput.matches("\\d{4}-\\d{2}-\\d{2}")) {
                try {
                    date = LocalDate.parse(dateInput, formatter);
                    validDate = true;
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date. Please check the values.");
                }
            } else {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            }
        }

        String time = "";
        boolean validTime = false;

        while (!validTime) {
            System.out.print("Enter Appointment Time (e.g., 09:30 or 14:00): ");
            time = scanner.nextLine().trim();
            validTime = time.matches("^([01]?\\d|2[0-3]):[0-5]\\d$");

            if (!validTime) {
                System.out.println("Invalid time format. Use HH:mm (24-hour).");
            }
        }

        System.out.println("Appointment set for " + date + " at " + time);

        System.out.print("Enter Reason (optional): ");
        String reason = scanner.nextLine().trim();

        System.out.print("Enter Notes (optional): ");
        String notes = scanner.nextLine().trim();

        String status = "Scheduled";

        Appointment appt = new Appointment(appointmentId, patientId, doctorId, date, time, status, reason, notes);
        return appt;
    }

    public static void save(Appointment appointment) {
        if (appointment != null) {
            appointmentList.add(appointment);
            System.out.println("Appointment saved successfully!\n");
        }
    }

    public static Appointment getAppointmentById(String appointmentId) {
        for (Appointment a : appointmentList) {
            if (a.getAppointmentId().equals(appointmentId)) return a;
        }
        return null;
    }

    public static List<Appointment> getAppointmentsByPatient(String patientId) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment a : appointmentList) {
            if (a.getPatientId() != null && a.getPatientId().equals(patientId)) result.add(a);
        }
        if (result.isEmpty()) System.out.println("No appointments found for patient ID: " + patientId);
        return result;
    }

    public static List<Appointment> getAppointmentsByDoctor(String doctorId) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment a : appointmentList) {
            if (a.getDoctorId() != null && a.getDoctorId().equals(doctorId)) result.add(a);
        }
        if (result.isEmpty()) System.out.println("No appointments found for doctor ID: " + doctorId);
        return result;
    }

    public static List<Appointment> getAppointmentsByDate(LocalDate date) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment a : appointmentList) {
            if (a.getAppointmentDate() != null && a.getAppointmentDate().equals(date)) result.add(a);
        }
        if (result.isEmpty()) System.out.println("No appointments found for date: " + date);
        return result;
    }

    public static boolean rescheduleAppointment(String appointmentId, LocalDate newDate, String newTime) {
        Appointment a = getAppointmentById(appointmentId);
        if (a == null) {
            System.out.println("Appointment with ID " + appointmentId + " not found.");
            return false;
        }
        if (newDate == null) {
            System.out.println("New date cannot be null.");
            return false;
        }
        if (newTime == null || !newTime.matches("^([01]?\\d|2[0-3]):[0-5]\\d$")) {
            System.out.println("New time is invalid. Expected HH:mm (24-hour).");
            return false;
        }
        a.setAppointmentDate(newDate);
        a.setAppointmentTime(newTime);
        a.setStatus("Rescheduled");
        System.out.println("Appointment " + appointmentId + " rescheduled to " + newDate + " " + newTime);
        return true;
    }

    public static boolean cancelAppointment(String appointmentId) {
        Appointment a = getAppointmentById(appointmentId);
        if (a == null) {
            System.out.println("Appointment with ID " + appointmentId + " not found.");
            return false;
        }
        a.setStatus("Cancelled");
        System.out.println("Appointment " + appointmentId + " cancelled.");
        return true;
    }

    public static boolean completeAppointment(String appointmentId) {
        Appointment a = getAppointmentById(appointmentId);
        if (a == null) {
            System.out.println("Appointment with ID " + appointmentId + " not found.");
            return false;
        }
        a.setStatus("Completed");
        System.out.println("Appointment " + appointmentId + " marked as completed.");
        return true;
    }

    public static void viewUpcomingAppointments(){
        LocalDate today = LocalDate.now();
        System.out.println("===== Upcoming Appointments =====");
        for (Appointment a : appointmentList) {
            if (a.getAppointmentDate() != null && !a.getAppointmentDate().isBefore(today) && a.getStatus().equals("Scheduled")) {
                a.displayInfo();
                System.out.println("------------------------");
            }
        }
    }

    public static void displayAllAppointments() {
        if (appointmentList.isEmpty()) {
            System.out.println("No appointments found.\n");
            return;
        }
        System.out.println("===== Appointments =====");
        for (Appointment a : appointmentList) {
            a.displayInfo();
            System.out.println("------------------------");
        }
    }
}
