package service;

import Interface.Manageable;
import Interface.Searchable;
import Utils.HelperUtils;
import Utils.InputHandler;
import entity.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientService implements Manageable, Searchable {
    public static List<Patient> patientList = new ArrayList<>();
    public static List<InPatient> inPatientList = new ArrayList<>();
    public static List<OutPatient> outPatientList = new ArrayList<>();
    public static List<EmergencyPatient> emergencyPatientList = new ArrayList<>();


    public static Patient addPatient() {
        Patient patient = new Patient();

        System.out.println("\n--- Patient Registration ---");
        patient.setId(HelperUtils.getRandomNumber(10));
        System.out.println("ID: " + patient.getId());
        initializePatient(patient);

        // Name
        patient.setFirstName(InputHandler.getStringInput("Enter First Name: "));
        patient.setLastName(InputHandler.getStringInput("Enter Last Name: "));

        //Date
        while (!HelperUtils.isValidAge(patient.getDateOfBirth())) {
            patient.setDateOfBirth(InputHandler.getDateInput("Enter Date of Birth"));
        }

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
//        patient.setEmergencyContact(InputHandler.getPhoneNumberInput("Enter Emergency Contact Phone number: "));
        String emergencyContact = InputHandler.getPhoneNumberInput("Enter Emergency Contact Phone number: ");
        while (emergencyContact.equals(patient.getPhoneNumber())) {
            System.out.println("Emergency contact cannot be the same as patient's phone number. Please enter a different number.");
            emergencyContact = InputHandler.getPhoneNumberInput("Enter Emergency Contact Phone number: ");
        }

        patient.setEmergencyContact(emergencyContact);

        // Insurance ID
        patient.setInsuranceId(HelperUtils.generateId("INS"));
        System.out.println("Assigned Insurance ID: " + patient.getInsuranceId());

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
        patient.setPatientId(generatedId);
        System.out.println("Patient ID: " + patient.getPatientId());

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

    public static void save(Patient patient) { //save Patient
        if (HelperUtils.isNotNull(patient)) {
            patientList.add(patient);
            System.out.println("Patient registered successfully!\n");
        }
    }

    public static void save(InPatient inPatient) { //save InPatient
        if (HelperUtils.isNotNull(inPatient)) {
            inPatientList.add(inPatient);
            System.out.println("InPatient registered successfully!\n");
        }
    }

    public static void save(OutPatient outPatient) { //save OutPatient
        if (HelperUtils.isNotNull(outPatient)) {
            outPatientList.add(outPatient);
            System.out.println("OutPatient registered successfully!\n");
        }
    }

    public static void save(EmergencyPatient emergencyPatient) { //save Emergency Patient
        if (HelperUtils.isNotNull(emergencyPatient)) {
            emergencyPatientList.add(emergencyPatient);
            System.out.println("Emergency patient registered successfully!\n");
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
        boolean removed = patientList.removeIf(patient -> patient.getPatientId().equals(patientId));
        if (removed) {
            System.out.println("Patient with ID " + patientId + " removed successfully!");

            // Remove patient from all doctors
            for (Doctor doctor : DoctorService.getAllDoctors()) {
                doctor.removePatient(patientId);
            }
        } else {
            System.out.println("Patient with ID " + patientId + " not found.");
        }
    }


    public static Patient getPatientById(String patientId) {
        for (Patient patient : patientList) {
            if (patient.getPatientId().equals(patientId)) {
                return patient;
            }
        }
        return null;
    }

//    public static void displayAllPatients() {
//        if (patientList.isEmpty()) {
//            System.out.println("===== Patients List =====");
//            System.out.println("------------------------");
//            System.out.println("No patients found!");
//            System.out.println("========================\n");
//            return;
//        }
//
//        System.out.println("===== Patients List =====");
//        for (Patient p : patientList) {
//            System.out.println(p); // make sure Patient.toString() is implemented
//            System.out.println("------------------------");
//        }
//        System.out.println("========================\n");

    public static void displayAllPatients() {
        System.out.println("\n===== All Patients =====\n");

        // --- General Patients ---
        if (!patientList.isEmpty()) {
            System.out.println("===== Patients List =====");
            for (Patient p : patientList) {
                System.out.println(p);
                System.out.println("------------------------");
            }
            System.out.println("========================\n");
        }

        // --- InPatients ---
        if (!inPatientList.isEmpty()) {
            System.out.println("===== InPatients List =====");
            for (InPatient p : inPatientList) {
                System.out.println(p);
                System.out.println("------------------------");
            }
            System.out.println("========================\n");
        }

        // --- OutPatients ---
        if (!outPatientList.isEmpty()) {
            System.out.println("===== OutPatients List =====");
            for (OutPatient p : outPatientList) {
                System.out.println(p);
                System.out.println("------------------------");
            }
            System.out.println("========================\n");
        }

        // --- Emergency Patients ---
        if (!emergencyPatientList.isEmpty()) {
            System.out.println("===== Emergency Patients List =====");
            for (EmergencyPatient p : emergencyPatientList) {
                System.out.println(p);
                System.out.println("------------------------");
            }
            System.out.println("========================\n");
        }

        // Optional: if no patients at all
        if (patientList.isEmpty() && inPatientList.isEmpty() && outPatientList.isEmpty() && emergencyPatientList.isEmpty()) {
            System.out.println("No patients found in the system.");
        }
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

    public static InPatient InpatientRegistration() {
        InPatient inPatient = new InPatient();
        System.out.println("\n--- Inpatient Registration ---");

        Patient basePatient = PatientService.addPatient();// fill basic patient info
        inPatient.setId(basePatient.getId());
        inPatient.setFirstName(basePatient.getFirstName());
        inPatient.setLastName(basePatient.getLastName());
        inPatient.setDateOfBirth(basePatient.getDateOfBirth());
        inPatient.setGender(basePatient.getGender());
        inPatient.setPhoneNumber(basePatient.getPhoneNumber());
        inPatient.setEmail(basePatient.getEmail());
        inPatient.setAddress(basePatient.getAddress());
        inPatient.setBloodGroup(basePatient.getBloodGroup());
        inPatient.setEmergencyContact(basePatient.getEmergencyContact());
        inPatient.setInsuranceId(basePatient.getInsuranceId());
        inPatient.setRegistrationDate(basePatient.getRegistrationDate());

        do {
            inPatient.setAdmissionDate(InputHandler.getDateInput("Enter Admission Date: "));
        } while (inPatient.getAdmissionDate() == null);
        inPatient.setDischargeDate(InputHandler.getDateInput("Enter Discharge Date: "));
        inPatient.setRoomNumber(InputHandler.getStringInput("Enter Room Number: "));
        inPatient.setBedNumber(InputHandler.getStringInput("Enter Bed Number: "));
        inPatient.setAdmittingDoctorId(InputHandler.getStringInput("Enter Admitting Doctor ID: "));
        inPatient.setDailyCharges(InputHandler.getDoubleInput("Enter Daily Charges: "));

        return inPatient;
    }

    public static OutPatient OutPatientRegistration() {
        OutPatient outPatient = new OutPatient();
        System.out.println("\n--- OutPatient Registration ---");

        Patient basePatient = PatientService.addPatient();// fill basic patient info
        outPatient.setId(basePatient.getId());
        outPatient.setFirstName(basePatient.getFirstName());
        outPatient.setLastName(basePatient.getLastName());
        outPatient.setDateOfBirth(basePatient.getDateOfBirth());
        outPatient.setGender(basePatient.getGender());
        outPatient.setPhoneNumber(basePatient.getPhoneNumber());
        outPatient.setEmail(basePatient.getEmail());
        outPatient.setAddress(basePatient.getAddress());
        outPatient.setBloodGroup(basePatient.getBloodGroup());
        outPatient.setEmergencyContact(basePatient.getEmergencyContact());
        outPatient.setInsuranceId(basePatient.getInsuranceId());
        outPatient.setRegistrationDate(basePatient.getRegistrationDate());

        outPatient.setVisitCount(InputHandler.getIntInput("Enter Visit Count: "));
        outPatient.setLastVisitDate(InputHandler.getDateInput("Enter Last Visit Date: "));
        outPatient.setPreferredDoctorId(InputHandler.getStringInput("Enter Preferred Doctor ID: "));
        return outPatient;
    }

    public static EmergencyPatient EmergencyPatientRegistration() {
        EmergencyPatient emergencyPatient = new EmergencyPatient();
        System.out.println("\n--- Emergency Patient Registration ---");

        InPatient inPatient = InpatientRegistration();

        //Initialize base Patient fields from InPatient
        emergencyPatient.setId(inPatient.getId());
        emergencyPatient.setFirstName(inPatient.getFirstName());
        emergencyPatient.setLastName(inPatient.getLastName());
        emergencyPatient.setDateOfBirth(inPatient.getDateOfBirth());
        emergencyPatient.setGender(inPatient.getGender());
        emergencyPatient.setPhoneNumber(inPatient.getPhoneNumber());
        emergencyPatient.setEmail(inPatient.getEmail());
        emergencyPatient.setAddress(inPatient.getAddress());
        emergencyPatient.setBloodGroup(inPatient.getBloodGroup());
        emergencyPatient.setEmergencyContact(inPatient.getEmergencyContact());
        emergencyPatient.setInsuranceId(inPatient.getInsuranceId());
        emergencyPatient.setRegistrationDate(inPatient.getRegistrationDate());

        // InPatient-specific fields
        emergencyPatient.setAdmissionDate(inPatient.getAdmissionDate());
        emergencyPatient.setDischargeDate(inPatient.getDischargeDate());
        emergencyPatient.setRoomNumber(inPatient.getRoomNumber());
        emergencyPatient.setBedNumber(inPatient.getBedNumber());
        emergencyPatient.setAdmittingDoctorId(inPatient.getAdmittingDoctorId());
        emergencyPatient.setDailyCharges(inPatient.getDailyCharges());

        // Emergency-specific fields
        emergencyPatient.setEmergencyType(InputHandler.getStringInput("Enter Emergency Type: "));
        emergencyPatient.setArrivalMode(InputHandler.getStringInput("Enter Arrival Mode (Ambulance/Walk-in): "));
        emergencyPatient.setTriageLevel(InputHandler.getIntInput("Enter Triage Level (1-5): "));
        emergencyPatient.setAdmittedViaER(InputHandler.getBooleanInput("Admitted via ER (true/false): "));

        return emergencyPatient;
    }

    public static List<Patient> getAllPatients() {
        return new ArrayList<>(patientList);
    }

    public static void addMedicalRecordToPatient(String patientId, MedicalRecord record) {
        Patient patient = getPatientById(patientId);
        if (HelperUtils.isNotNull(patient)) {
            patient.addMedicalRecord(record);
        } else {
            System.out.println("Patient not found: " + patientId);
        }
    }

    public static void addAppointmentToPatient(String patientId, Appointment appointment) {
        Patient patient = getPatientById(patientId);
        if (patient != null) {
            patient.addAppointment(appointment);
        } else {
            System.out.println("Patient not found: " + patientId);
        }
    }

    public static void updatePatientInsurance(String patientId, String newInsuranceId) {
        Patient patient = getPatientById(patientId);
        if (patient != null) {
            patient.updateInsurance(newInsuranceId);
        } else {
            System.out.println("Patient not found: " + patientId);
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
        for (Patient p : results)
            sb.append(p.getId()).append(" - ")
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