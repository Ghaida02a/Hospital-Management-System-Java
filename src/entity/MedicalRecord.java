package entity;

import Interface.Displayable;
import Utils.HelperUtils;

import java.time.LocalDate;

public class MedicalRecord implements Displayable {
    private String recordId;
    private String patientId;
    private String doctorId;
    private LocalDate visitDate;
    private String diagnosis;
    private String prescription;
    private String testResults;
    private String notes;

    public MedicalRecord(String recordId, String patientId, String doctorId, LocalDate visitDate,
                         String diagnosis, String prescription, String testResults, String notes) {
        this.recordId = recordId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.visitDate = visitDate;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.testResults = testResults;
        this.notes = notes;
    }

    public MedicalRecord() {
        this.recordId = HelperUtils.generateId("MR");
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = HelperUtils.isNotNull(recordId) ? recordId : HelperUtils.generateId("MR");
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        if (HelperUtils.isNotNull(patientId)) {
            this.patientId = patientId;
        } else {
            this.patientId = "";
            System.out.println("Invalid patient ID.");
        }
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        if (HelperUtils.isNotNull(doctorId)) {
            this.doctorId = doctorId;
        } else {
            this.doctorId = "";
            System.out.println("Invalid doctor ID.");
        }
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        if (HelperUtils.isNotNull(visitDate) && !visitDate.isAfter(LocalDate.now())) {
            this.visitDate = visitDate;
        } else {
            this.visitDate = LocalDate.now();
            System.out.println("Invalid visit date. Setting to today.");
        }
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        if (HelperUtils.isValidString(diagnosis)) {
            this.diagnosis = diagnosis.trim();
        } else {
            this.diagnosis = "";
            System.out.println("Invalid diagnosis.");
        }
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        if (HelperUtils.isValidString(prescription)) {
            this.prescription = prescription.trim();
        } else {
            this.prescription = "";
            System.out.println("Invalid prescription.");
        }
    }

    public String getTestResults() {
        return testResults;
    }

    public void setTestResults(String testResults) {
        if (HelperUtils.isValidString(testResults)) {
            this.testResults = testResults.trim();
        } else {
            this.testResults = "";
            System.out.println("Invalid test results.");
        }
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        if (HelperUtils.isValidString(notes)) {
            this.notes = notes.trim();
        } else {
            this.notes = "";
            System.out.println("Invalid notes.");
        }
    }

    public void displayInfo() {
        System.out.println("Record Id: " + recordId);
        System.out.println("Patient Id: " + patientId);
        System.out.println("Doctor Id: " + doctorId);
        System.out.println("Visit Date: " + visitDate);
        System.out.println("Diagnosis: " + diagnosis);
        System.out.println("Prescription: " + prescription);
        System.out.println("Test Results: " + testResults);
        System.out.println("Notes: " + notes);
    }

    @Override
    public String displayInfo(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("Record Id: ").append(recordId).append(System.lineSeparator());
        sb.append("Patient Id: ").append(patientId).append(System.lineSeparator());
        sb.append("Doctor Id: ").append(doctorId).append(System.lineSeparator());
        sb.append("Visit Date: ").append(visitDate).append(System.lineSeparator());
        sb.append("Diagnosis: ").append(diagnosis).append(System.lineSeparator());
        sb.append("Prescription: ").append(prescription).append(System.lineSeparator());
        sb.append("Test Results: ").append(testResults).append(System.lineSeparator());
        sb.append("Notes: ").append(notes);
        String out = sb.toString();
        System.out.println(out);
        return out;
    }

    @Override
    public String displaySummary(String str) {
        return "MedicalRecord{" + recordId + ", Patient:" + patientId + ", Date:" + visitDate + "}";
    }
}
