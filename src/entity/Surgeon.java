package entity;

import Interface.Displayable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Utils.HelperUtils;
import Utils.InputHandler;
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
        if (HelperUtils.isNull(type) || type.trim().isEmpty()) return;
        if (HelperUtils.isNull(this.surgeryTypes)) this.surgeryTypes = new ArrayList<>();
        String t = type.trim();
        if (!this.surgeryTypes.contains(t)) this.surgeryTypes.add(t);
    }

    public int getSurgeriesPerformed() {
        return surgeriesPerformed;
    }

    public void setSurgeriesPerformed(int surgeriesPerformed) {
        if (HelperUtils.isNegative(surgeriesPerformed)) {
            System.out.println("Surgeries performed cannot be negative.");
        } else {
            this.surgeriesPerformed = surgeriesPerformed;
        }
    }

    public List<String> getSurgeryTypes() {
        return surgeryTypes;
    }

    public void setSurgeryTypes(List<String> surgeryTypes) {
        if (HelperUtils.isNull(surgeryTypes)) {
            this.surgeryTypes = new ArrayList<>();
        } else {
            this.surgeryTypes = surgeryTypes;
        }
    }

    public static void setSurgeryTypeForSurgeon(Surgeon surgeon) {
        String type = InputHandler.getStringInput("Enter surgery type: ");
        if (HelperUtils.isNull(type) || type.isEmpty()) {
            System.out.println("Invalid input. Surgery type cannot be empty.");
            return;
        }
        surgeon.setSurgeryTypes(List.of(type));
        System.out.println("Surgery type set to: " + type);
    }

    public boolean isOperationTheatreAccess() {
        return operationTheatreAccess;
    }

    public void setOperationTheatreAccess(boolean operationTheatreAccess) {
        if (!operationTheatreAccess) {
            System.out.println("Warning: Revoking operation theatre access for surgeon " + this.getDoctorId());
        }
        this.operationTheatreAccess = operationTheatreAccess;
    }

    @Override
    public String displayInfo(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(super.displayInfo(""));
        sb.append(System.lineSeparator());
        sb.append("DR Surgeon ID: ").append(this.getDoctorId()).append(System.lineSeparator());
        sb.append("Surgeries Performed: ").append(surgeriesPerformed).append(System.lineSeparator());
        sb.append("Surgery Types: ").append(HelperUtils.isNull(surgeryTypes) ? "[]" : surgeryTypes.toString()).append(System.lineSeparator());
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
        if (HelperUtils.isNull(appt)) {
            return null;
        }
        String noteAppend = operationTheatreAccess ? " [Surgeon consult - may require pre-op evaluation]" : " [Surgeon consult]";
        String existing = HelperUtils.isNull(appt.getNotes()) ? "" : appt.getNotes();
        appt.setNotes(existing + noteAppend);
        System.out.println("Surgeon: scheduleConsultation applied extra surgeon-specific notes.");
        return appt;
    }

    @Override
    public Appointment scheduleConsultation(Appointment appt) {
        Appointment created = super.scheduleConsultation(appt);
        if (HelperUtils.isNull(created)) return null;
        String noteAppend = operationTheatreAccess ? " [Surgeon consult - may require pre-op evaluation]" : " [Surgeon consult]";
        String existing = HelperUtils.isNull(created.getNotes()) ? "" : created.getNotes();
        created.setNotes(existing + noteAppend);
        System.out.println("Surgeon: scheduled Appointment object with surgeon-specific notes.");
        return created;
    }

    @Override
    public MedicalRecord provideSecondOpinion(String patientId, String originalDoctorId, String opinionNotes) {
        MedicalRecord mr = super.provideSecondOpinion(patientId, originalDoctorId, opinionNotes);
        if (HelperUtils.isNull(mr)) {
            return null;
        }
        String existing = HelperUtils.isNull(mr.getNotes()) ? "" : mr.getNotes();
        mr.setNotes(existing + " [Second opinion by Surgeon: " + this.getDoctorId() + "]");
        System.out.println("Surgeon: provided second opinion and appended surgeon-specific note.");
        return mr;
    }

    // Perform a surgery for a patient: create a medical record, add to the patient's records
    public void performSurgery(String type) {
        if (operationTheatreAccess) {
            surgeriesPerformed++;
            surgeryTypes.add(type);
            System.out.println("Performed surgery: " + type);
        } else {
            System.out.println("Access to operation theatre denied.");
        }
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
