package entity;

import Interface.Displayable;

import java.time.LocalDate;
import java.util.List;

public class InPatient extends Patient implements Displayable{
    private LocalDate admissionDate;
    private LocalDate dischargeDate;
    private String roomNumber;
    private String bedNumber;
    private String admittingDoctorId;
    private double dailyCharges;

    public InPatient(LocalDate admissionDate, LocalDate dischargeDate, String roomNumber, String bedNumber, String admittingDoctorId, double dailyCharges) {
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
        this.roomNumber = roomNumber;
        this.bedNumber = bedNumber;
        this.admittingDoctorId = admittingDoctorId;
        this.dailyCharges = dailyCharges;
    }

    public InPatient(String id, String firstName, String lastName, LocalDate dateOfBirth, String gender, String phoneNumber, String email, String address, String bloodGroup, List<Allergies> allergies, String emergencyContact, LocalDate registrationDate, String insuranceId, List<MedicalRecord> medicalRecord, List<Appointment> appointment, LocalDate admissionDate, LocalDate dischargeDate, String roomNumber, String bedNumber, String admittingDoctorId, double dailyCharges) {
        super(id, firstName, lastName, dateOfBirth, gender, phoneNumber, email, address, bloodGroup, allergies, emergencyContact, registrationDate, insuranceId, medicalRecord, appointment);
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
        this.roomNumber = roomNumber;
        this.bedNumber = bedNumber;
        this.admittingDoctorId = admittingDoctorId;
        this.dailyCharges = dailyCharges;
    }

    public InPatient() {
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }

    public LocalDate getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(LocalDate dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    public String getAdmittingDoctorId() {
        return admittingDoctorId;
    }

    public void setAdmittingDoctorId(String admittingDoctorId) {
        this.admittingDoctorId = admittingDoctorId;
    }

    public double getDailyCharges() {
        return dailyCharges;
    }

    public void setDailyCharges(double dailyCharges) {
        this.dailyCharges = dailyCharges;
    }

    /**
     *  Override methods
     * • Add: calculateStayDuration(), calculateTotalCharges()
     */

    @Override
    public String displayInfo(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(super.displayInfo(""));
        sb.append(System.lineSeparator());
        sb.append("Admission Date: ").append(admissionDate).append(System.lineSeparator());
        sb.append("Discharge Date: ").append(dischargeDate).append(System.lineSeparator());
        sb.append("Room Number: ").append(roomNumber).append(System.lineSeparator());
        sb.append("Bed Number: ").append(bedNumber).append(System.lineSeparator());
        sb.append("Admitting Doctor ID: ").append(admittingDoctorId).append(System.lineSeparator());
        sb.append("Daily Charges: ").append(dailyCharges);
        String out = sb.toString();
        System.out.println(out);
        return out;
    }

    @Override
    public String displaySummary(String str) {
        return "InPatient{" + getId() + ": " + getFirstName() + " " + getLastName() + ", room=" + roomNumber + "}";
    }
}
