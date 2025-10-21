package entity;

import Interface.Displayable;

import java.time.LocalDate;
import java.util.List;

public class OutPatient extends Patient implements Displayable{
    private int visitCount;
    private LocalDate lastVisitDate;
    private String preferredDoctorId;

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
        this.visitCount = visitCount;
    }

    public LocalDate getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(LocalDate lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    public String getPreferredDoctorId() {
        return preferredDoctorId;
    }

    public void setPreferredDoctorId(String preferredDoctorId) {
        this.preferredDoctorId = preferredDoctorId;
    }

    /**
     * Override methods
     * • Add: scheduleFollowUp(), updateVisitCount()
     */

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
}
