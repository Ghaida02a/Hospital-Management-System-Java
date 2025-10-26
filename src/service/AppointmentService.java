package service;

import Utils.HelperUtils;
import Utils.InputHandler;
import entity.Appointment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Interface.Manageable;
import Interface.Searchable;

public class AppointmentService implements Manageable, Searchable {
    public static List<Appointment> appointmentList = new ArrayList<>();

    @Override
    public String add(Object entity) {
        if (entity instanceof Appointment) {
            Appointment appt = (Appointment) entity;
            save(appt);
            return "Appointment added: " + appt.getAppointmentId();
        }
        return "Invalid entity type for AppointmentService.add";
    }

    @Override
    public String remove(String id) {
        if (id == null) {
            return "Invalid id";
        }
        boolean removed = appointmentList.removeIf(a -> a.getAppointmentId() != null && a.getAppointmentId().equals(id));
        return removed ? "Appointment " + id + " removed." : "Appointment " + id + " not found.";
    }

    @Override
    public String getAll() {
        if (appointmentList.isEmpty()) {
            return "No appointments found.";
        }
        StringBuilder sb = new StringBuilder();
        for (Appointment a : appointmentList) {
            sb.append("Appointment ID: ").append(a.getAppointmentId())
                    .append(", Patient ID: ").append(a.getPatientId())
                    .append(", Doctor ID: ").append(a.getDoctorId())
                    .append(", Date: ").append(a.getAppointmentDate())
                    .append(", Time: ").append(a.getAppointmentTime())
                    .append(", Status: ").append(a.getStatus())
                    .append("\n");
        }
        return sb.toString();
    }

    @Override
    public String search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return "No search keyword provided.";
        String key = keyword.toLowerCase();
        StringBuilder sb = new StringBuilder();
        for (Appointment a : appointmentList) {
            if ((a.getAppointmentId() != null && a.getAppointmentId().toLowerCase().contains(key))
                    || (a.getPatientId() != null && a.getPatientId().toLowerCase().contains(key))
                    || (a.getDoctorId() != null && a.getDoctorId().toLowerCase().contains(key))
                    || (a.getReason() != null && a.getReason().toLowerCase().contains(key))
                    || (a.getStatus() != null && a.getStatus().toLowerCase().contains(key))
                    || (a.getAppointmentDate() != null && a.getAppointmentDate().toString().contains(key))) {
                sb.append("Appointment ID: ").append(a.getAppointmentId())
                        .append(", Patient ID: ").append(a.getPatientId())
                        .append(", Doctor ID: ").append(a.getDoctorId())
                        .append(", Date: ").append(a.getAppointmentDate())
                        .append(", Time: ").append(a.getAppointmentTime())
                        .append(", Status: ").append(a.getStatus())
                        .append("\n");
            }
        }
        return sb.length() == 0 ? "No appointments matched '" + keyword + "'." : sb.toString();
    }


    @Override
    public String searchById(String id) {
        Appointment found = getAppointmentById(id);
        return found != null
                ? "Found appointment: " + found.getAppointmentId()
                : "Appointment not found.";
    }

    public static Appointment addAppointment() {
        Appointment appointment = new Appointment();
        System.out.println("--- Add Appointment ---");
        String generatedId = HelperUtils.generateId("Appt");
        appointment.setAppointmentId(generatedId);
        System.out.println("Appointment ID: " + appointment.getAppointmentId());

        appointment.setPatientId(InputHandler.getStringInput("Enter Patient ID: "));

        appointment.setDoctorId(InputHandler.getStringInput("Enter Doctor ID: "));

        appointment.setAppointmentDate(InputHandler.getDateInput("Enter Appointment Date"));

        appointment.setAppointmentTime(InputHandler.getTimeInput("Enter Appointment Time").toString());

        System.out.println("Appointment set for " + appointment.getAppointmentDate() + " at " + appointment.getAppointmentTime());

        appointment.setReason(InputHandler.getStringInput("Enter Reason (optional): "));

        appointment.setNotes(InputHandler.getStringInput("Notes (optional): "));

        appointment.setStatus("Scheduled");

        return appointment;
    }

    public static void save(Appointment appointment) {
        if (appointment != null) {
            appointmentList.add(appointment);
            System.out.println("Appointment saved successfully!\n");
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
            if (a.getPatientId() != null && a.getPatientId().equals(patientId)) result.add(a);
        }
        if (result.isEmpty()) {
            System.out.println("No appointments found for patient ID: " + patientId);
        }
        return result;
    }

    public static List<Appointment> getAppointmentsByDoctor(String doctorId) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment appointment : appointmentList) {
            if (appointment.getDoctorId() != null && appointment.getDoctorId().equals(doctorId))
                result.add(appointment);
        }
        if (result.isEmpty()) System.out.println("No appointments found for doctor ID: " + doctorId);
        return result;
    }

    public static List<Appointment> getAppointmentsByDate(LocalDate date) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentDate() != null && appointment.getAppointmentDate().equals(date))
                result.add(appointment);
        }
        if (result.isEmpty()) System.out.println("No appointments found for date: " + date);
        return result;
    }

    public static boolean rescheduleAppointment(String appointmentId, LocalDate newDate, String newTime) {
        Appointment appointment = getAppointmentById(appointmentId);
        if (appointment == null) {
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
        appointment.setAppointmentDate(newDate);
        appointment.setAppointmentTime(newTime);
        appointment.setStatus("Rescheduled");
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

    public static void viewUpcomingAppointments() {
        LocalDate today = LocalDate.now();
        System.out.println("===== Upcoming Appointments =====");
        for (Appointment a : appointmentList) {
            if (a.getAppointmentDate() != null && !a.getAppointmentDate().isBefore(today) && a.getStatus().equals("Scheduled")) {
                a.displayInfo("");
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
            a.displayInfo("");
            System.out.println("------------------------");
        }
    }

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
        if (appointment.getAppointmentId() == null || appointment.getAppointmentId().isEmpty()) {
            appointment.setAppointmentId(HelperUtils.generateId("Appt"));
        }
        save(appointment);
        return appointment;
    }

    public static boolean rescheduleAppointment(String appointmentId, LocalDate newDate) {
        return rescheduleAppointment(appointmentId, newDate, "09:00"); // default time
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
}
