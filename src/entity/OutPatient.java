package entity;

import Interface.Displayable;
import Utils.HelperUtils;

import java.time.LocalDate;
import java.util.List;

public class OutPatient extends Patient implements Displayable{
    private int visitCount;
    private LocalDate lastVisitDate;
    private String preferredDoctorId;

    public OutPatient() {
        super();
    }
    public OutPatient(int visitCount, LocalDate lastVisitDate, String preferredDoctorId) {
        this.visitCount = visitCount;
        this.lastVisitDate = lastVisitDate;
        this.preferredDoctorId = preferredDoctorId;
    }

    public OutPatient(String id, String firstName, String lastName, LocalDate dateOfBirth, String gender, String phoneNumber, String email, String address, String bloodGroup, List<Allergies> allergies, String emergencyContact, LocalDate registrationDate, String insuranceId, List<MedicalRecord> medicalRecord, List<Appointment> appointment, int visitCount, LocalDate lastVisitDate, String preferredDoctorId) {
        super(id, firstName, lastName, dateOfBirth, gender, phoneNumber, email, address, bloodGroup, allergies, emergencyContact, registrationDate, insuranceId, medicalRecord, appointment);
        this.visitCount = visitCount;
        this.lastVisitDate = lastVisitDate;
        this.preferredDoctorId = preferredDoctorId;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        if(HelperUtils.isNegative(visitCount)) {
            System.out.println("Visit count cannot be negative.");
        } else {
            this.visitCount = visitCount;
        }
    }

    public LocalDate getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(LocalDate lastVisitDate) {
        if(lastVisitDate.isAfter(LocalDate.now())) {
            System.out.println("Last visit date cannot be in the future.");
        } else {
            this.lastVisitDate = lastVisitDate;
        }
    }

    public String getPreferredDoctorId() {
        return preferredDoctorId;
    }

    public void setPreferredDoctorId(String preferredDoctorId) {
        if(HelperUtils.isNotNull(preferredDoctorId)) {
            this.preferredDoctorId = preferredDoctorId;
        } else {
            System.out.println("Preferred doctor ID cannot be null.");
        }
    }

    @Override
    public String displayInfo(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(super.displayInfo(""));
        sb.append(System.lineSeparator());
        sb.append("Visit Count: ").append(visitCount).append(System.lineSeparator());
        sb.append("Last Visit Date: ").append(lastVisitDate).append(System.lineSeparator());
        sb.append("Preferred Doctor ID: ").append(preferredDoctorId);
        String out = sb.toString();
        System.out.println(out);
        return out;
    }

    @Override
    public String displaySummary(String str) {
        return "OutPatient{" + getId() + ": " + getFirstName() + " " + getLastName() + ", visits=" + visitCount + "}";
    }
    /**
     * Override methods
     * • Add: scheduleFollowUp(), updateVisitCount()
     */
}
