package service;

import Utils.HelperUtils;
import Utils.InputHandler;
import entity.Doctor;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class DoctorService {
    public static List<Doctor> doctorsList = new ArrayList<>();

    public static Doctor addDoctor() {
        Doctor doctor = new Doctor();

        String generatedId;
        do {
            generatedId = HelperUtils.generateId("DR");
        }
        while (HelperUtils.checkIfIdExists(doctorsList, generatedId)); // ensure uniqueness
        doctor.setId(generatedId);
        System.out.println("Doctor ID: " + doctor.getId());

        doctor.setFirstName(InputHandler.getStringInput("Enter First Name: "));
        doctor.setLastName(InputHandler.getStringInput("Enter Last Name: "));

        doctor.setDateOfBirth(InputHandler.getDateInput("Enter Date of Birth"));

        doctor.setGender(InputHandler.getGenderInput("Enter Gender: "));

        doctor.setPhoneNumber(InputHandler.getPhoneNumberInput("Enter Phone Number: "));

        doctor.setEmail(InputHandler.getEmailInput("Enter Email: "));

        doctor.setAddress(InputHandler.getStringInput("Enter Address: "));

        doctor.setSpecialization(InputHandler.getStringInput("Enter Specialization: "));

        doctor.setQualification(InputHandler.getStringInput("Enter Qualification: "));

        doctor.setExperienceYears(InputHandler.getIntInput("Enter Experience Years: "));

        doctor.setDepartmentId(InputHandler.getStringInput("Enter Department ID: "));

        doctor.setConsultationFee(InputHandler.getDoubleInput("Enter Consultation Fee: "));


        // Available slots (comma-separated integers 0-23)
//        System.out.print("Enter available slots as comma-separated integers (0-23) or leave blank: ");
        String slotsInput = InputHandler.getIntInput("Enter available slots: ", 0, 23).toString();
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

        doctor.setAvailable(InputHandler.getConfirmation("Is the doctor available?"));
        return doctor;
    }


    public static void save(Doctor doctor) {
        doctorsList.add(doctor);
        System.out.println("\n===== Doctor Added Successfully =====\n");
    }

    public static void editDoctor(String doctorId, Doctor updatedDoctor) {
        Doctor found = getDoctorById(doctorId);
        if (found != null) {
            int index = doctorsList.indexOf(found);
            doctorsList.set(index, updatedDoctor);
            System.out.println("Doctor with ID " + doctorId + " has been updated.");
        } else {
            System.out.println("Doctor with ID " + doctorId + " not found.");
        }
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

        Doctor found = getDoctorById(doctorId); // use new method
        if (found == null) {
            System.out.println("Doctor with ID " + doctorId + " not found.");
            return false;
        }

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
        Doctor found = getDoctorById(doctorId);
        if (found != null) {
            doctorsList.remove(found);
            System.out.println("Doctor with ID " + doctorId + " has been removed.");
        } else {
            System.out.println("Doctor with ID " + doctorId + " not found.");
        }
    }

    public static Doctor getDoctorById(String doctorId) {
        for (Doctor doctor : doctorsList) {
            if (doctor.getId().equals(doctorId)) {
                return doctor;
            }
        }
        return null; // not found
    }

    public static void displayAllDoctors() {
        if (doctorsList.isEmpty()) {
            System.out.println("No doctors found.\n");
            return;
        }

        System.out.println("===== Doctors List =====");
        for (Doctor doctor : doctorsList) {
            doctor.displayInfo("");
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
                doc.displayInfo("");
                System.out.println("------------------------");
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
