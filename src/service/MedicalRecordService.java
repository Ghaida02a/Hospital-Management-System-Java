package service;

import entity.MedicalRecord;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MedicalRecordService {
    public static List<MedicalRecord> medicalRecordList = new ArrayList<>();
    public static Scanner scanner = new Scanner(System.in);

    // Interactive creation helper
    public static MedicalRecord createRecord() {
        MedicalRecord medicalRecord = new MedicalRecord();
        System.out.print("Enter Record ID: ");
        String recordId = scanner.nextLine();
        while (recordId.isEmpty()) {
            System.out.println("Record ID must not be empty.");
            System.out.print("Enter Record ID: ");
            recordId = scanner.nextLine();
        }
        medicalRecord.setRecordId(recordId);


        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine().trim();
        while (patientId.isEmpty()) {
            System.out.println("Patient ID must not be empty.");
            System.out.print("Enter Patient ID: ");
            patientId = scanner.nextLine();
        }
        medicalRecord.setPatientId(patientId);

        System.out.print("Enter Doctor ID: ");
        String doctorId = scanner.nextLine();
        while (doctorId.isEmpty()) {
            System.out.println("Doctor ID must not be empty.");
            System.out.print("Enter Doctor ID: ");
            doctorId = scanner.nextLine();
        }
        medicalRecord.setDoctorId(doctorId);

        System.out.print("Enter Visit Date (yyyy-MM-dd): ");
        String dateInput = scanner.nextLine();
        LocalDate visitDate = LocalDate.parse(dateInput);
        medicalRecord.setVisitDate(LocalDate.parse(dateInput));

        System.out.print("Enter Diagnosis: ");
        String diagnosis = scanner.nextLine();
        medicalRecord.setDiagnosis(diagnosis);

        System.out.print("Enter Prescription: ");
        String prescription = scanner.nextLine();
        medicalRecord.setPrescription(prescription);

        System.out.print("Enter Test Results: ");
        String testResults = scanner.nextLine();
        medicalRecord.setTestResults(testResults);

        System.out.print("Enter Notes: ");
        String notes = scanner.nextLine();
        medicalRecord.setNotes(notes);

        return medicalRecord;
    }

    public static boolean saveRecord(MedicalRecord record) {
        if (record == null || record.getRecordId() == null || record.getRecordId().isEmpty()) {
            System.out.println("MedicalRecord or recordId must not be null/empty");
        }
        if (getRecordById(record.getRecordId()) != null) {
            return false;
        }
        medicalRecordList.add(record);
        System.out.println("Medical record saved successfully.");
        return true;
    }

    // get by id
    public static MedicalRecord getRecordById(String recordId) {
        if (recordId == null) return null;
        for (MedicalRecord record : medicalRecordList) {
            if (recordId.equals(record.getRecordId())){
                return record;
            }
        }
        return null;
    }

    // get records by patient id
    public static List<MedicalRecord> displayRecordsByPatient(String patientId) {
        List<MedicalRecord> recordsByPatient = new ArrayList<>();

        if (patientId == null || patientId.trim().isEmpty()) {
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

        if (doctorId == null || doctorId.trim().isEmpty()) {
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


    // Update - replace fields for an existing recordId (preserve id)
    public static boolean updateRecord(String recordId, MedicalRecord updated) {
        if (recordId == null || updated == null) return false;
        for (int i = 0; i < medicalRecordList.size(); i++) {
            MedicalRecord r = medicalRecordList.get(i);
            if (recordId.equals(r.getRecordId())) {
                MedicalRecord newRec = new MedicalRecord(r.getRecordId(),
                        updated.getPatientId(), updated.getDoctorId(), updated.getVisitDate(),
                        updated.getDiagnosis(), updated.getPrescription(), updated.getTestResults(), updated.getNotes());
                medicalRecordList.set(i, newRec);
                return true;
            }
        }
        return false;
    }

    // Delete
    public static boolean deleteRecord(String recordId) {
        if (recordId == null) return false;
        return medicalRecordList.removeIf(r -> recordId.equals(r.getRecordId()));
    }

    //patient history report (most recent first)
    public static void generatePatientHistoryReport(String patientId) {
        if (patientId == null || patientId.trim().isEmpty()) {
            System.out.println("Invalid patient ID.");
            return;
        }

        List<MedicalRecord> records = displayRecordsByPatient(patientId);

        if (records.isEmpty()) {
            System.out.println("No medical records found for patient: " + patientId);
            return;
        }

        System.out.println("--- Medical History for Patient: " + patientId + " ---");
        for (MedicalRecord record : records) {
            record.displayInfo();
            System.out.println("---------------------------");
        }
    }

    public static void displayAllRecords() {
        if (medicalRecordList.isEmpty()) {
            System.out.println("No medical records available.");
            return;
        }
        System.out.println("--- All Medical Records ---");
        for (MedicalRecord r : medicalRecordList) {
            r.displayInfo();
            System.out.println("---------------------------");
        }
    }
}

