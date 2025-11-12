package service;

import Interface.Editable;
import Interface.Manageable;
import Interface.Searchable;
import Utils.HelperUtils;
import Utils.InputHandler;
import entity.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PatientService implements Manageable, Searchable {
    public static List<Patient> patientList = new ArrayList<>();
    public static List<InPatient> inPatientList = new ArrayList<>();
    public static List<OutPatient> outPatientList = new ArrayList<>();
    public static List<EmergencyPatient> emergencyPatientList = new ArrayList<>();

    public static Patient initializePatient(Patient patient) {
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

    public static void addAllergyToPatient(String patientId) {
        Patient patient = getPatientById(patientId);
        if (HelperUtils.isNull(patient)) {
            System.out.println("Error: Patient with ID " + patientId + " not found.\n");
            return;
        }

        System.out.println("\n--- Adding Allergy for " + patient.getFirstName() + " ---");

        String allergyId = HelperUtils.getRandomNumber(2);
        System.out.println("Allergy ID assigned: " + allergyId);

        String allergyName = InputHandler.getStringInput("Enter Allergy Name (e.g., Peanuts): ");

        Allergies newAllergy = new Allergies(allergyId, allergyName);

        if (HelperUtils.isNull(patient.getAllergies())) {
            patient.setAllergies(new ArrayList<>());
        }
        patient.getAllergies().add(newAllergy);

        System.out.println("Successfully added allergy '" + allergyName + "' to patient " + patientId + ".\n");
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
        } while (HelperUtils.isNull(inPatient.getAdmissionDate()));
        do {
            inPatient.setDischargeDate(InputHandler.getDateInput("Enter Discharge Date: "));
        } while (HelperUtils.isNull(inPatient.getDischargeDate()));
        inPatient.setRoomNumber(InputHandler.getStringInput("Enter Room Number: "));
        inPatient.setBedNumber(InputHandler.getStringInput("Enter Bed Number: "));

        DoctorService.displayDoctorNamesAndIds();
        String doctorId = " ";
        boolean valid = false;

        while (!valid) {
            doctorId = InputHandler.getStringInput("Enter Admitting Doctor ID: ");
            if (DoctorService.getDoctorById(doctorId) != null) {
                valid = true;
            } else {
                System.out.println("Invalid Doctor ID. Please try again.");
            }
        }
        inPatient.setAdmittingDoctorId(doctorId);
//        inPatient.setAdmittingDoctorId(InputHandler.getStringInput("Enter Admitting Doctor ID: "));
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

        // display Doctors ID
        DoctorService.displayDoctorNamesAndIds();
        String doctorId = " ";
        boolean valid = false;

        while (!valid) {
            doctorId = InputHandler.getStringInput("Enter Preferred Doctor ID: ");
            if (DoctorService.getDoctorById(doctorId) != null) {
                valid = true;
            } else {
                System.out.println("Invalid Doctor ID. Please try again.");
            }
        }
        outPatient.setPreferredDoctorId(doctorId);
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
        String[] emergencyTypes = {"Medical", "Trauma", "Burns", "Surgical", "Environmental"};
        boolean valid = false;
        String emergencyType = "";

        while (!valid) {
            System.out.println("Available emergency types: " + Arrays.toString(emergencyTypes));
            emergencyType = InputHandler.getStringInput("Enter Emergency Type: ");
            if (emergencyType.isEmpty() || !Arrays.asList(emergencyTypes).contains(emergencyType)) {
                System.out.println("Invalid emergency type. Please try again.");
            } else {
                valid = true;
            }
        }

        emergencyPatient.setEmergencyType(emergencyType);

        String arrivalMode = InputHandler.getStringInput("Enter Arrival Mode (Ambulance/Walk-in): ");
        while (!arrivalMode.matches("^(Ambulance|Walk-in)$")) {
            arrivalMode = InputHandler.getStringInput("Invalid arrival mode. Enter 'Ambulance' or 'Walk-in': ");
        }
        emergencyPatient.setArrivalMode(arrivalMode);

        Integer triageLevel = InputHandler.getIntInput("Enter Triage Level (1-5): ");
        while (triageLevel < 1 || triageLevel > 5) {
            triageLevel = InputHandler.getIntInput("Invalid triage level. Enter a number between 1 and 5: ");
        }
        emergencyPatient.setTriageLevel(triageLevel);

        emergencyPatient.setAdmittedViaER(InputHandler.getBooleanInput("Admitted via ER (true/false): "));

        return emergencyPatient;
    }

    public static void savePatient(Patient patient) { //save Patient
        if (HelperUtils.isNotNull(patient)) {
            patientList.add(patient);
            System.out.println("Patient registered successfully!\n");
        }
    }

    public static void saveInPatient(InPatient inPatient) { //save InPatient
        if (HelperUtils.isNotNull(inPatient)) {
            inPatientList.add(inPatient);
            System.out.println("InPatient registered successfully!\n");
        }
    }

    public static void saveOutPatient(OutPatient outPatient) { //save OutPatient
        if (HelperUtils.isNotNull(outPatient)) {
            outPatientList.add(outPatient);
            System.out.println("OutPatient registered successfully!\n");
        }
    }

    public static void saveEmergencyPatient(EmergencyPatient emergencyPatient) { //save Emergency Patient
        if (HelperUtils.isNotNull(emergencyPatient)) {
            emergencyPatientList.add(emergencyPatient);
            System.out.println("Emergency patient registered successfully!\n");
        }
    }

    public static String editPatient(String patientId, Patient updatedPatient) {
        if (patientList.isEmpty()) {
            System.out.println("No Patients available to edit.");
            return "No Patients available to edit.";
        }
        for (int i = 0; i < patientList.size(); i++) {
            if (patientList.get(i).getPatientId().equals(patientId)) {
                updatedPatient.setPatientId(patientId);
                patientList.set(i, updatedPatient);
                System.out.println("Patient updated successfully!\n");
                return "Patient updated successfully!";
            }
        }
        //System.out.println("Patient with ID " + patientId + " not found.\n");
        return "Patient with ID " + patientId + " not found.";
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

    public static void displayAllPatients() {
        System.out.println("\n===== All Patients =====\n");

        // --- General Patients ---
        if (!patientList.isEmpty()) {
            System.out.println("===== Patients List =====");
            for (Patient patient : patientList) {
                patient.displayInfo();
                System.out.println("------------------------");
            }
            System.out.println("========================\n");
        }

        // --- InPatients ---
        if (!inPatientList.isEmpty()) {
            System.out.println("===== InPatients List =====");
            for (InPatient inPatient : inPatientList) {
                inPatient.displayInfo("");
                System.out.println("------------------------");
            }
            System.out.println("========================\n");
        }

        // --- OutPatients ---
        if (!outPatientList.isEmpty()) {
            System.out.println("===== OutPatients List =====");
            for (OutPatient outPatient : outPatientList) {
                outPatient.displayInfo("");
                System.out.println("------------------------");
            }
            System.out.println("========================\n");
        }

        // --- Emergency Patients ---
        if (!emergencyPatientList.isEmpty()) {
            System.out.println("===== Emergency Patients List =====");
            for (EmergencyPatient emergencyPatient : emergencyPatientList) {
                emergencyPatient.displayInfo("");
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

    public static void viewPatientMedicalHistory(String patientId) {
        Patient patient = getPatientById(patientId);
        if (HelperUtils.isNotNull(patient)) {
            System.out.println("===== Medical History for Patient: " + patient.getPatientId() + " =====");
            patient.displayInfo(); // basic info
            System.out.println("---- Medical Records ----");
            for (MedicalRecord record : patient.getMedicalRecord()) {
                System.out.println(record);
            }
            System.out.println("=========================");
        } else {
            System.out.println("Patient with ID " + patientId + " not found.");
        }
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
        if (HelperUtils.isNotNull(patient)) {
            patient.addAppointment(appointment);
        } else {
            System.out.println("Patient not found: " + patientId);
        }
    }

    public static void updatePatientInsurance(String patientId, String newInsuranceId) {
        Patient patient = getPatientById(patientId);
        if (HelperUtils.isNotNull(patient)) {
            patient.updateInsurance(newInsuranceId);
        } else {
            System.out.println("Patient not found: " + patientId);
        }
    }

    //overloaded methods
    public static Patient addPatient(String firstName, String lastName, String phone) { // minimal info
        Patient patient = new Patient();
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setPhoneNumber(phone);
        return initializePatient(patient);
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
                    || (HelperUtils.isNotNull(patient.getEmail()) && patient.getEmail().toLowerCase().contains(key))) {
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
            if (HelperUtils.isNotNull(p.getBloodGroup()) && p.getBloodGroup().equalsIgnoreCase(filter)) {
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


    public static void displayPatientNamesAndIds() {
        List<Patient> allPatients = new ArrayList<>();
        allPatients.addAll(patientList);
        allPatients.addAll(inPatientList);
        allPatients.addAll(outPatientList);
        allPatients.addAll(emergencyPatientList);

        if (allPatients.isEmpty()) {
            System.out.println("No patients available.");
            return;
        }

        System.out.println("===== Patients (ID + Name) =====");
        for (Patient patient : allPatients) {
            System.out.println("ID: " + patient.getPatientId() +
                    " -> Name: " + patient.getFirstName() + " " + patient.getLastName());
        }
    }

    @Override
    public String add(Object entity) {
        if (entity instanceof InPatient) {
            inPatientList.add((InPatient) entity);
            return "InPatient added successfully!";
        }

        if (entity instanceof OutPatient) {
//            outPatientList.add(OutPatientRegistration(outPatient)); // ensure ID & registration date
            outPatientList.add((OutPatient) entity);
            return "OutPatient added successfully!";
        }

        if (entity instanceof EmergencyPatient) {
//            emergencyPatientList.add(EmergencyPatientRegistration(emergencyPatient)); // ensure ID & registration date
            emergencyPatientList.add((EmergencyPatient) entity);
            return "Emergency Patient added successfully!";
        }

        if (entity instanceof Patient) {
//            patientList.add(addPatient(patient)); // ensure ID & registration date
            patientList.add((Patient) entity);
            return "Patient added successfully!";
        }
        return "Invalid entity type.";
    }

    @Override
    public String remove(String id) {
//        boolean removed = patientList.removeIf(p -> p.getId().equals(id));
//        return removed ? "Patient removed successfully!" : "Patient not found!";
        boolean removedGeneral = patientList.removeIf(p -> p.getPatientId().equals(id));
        boolean removedIn = inPatientList.removeIf(p -> p.getPatientId().equals(id));
        boolean removedOut = outPatientList.removeIf(p -> p.getPatientId().equals(id));
        boolean removedEmergency = emergencyPatientList.removeIf(p -> p.getPatientId().equals(id));

        if (removedGeneral || removedIn || removedOut || removedEmergency) {
            return "Patient removed successfully!";
        } else {
            return "Patient not found!";
        }
    }

    @Override
    public String getAll() {
        if (patientList.isEmpty() && inPatientList.isEmpty()
                && outPatientList.isEmpty() && emergencyPatientList.isEmpty()) {
            return "No patients available.";
        }

        StringBuilder result = new StringBuilder("===== Patient List =====\n");

        if (!patientList.isEmpty()) {
            result.append("\n-- General Patients --\n");
            for (Patient p : patientList) {
                if (!(p instanceof InPatient) && !(p instanceof OutPatient) && !(p instanceof EmergencyPatient)) {
                    result.append(p.displayInfo()).append("\n------------------------\n");
                }
            }
        }

        if (!inPatientList.isEmpty()) {
            result.append("\n-- InPatients --\n");
            for (InPatient ip : inPatientList) {
                result.append(ip.displayInfo("")).append("\n------------------------\n");
            }
        }

        if (!outPatientList.isEmpty()) {
            result.append("\n-- OutPatients --\n");
            for (OutPatient op : outPatientList) {
                result.append(op.displayInfo("")).append("\n------------------------\n");
            }
        }

        if (!emergencyPatientList.isEmpty()) {
            result.append("\n-- Emergency Patients --\n");
            for (EmergencyPatient ep : emergencyPatientList) {
                result.append(ep.displayInfo("")).append("\n------------------------\n");
            }
        }

        return result.toString();
    }

    @Override
    public String search(String keyword) {
        String result = "Search Results:\n";
        boolean found = false;

        // General patients
        for (Patient p : patientList) {
            if (p.getFirstName().contains(keyword) || p.getLastName().contains(keyword)) {
                result += "ID" + p.getId() + " - Patient ID: " + p.getPatientId() + " - " + p.getFirstName() + " " + p.getLastName() + "\n";
                found = true;
            }
        }

        // InPatients
        for (InPatient ip : inPatientList) {
            if (ip.getFirstName().contains(keyword) || ip.getLastName().contains(keyword)) {
                result += "ID" + ip.getId() + " - Patient ID: " + ip.getPatientId() + " - " + ip.getFirstName() + " " + ip.getLastName() + "\n";
                found = true;
            }
        }

        // OutPatients
        for (OutPatient op : outPatientList) {
            if (op.getFirstName().contains(keyword) || op.getLastName().contains(keyword)) {
                result += "ID" + op.getId() + " - Patient ID: " + op.getPatientId() + " - " + op.getFirstName() + " " + op.getLastName() + "\n";
                found = true;
            }
        }

        // EmergencyPatients
        for (EmergencyPatient ep : emergencyPatientList) {
            if (ep.getFirstName().contains(keyword) || ep.getLastName().contains(keyword)) {
                result += "ID" + ep.getId() + " - Patient ID: " + ep.getPatientId() + " - " + ep.getFirstName() + " " + ep.getLastName() + "\n";
                found = true;
            }
        }

        if (!found) {
            return "No patients found for keyword: " + keyword;
        }
        return result;
    }

    @Override
    public String searchById(String id) {
        // Check general list
        for (Patient p : patientList) {
            if (p.getId().equals(id)) {
                return "Patient found: " + p.getFirstName() + " " + p.getLastName();
            }
        }

        // Check InPatients
        for (InPatient ip : inPatientList) {
            if (ip.getId().equals(id)) {
                return "InPatient found: " + ip.getFirstName() + " " + ip.getLastName();
            }
        }

        // Check OutPatients
        for (OutPatient op : outPatientList) {
            if (op.getId().equals(id)) {
                return "OutPatient found: " + op.getFirstName() + " " + op.getLastName();
            }
        }

        // Check EmergencyPatients
        for (EmergencyPatient ep : emergencyPatientList) {
            if (ep.getId().equals(id)) {
                return "EmergencyPatient found: " + ep.getFirstName() + " " + ep.getLastName();
            }
        }

        return "Patient not found for ID: " + id;
    }

    public static void addSamplePatients() {
        String[] names = {"Sara", "Fatima", "Nour", "Ruba", "Layla", "Aisha"};
        String[] lastNames = {"AlHamdi", "AlRashid", "AlBalushi"};
        String[] allergies = {"Peanuts", "Shellfish", "Dairy", "Eggs", "Soy", "Wheat"};
        String[] bloodGroups = {"A+", "B+", "AB+", "O+", "A-", "B-", "AB-", "O-"};
        String[] emailAddress = {"gmail.com", "yahoo.com", "hotmail.com"};
        String[] emergencyTypes = {"Medical", "Trauma", "Burns", "Surgical", "Environmental"};
        String[] arrivalModes = {"Ambulance", "Walk-in"};

        //2 patient
        for (int i = 0; i < 2; i++) {
            Patient patient = new Patient();
            patient.setId(HelperUtils.getRandomNumber(10));
            patient.setPatientId(HelperUtils.generateId("PAT"));
            patient.setFirstName(names[i]);
            patient.setLastName(lastNames[i]);
            patient.setDateOfBirth(LocalDate.of(2022, 03, 1));
            patient.setGender("Female");
            patient.setPhoneNumber("92923232" + i);
            patient.setEmail(patient.getFirstName().toLowerCase() + "." + patient.getLastName().toLowerCase() + i + emailAddress[i % emailAddress.length]);
            patient.setAddress("123 Main St");

            patient.setBloodGroup(bloodGroups[i]);
            patient.setAllergies(new ArrayList<>());
            patient.getAllergies().add(new Allergies(patient.getPatientId(), allergies[i]));
            patient.setEmergencyContact("9876543210");
            patient.setRegistrationDate(LocalDate.now());
            patient.setInsuranceId(HelperUtils.generateId("INS"));
            patient.setMedicalRecord(new ArrayList<>());
            patient.setAppointment(new ArrayList<>());
            savePatient(patient);
        }

        //3 inpatient
        for (int i = 0; i < 3; i++) {
            InPatient inPatient = new InPatient();
            inPatient.setId(HelperUtils.getRandomNumber(10));
            inPatient.setPatientId(HelperUtils.generateId("PAT"));
            inPatient.setFirstName(names[i]);
            inPatient.setLastName(lastNames[i]);
            inPatient.setDateOfBirth(LocalDate.of(2022, 03, 1));
            inPatient.setGender("Female");
            inPatient.setPhoneNumber("92923232" + i);
            inPatient.setEmail(inPatient.getFirstName().toLowerCase() + "." + inPatient.getLastName().toLowerCase() + i + emailAddress[i % emailAddress.length]);
            inPatient.setAddress("123 Main St");

            inPatient.setBloodGroup(bloodGroups[i]);
            inPatient.setAllergies(new ArrayList<>());
            inPatient.getAllergies().add(new Allergies(inPatient.getPatientId(), allergies[i]));
            inPatient.setEmergencyContact("9876543210");
            inPatient.setRegistrationDate(LocalDate.now());
            inPatient.setInsuranceId(HelperUtils.generateId("INS"));
            inPatient.setMedicalRecord(new ArrayList<>());
            inPatient.setAppointment(new ArrayList<>());


            inPatient.setAdmissionDate(LocalDate.of(2022, 03, 01));
            inPatient.setDischargeDate(LocalDate.of(2026, 03, 05));
            inPatient.setRoomNumber("R01");
            inPatient.setBedNumber("B01");
            inPatient.setAdmittingDoctorId("DR-234" + i);
            inPatient.setDailyCharges(100.0);
            saveInPatient(inPatient);
        }

        //3 outpatient
        for (int i = 0; i < 3; i++) {
            OutPatient outPatient = new OutPatient();
            outPatient.setId(HelperUtils.getRandomNumber(10));
            outPatient.setPatientId(HelperUtils.generateId("PAT"));
            outPatient.setFirstName(names[i]);
            outPatient.setLastName(lastNames[i]);
            outPatient.setDateOfBirth(LocalDate.of(2022, 03, 1));
            outPatient.setGender("Female");
            outPatient.setPhoneNumber("92923232" + i);
            outPatient.setEmail(outPatient.getFirstName().toLowerCase() + "." + outPatient.getLastName().toLowerCase() + i + emailAddress[i % emailAddress.length]);
            outPatient.setAddress("123 Main St");

            outPatient.setBloodGroup(bloodGroups[i]);
            outPatient.setAllergies(new ArrayList<>());
            outPatient.getAllergies().add(new Allergies(outPatient.getPatientId(), allergies[i]));
            outPatient.setEmergencyContact("9876543210");
            outPatient.setRegistrationDate(LocalDate.now());
            outPatient.setInsuranceId(HelperUtils.generateId("INS"));
            outPatient.setMedicalRecord(new ArrayList<>());
            outPatient.setAppointment(new ArrayList<>());

            outPatient.setVisitCount(1);
            outPatient.setLastVisitDate(LocalDate.now());
            outPatient.setPreferredDoctorId("DR-345" + i);
            saveOutPatient(outPatient);
        }

        //2 Emergency Patients
        for (int i = 0; i < 2; i++) {
            EmergencyPatient emergencyPatient = new EmergencyPatient();
            emergencyPatient.setId(HelperUtils.getRandomNumber(10));
            emergencyPatient.setPatientId(HelperUtils.generateId("PAT"));
            emergencyPatient.setFirstName(names[i]);
            emergencyPatient.setLastName(lastNames[i]);
            emergencyPatient.setDateOfBirth(LocalDate.of(2022, 03, 1));
            emergencyPatient.setGender("Female");
            emergencyPatient.setPhoneNumber("92923232" + i);
            emergencyPatient.setEmail(emergencyPatient.getFirstName().toLowerCase() + "." + emergencyPatient.getLastName().toLowerCase() + i + emailAddress[i % emailAddress.length]);
            emergencyPatient.setAddress("123 Main St");

            emergencyPatient.setBloodGroup(bloodGroups[i]);
            emergencyPatient.setAllergies(new ArrayList<>());
            emergencyPatient.getAllergies().add(new Allergies(emergencyPatient.getPatientId() + i, allergies[i]));
            emergencyPatient.setEmergencyContact("9876543210");
            emergencyPatient.setRegistrationDate(LocalDate.now());
            emergencyPatient.setInsuranceId(HelperUtils.generateId("INS"));
            emergencyPatient.setMedicalRecord(new ArrayList<>());
            emergencyPatient.setAppointment(new ArrayList<>());

            emergencyPatient.setAdmissionDate(LocalDate.of(2022, 03, 01));
            emergencyPatient.setDischargeDate(LocalDate.of(2026, 03, 05));
            emergencyPatient.setRoomNumber("R01");
            emergencyPatient.setBedNumber("B01");
            emergencyPatient.setAdmittingDoctorId("DR-456" + i);
            emergencyPatient.setDailyCharges(100.0);

            emergencyPatient.setEmergencyType(emergencyTypes[i]);
            emergencyPatient.setArrivalMode(arrivalModes[i]);
            emergencyPatient.setTriageLevel(3);
            emergencyPatient.setAdmittedViaER(true);

            saveEmergencyPatient(emergencyPatient);
        }
    }
}