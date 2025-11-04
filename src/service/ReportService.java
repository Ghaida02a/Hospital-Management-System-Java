package service;

import Utils.HelperUtils;
import entity.Appointment;
import entity.Department;
import entity.Doctor;
import entity.Patient;

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
                System.out.println("Time: " + appt.getAppointmentTime());
                System.out.println("Status: " + appt.getStatus());
                System.out.println("--------------------------------------------");
            }
        }
    }

    public static void generateDoctorPerformanceReport(String doctorId) {
        List<Doctor> doctors = DoctorService.getAllDoctors();  // get all doctors from DoctorService

        System.out.println("===== Doctor Performance Report =====");
        if (HelperUtils.isNull(doctors) || doctors.isEmpty()) {
            System.out.println("No doctors found in the system.");
            return;
        }

        for (Doctor doctor : doctors) {
            // Get appointments for each doctor
            List<Appointment> doctorAppointments = AppointmentService.getAppointmentsByDoctor(doctor.getDoctorId());
            long totalAppointments = doctorAppointments.size(); //total appointments for the doctor

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

            System.out.println("Doctor: " + doctor.getFirstName() + " " + doctor.getLastName());
            System.out.println("  Total Appointments: " + totalAppointments);
            System.out.println("  Completed: " + completedAppointments);
            System.out.println("  Cancelled: " + cancelledAppointments);
            System.out.println("-------------------------------------------");
        }
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
        List<Patient> patients = PatientService.getAllPatients(); // Assuming you have this method

        System.out.println("===== Emergency Cases Report =====");

        if (HelperUtils.isNull(patients) || patients.isEmpty()) {
            System.out.println("No patients found in the system.");
            return;
        }

        boolean found = false;
        for (Patient patient : patients) {
            String emergencyContact = patient.getEmergencyContact();
            String phone = patient.getPhoneNumber();

            boolean missingOrInvalid = HelperUtils.isNull(emergencyContact) || emergencyContact.trim().isEmpty();
            boolean sameAsPhone = HelperUtils.isNotNull(phone) && phone.equals(emergencyContact);

            if (missingOrInvalid || sameAsPhone) {
                found = true;
                System.out.println("------------------------------");
                System.out.println("Patient ID: " + patient.getId());
                System.out.println("Name: " + patient.getFirstName() + " " + patient.getLastName());
                System.out.println("Phone: " + phone);
                System.out.println("Emergency Contact: " + (missingOrInvalid ? "MISSING/INVALID" : emergencyContact));
                if (sameAsPhone) {
                    System.out.println("⚠️ Warning: Emergency contact same as patient phone number!");
                }
            }
        }

        if (!found) {
            System.out.println("All patients have valid emergency contact information.");
        }
    }
}
