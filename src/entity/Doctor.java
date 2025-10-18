package entity;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Iterator;

public class Doctor extends Person{
    private String doctorId;
    private String specialization;
    private String qualification;
    private int experienceYears;
    private String departmentId;
    private double consultationFee;
    private List<Integer> availableSlots;
    private boolean available = false;
    private List<Patient> assignedPatients;

    public Doctor() {
        super();
        // ensure lists are initialized
        this.availableSlots = new ArrayList<>();
        this.assignedPatients = new ArrayList<>();
    }
    public Doctor(String id, String firstName, String lastName, LocalDate dateOfBirth, String gender, String phoneNumber, String email, String address) {
        super(id, firstName, lastName, dateOfBirth, gender, phoneNumber, email, address);
        // initialize lists
        this.availableSlots = new ArrayList<>();
        this.assignedPatients = new ArrayList<>();
    }

    public Doctor(String id, String firstName, String lastName, LocalDate dateOfBirth, String gender, String phoneNumber, String email, String address, String specialization, String qualification, int experienceYears, String departmentId, double consultationFee, List<Integer> availableSlots, List<Patient> assignedPatients) {
        super(id, firstName, lastName, dateOfBirth, gender, phoneNumber, email, address);
        // use setters so validations apply
        setSpecialization(specialization);
        setQualification(qualification);
        setExperienceYears(experienceYears);
        setDepartmentId(departmentId);
        setConsultationFee(consultationFee);
        setAvailableSlots(availableSlots);
        setAssignedPatients(assignedPatients);
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        if (doctorId == null || doctorId.trim().isEmpty()) {
            System.out.println("Warning: doctorId is null or empty; leaving unset.");
            this.doctorId = null;
            return;
        }
        String trimmed = doctorId.trim();
        if (trimmed.contains(" ")) {
            System.out.println("Warning: doctorId contains spaces; trimming whitespace.");
            trimmed = trimmed.replaceAll("\\s+", "");
        }
        this.doctorId = trimmed;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        if (specialization == null || specialization.trim().isEmpty()) {
            this.specialization = "General"; // sensible default
            return;
        }
        this.specialization = specialization.trim();
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        if (qualification == null || qualification.trim().isEmpty()) {
            this.qualification = "Unknown";
            return;
        }
        this.qualification = qualification.trim();
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        if (experienceYears < 0) {
            System.out.println("Warning: experienceYears cannot be negative; setting to 0.");
            this.experienceYears = 0;
            return;
        }
        this.experienceYears = experienceYears;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        if (departmentId == null || departmentId.trim().isEmpty()) {
            this.departmentId = null;
            return;
        }
        this.departmentId = departmentId.trim();
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(double consultationFee) {
        if (Double.isNaN(consultationFee) || consultationFee < 0) {
            System.out.println("Warning: consultationFee must be non-negative; setting to 0.0.");
            this.consultationFee = 0.0;
            return;
        }
        this.consultationFee = consultationFee;
    }

    public List<Integer> getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(List<Integer> availableSlots) {
        if (availableSlots == null) {
            this.availableSlots = new ArrayList<>();
            return;
        }
        // validate 0-23, remove duplicates and preserve order
        Set<Integer> seen = new LinkedHashSet<>();
        for (Integer s : availableSlots) {
            if (s == null) {
                System.out.println("Warning: null slot ignored.");
                continue;
            }
            if (s < 0 || s > 23) {
                System.out.println("Warning: invalid slot '" + s + "' ignored. Expected 0-23.");
                continue;
            }
            seen.add(s);
        }
        this.availableSlots = new ArrayList<>(seen);
    }

    public List<Patient> getAssignedPatients() {
        return assignedPatients;
    }

    public void setAssignedPatients(List<Patient> assignedPatients) {
        if (assignedPatients == null) {
            this.assignedPatients = new ArrayList<>();
            return;
        }
        // deduplicate by patient ID
        List<Patient> unique = new ArrayList<>();
        Set<String> seenIds = new LinkedHashSet<>();
        for (Patient p : assignedPatients) {
            if (p == null) {
                System.out.println("Warning: null patient in assigned list ignored.");
                continue;
            }
            String pid = p.getId();
            if (pid == null || pid.trim().isEmpty()) {
                System.out.println("Warning: patient with null/empty ID ignored.");
                continue;
            }
            if (seenIds.add(pid)) {
                unique.add(p);
            } else {
                System.out.println("Warning: duplicate patient ID '" + pid + "' ignored.");
            }
        }
        this.assignedPatients = unique;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Specialization: " + specialization);
        System.out.println("Qualification: " + qualification);
        System.out.println("Experience Years: " + experienceYears);
        System.out.println("Department Id: " + departmentId);
        System.out.println("Consultation Fee: " + consultationFee);
        System.out.println("Available: " + available);
        System.out.println("Available Slots: " + (availableSlots == null ? "[]" : availableSlots.toString()));
        System.out.println("Assigned Patients Count: " + (assignedPatients == null ? 0 : assignedPatients.size()));
    }

    public boolean isAvailable() {
        // doctor is considered available if explicitly marked available or has non-empty slots
        return available || (availableSlots != null && !availableSlots.isEmpty());
    }

    public void setAvailable(boolean availability) {
        this.available = availability;
        // If setting unavailable, clear slots to reflect the state
        if (!availability) {
            if (availableSlots != null) {
                availableSlots.clear();
            } else {
                availableSlots = new ArrayList<>();
            }
        }
    }

    public boolean assignPatient(Patient patient) {
        if (patient == null) {
            System.out.println("Cannot assign null patient.");
            return false;
        }
        String pid = patient.getId();
        if (pid == null || pid.trim().isEmpty()) {
            System.out.println("Cannot assign patient with null/empty ID.");
            return false;
        }
        if (assignedPatients == null) {
            assignedPatients = new ArrayList<>();
        }
        for (Patient p : assignedPatients) {
            if (p != null && pid.equals(p.getId())) {
                System.out.println("Patient with ID " + pid + " is already assigned to doctor " + this.getDoctorId());
                return false;
            }
        }
        assignedPatients.add(patient);
        System.out.println("Assigned patient ID " + pid + " to doctor " + this.getDoctorId());
        return true;
    }

    public boolean removePatient(String patientId) {
        if (patientId == null || patientId.trim().isEmpty()) {
            System.out.println("Invalid patient ID provided for removal.");
            return false;
        }
        if (assignedPatients == null || assignedPatients.isEmpty()) {
            System.out.println("No assigned patients to remove from doctor " + this.getDoctorId());
            return false;
        }
        Iterator<Patient> it = assignedPatients.iterator();
        while (it.hasNext()) {
            Patient p = it.next();
            if (p != null && patientId.equals(p.getId())) {
                it.remove();
                System.out.println("Removed patient ID " + patientId + " from doctor " + this.getDoctorId());
                return true;
            }
        }
        System.out.println("Patient ID " + patientId + " not found among assigned patients for doctor " + this.getDoctorId());
        return false;
    }

    public void updateAvailability(List<Integer> newSlots) {
        if (newSlots == null) {
            if (this.availableSlots != null) {
                this.availableSlots.clear();
            } else {
                this.availableSlots = new ArrayList<>();
            }
            this.available = false;
            System.out.println("Doctor " + this.getDoctorId() + " availability cleared (no slots).");
            return;
        }
        // Validate and filter slots to be within 0-23
        Set<Integer> seen = new LinkedHashSet<>();
        for (Integer s : newSlots) {
            if (s == null) {
                System.out.println("Warning: null slot ignored.");
                continue;
            }
            if (s < 0 || s > 23) {
                System.out.println("Warning: invalid slot '" + s + "' ignored. Expected 0-23.");
                continue;
            }
            seen.add(s);
        }
        List<Integer> validated = new ArrayList<>(seen);
        this.availableSlots = validated;
        // set availability flag based on whether any slots are present
        if (validated.isEmpty()) {
            this.available = false;
            System.out.println("Doctor " + this.getDoctorId() + " has no valid slots and is now unavailable.");
        } else {
            this.available = true;
            System.out.println("Doctor " + this.getDoctorId() + " availability updated: " + validated);
        }
    }

}
