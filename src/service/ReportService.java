package service;

import Utils.HelperUtils;
import entity.*;

import java.util.List;
import java.time.LocalDate;

public class ReportService {


    public static void generateDailyAppointmentsReport(LocalDate date) {
        List<Appointment> dailyAppointments = AppointmentService.getAppointmentsByDate(date);

        if (HelperUtils.isNotNull(dailyAppointments)) {
            System.out.println("===== Daily Appointments Report for " + date + " =====");
            for (Appointment appt : dailyAppointments) {
                System.out.println("Appointment ID: " + appt.getAppointmentId());
                System.out.println("Patient ID: " + appt.getPatientId());
                System.out.println("Doctor ID: " + appt.getDoctorId());
                System.out.println("Date: " + appt.getAppointmentDate());
                System.out.println("Time: " + appt.getAppointmentTime());
                System.out.println("Status: " + appt.getStatus());
                System.out.println("Reason: " + appt.getReason());
                System.out.println("Notes: " + appt.getNotes());
                System.out.println("--------------------------------------------");
            }
        }
    }

    public static void generateDoctorPerformanceReport(String doctorId) {
        System.out.println("===== Doctor Performance Report =====");

        // Get the doctor by ID
        Doctor doctor = DoctorService.getDoctorById(doctorId);

        if (HelperUtils.isNull(doctor)) {
            System.out.println("Doctor not found in the system.");
            return;
        }

        // Get all appointments for this doctor
        List<Appointment> doctorAppointments = AppointmentService.getAppointmentsByDoctor(doctor.getDoctorId());

        long totalAppointments = doctorAppointments.size();
        long completedAppointments = 0;
        long cancelledAppointments = 0;

        // Count completed and cancelled appointments
        for (Appointment appointment : doctorAppointments) {
            String status = appointment.getStatus();
            if (HelperUtils.isNotNull(status)) {
                if (status.equalsIgnoreCase("Completed")) {
                    completedAppointments++;
                } else if (status.equalsIgnoreCase("Cancelled")) {
                    cancelledAppointments++;
                }
            }
        }

        // Print the report for Dr performance
        System.out.println("Doctor: " + doctor.getFirstName() + " " + doctor.getLastName());
        System.out.println("  Total Appointments: " + totalAppointments);
        System.out.println("  Completed: " + completedAppointments);
        System.out.println("  Cancelled: " + cancelledAppointments);
        System.out.println("-------------------------------------------");
    }


    public static void generateDepartmentOccupancyReport(String departmentId) {
        List<Department> departments = DepartmentService.getAllDepartments();  // get all departments from DepartmentService

        System.out.println("===== Department Occupancy Report =====");
        if (HelperUtils.isNull(departments)) {
            System.out.println("No Department found in the system.");
            return;
        }

        for (Department department : departments) {
            if (departmentId.equalsIgnoreCase(department.getDepartmentId())) {
                System.out.println("Department: " + department.getDepartmentName());
                System.out.println("  Bed Capacity: " + department.getBedCapacity());
                System.out.println("  Available Beds: " + department.getAvailableBeds());
                System.out.println("  Occupied Beds: " + (department.getBedCapacity() - department.getAvailableBeds()));
                System.out.println("-------------------------------------------");

                List<Appointment> doctorAppointments = AppointmentService.getAppointmentsByDoctor(department.getDepartmentId());
                long totalAppointments = doctorAppointments.size(); //total appointments

                long completedAppointments = 0; //count of completed appointments
                long cancelledAppointments = 0; // count of cancelled appointments

                for (Appointment appointment : doctorAppointments) {
                    String status = appointment.getStatus();
                    if (status.equalsIgnoreCase("Completed")) {
                        completedAppointments++;
                    } else if (status.equalsIgnoreCase("Cancelled")) {
                        cancelledAppointments++;
                    }
                }

                System.out.println("Department Name: " + department.getDepartmentName());
                System.out.println("  Total Appointments: " + totalAppointments);
                System.out.println("  Completed: " + completedAppointments);
                System.out.println("  Cancelled: " + cancelledAppointments);
                System.out.println("-------------------------------------------");
            }
        }
    }

    public static void generatePatientStatisticsReport(String patientId) {
        Patient patient = PatientService.getPatientById(patientId);
        if (HelperUtils.isNull(patient)) {
            System.out.println("Patient with ID " + patientId + " not found.");
            return;
        }

        List<Appointment> patientAppointments = AppointmentService.getAppointmentsByPatient(patientId);
        long totalAppointments = patientAppointments.size();

        long completedAppointments = 0;
        long cancelledAppointments = 0;

        for (Appointment appointment : patientAppointments) {
            String status = appointment.getStatus();
            if (status.equalsIgnoreCase("Completed")) {
                completedAppointments++;
            } else if (status.equalsIgnoreCase("Cancelled")) {
                cancelledAppointments++;
            }
        }

        System.out.println("===== Patient Statistics Report =====");
        System.out.println("Patient Name: " + patient.getFirstName() + " " + patient.getLastName());
        System.out.println("Total Appointments: " + totalAppointments);
        System.out.println("Completed Appointments: " + completedAppointments);
        System.out.println("Cancelled Appointments: " + cancelledAppointments);
        System.out.println("-------------------------------------------");
    }

    public static void generateEmergencyCasesReport(String patientId) {
        System.out.println("===== Emergency Case Report =====");

        Patient p = PatientService.getPatientById(patientId);
        if (HelperUtils.isNull(p)) {
            System.out.println("Patient not found with ID: " + patientId);
            return;
        }

        System.out.println("Patient ID: " + p.getPatientId());
        System.out.println("Name: " + p.getFirstName() + " " + p.getLastName());
        System.out.println("Phone: " + p.getPhoneNumber());
        System.out.println("Address: " + p.getAddress());
        System.out.println("Emergency Contact: " + p.getEmergencyContact());
        System.out.println("Blood Type: " + p.getBloodGroup());
        System.out.println("Allergies: " + p.getAllergies());
        System.out.println("-------------------------------------------");
    }

}
