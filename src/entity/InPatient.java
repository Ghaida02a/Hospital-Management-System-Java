package entity;

import Interface.Billable;
import Interface.Displayable;
import Utils.HelperUtils;

import java.time.LocalDate;
import java.util.List;

public class InPatient extends Patient implements Displayable, Billable {
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
        if (admissionDate == null) {
            System.out.println("Admission date cannot be null.");
            this.admissionDate = null;
            return;
        }

        if (admissionDate.isAfter(LocalDate.now())) {
            System.out.println("Admission date cannot be in the future.");
            this.admissionDate = null;
        } else {
            this.admissionDate = admissionDate;
        }
    }

    public LocalDate getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(LocalDate dischargeDate) {
        if (dischargeDate == null) {
            System.out.println("Discharge date cannot be null.");
            this.dischargeDate = null;
            return;
        }

        if (this.admissionDate == null) {
            System.out.println("Cannot set discharge date before admission date is set.");
            this.dischargeDate = null;
            return;
        }

        if (dischargeDate.isBefore(this.admissionDate)) {
            System.out.println("Discharge date cannot be before admission date.");
            this.dischargeDate = null;
        } else {
            this.dischargeDate = dischargeDate;
        }
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        if (HelperUtils.isNull(roomNumber) || roomNumber.trim().isEmpty()) {
            System.out.println("Room number cannot be empty.");
        } else {
            this.roomNumber = roomNumber;
        }
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        if (HelperUtils.isNull(bedNumber) || bedNumber.trim().isEmpty()) {
            System.out.println("Bed number cannot be empty.");
        } else {
            this.bedNumber = bedNumber;
        }
    }

    public String getAdmittingDoctorId() {
        return admittingDoctorId;
    }

    public void setAdmittingDoctorId(String admittingDoctorId) {
        if (HelperUtils.isNull(admittingDoctorId) || admittingDoctorId.isEmpty()) {
            System.out.println("Admitting Doctor ID cannot be empty.");
        } else {
            this.admittingDoctorId = admittingDoctorId;
        }
    }

    public double getDailyCharges() {
        return dailyCharges;
    }

    public void setDailyCharges(double dailyCharges) {
        if (HelperUtils.isNegative(dailyCharges)) {
            System.out.println("Daily charges cannot be negative.");
        } else {
            this.dailyCharges = dailyCharges;
        }
    }

    public long calculateStayDuration() {
        if (HelperUtils.isNull(admissionDate) || HelperUtils.isNull(dischargeDate)) {
            System.out.println("Cannot calculate stay duration — missing dates.");
            return 0;
        }
        return dischargeDate.toEpochDay() - admissionDate.toEpochDay();
    }

    public double calculateTotalCharges() {
        long days = calculateStayDuration();
        if (HelperUtils.isNegative(days) || days == 0) {
            days = 1; // at least 1 day charge
        }
        return days * dailyCharges;
    }

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

    @Override
    public double calculateCharges() {
        return calculateTotalCharges();
    }

    @Override
    public void generateBill() {
        double total = calculateTotalCharges();
        System.out.println("Generated Bill for " + getFirstName() + " " + getLastName() +
                ": OMR " + total + " (" + calculateStayDuration() + " days)");
    }

    @Override
    public void processPayment(double amount) {
        double total = calculateTotalCharges();
        if (amount < total) {
            System.out.println("Insufficient payment. Remaining: OMR " + (total - amount));
        } else {
            System.out.println("Payment successful. Change: OMR " + (amount - total));
        }
    }
}
