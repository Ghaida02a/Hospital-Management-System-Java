package entity;

import Interface.Displayable;
import Utils.HelperUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Appointment implements Displayable {
    private String appointmentId;
    private String patientId;
    private String doctorId;
    private LocalDate appointmentDate;
    private String appointmentTime;
    private String status; // Scheduled/Completed/Cancelled/Rescheduled
    private String reason;
    private String notes;

    public Appointment(String appointmentId, String patientId, String doctorId, LocalDate appointmentDate, String appointmentTime, String status, String reason, String notes) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
        this.reason = reason;
        this.notes = notes;
    }

    public Appointment() {

    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        validateId(appointmentId, "appointmentId");
        this.appointmentId = appointmentId;
    }

    private void validateId(String id, String fieldName) {
        if (HelperUtils.isNull(id) || id.isEmpty()) {
            System.out.println(fieldName + " cannot be empty.");
        } else {
            System.out.println(fieldName + " is valid.");
        }
    }


    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        validateId(patientId, "patientId");
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        validateId(doctorId, "doctorId");
        this.doctorId = doctorId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        if (appointmentDate.isBefore(LocalDate.now())) {
            System.out.println("Appointment date cannot be in the past.");
        } else {
            this.appointmentDate = appointmentDate;
        }
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        if (HelperUtils.isNull(appointmentTime) || appointmentTime.isEmpty()) {
            System.out.println("Appointment time cannot be empty.");
        } else {
            this.appointmentTime = appointmentTime;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (HelperUtils.isNull(status) || status.isEmpty()) {
            System.out.println("Status cannot be empty.");
        } else {
            this.status = status;
        }
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        if (HelperUtils.isNull(reason) || reason.isEmpty()) {
            System.out.println("Reason cannot be empty.");
        } else {
            this.reason = reason;
        }
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        if (HelperUtils.isNull(notes) || notes.isEmpty()) {
            System.out.println("Notes cannot be empty.");
        } else {
            this.notes = notes;
        }
    }

    public void reschedule(LocalDate newDate, String newTime) {
        this.appointmentDate = newDate;
        this.appointmentTime = newTime;
        this.status = "Rescheduled";
    }

    public void cancel() {
        this.status = "Cancelled";
    }

    public void complete() {
        this.status = "Completed";
    }

    @Override
    public String displayInfo(String str) {
        System.out.println("Appointment Id: " + appointmentId);
        System.out.println("Patient Id: " + patientId);
        System.out.println("Doctor Id: " + doctorId);
        System.out.println("Appointment Date: " + appointmentDate);
        System.out.println("Appointment Time: " + appointmentTime);
        System.out.println("Status: " + status);
        System.out.println("Reason: " + reason);
        System.out.println("Notes: " + notes);
        return "";
    }

    @Override
    public String displaySummary(String str) {
        return "Appointment{" +
                ", patientId='" + patientId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", appointmentDate=" + appointmentDate +
                ", appointmentTime='" + appointmentTime + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    // Methods Overloading
    public void addNotes(String notes) {
        this.notes = notes;
    }

    public void addNotes(String notes, String addedBy) {
        this.notes = notes + " (Added by: " + addedBy + ")";
    }

    public void addNotes(String notes, String addedBy, LocalDateTime timestamp) {
        this.notes = notes + " (Added by: " + addedBy + " on " + timestamp + ")";
    }
}
