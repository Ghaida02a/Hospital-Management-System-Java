package service;

import Utils.HelperUtils;
import Utils.InputHandler;
import entity.Allergies;
import entity.Patient;

import java.lang.ref.SoftReference;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PatientService {
    public static List<Patient> patientList = new ArrayList<>();

    public static Patient addPatient() {
        Patient patient = new Patient();

        // Generate ID
        String generatedId;
        do {
            generatedId = HelperUtils.generateId("PAT");
        }
        while (HelperUtils.checkIfIdExists(patientList, generatedId)); // ensure uniqueness
        patient.setId(generatedId);
        System.out.println("Patient ID: " + patient.getId());

        // Name
        patient.setFirstName(InputHandler.getStringInput("Enter First Name: "));
        patient.setLastName(InputHandler.getStringInput("Enter Last Name: "));

        //Date
        patient.setDateOfBirth(InputHandler.getDateInput("Enter Date of Birth"));

        //Gender
        patient.setGender(InputHandler.getGenderInput("Enter Gender: "));

        // Phone
        patient.setPhoneNumber(InputHandler.getPhoneNumberInput("Enter Phone Number: "));

        // Email
        patient.setEmail(InputHandler.getEmailInput("Enter Email: "));

        // Address
        patient.setAddress(InputHandler.getStringInput("Enter Address: "));

        // Blood Group
        patient.setBloodGroup(InputHandler.getStringInput("Enter Blood Group: "));

        // Emergency Contact
        patient.setEmergencyContact(InputHandler.getPhoneNumberInput("Enter Emergency Contact Phone number: "));

        // Insurance ID
        patient.setInsuranceId(InputHandler.getStringInput("Enter Insurance ID: "));

        // Registration Date
        patient.setRegistrationDate(LocalDate.now());
        System.out.println("Registration Date set to today: " + patient.getRegistrationDate());

        return patient;
    }

    public static void addAllergyToPatient(String patientId) {
        Patient patient = getPatientById(patientId);
        if (patient == null) {
            System.out.println("Error: Patient with ID " + patientId + " not found.\n");
            return;
        }

        System.out.println("\n--- Adding Allergy for " + patient.getFirstName() + " ---");

        System.out.println("Allergy Id:");
        String allergyId = HelperUtils.getRandomNumber(2);

        String allergyName = InputHandler.getStringInput("Enter Allergy Name (e.g., Peanuts): ");

        Allergies newAllergy = new Allergies(allergyId, allergyName);

        if (patient.getAllergies() == null) {
            patient.setAllergies(new ArrayList<>());
        }
        patient.getAllergies().add(newAllergy);

        System.out.println("Successfully added allergy '" + allergyName + "' to patient " + patientId + ".\n");
    }

    public static void save(Patient patient) {
        if (HelperUtils.isNotNull(patient)) {
            patientList.add(patient);
            System.out.println("Patient added successfully!\n");
        }
    }

    public static void editPatient(String patientId, Patient updatedPatient) {
        if (patientList.isEmpty()) {
            System.out.println("No Patients available to edit.");
            return;
        }
        for (int i = 0; i < patientList.size(); i++) {
            if (patientList.get(i).getId().equals(patientId)) {
                patientList.set(i, updatedPatient);
                System.out.println("Patient updated successfully!\n");
                return;
            }
        }
        System.out.println("Patient with ID " + patientId + " not found.\n");
    }

    public static void removePatient(String patientId) {
        if (patientList.isEmpty()) {
            System.out.println("The list is empty. No patient removed.");
            return;
        }

        boolean removed = patientList.removeIf(patient -> patient.getId().equals(patientId));

        if (removed) {
            System.out.println("Patient with ID " + patientId + " removed successfully!");
        } else {
            System.out.println("Patient with ID " + patientId + " not found.");
        }
    }

    public static Patient getPatientById(String patientId) {
        for (Patient patient : patientList) {
            if (patient.getId().equals(patientId)) {
                return patient;
            }
        }
        System.out.println("Patient not found.\n");
        return null;
    }

    public static void displayAllPatients() {
        if (patientList.isEmpty()) {
            System.out.println("No patients found.\n");
            return;
        }

        System.out.println("===== Patients List =====");
        for (Patient patient : patientList) {
            patient.displayInfo("");
            System.out.println("------------------------");
        }
        System.out.println("========================\n");
    }

    public static List<Patient> searchPatientsByName(String name) {
        List<Patient> searchResults = new ArrayList<>();
        String searchName = name.toLowerCase();

        for (Patient patient : patientList) {
            if (patient.getFirstName().toLowerCase().contains(searchName)) {
                searchResults.add(patient);
            }
        }
        return searchResults;
    }

    public static void viewPatientMedicalHistory(Integer patientId) {

        Patient patient = PatientService.getPatientById(String.valueOf(patientId));

        if (patient != null) {
            System.out.println("\n===== Patient Medical History =====");
            patient.displayInfo("");

            if (patient.getAllergies() != null && !patient.getAllergies().isEmpty()) {
                System.out.println("\nAllergies:");
                for (Allergies allergy : patient.getAllergies()) {
                    System.out.println("- " + allergy.getAllergyName());
                }
            } else {
                System.out.println("\nNo allergies recorded.");
            }

            System.out.println("===================================\n");
        }
    }
}
