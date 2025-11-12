package service;

import Interface.Manageable;
import Interface.Searchable;
import Utils.HelperUtils;
import Utils.InputHandler;
import entity.Department;
import entity.MedicalRecord;
import entity.Patient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MedicalRecordService implements Manageable, Searchable {
    public static List<MedicalRecord> medicalRecordList = new ArrayList<>();

    public static MedicalRecord createRecord() {
        MedicalRecord medicalRecord = new MedicalRecord();

        String generatedId;
        do {
            generatedId = HelperUtils.generateId("MR");
        } while (HelperUtils.isNotNull(getRecordById(generatedId))); // ensure uniqueness
        medicalRecord.setRecordId(generatedId);
        System.out.println("Medical Record ID: " + medicalRecord.getRecordId());

        // Patient ID
//        String patientId = InputHandler.getStringInput("Enter Patient ID: ");
//        medicalRecord.setPatientId(patientId);
        System.out.println("Patient List:");
        PatientService.displayPatientNamesAndIds();
        String patientId = InputHandler.getStringInput("Enter Patient ID: ");
        while (PatientService.getPatientById(patientId) == null) {
            System.out.println("Patient not found. Please try again.");
            patientId = InputHandler.getStringInput("Enter Patient ID: ");
        }
        medicalRecord.setPatientId(patientId);
        System.out.println("Patient ID: " + medicalRecord.getPatientId());

        // Doctor ID
        System.out.println("Doctor List:");
        DoctorService.displayDoctorNamesAndIds();
        String doctorId = InputHandler.getStringInput("Enter Doctor ID: ");
        while (DoctorService.getDoctorById(doctorId) == null) {
            System.out.println("Doctor not found. Please try again.");
            doctorId = InputHandler.getStringInput("Enter Doctor ID: ");
        }
        medicalRecord.setDoctorId(doctorId);
        System.out.println("Doctor ID: " + medicalRecord.getDoctorId());

        // Visit Date
        LocalDate visitDate = InputHandler.getDateInput("Enter Visit Date");
        medicalRecord.setVisitDate(visitDate);

        String diagnosis = InputHandler.getStringInput("Enter Diagnosis: ");
        medicalRecord.setDiagnosis(diagnosis);

        // Prescription
        String prescription = InputHandler.getStringInput("Enter Prescription: ");
        medicalRecord.setPrescription(prescription);

        // Test Results
        String testResults = InputHandler.getStringInput("Enter Test Results: ");
        medicalRecord.setTestResults(testResults);

        // Notes
        String notes = InputHandler.getStringInput("Enter Notes: ");
        medicalRecord.setNotes(notes);

        if (!HelperUtils.isNotNull(medicalRecord.getPatientId()) ||
                !HelperUtils.isNotNull(medicalRecord.getDoctorId())) {
            System.out.println("Error: Patient ID or Doctor ID cannot be empty.");
            return null;
        }


        return medicalRecord;
    }

    public static boolean saveRecord(MedicalRecord record) {
        if (HelperUtils.isNull(record) || HelperUtils.isNull(record.getRecordId())) {
            System.out.println("MedicalRecord or recordId must not be null/empty");
            return false;
        }
        if (HelperUtils.isNotNull(getRecordById(record.getRecordId()))) {
            System.out.println("Record ID already exists!");
            return false;
        }
        medicalRecordList.add(record);
        System.out.println("Medical record saved successfully.");
        return true;
    }

    // get by id
    public static MedicalRecord getRecordById(String recordId) {
        if (!HelperUtils.isNotNull(recordId)) {
            return null;
        }
        for (MedicalRecord record : medicalRecordList) {
            if (recordId.equals(record.getRecordId())) {
                return record;
            }
        }
        return null;
    }

    // get records by patient id
    public static List<MedicalRecord> displayRecordsByPatient(String patientId) {
        List<MedicalRecord> recordsByPatient = new ArrayList<>();
        if (HelperUtils.isNull(patientId)) {
            System.out.println("Invalid patient ID.");
            return recordsByPatient;
        }
        for (MedicalRecord record : medicalRecordList) {
            if (patientId.equals(record.getPatientId())) {
                recordsByPatient.add(record);
            }
        }
        return recordsByPatient;
    }

    // get records by doctor id
    public static List<MedicalRecord> displayRecordsByDoctor(String doctorId) {
        List<MedicalRecord> recordsByDoctor = new ArrayList<>();
        if (HelperUtils.isNull(doctorId)) {
            System.out.println("Invalid doctor ID.");
            return recordsByDoctor;
        }
        for (MedicalRecord record : medicalRecordList) {
            if (doctorId.equals(record.getDoctorId())) {
                recordsByDoctor.add(record);
            }
        }
        return recordsByDoctor;
    }

    public static boolean updateRecord(String recordId, MedicalRecord updated) {
        if (HelperUtils.isNull(recordId) || HelperUtils.isNull(updated)) {
            System.out.println("Invalid input: record ID or updated record is null.");
            return false;
        }

        MedicalRecord record = getRecordById(recordId);

        if (HelperUtils.isNull(record)) {
            System.out.println("Medical Record ID not found!");
            return false;
        }

        System.out.println("Updating Medical Record: " + recordId);
        System.out.println("Patient ID (cannot change): " + record.getPatientId());
        System.out.println("Doctor ID (cannot change): " + record.getDoctorId());
        System.out.println("Visit Date (cannot change): " + record.getVisitDate());

        // Update Diagnosis
        String diagnosis = updated.getDiagnosis();
        if (!HelperUtils.isNull(diagnosis)) {
            record.setDiagnosis(diagnosis);
        } else {
            String input = InputHandler.getStringInput("Enter Diagnosis (" + record.getDiagnosis() + "): ");
            if (!input.isEmpty()) record.setDiagnosis(input);
        }

        // Update Prescription
        String prescription = updated.getPrescription();
        if (!HelperUtils.isNull(prescription)) {
            record.setPrescription(prescription);
        } else {
            String input = InputHandler.getStringInput("Enter Prescription (" + record.getPrescription() + "): ");
            if (!input.isEmpty()) record.setPrescription(input);
        }

        // Update Test Results
        String testResults = updated.getTestResults();
        if (!HelperUtils.isNull(testResults)) {
            record.setTestResults(testResults);
        } else {
            String input = InputHandler.getStringInput("Enter Test Results (" + record.getTestResults() + "): ");
            if (!input.isEmpty()) record.setTestResults(input);
        }

        // Update Notes
        String notes = updated.getNotes();
        if (!HelperUtils.isNull(notes)) {
            record.setNotes(notes);
        } else {
            String input = InputHandler.getStringInput("Enter Notes (" + record.getNotes() + "): ");
            if (!input.isEmpty()) record.setNotes(input);
        }

        System.out.println("Medical Record updated successfully!");
        return true;
    }

    // Delete
    public static boolean deleteRecord(String recordId) {
        if (HelperUtils.isNull(recordId)) {
            return false;
        }
        System.out.println("Deleting medical record with ID: " + recordId);
        return medicalRecordList.removeIf(r -> recordId.equals(r.getRecordId()));
    }

    //patient history report
    public static void generatePatientHistoryReport(String patientId) {
        // Check for null or empty input
        if (!HelperUtils.isNotNull(patientId)) {
            System.out.println("Invalid patient ID.");
            return;
        }

        // Get records by patient ID
        List<MedicalRecord> records = displayRecordsByPatient(patientId);

        if (HelperUtils.isNull(records) || records.isEmpty()) {
            System.out.println("No medical records found for patient: " + patientId);
            return;
        }

        // Display patient history
        System.out.println("=== Medical History for Patient: " + patientId + " ===");
        for (MedicalRecord record : records) {
            if (HelperUtils.isNotNull(record)) {
                record.displayInfo("");
                System.out.println("---------------------------");
            }
        }
        System.out.println("=== End of Medical History ===\n");
    }

    public static void displayAllRecords() {
        if (medicalRecordList.isEmpty()) {
            System.out.println("No medical records available.");
            return;
        }
        System.out.println("--- All Medical Records ---");
        for (MedicalRecord r : medicalRecordList) {
            r.displayInfo("");
            System.out.println("---------------------------");
        }
    }

    public static MedicalRecord createRecordForPatient(Patient patient) {//create record for existing patient
        MedicalRecord record = new MedicalRecord();
        record.setRecordId(HelperUtils.generateId("MR"));
        System.out.println("Medical Record ID: " + record.getRecordId());

        record.setPatientId(patient.getPatientId()); // use existing patient

        DoctorService.displayDoctorNamesAndIds();
        String doctorId = " ";
        boolean valid = false;

        while (!valid) {
            doctorId = InputHandler.getStringInput("Enter Doctor ID: ");
            if (DoctorService.getDoctorById(doctorId) != null) {
                valid = true;
            } else {
                System.out.println("Invalid Doctor ID. Please try again.");
            }
        }
        record.setDoctorId(doctorId);
//        record.setDoctorId(InputHandler.getStringInput("Enter Doctor ID: "));
        record.setVisitDate(InputHandler.getDateInput("Enter Visit Date"));
        record.setDiagnosis(InputHandler.getStringInput("Enter Diagnosis: "));
        record.setPrescription(InputHandler.getStringInput("Enter Prescription: "));
        record.setTestResults(InputHandler.getStringInput("Enter Test Results: "));
        record.setNotes(InputHandler.getStringInput("Enter Notes: "));

        return record;
    }

    @Override
    public String add(Object entity) {
        if (entity instanceof MedicalRecord) {
            medicalRecordList.add((MedicalRecord) entity);
            return "Medical record added successfully!";
        }
        return "Invalid object type.";
    }

    @Override
    public String remove(String id) {
        return deleteRecord(id) ? "Medical record deleted successfully." : "Failed to delete medical record.";
    }

    @Override
    public String getAll() {
        if (medicalRecordList.isEmpty()) {
            return "No medical records available.";
        }

        StringBuilder sb = new StringBuilder("===== Medical Records =====\n");
        for (MedicalRecord r : medicalRecordList) {
            sb.append(r.displayInfo("")).append(System.lineSeparator());
            sb.append("------------------------").append(System.lineSeparator());
        }
        return sb.toString();
    }


    @Override
    public String search(String keyword) {
        StringBuilder sb = new StringBuilder();
        for (MedicalRecord r : medicalRecordList) {
            if (r.getDiagnosis().toLowerCase().contains(keyword.toLowerCase()) ||
                r.getPrescription().toLowerCase().contains(keyword.toLowerCase()) ||
                r.getNotes().toLowerCase().contains(keyword.toLowerCase()) ||
                r.getTestResults().toLowerCase().contains(keyword.toLowerCase()) ||
                r.getPatientId().toLowerCase().contains(keyword.toLowerCase()) ||
                r.getDoctorId().toLowerCase().contains(keyword.toLowerCase())) {
                sb.append(r.displayInfo(""));
                sb.append(System.lineSeparator());
            }
        }
        return sb.length() > 0 ? sb.toString() : "No medical records found for: " + keyword;
    }

    @Override
    public String searchById(String id) {
        MedicalRecord record = getRecordById(id);
        if (HelperUtils.isNull(record)) {
            return "Medical record not found for ID: " + id;
        }
        record.displayInfo("");
        return "Search complete.";
    }

    public static void addSampleMedicalRecords() {
        String[] mrDates = {"2023-04-01", "2023-04-02", "2023-04-03", "2023-04-04", "2023-04-05", "2023-04-06", "2023-04-07", "2023-04-08", "2023-04-09", "2023-04-10"};
        for (int i = 0; i < 12; i++) {
            MedicalRecord record = new MedicalRecord();
            record.setRecordId("MR-123" + i);
            record.setPatientId("PAT-123" + i);
            record.setDoctorId("DR-123" + i);
            record.setVisitDate(LocalDate.parse(mrDates[i % mrDates.length]));
            record.setDiagnosis("Diagnosis " + i);
            record.setPrescription("Prescription " + i);
            record.setTestResults("Test Results " + i);
            record.setNotes("Notes " + i);
            saveRecord(record);
        }
    }
}
