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
    }
    public Patient(String id, String firstName, String lastName, LocalDate dateOfBirth, String gender, String phoneNumber, String email, String address, String bloodGroup, List<Allergies> allergies, String emergencyContact, LocalDate registrationDate, String insuranceId, List<MedicalRecord> medicalRecord, List<Appointment> appointment) {
        super(id, firstName, lastName, dateOfBirth, gender, phoneNumber, email, address);
        this.bloodGroup = bloodGroup;
        this.allergies = allergies;
        this.emergencyContact = emergencyContact;
        this.registrationDate = registrationDate;
        this.insuranceId = insuranceId;
        this.medicalRecord = medicalRecord;
        this.appointment = appointment;
    }



    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public List<Allergies> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<Allergies> allergies) {
        this.allergies = allergies;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(String insuranceId) {
        this.insuranceId = insuranceId;
    }

    public List<MedicalRecord> getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(List<MedicalRecord> medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public List<Appointment> getAppointment() {
        return appointment;
    }

    public void setAppointment(List<Appointment> appointment) {
        this.appointment = appointment;
    }

    public void addMedicalRecord(MedicalRecord record) {
        if (this.medicalRecord == null) {
            this.medicalRecord = new ArrayList<>();
        }
        if (record != null) {
            this.medicalRecord.add(record);
            System.out.println("Medical record added for patient " + this.getId());
        }
    }

    public void addAppointment(Appointment appointment) {
        if (this.appointment == null) {
            this.appointment = new ArrayList<>();
        }
        if (appointment != null) {
            this.appointment.add(appointment);
            System.out.println("Appointment scheduled for patient " + this.getId());
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
