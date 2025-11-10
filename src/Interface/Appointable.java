package Interface;

import entity.Appointment;

import java.time.LocalDate;

public interface Appointable {
    void scheduleAppointment(Appointment appointment);
    boolean cancelAppointment(String appointmentId);
    void rescheduleAppointment(String appointmentId, LocalDate newDate);
}
