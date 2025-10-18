package entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Patient extends Person {
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



    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        if (bloodGroup == null || bloodGroup.trim().isEmpty()) {
            this.bloodGroup = null;
            return;
        }
        String normalized = bloodGroup.trim().toUpperCase().replaceAll("\\s+", "");
        // Accept forms like A+, A-, B+, B-, AB+, AB-, O+, O-
        if (normalized.matches("^(A|B|AB|O)[+-]$")) {
            this.bloodGroup = normalized;
        } else {
            System.out.println("Warning: invalid blood group '" + bloodGroup + "'. Expected one of A+, A-, B+, B-, AB+, AB-, O+, O-. Setting to null.");
            this.bloodGroup = null;
        }
    }

    public List<Allergies> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<Allergies> allergies) {
        if (allergies == null) {
            this.allergies = new ArrayList<>();
        } else {
            this.allergies = allergies;
        }
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        if (emergencyContact == null || emergencyContact.trim().isEmpty()) {
            this.emergencyContact = null;
            return;
        }
        String normalized = emergencyContact.trim();
        // Allow digits, spaces, dashes and leading +. Require at least 7 digits overall.
        String digitsOnly = normalized.replaceAll("[^0-9]", "");
        if (digitsOnly.length() < 7) {
            System.out.println("Warning: emergency contact '" + emergencyContact + "' appears too short. Setting to null.");
            this.emergencyContact = null;
            return;
        }
        if (normalized.matches("^[+]?[0-9][0-9\s-]{5,}$")) {
            this.emergencyContact = normalized;
        } else {
            System.out.println("Warning: emergency contact '" + emergencyContact + "' contains invalid characters. Setting to null.");
            this.emergencyContact = null;
        }
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        if (registrationDate == null) {
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
        if (insuranceId == null || insuranceId.trim().isEmpty()) {
            this.insuranceId = null;
            return;
        }
        // Basic validation: length between 3 and 50 and no control characters
        String trimmed = insuranceId.trim();
        if (trimmed.length() < 3 || trimmed.length() > 50 || trimmed.matches(".*\\p{Cntrl}.*")) {
            System.out.println("Warning: insurance ID appears invalid. Setting to null.");
            this.insuranceId = null;
        } else {
            this.insuranceId = trimmed;
        }
    }

    public List<MedicalRecord> getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(List<MedicalRecord> medicalRecord) {
        if (medicalRecord == null) {
            this.medicalRecord = new ArrayList<>();
        } else {
            this.medicalRecord = medicalRecord;
        }
    }

    public List<Appointment> getAppointment() {
        return appointment;
    }

    public void setAppointment(List<Appointment> appointment) {
        if (appointment == null) {
            this.appointment = new ArrayList<>();
        } else {
            this.appointment = appointment;
        }
    }

    public void addMedicalRecord(MedicalRecord record) {
        if (this.medicalRecord == null) {
            this.medicalRecord = new ArrayList<>();
        }
        if (record != null) {
            this.medicalRecord.add(record);
            System.out.println("Medical record added for patient " + this.getId());
        } else {
            System.out.println("Warning: null medical record not added.");
        }
    }

    public void addAppointment(Appointment appointment) {
        if (this.appointment == null) {
            this.appointment = new ArrayList<>();
        }
        if (appointment != null) {
            this.appointment.add(appointment);
            System.out.println("Appointment scheduled for patient " + this.getId());
        } else {
            System.out.println("Warning: null appointment not added.");
        }
    }

    public void updateInsurance(String newInsuranceId) {
        if (newInsuranceId != null && !newInsuranceId.trim().isEmpty()) {
            this.setInsuranceId(newInsuranceId);
            System.out.println("Insurance ID updated successfully to: " + newInsuranceId);
        } else {
            System.out.println("Insurance ID cannot be empty or null.");
        }
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Blood Group: " + bloodGroup);
        System.out.println("Emergency Contact: " + emergencyContact);
        System.out.println("Insurance ID: " + insuranceId);
        System.out.println("Registration Date: " + registrationDate);

        if (allergies != null && !allergies.isEmpty()) {
            System.out.println("Allergies:");
            for (Allergies allergy : allergies) {
                System.out.println("  - " + allergy);
            }
        }
        if (medicalRecord != null && !medicalRecord.isEmpty()) {
            System.out.println("Medical Records Count: " + medicalRecord.size());
        }
        if (appointment != null && !appointment.isEmpty()) {
            System.out.println("Appointment Count: " + appointment.size());
        }
    }

    @Override
    public String toString() {
        return "Patient{" +
                super.toString() + // Retrieves all fields from Person
                ", bloodGroup='" + bloodGroup + '\'' +
                ", emergencyContact='" + emergencyContact + '\'' +
                ", registrationDate=" + registrationDate +
                ", insuranceId='" + insuranceId + '\'' +
                '}';
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
}
