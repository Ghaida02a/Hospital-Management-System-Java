package service;

import entity.Doctor;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class DoctorService {
    public static List<Doctor> doctorsList = new ArrayList<>();
    public static Scanner scanner = new Scanner(System.in);

    public static Doctor addDoctor() {
        Doctor doctor = new Doctor();

        System.out.print("Enter Doctor ID: ");
        String idInput = scanner.nextLine();
        doctor.setId(idInput);
        doctor.setDoctorId(idInput);

        System.out.print("Enter First Name: ");
        doctor.setFirstName(scanner.nextLine());

        System.out.print("Enter Last Name: ");
        doctor.setLastName(scanner.nextLine());

        System.out.print("Enter Gender: ");
        doctor.setGender(scanner.nextLine());

        System.out.print("Enter Phone Number: ");
        doctor.setPhoneNumber(scanner.nextLine());

        System.out.print("Enter Email: ");
        doctor.setEmail(scanner.nextLine());

        System.out.print("Enter Address: ");
        doctor.setAddress(scanner.nextLine());

        System.out.print("Enter Specialization: ");
        doctor.setSpecialization(scanner.nextLine());

        System.out.print("Enter Qualification: ");
        doctor.setQualification(scanner.nextLine());

        boolean validExp = false;
        while (!validExp) {
            System.out.print("Enter Experience Years (integer) or leave blank for 0: ");
            String expInput = scanner.nextLine().trim();
            if (expInput.isEmpty()) {
                doctor.setExperienceYears(0);
                validExp = true;
            } else if (expInput.matches("\\d+")) {
                doctor.setExperienceYears(Integer.parseInt(expInput));
                validExp = true;
            } else {
                System.out.println("Invalid number. Please enter an integer.");
            }
        }

        System.out.print("Enter Department ID: ");
        doctor.setDepartmentId(scanner.nextLine());

        boolean validFee = false;
        while (!validFee) {
            System.out.print("Enter Consultation Fee (number) or leave blank for 0: ");
            String feeInput = scanner.nextLine().trim();
            if (feeInput.isEmpty()) {
                doctor.setConsultationFee(0.0);
                validFee = true;
            } else if (feeInput.matches("\\d+(\\.\\d+)?")) {
                doctor.setConsultationFee(Double.parseDouble(feeInput));
                validFee = true;
            } else {
                System.out.println("Invalid number. Please enter a valid fee (e.g., 50.0).");
            }
        }

        // Available slots (comma-separated integers 0-23)
        System.out.print("Enter available slots as comma-separated integers (0-23) or leave blank: ");
        String slotsInput = scanner.nextLine().trim();
        List<Integer> slots = new ArrayList<>();
        if (!slotsInput.isEmpty()) {
            String[] parts = slotsInput.split(",");
            Set<Integer> seen = new LinkedHashSet<>();
            for (String p : parts) {
                String t = p.trim();
                if (t.matches("\\d+")) {
                    int val = Integer.parseInt(t);
                    if (val < 0 || val > 23) {
                        System.out.println("Warning: invalid slot '" + t + "' ignored (expected 0-23).");
                        continue;
                    }
                    seen.add(val);
                } else {
                    System.out.println("Warning: invalid slot '" + p + "' ignored.");
                }
            }
            slots.addAll(seen);
        }
        doctor.setAvailableSlots(slots);

//        doctor.setAssignedPatients(new ArrayList<>());

        System.out.print("Enter Availability (true/false or yes/no) or press Enter to infer from provided slots: ");
        String availInput = scanner.nextLine().trim();
        doctor.setAvailable(Boolean.parseBoolean(availInput));
        return doctor;
    }


    public static void save(Doctor doctor) {
        doctorsList.add(doctor);
        System.out.println("Doctor added successfully!\n");
    }

    public static void editDoctor(String doctorId, Doctor updatedDoctor) {
        for (int i = 0; i < doctorsList.size(); i++) {
            if (doctorsList.get(i).getId().equals(doctorId)) {
                doctorsList.set(i, updatedDoctor);
                System.out.println("Doctor with ID " + doctorId + " has been updated.");
                return;
            }
        }
        System.out.println("Doctor with ID " + doctorId + " not found.");
    }

    public static boolean assignPatientToDoctor(String doctorId, String patientId) {
        if (doctorId == null || doctorId.trim().isEmpty()) {
            System.out.println("Invalid doctor ID.");
            return false;
        }
        if (patientId == null || patientId.trim().isEmpty()) {
            System.out.println("Invalid patient ID.");
            return false;
        }

        Doctor found = null;
        for (Doctor d : doctorsList) {
            if (d != null && doctorId.equals(d.getId())) {
                found = d;
                break;
            }
        }
        if (found == null) {
            System.out.println("Doctor with ID " + doctorId + " not found.");
            return false;
        }

        // Use PatientService to find patient
        entity.Patient patient = PatientService.getPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient with ID " + patientId + " not found.");
            return false;
        }

        boolean assigned = found.assignPatient(patient);
        if (assigned) {
            System.out.println("Patient " + patientId + " assigned to Doctor " + doctorId + ".");
        } else {
            System.out.println("Failed to assign patient " + patientId + " to Doctor " + doctorId + ".");
        }
        return assigned;
    }

    public static void removeDoctor(String doctorId) {
        boolean removed = doctorsList.removeIf(doctor -> doctor.getId().equals(doctorId));
        if (removed) {
            System.out.println("Doctor with ID " + doctorId + " has been removed.");
        } else {
            System.out.println("Doctor with ID " + doctorId + " not found.");
        }
    }

    public static void getDoctorById(String doctorId) {
        for (Doctor doctor : doctorsList) {
            if (doctor.getId().equals(doctorId)) {
                System.out.println("Doctor found: " + doctor);
                return;
            }
        }
        System.out.println("Doctor with ID " + doctorId + " not found.");
    }

    public static void displayAllDoctors() {
        if (doctorsList.isEmpty()) {
            System.out.println("No doctors found.\n");
            return;
        }

        System.out.println("===== Doctors List =====");
        for (Doctor doctor : doctorsList) {
            doctor.displayInfo();
            System.out.println("------------------------");
        }
    }

    public static List<Doctor> getDoctorsBySpecialization(String specialization) {
        List<Doctor> specializedDoctors = new ArrayList<>();
        for (Doctor doctor : doctorsList) {
            if (doctor.getSpecialization().equalsIgnoreCase(specialization)) {
                specializedDoctors.add(doctor);
            }
        }
        if (specializedDoctors.isEmpty()) {
            System.out.println("No doctors found with specialization: " + specialization);
        } else {
            System.out.println("Doctors with specialization " + specialization + ":");
            for (Doctor doc : specializedDoctors) {
                System.out.println(doc);
            }
        }
        return specializedDoctors;
    }

    public static List<Doctor> getAvailableDoctors() {
        List<Doctor> availableDoctors = new ArrayList<>();
        for (Doctor doctor : doctorsList) {
            if (doctor.isAvailable()) {
                availableDoctors.add(doctor);
            }
        }
        if (availableDoctors.isEmpty()) {
            System.out.println("No available doctors found.");
        } else {
            System.out.println("Available Doctors:");
            for (Doctor doc : availableDoctors) {
                System.out.println(doc);
            }
        }
        return availableDoctors;
    }
}
