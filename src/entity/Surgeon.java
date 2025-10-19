package entity;

import Interface.Displayable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import service.MedicalRecordService;

public class Surgeon extends Doctor implements Displayable {

    private int surgeriesPerformed;
    private List<String> surgeryTypes;
    private boolean operationTheatreAccess;

    public Surgeon() {
        super();
        this.surgeriesPerformed = 0;
        this.surgeryTypes = new ArrayList<>();
        this.operationTheatreAccess = false;
    }

    public Surgeon(String id, String firstName, String lastName, LocalDate dateOfBirth, String gender, String phoneNumber, String email, String address, int surgeriesPerformed, List<String> surgeryTypes, boolean operationTheatreAccess) {
        super(id, firstName, lastName, dateOfBirth, gender, phoneNumber, email, address);
        this.surgeriesPerformed = surgeriesPerformed;
        this.surgeryTypes = (surgeryTypes == null) ? new ArrayList<>() : surgeryTypes;
        this.operationTheatreAccess = operationTheatreAccess;
    }

    public Surgeon(String id, String firstName, String lastName, LocalDate dateOfBirth, String gender, String phoneNumber, String email, String address, String specialization, String qualification, int experienceYears, String departmentId, double consultationFee, List<Integer> availableSlots, List<Patient> assignedPatients, int surgeriesPerformed, List<String> surgeryTypes, boolean operationTheatreAccess) {
        super(id, firstName, lastName, dateOfBirth, gender, phoneNumber, email, address, specialization, qualification, experienceYears, departmentId, consultationFee, availableSlots, assignedPatients);
        this.surgeriesPerformed = surgeriesPerformed;
        this.surgeryTypes = (surgeryTypes == null) ? new ArrayList<>() : surgeryTypes;
        this.operationTheatreAccess = operationTheatreAccess;
    }

    public void addSurgeryType(String type) {
        if (type == null || type.trim().isEmpty()) return;
        if (this.surgeryTypes == null) this.surgeryTypes = new ArrayList<>();
        String t = type.trim();
        if (!this.surgeryTypes.contains(t)) this.surgeryTypes.add(t);
    }

    public int getSurgeriesPerformed() {
        return surgeriesPerformed;
    }

    public void setSurgeriesPerformed(int surgeriesPerformed) {
        if (surgeriesPerformed < 0) return;
        this.surgeriesPerformed = surgeriesPerformed;
    }

    public List<String> getSurgeryTypes() {
        return surgeryTypes;
    }

    public void setSurgeryTypes(List<String> surgeryTypes) {
        this.surgeryTypes = surgeryTypes;
    }

    public boolean isOperationTheatreAccess() {
        return operationTheatreAccess;
    }

    public void setOperationTheatreAccess(boolean operationTheatreAccess) {
        this.operationTheatreAccess = operationTheatreAccess;
    }

    @Override
    public String displayInfo(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(super.displayInfo(""));
        sb.append(System.lineSeparator());
        sb.append("Surgeries Performed: ").append(surgeriesPerformed).append(System.lineSeparator());
        sb.append("Surgery Types: ").append(surgeryTypes == null ? "[]" : surgeryTypes.toString()).append(System.lineSeparator());
        sb.append("Operation Theatre Access: ").append(operationTheatreAccess);
        String out = sb.toString();
        System.out.println(out);
        return out;
    }

    @Override
    public String displaySummary(String str) {
        return "Surgeon{" + getId() + ": " + getFirstName() + " " + getLastName() + "}";
    }

    // Surgeon-specific scheduling behavior: prefer to mark consults that may lead to surgery
    @Override
    public Appointment scheduleConsultation(String patientId, LocalDate date, String time, String reason) {
        Appointment appt = super.scheduleConsultation(patientId, date, time, reason);
        if (appt == null) return null;
        String noteAppend = operationTheatreAccess ? " [Surgeon consult - may require pre-op evaluation]" : " [Surgeon consult]";
        String existing = appt.getNotes() == null ? "" : appt.getNotes();
        appt.setNotes(existing + noteAppend);
        System.out.println("Surgeon: scheduleConsultation applied extra surgeon-specific notes.");
        return appt;
    }

    @Override
    public Appointment scheduleConsultation(Appointment appt) {
        Appointment created = super.scheduleConsultation(appt);
        if (created == null) return null;
        String noteAppend = operationTheatreAccess ? " [Surgeon consult - may require pre-op evaluation]" : " [Surgeon consult]";
        String existing = created.getNotes() == null ? "" : created.getNotes();
        created.setNotes(existing + noteAppend);
        System.out.println("Surgeon: scheduled Appointment object with surgeon-specific notes.");
        return created;
    }

    @Override
    public MedicalRecord provideSecondOpinion(String patientId, String originalDoctorId, String opinionNotes) {
        MedicalRecord mr = super.provideSecondOpinion(patientId, originalDoctorId, opinionNotes);
        if (mr == null) return null;
        String existing = mr.getNotes() == null ? "" : mr.getNotes();
        mr.setNotes(existing + " [Second opinion by Surgeon: " + this.getDoctorId() + "]");
        System.out.println("Surgeon: provided second opinion and appended surgeon-specific note.");
        return mr;
    }

    // Perform a surgery for a patient: create a medical record, add to the patient's records
    public void performSurgery(Patient patient, String surgeryType, String notes) {
        if (patient == null) {
            System.out.println("Cannot perform surgery: patient is null.");
            return;
        }
        if (!this.operationTheatreAccess) {
            System.out.println("Cannot perform surgery: Surgeon does not have operation theatre access.");
            return;
        }

        MedicalRecord record = new MedicalRecord();
        // simple, mostly-unique id based on timestamp; projects can replace with proper id generator
        record.setRecordId("MR-" + System.currentTimeMillis());
        record.setPatientId(patient.getId());
        record.setDoctorId(this.getDoctorId());
        record.setVisitDate(LocalDate.now());
        record.setDiagnosis("Surgery: " + (surgeryType == null ? "Unknown" : surgeryType));
        record.setPrescription(null);
        record.setTestResults(null);
        record.setNotes(notes == null ? "" : notes);

        // attach to patient
        patient.addMedicalRecord(record);

        // persist
        MedicalRecordService.saveRecord(record);

        // update counters
        updateSurgeryCount(1);

        // track surgery type for this surgeon
        if (surgeryType != null && !surgeryType.trim().isEmpty()) {
            addSurgeryType(surgeryType.trim());
        }

        System.out.println("Surgery performed: '" + (surgeryType == null ? "Unknown" : surgeryType) + "' on patient " + patient.getId());
    }

    // Increment the surgeon's surgery count by a positive number
    public void updateSurgeryCount(int additionalSurgeries) {
        if (additionalSurgeries <= 0) {
            System.out.println("updateSurgeryCount called with non-positive value; no change made.");
            return;
        }
        this.surgeriesPerformed += additionalSurgeries;
        System.out.println("Surgeries performed updated. New total: " + this.surgeriesPerformed);
    }

    // Convenience method to increment by one
    public void updateSurgeryCount() {
        updateSurgeryCount(1);
    }
}
