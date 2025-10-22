package service;

import Utils.HelperUtils;
import Utils.InputHandler;
import entity.MedicalRecord;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MedicalRecordService {
    public static List<MedicalRecord> medicalRecordList = new ArrayList<>();
    public static Scanner scanner = new Scanner(System.in);

    public static MedicalRecord createRecord() {
        MedicalRecord medicalRecord = new MedicalRecord();

        String generatedId;
        do {
            generatedId = HelperUtils.generateId("MR");
        } while (getRecordById(generatedId) != null); // ensure uniqueness
        medicalRecord.setRecordId(generatedId);
        System.out.println("Medical Record ID: " + medicalRecord.getRecordId());

        // Patient ID
        String patientId = InputHandler.getStringInput("Enter Patient ID: ");
        medicalRecord.setPatientId(patientId);

        // Doctor ID
        String doctorId = InputHandler.getStringInput("Enter Doctor ID: ");
        medicalRecord.setDoctorId(doctorId);

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


        return medicalRecord;
    }

    public static boolean saveRecord(MedicalRecord record) {
        if (record == null || HelperUtils.isNull(record.getRecordId())) {
            System.out.println("MedicalRecord or recordId must not be null/empty");
            return false;
        }
        if (getRecordById(record.getRecordId()) != null) {
            System.out.println("Record ID already exists!");
            return false;
        }
        medicalRecordList.add(record);
        System.out.println("Medical record saved successfully.");
        return true;
    }

    // get by id
    public static MedicalRecord getRecordById(String recordId) {
        if (recordId == null) {
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

//    public static boolean updateRecord(String recordId, MedicalRecord updated) {
//        if (HelperUtils.isNull(recordId) || updated == null) {
//            return false;
//        }
//        for (int i = 0; i < medicalRecordList.size(); i++) {
//            MedicalRecord r = medicalRecordList.get(i);
//            if (recordId.equals(r.getRecordId())) {
//                MedicalRecord newRec = new MedicalRecord(
//                        r.getRecordId(),
//                        updated.getPatientId(),
//                        updated.getDoctorId(),
//                        updated.getVisitDate(),
//                        updated.getDiagnosis(),
//                        updated.getPrescription(),
//                        updated.getTestResults(),
//                        updated.getNotes()
//                );
//                medicalRecordList.set(i, newRec);
//                return true;
//            }
//        }
//        return false;
//    }

    public static boolean updateRecord(String recordId, MedicalRecord updated) {
        if (HelperUtils.isNull(recordId) || updated == null) {
            System.out.println("Invalid input: record ID or updated record is null.");
            return false;
        }

        MedicalRecord record = getRecordById(recordId);

        if (record == null) {
            System.out.println("Medical Record ID not found!");
            return false;
        }

        Scanner sc = new Scanner(System.in);

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
        System.out.println("Deleting record with ID: " + recordId);
        return medicalRecordList.removeIf(r -> recordId.equals(r.getRecordId()));
    }

    //patient history report
    public static void generatePatientHistoryReport(String patientId) {
        // Check for null or empty input
        if (patientId == null || patientId.trim().isEmpty()) {
            System.out.println("Invalid patient ID.");
            return;
        }

        // Get records by patient ID
        List<MedicalRecord> records = displayRecordsByPatient(patientId);

        if (records == null || records.isEmpty()) {
            System.out.println("No medical records found for patient: " + patientId);
            return;
        }

        // Display patient history
        System.out.println("=== Medical History for Patient: " + patientId + " ===");
        for (MedicalRecord record : records) {
            if (record != null) {
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
}
