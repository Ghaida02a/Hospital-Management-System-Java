package entity;

import Interface.Displayable;
import Utils.HelperUtils;
import Utils.InputHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Patient extends Person implements Displayable {
    private String patientId;
    private String bloodGroup;
    private List<Allergies> allergies;
    private String emergencyContact;
    private LocalDate registrationDate;
    private String insuranceId;
    private List<MedicalRecord> medicalRecord;
    private List<Appointment> appointment;


    public Patient() {
        super();
        // Ensure lists are initialized
        this.allergies = new ArrayList<>();
        this.medicalRecord = new ArrayList<>();
        this.appointment = new ArrayList<>();
        this.registrationDate = LocalDate.now();
    }

    public Patient(String patientId, String bloodGroup, List<Allergies> allergies, String emergencyContact, LocalDate registrationDate, String insuranceId, List<MedicalRecord> medicalRecord, List<Appointment> appointment) {
        this.patientId = patientId;
        this.bloodGroup = bloodGroup;
        this.allergies = allergies;
        this.emergencyContact = emergencyContact;
        this.registrationDate = registrationDate;
        this.insuranceId = insuranceId;
        this.medicalRecord = medicalRecord;
        this.appointment = appointment;
    }

    public Patient(String id, String firstName, String lastName, LocalDate dateOfBirth, String gender, String phoneNumber, String email, String address, String patientId, String bloodGroup, List<Allergies> allergies, String emergencyContact, LocalDate registrationDate, String insuranceId, List<MedicalRecord> medicalRecord, List<Appointment> appointment) {
        super(id, firstName, lastName, dateOfBirth, gender, phoneNumber, email, address);
        this.patientId = patientId;
        this.bloodGroup = bloodGroup;
        this.allergies = allergies;
        this.emergencyContact = emergencyContact;
        this.registrationDate = registrationDate;
        this.insuranceId = insuranceId;
        this.medicalRecord = medicalRecord;
        this.appointment = appointment;
    }

    public Patient(String id, String firstName, String lastName, LocalDate dateOfBirth, String gender, String phoneNumber, String email, String address, String bloodGroup, List<Allergies> allergies, String emergencyContact, LocalDate registrationDate, String insuranceId, List<MedicalRecord> medicalRecord, List<Appointment> appointment) {
        super(id, firstName, lastName, dateOfBirth, gender, phoneNumber, email, address);
        // Use setters to apply validations and normalization
        setBloodGroup(bloodGroup);
        setAllergies(allergies);
        setEmergencyContact(emergencyContact);
        setRegistrationDate(registrationDate);
        setInsuranceId(insuranceId);
        setMedicalRecord(medicalRecord);
        setAppointment(appointment);
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        if (HelperUtils.isNull(patientId) || patientId.trim().isEmpty()) {
            this.patientId = HelperUtils.generateId("PAT");
        } else {
            this.patientId = patientId.trim();
        }
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        boolean valid = false; // flag to control the loop

        while (!valid) {
            if (HelperUtils.isNull(bloodGroup) || bloodGroup.isEmpty()) {
                System.out.println("Blood group cannot be empty. Please enter again:");
                bloodGroup = InputHandler.getStringInput("Enter blood group: ");
            } else {
                String normalized = bloodGroup.trim().toUpperCase().replaceAll("\\s+", "");

                // Accept valid forms only
                if (normalized.matches("^(A|B|AB|O)[+-]$")) {
                    this.bloodGroup = normalized;
                    valid = true;
                } else {
                    System.out.println("Invalid blood group '" + bloodGroup +
                            "'. Expected one of: A+, A-, B+, B-, AB+, AB-, O+, O-");
                    bloodGroup = InputHandler.getStringInput("Enter valid blood group: ");
                }
            }
        }
    }

    public List<Allergies> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<Allergies> allergies) {
        if (HelperUtils.isNull(allergies)) {
            this.allergies = new ArrayList<>();
        } else {
            this.allergies = allergies;
        }
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        if (HelperUtils.isNull(emergencyContact) || emergencyContact.isEmpty()) {
            System.out.println("Warning: Emergency contact cannot be empty.");
            this.emergencyContact = "";
            return;
        }

        // Normalize input
        emergencyContact = emergencyContact.trim();

        // Validate: cannot be the same as patient’s phone number
        if (HelperUtils.isNotNull(this.getPhoneNumber()) && this.getPhoneNumber().equals(emergencyContact)) {
            System.out.println("Warning: Emergency contact cannot be the same as patient's phone number.");
            this.emergencyContact = "";
            return;
        }

        // Validate format (optional, if you want to use HelperUtils)
        if (!HelperUtils.isValidString(emergencyContact)) {
            System.out.println("Warning: Invalid emergency contact format.");
            this.emergencyContact = "";
            return;
        }

        this.emergencyContact = emergencyContact;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        if (HelperUtils.isNull(registrationDate)) {
            this.registrationDate = LocalDate.now();
        } else {
            // Prevent future dates
            if (registrationDate.isAfter(LocalDate.now())) {
                System.out.println("Warning: registration date cannot be in the future. Using today's date instead.");
                this.registrationDate = LocalDate.now();
            } else {
                this.registrationDate = registrationDate;
            }
        }
    }

    public String getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(String insuranceId) {
        if (HelperUtils.isNull(insuranceId) || insuranceId.trim().isEmpty()) {
            System.out.println("Insurance ID cannot be empty.");
            this.insuranceId = null;
            return;
        }

        // Accept IDs between 2 and 30 characters
        if (insuranceId.length() < 2 || insuranceId.length() > 30) {
            System.out.println("Warning: insurance ID length must be between 2 and 30.");
            this.insuranceId = null;
        } else {
            this.insuranceId = insuranceId.trim();
        }
    }

    public List<MedicalRecord> getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(List<MedicalRecord> medicalRecord) {
        if (HelperUtils.isNull(medicalRecord)) {
            this.medicalRecord = new ArrayList<>();
        } else {
            this.medicalRecord = medicalRecord;
        }
    }

    public List<Appointment> getAppointment() {
        return appointment;
    }

    public void setAppointment(List<Appointment> appointment) {
        if (HelperUtils.isNull(appointment)) {
            this.appointment = new ArrayList<>();
        } else {
            this.appointment = appointment;
        }
    }

    public void addMedicalRecord(MedicalRecord record) {
        if (HelperUtils.isNull(this.medicalRecord)) {
            this.medicalRecord = new ArrayList<>();
        }
        if (HelperUtils.isNotNull(record)) {
            this.medicalRecord.add(record);
            System.out.println("Medical record added for patient " + this.getPatientId());
        } else {
            System.out.println("Warning: null medical record not added.");
        }
    }

    public void addAppointment(Appointment appointment) {
        if (HelperUtils.isNull(this.appointment)) {
            this.appointment = new ArrayList<>();
        }
        if (HelperUtils.isNotNull(appointment)) {
            this.appointment.add(appointment);
            System.out.println("Appointment scheduled for patient " + this.getId());
        } else {
            System.out.println("Warning: null appointment not added.");
        }
    }

    public void updateInsurance(String newInsuranceId) {
        if (HelperUtils.isNotNull(newInsuranceId) && !newInsuranceId.trim().isEmpty()) {
            this.setInsuranceId(newInsuranceId);
            System.out.println("Insurance ID updated successfully to: " + newInsuranceId);
        } else {
            System.out.println("Insurance ID cannot be empty or null.");
        }
    }

    @Override
    public String displayInfo() {
        StringBuilder newStr = new StringBuilder();
        newStr.append(super.displayInfo("")).append(System.lineSeparator());
        newStr.append("Patient ID: ").append(patientId).append(System.lineSeparator());
        newStr.append("Blood Group: ").append(bloodGroup).append(System.lineSeparator());
        newStr.append("Emergency Contact: ").append(emergencyContact).append(System.lineSeparator());
        newStr.append("Insurance ID: ").append(insuranceId).append(System.lineSeparator());
        newStr.append("Registration Date: ").append(registrationDate).append(System.lineSeparator());

        if (HelperUtils.isNotNull(allergies) && !allergies.isEmpty()) {
            newStr.append("Allergies:").append(System.lineSeparator());
            for (Allergies allergy : allergies) {
                newStr.append("  - ").append(allergy.toString()).append(System.lineSeparator());
            }
        }
        newStr.append("Medical Records Count: ").append(medicalRecord == null ? 0 : medicalRecord.size()).append(System.lineSeparator());
        newStr.append("Appointment Count: ").append(appointment == null ? 0 : appointment.size());

        String out = newStr.toString();
        System.out.println(out);
        return out;
    }

    @Override
    public String displaySummary(String str) {
        return "Patient{" + getId() + ": " + getFirstName() + " " + getLastName() + "}";
    }

    @Override
    public String toString() {
        return "Patient ID: " + getId() +
                "\nName: " + getFirstName() + " " + getLastName() +
                "\nDOB: " + getDateOfBirth() +
                "\nGender: " + getGender() +
                "\nPhone: " + getPhoneNumber() +
                "\nEmail: " + getEmail() +
                "\nAddress: " + getAddress() +
                "\nBlood Group: " + getBloodGroup() +
                "\nEmergency Contact: " + getEmergencyContact() +
                "\nInsurance ID: " + getInsuranceId() +
                "\nRegistration Date: " + getRegistrationDate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        // Check if the Person's attributes are equal first
        if (!super.equals(o)) return false;

        Patient patient = (Patient) o;
        // Only check the unique identifier (ID) for equality
        return Objects.equals(this.getId(), patient.getId());
    }

    @Override
    public int hashCode() {
        // Generate the hash based on the ID, since it's the primary key
        return Objects.hash(super.hashCode(), this.getId());
    }

    // Overloaded methods to update contact information
    public void updateContact(String phone) {
        setPhoneNumber(phone);
        System.out.println("Phone number updated to: " + phone);
    }

    public void updateContact(String phone, String email) {
        setPhoneNumber(phone);
        setEmail(email);
        System.out.println("Phone number updated to: " + phone);
        System.out.println("Email updated to: " + email);
    }

    public void updateContact(String phone, String email, String address) {
        setPhoneNumber(phone);
        setEmail(email);
        setAddress(address);
        System.out.println("Phone number updated to: " + phone);
        System.out.println("Email updated to: " + email);
        System.out.println("Address updated to: " + address);
    }
}
