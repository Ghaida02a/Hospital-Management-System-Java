package service;

import Interface.Manageable;
import Interface.Searchable;
import Utils.HelperUtils;
import Utils.InputHandler;
import entity.Allergies;
import entity.Patient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientService implements Manageable, Searchable {
    public static List<Patient> patientList = new ArrayList<>();

    public static Patient addPatient() {
        Patient patient = new Patient();

        initializePatient(patient);

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

        return patient;
    }

    public static Patient addPatient(String firstName, String lastName, String phone) { // minimal info
        Patient patient = new Patient();
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setPhoneNumber(phone);
        return initializePatient(patient);
    }

    private static Patient initializePatient(Patient patient) {
        // Generate ID
        String generatedId;
        do {
            generatedId = HelperUtils.generateId("PAT");
        }
        while (HelperUtils.checkIfIdExists(patientList, generatedId)); // ensure uniqueness
        patient.setId(generatedId);
        System.out.println("Patient ID: " + patient.getId());

        // Registration Date
        patient.setRegistrationDate(LocalDate.now());
        System.out.println("Registration Date set to today: " + patient.getRegistrationDate());

        return patient;
    }

    // with blood group
    public static Patient addPatient(String firstName, String lastName, String phone, String bloodGroup, String email) {
        Patient patient = new Patient();
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setPhoneNumber(phone);
        patient.setBloodGroup(bloodGroup);
        patient.setEmail(email);
        return initializePatient(patient);
    }

    //full object
    public static Patient addPatient(Patient patient) {
        return initializePatient(patient);
    }

    // Search by keyword (any field)
    public static List<Patient> searchPatients(String keyword) {
        List<Patient> results = new ArrayList<>();
        String key = keyword.toLowerCase();

        for (Patient patient : patientList) {
            if (patient.getId().toLowerCase().contains(key)
                    || patient.getFirstName().toLowerCase().contains(key)
                    || patient.getLastName().toLowerCase().contains(key)
                    || patient.getPhoneNumber().toLowerCase().contains(key)
                    || (patient.getEmail() != null && patient.getEmail().toLowerCase().contains(key))) {
                results.add(patient);
            }
        }
        return results;
    }

    // Search by full name
    public static List<Patient> searchPatients(String firstName, String lastName) {
        List<Patient> results = new ArrayList<>();
        for (Patient patient : patientList) {
            if (patient.getFirstName().equalsIgnoreCase(firstName) &&
                    patient.getLastName().equalsIgnoreCase(lastName)) {
                results.add(patient);
            }
        }
        return results;
    }

    // display all
    public static void displayPatients() {
        if (patientList.isEmpty()) {
            System.out.println("No patients found!\n");
            return;
        }
        for (Patient p : patientList) {
            System.out.println(p); // requires Patient.toString()
            System.out.println("------------------------");
        }
    }

    // display filtered by criteria
    public static Patient displayPatients(String filter) {
        if (patientList.isEmpty()) {
            System.out.println("No patients found!\n");
            return null;
        }

        System.out.println("===== Patients List (Filtered by: " + filter + ") =====");
        boolean found = false;
        for (Patient p : patientList) {
            if (p.getBloodGroup() != null && p.getBloodGroup().equalsIgnoreCase(filter)) {
                System.out.println(p);
                System.out.println("------------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No patients found with filter: " + filter);
        }
        System.out.println("========================\n");
        return null;
    }

    //display limited number
    public static Patient displayPatients(int limit) {
        if (patientList.isEmpty()) {
            System.out.println("No patients found!\n");
            return null;
        }

        System.out.println("===== Patients List (Limited to: " + limit + ") =====");
        int count = 0;
        for (Patient p : patientList) {
            if (count >= limit) break;
            System.out.println(p);
            System.out.println("------------------------");
            count++;
        }
        System.out.println("========================\n");
        return null;
    }

    public static void addAllergyToPatient(String patientId) {
        Patient patient = getPatientById(patientId);
        if (patient == null) {
            System.out.println("Error: Patient with ID " + patientId + " not found.\n");
            return;
        }

        System.out.println("\n--- Adding Allergy for " + patient.getFirstName() + " ---");

        String allergyId = HelperUtils.getRandomNumber(2);
        System.out.println("Allergy ID assigned: " + allergyId);

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
                updatedPatient.setId(patientId);
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
            System.out.println("===== Patients List =====");
            System.out.println("------------------------");
            System.out.println("No patients found!");
            System.out.println("========================\n");
            return;
        }

        System.out.println("===== Patients List =====");
        for (Patient p : patientList) {
            System.out.println(p); // make sure Patient.toString() is implemented
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

    @Override
    public String add(Object entity) {
        if (entity instanceof Patient patient) {
            patientList.add(addPatient(patient)); // ensure ID & registration date
            return "Patient added successfully!";
        }
        return "Invalid entity type.";
    }

    @Override
    public String remove(String id) {
        boolean removed = patientList.removeIf(p -> p.getId().equals(id));
        return removed ? "Patient removed successfully!" : "Patient not found!";
    }

    @Override
    public String getAll() {
        if (patientList.isEmpty()) return "No patients available.";
        StringBuilder sb = new StringBuilder("===== Patient List =====\n");
        for (Patient p : patientList) {
            sb.append(p.getId()).append(" - ").append(p.getFirstName())
                    .append(" ").append(p.getLastName()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String search(String keyword) {
        List<Patient> results = searchPatients(keyword);
        if (results.isEmpty()) return "No patients found for keyword: " + keyword;
        StringBuilder sb = new StringBuilder("Search Results:\n");
        for (Patient p : results) sb.append(p.getId()).append(" - ")
                .append(p.getFirstName()).append(" ")
                .append(p.getLastName()).append("\n");
        return sb.toString();
    }

    @Override
    public String searchById(String id) {
        Patient patient = getPatientById(id);
        return (patient != null) ? "Patient found: " + patient.getFirstName() + " " + patient.getLastName()
                : "Patient not found for ID: " + id;
    }
}