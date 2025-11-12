package entity;

import Interface.Displayable;
import Utils.HelperUtils;

import java.time.LocalDate;
import java.util.List;

public class EmergencyPatient extends InPatient implements Displayable {
    private String emergencyType;
    private String arrivalMode; // Ambulance/Walk-in
    private int triageLevel; // 1 to 5
    private boolean admittedViaER;

    public EmergencyPatient() {
        super();
    }

    public EmergencyPatient(LocalDate admissionDate, LocalDate dischargeDate, String roomNumber, String bedNumber, String admittingDoctorId, double dailyCharges, String emergencyType, String arrivalMode, int triageLevel, boolean admittedViaER) {
        super(admissionDate, dischargeDate, roomNumber, bedNumber, admittingDoctorId, dailyCharges);
        this.emergencyType = emergencyType;
        this.arrivalMode = arrivalMode;
        this.triageLevel = triageLevel;
        this.admittedViaER = admittedViaER;
    }

    public EmergencyPatient(String id, String firstName, String lastName, LocalDate dateOfBirth, String gender, String phoneNumber, String email, String address, String bloodGroup, List<Allergies> allergies, String emergencyContact, LocalDate registrationDate, String insuranceId, List<MedicalRecord> medicalRecord, List<Appointment> appointment, LocalDate admissionDate, LocalDate dischargeDate, String roomNumber, String bedNumber, String admittingDoctorId, double dailyCharges, String emergencyType, String arrivalMode, int triageLevel, boolean admittedViaER) {
        super(id, firstName, lastName, dateOfBirth, gender, phoneNumber, email, address, bloodGroup, allergies, emergencyContact, registrationDate, insuranceId, medicalRecord, appointment, admissionDate, dischargeDate, roomNumber, bedNumber, admittingDoctorId, dailyCharges);
        this.emergencyType = emergencyType;
        this.arrivalMode = arrivalMode;
        this.triageLevel = triageLevel;
        this.admittedViaER = admittedViaER;
    }

    public EmergencyPatient(String emergencyType, String arrivalMode, int triageLevel, boolean admittedViaER) {
        this.emergencyType = emergencyType;
        this.arrivalMode = arrivalMode;
        this.triageLevel = triageLevel;
        this.admittedViaER = admittedViaER;
    }

    public String getEmergencyType() {
        return emergencyType;
    }

    public void setEmergencyType(String emergencyType) {
        if (HelperUtils.isValidString(emergencyType, 3, 50)) {
            this.emergencyType = emergencyType;
        } else {
            System.out.println("Emergency type must be between 3 and 50 characters.");
        }
    }

    public String getArrivalMode() {
        return arrivalMode;
    }

    public void setArrivalMode(String arrivalMode) {
        if (HelperUtils.isValidString(arrivalMode, 3, 20)) {
            this.arrivalMode = arrivalMode;
        } else {
            System.out.println("Arrival mode must be between 3 and 20 characters.");
        }
    }

    public int getTriageLevel() {
        return triageLevel;
    }

    public void setTriageLevel(int triageLevel) {
        if (triageLevel >= 1 && triageLevel <= 5) {
            this.triageLevel = triageLevel;
        } else {
            System.out.println("Triage level must be between 1 and 5.");
        }
    }

    public boolean isAdmittedViaER() {
        return admittedViaER;
    }

    public void setAdmittedViaER(boolean admittedViaER) {
        if (HelperUtils.isNotNull(admittedViaER)) {
            this.admittedViaER = admittedViaER;
        } else {
            System.out.println("Admitted via ER must be specified.");
        }
    }

    public void prioritizeTreatment() {
        switch (triageLevel) {
            case 1 -> System.out.println("Critical case! Immediate treatment required.");
            case 2, 3 -> System.out.println("High priority case. Treat urgently.");
            case 4, 5 -> System.out.println("Stable case. Can wait for treatment.");
            default -> System.out.println("Invalid triage level.");
        }
    }

    //Mark patient as admitted via ER.
    public void admitThroughER() {
        this.admittedViaER = true;
        System.out.println("Patient admitted via Emergency Room.");
    }

    @Override
    public String displayInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.displayInfo(""));
        sb.append(System.lineSeparator());
        sb.append("Patient ID: ").append(getPatientId()).append(System.lineSeparator());
        sb.append("Emergency Type: ").append(emergencyType).append(System.lineSeparator());
        sb.append("Arrival Mode: ").append(arrivalMode).append(System.lineSeparator());
        sb.append("Triage Level: ").append(triageLevel).append(System.lineSeparator());
        sb.append("Admitted Via ER: ").append(admittedViaER);
        String out = sb.toString();
        System.out.println(out);

        // Emergency-specific logic: highlight critical cases
        if (triageLevel == 1) {
            System.out.println("⚠ CRITICAL: Immediate intervention required!");
        } else if (triageLevel <= 3) {
            System.out.println("⚠ High priority case.");
        } else {
            System.out.println("Stable case, can wait.");
        }

        return out;
    }

    @Override
    public String displaySummary(String str) {
        return "EmergencyPatient{" + getId() + ": " + getFirstName() + " " + getLastName() + ", triage=" + triageLevel + "}";
    }
}
