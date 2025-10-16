package service;

import Utils.HelperUtils;
import entity.Allergies;
import entity.Patient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PatientService {
    public static List<Patient> patientList = new ArrayList<>();
    public static Scanner scanner = new Scanner(System.in);

    public static Patient addPatient() {
        Patient patient = new Patient();

        System.out.print("Enter Patient ID: ");
        String idInput = scanner.nextLine();
        if (!HelperUtils.checkIfIdExists(patientList, idInput)) {
            patient.setId(idInput);
        }

        System.out.print("Enter First Name: ");
        patient.setFirstName(scanner.nextLine());

        System.out.print("Enter Last Name: ");
        patient.setLastName(scanner.nextLine());

        System.out.println("Enter Date of Birth (YYYY-MM-DD): ");
        LocalDate dob = LocalDate.parse(scanner.nextLine());
        patient.setDateOfBirth(dob);

        System.out.print("Enter Gender: ");
        patient.setGender(scanner.nextLine());

        System.out.print("Enter Phone Number: ");
        patient.setPhoneNumber(scanner.nextLine());

        System.out.print("Enter Email: ");
        patient.setEmail(scanner.nextLine());

        System.out.print("Enter Address: ");
        patient.setAddress(scanner.nextLine());

        System.out.print("Enter Blood Group: ");
        patient.setBloodGroup(scanner.nextLine());

        System.out.print("Emergency Contact: ");
        String emergencyInput = scanner.nextLine();
        patient.setEmergencyContact(emergencyInput);

        System.out.print("Insurance ID: ");
        String insuranceId = scanner.nextLine();
        patient.setInsuranceId(insuranceId);


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
        System.out.print("Enter Allergy ID: ");
        String allergyId = scanner.nextLine();

        System.out.print("Enter Allergy Name (e.g., Peanuts): ");
        String allergyName = scanner.nextLine();

        System.out.print("Enter Allergy Type (e.g., Food, Drug, Environmental): ");
        String allergyType = scanner.nextLine();

        Allergies newAllergy = new Allergies(allergyId, allergyName, allergyType);

        if (patient.getAllergies() == null) {
            patient.setAllergies(new ArrayList<>());
        }
        patient.getAllergies().add(newAllergy);

        System.out.println("Successfully added allergy '" + allergyName + "' to patient " + patientId + ".\n");
    }

    public static void save(Patient patient) {
        if (patient != null) {
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
            patient.displayInfo();
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
}
