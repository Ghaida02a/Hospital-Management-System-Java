package main;

import Utils.HelperUtils;
import Utils.InputHandler;
import entity.*;
import service.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static service.DoctorService.*;
import static service.PatientService.*;

public class HospitalManagementApp {
    public static Scanner scanner = new Scanner(System.in);
    public static Integer mainMenuOption = 0;
    public static Integer option = 0;

    public static void main(String[] args) {
        System.out.println("Welcome to the Hospital Management System");
        while (mainMenuOption != 8) {
            showMainMenu();
            switch (mainMenuOption) {
                case 1 -> showPatientManagementMenu();
                case 2 -> showDoctorManagementMenu();
                case 3 -> showNurseManagementMenu();
                case 4 -> showAppointmentManagementMenu();
                case 5 -> showMedicalRecordsManagementMenu();
                case 6 -> showDepartmentManagementMenu();
                case 7 -> showReportsAndStatisticsMenu();
                case 8 -> {
                    System.out.println("Exiting the system... Goodbye!");
                    mainMenuOption = 8;
                }
                default -> System.out.println("Please enter a valid option (1–8).");
            }
        }
    }

    public static void showMainMenu() {
        System.out.println("===== Main Menu =====");
        System.out.print("""
                1- Patient Management
                2- Doctor Management
                3- Nurse Management
                4- Appointment Management
                5- Medical Records Management
                6- Department Management
                7- Reports and Statistics
                8- Exit
                """);
        System.out.println("===============");
        mainMenuOption = InputHandler.getIntInput("Please enter your choice: ");
    }

    private static void patientManagementMenu() {
        System.out.println("===== Patient Menu =====");
        System.out.print("""
                1- Register New Patient
                2- Register InPatient
                3- Register OutPatient
                4- Register Emergency Patient
                5- View All Patients
                6- Search Patient
                7- Update Patient Information
                8- Remove Patient
                9- View Patient Medical History
                10- Exit
                """);
        System.out.println("===============");
        option = InputHandler.getIntInput("Please enter your choice: ");
    }

    private static void showPatientManagementMenu() {
        option = 0;
        while (option != 10) {
            patientManagementMenu();
            switch (option) {
                case 1 -> {
                    Patient patient = PatientService.addPatient();
                    PatientService.savePatient(patient);
                    while (InputHandler.getConfirmation("Do you want to add allergies for this patient? ")) {
                        PatientService.addAllergyToPatient(patient.getId());
                    }
                    if (InputHandler.getConfirmation("Do you want to add a medical record now? ")) {
                        MedicalRecord record = MedicalRecordService.createRecordForPatient(patient);
                        patient.addMedicalRecord(record);
                        MedicalRecordService.saveRecord(record);
                    }
                }
                case 2 -> {
                    InPatient inPatient = InpatientRegistration();
                    PatientService.saveInPatient(inPatient);     //adds to inPatientList

                    long days = inPatient.calculateStayDuration(); //calculate stay duration
                    double total = inPatient.calculateTotalCharges(); //calculate total charges

                    System.out.println("Stay Duration: " + days + " days");
                    System.out.println("Total Charges: OMR " + total);
                }

                case 3 -> {
                    OutPatient outPatient = OutPatientRegistration();
                    PatientService.saveOutPatient(outPatient);

                    // First visit logged
                    outPatient.updateVisitCount(); //update visit count
                }
                case 4 -> {
                    EmergencyPatient ep = EmergencyPatientRegistration();
                    PatientService.saveEmergencyPatient(ep);

                    ep.prioritizeTreatment();
                    if ("Ambulance".equalsIgnoreCase(ep.getArrivalMode())) {
                        ep.admitThroughER(); //admit through ER
                    }
                }
                case 5 -> PatientService.displayAllPatients();
                case 6 -> {
                    String name = InputHandler.getStringInput("Enter patient name to search: ").toString();
                    List<Patient> results = PatientService.searchPatientsByName(name);

                    if (results.isEmpty()) {
                        System.out.println("No patients found matching \"" + name);
                    } else {
                        System.out.println("Found " + results.size() + " patient(s) matching \"" + name + "\":");
                        for (Patient p : results) {
                            System.out.println("- Name: " + p.getFirstName() + p.getLastName() + " (ID: " + p.getId() + ")");
                        }
                    }
                    System.out.println();
                }
                case 7 -> {
//                    String id = InputHandler.getStringInput("Enter patient ID to update: ");
//                    Patient updatedPatient = PatientService.addPatient(); // reuse input method
//                    PatientService.editPatient(id, updatedPatient);

                    String id = InputHandler.getStringInput("Enter patient ID to update: ");
                    Patient patient = PatientService.getPatientById(id);
                    if (patient != null) {
                        String newInsuranceId = InputHandler.getStringInput("Enter new Insurance ID: ");
                        patient.updateInsurance(newInsuranceId);
                    }
                }
                case 8 -> {
                    String id = InputHandler.getIntInput("Enter patient ID to remove: ").toString();
                    PatientService.removePatient(id);
                }
                case 9 -> {
                    Integer patientId = InputHandler.getIntInput("Enter patient ID to view medical history: ");
                    PatientService.viewPatientMedicalHistory(patientId);
                }
                case 10 -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Please enter a valid option (1–10).");
            }
        }
    }

    private static void doctorManagementMenu() {
        System.out.println("===== Doctor Menu =====");
        System.out.print("""
                1- Add Doctor
                2- Add Surgeon
                3- Add Consultant
                4- Add General Practitioner
                5- View All Doctors
                6- Search Doctor by Specialization
                7- View Available Doctors
                8- Assign Patient to Doctor
                9- Update Doctor Information
                10- Remove Doctor
                11- Exit
                """);
        System.out.println("===============");
        option = InputHandler.getIntInput("Please enter your choice: ");
    }

    private static void showDoctorManagementMenu() {
        while (option != 11) {
            doctorManagementMenu();
            switch (option) {
                case 1 -> {
                    DoctorService.save(DoctorService.addDoctor());
                }
                case 2 -> DoctorService.save(addSurgeon());
                case 3 -> DoctorService.save(addConsultant());
                case 4 -> DoctorService.save(addGeneralPractitioner());
                case 5 -> DoctorService.displayAllDoctors();
                case 6 -> {
                    String specialization = InputHandler.getStringInput("Enter specialization to search:");
                    List<Doctor> results = DoctorService.getDoctorsBySpecialization(specialization);

                    if (results.isEmpty()) {
                        System.out.println("No doctors found with specialization \"" + specialization + "\".");
                    } else {
                        System.out.println("Found " + results.size() + " doctor(s) with specialization \"" + specialization + "\":");
                        for (Doctor d : results) {
                            System.out.println("- Dr. " + d.getFirstName() + " " + d.getLastName() + " (ID: " + d.getId() + ")");
                        }
                    }
                    System.out.println();
                }
                case 7 -> {
                    List<Doctor> availableDoctors = DoctorService.getAvailableDoctors();

                    System.out.println("===== Available Doctors =====");
                    if (availableDoctors.isEmpty()) {
                        System.out.println("No available doctors found.\n");
                    } else {
                        for (Doctor doc : availableDoctors) {
                            System.out.println("- Dr. " + doc.getFirstName() + " " + doc.getLastName() + " (ID: " + doc.getId() + ")");
                        }
                        System.out.println();
                    }
                }
                case 8 -> {
                    String doctorId = InputHandler.getStringInput("Enter Doctor ID: ");
                    String patientId = InputHandler.getStringInput("Enter Patient ID to assign: ");
                    boolean ok = DoctorService.assignPatientToDoctor(doctorId, patientId);
                    if (ok) System.out.println("Assignment succeeded.");
                    else System.out.println("Assignment failed.");
                }
                case 9 -> {
                    String id = InputHandler.getStringInput("Enter doctor ID to update: ");
                    Doctor updatedDoctor = DoctorService.addDoctor();
                    DoctorService.editDoctor(id, updatedDoctor);
                }
                case 10 -> {
                    String id = InputHandler.getStringInput("Enter doctor ID to remove: ");
                    DoctorService.removeDoctor(id);
                }
                case 11 -> System.out.println("Exiting Doctor Management...");
                default -> System.out.println("Please enter a valid option (1–11).");
            }
        }
    }

    private static void nurseManagementMenu() {
        System.out.println("===== Nurse Management =====");
        System.out.print("""
                1. Add Nurse
                2. View All Nurses
                3. View Nurses by Department
                4. View Nurses by Shift
                5. Assign Nurse to Patient
                6. Update Nurse Information
                7. Remove Nurse
                8. Exit
                """);
        System.out.println("===============");
        option = InputHandler.getIntInput("Please enter your choice: ");
    }

    private static void showNurseManagementMenu() {
        while (option != 8) {
            nurseManagementMenu();
            switch (option) {
                case 1 -> {
                    Nurse nurse = NurseService.addNurse();
                    NurseService.save(nurse);
                }
                case 2 -> NurseService.displayAllNurses();
                case 3 -> {
                    String dept = InputHandler.getStringInput("Enter department ID to search: ");
                    List<Nurse> nursesByDepartment = NurseService.getNursesByDepartment(dept);
                    if (nursesByDepartment.isEmpty()) {
                        System.out.println("No nurses found in department \"" + dept + "\".");
                    } else {
                        System.out.println("Found " + nursesByDepartment.size() + " nurse(s):");
                        for (Nurse n : nursesByDepartment) {
                            System.out.println("- " + n.getFirstName() + " " + n.getLastName() + " (Nurse ID: " + n.getNurseId() + ")");
                        }
                    }
                    System.out.println();
                }
                case 4 -> {
                    String shift = InputHandler.getStringInput("Enter shift (Morning/Evening/Night) to search: ");
                    List<Nurse> res = NurseService.getNursesByShift(shift);
                    if (res.isEmpty()) {
                        System.out.println("No nurses found with shift \"" + shift + "\".");
                    } else {
                        System.out.println("Found " + res.size() + " nurse(s):");
                        for (Nurse n : res) {
                            System.out.println("- " + n.getFirstName() + " " + n.getLastName() + " (Nurse ID: " + n.getNurseId() + ")");
                        }
                    }
                    System.out.println();
                }
                case 5 -> {
                    String nurseId = InputHandler.getStringInput("Enter Nurse ID: ");
                    String patientId = InputHandler.getStringInput("Enter Patient ID to assign: ");
                    Nurse nurse = NurseService.getNurseById(nurseId);
                    Patient patient = PatientService.getPatientById(patientId);
                    if (nurse.assignPatient(patient)) {
                        System.out.println("Patient " + patient.getPatientId() + " assigned to Nurse " + nurse.getNurseId());
                    } else {
                        System.out.println("Assignment failed or patient already assigned.");
                    }

                }
                case 6 -> {
                    String id = InputHandler.getStringInput("Enter nurse ID to update: ");
                    Nurse updated = NurseService.addNurse();
                    NurseService.editNurse(id, updated);
                }
                case 7 -> {
                    String id = InputHandler.getStringInput("Enter nurse ID to remove: ");
                    NurseService.removeNurse(id);
                }
                case 8 -> System.out.println("Exiting Nurse Management...");
                default -> System.out.println("Please enter a valid option (1–8).");
            }
        }
    }

    private static void appointmentManagementMenu() {
        System.out.println("===== Appointment Management =====");
        System.out.print("""
                1- Add Appointment
                2- View All Appointments
                3- Search Appointments by Patient ID
                4- Search Appointments by Doctor ID
                5- Search Appointments by Date
                6- Reschedule Appointment
                7- Cancel Appointment
                8- Complete Appointment
                9- View Upcoming Appointments
                10- Exit
                """);
        System.out.println("===============");
        option = InputHandler.getIntInput("Please enter your choice: ");
    }

    private static void showAppointmentManagementMenu() {
        while (option != 10) {
            appointmentManagementMenu();
            switch (option) {
                case 1 -> {
                    Appointment appt = AppointmentService.addAppointment();
                    AppointmentService.save(appt);

                    Patient patient = PatientService.getPatientById(appt.getPatientId());
                    if (HelperUtils.isNotNull(patient)) {
                        patient.addAppointment(appt);
                    }

                    // Consultant-specific logic
                    Doctor doctor = DoctorService.getDoctorById(appt.getDoctorId());
                    if (doctor instanceof Consultant consultant) {
                        String type = InputHandler.getStringInput("Enter consultation type for this appointment: ");
                        consultant.scheduleConsultation(type);
                    }

                    // GP-specific logic
                    if (doctor instanceof GeneralPractitioner) {
                        GeneralPractitioner gp = (GeneralPractitioner) doctor;
                        if (gp.isHomeVisitAvailable()) {
                            String dateTime = InputHandler.getStringInput("Enter home visit date/time: ");
                            gp.scheduleHomeVisit(patient.getId(), dateTime);
                        }
                    }

                    //Each time an outpatient books or completes an appointment, should increment their visit count
                    if (patient instanceof OutPatient outPatient) {
                        outPatient.updateVisitCount();
                        if (InputHandler.getConfirmation("Do you want to schedule a follow-up? ")) {
                            LocalDate nextVisit = InputHandler.getDateInput("Enter follow-up date: ");
                            outPatient.scheduleFollowUp(nextVisit);
                        }
                    }
                }
                case 2 -> AppointmentService.displayAllAppointments();
                case 3 -> {
                    String pid = InputHandler.getStringInput("Enter patient ID: ");
                    List<entity.Appointment> res = AppointmentService.getAppointmentsByPatient(pid);
                    if (res.isEmpty()) System.out.println("No appointments found for patient " + pid);
                    else {
                        for (entity.Appointment a : res) a.displayInfo("");
                    }
                }
                case 4 -> {
                    String did = InputHandler.getStringInput("Enter doctor ID: ");
                    List<entity.Appointment> res = AppointmentService.getAppointmentsByDoctor(did);
                    if (res.isEmpty()) System.out.println("No appointments found for doctor " + did);
                    else {
                        for (entity.Appointment a : res) a.displayInfo("");
                    }
                }
                case 5 -> {
                    LocalDate date = InputHandler.getDateInput("Enter date (yyyy-MM-dd): ");
                    if (HelperUtils.isNull(date)) {
                        System.out.println("Invalid date format.");
                        break;
                    }
                    List<Appointment> res = AppointmentService.getAppointmentsByDate(date);
                    if (res.isEmpty()) {
                        System.out.println("No appointments found on " + date);
                    } else {
                        for (Appointment a : res) {
                            a.displayInfo("");
                        }
                    }
                }

                case 6 -> {
                    String aid = InputHandler.getStringInput("Enter appointment ID to reschedule: ");
                    LocalDate newDate = InputHandler.getDateInput("Enter new date");
                    String newTime = InputHandler.getStringInput("Enter new time (HH:MM): ");
                    boolean success = AppointmentService.rescheduleAppointment(aid, newDate, newTime);
                    if (!success) {
                        System.out.println("Rescheduling failed.");
                    }
                }

                case 7 -> {
                    String aid = InputHandler.getStringInput("Enter appointment ID to cancel: ");
                    boolean success = AppointmentService.cancelAppointmentById(aid);
                    if (!success) {
                        System.out.println("Cancellation failed.");
                    }
                }
                case 8 -> {
                    String aid = InputHandler.getStringInput("Enter appointment ID to complete: ");
                    boolean success = AppointmentService.completeAppointment(aid);
                    if (!success) {
                        System.out.println("Completion failed.");
                    }
                }
                case 9 -> {
                    List<Appointment> upcoming = AppointmentService.viewUpcomingAppointments();
                    if (HelperUtils.isNull(upcoming) || upcoming.isEmpty()) {
                        System.out.println("No upcoming appointments found.");
                    } else {
                        for (Appointment appointment : upcoming) {
                            appointment.displayInfo("");
                        }
                    }
                }
                case 10 -> System.out.println("Exiting Appointment Management...");
                default -> System.out.println("Please enter a valid option (1-10).");
            }
        }
    }

    private static void medicalRecordsManagementMenu() {
        System.out.println("===== Medical Records Management =====");
        System.out.print("""
                1- Create Medical Record
                2- View All Records
                3- View Records by Patient
                4- View Records by Doctor
                5- Update Medical Record
                6- Delete Medical Record
                7- Generate Patient History Report
                8- Exit
                """);
        System.out.println("===============");
        option = InputHandler.getIntInput("Please enter your choice: ");
    }

    private static void showMedicalRecordsManagementMenu() {
        while (option != 8) {
            medicalRecordsManagementMenu();
            switch (option) {
                case 1 -> {
                    MedicalRecord record = MedicalRecordService.createRecord();
                    MedicalRecordService.saveRecord(record);

                    // Consultant-specific logic
                    Doctor doctor = DoctorService.getDoctorById(record.getDoctorId());
                    if (doctor instanceof Consultant) {
                        Consultant consultant = (Consultant) doctor;
                        String caseDetails = InputHandler.getStringInput("Enter case details for second opinion: ");
                        consultant.provideSecondOpinion(caseDetails);
                    }

                    // GP-specific logic
                    if (doctor instanceof GeneralPractitioner) {
                        GeneralPractitioner gp = (GeneralPractitioner) doctor;
                        if (gp.isVaccinationCertified()) {
                            String vaccineName = InputHandler.getStringInput("Enter vaccine name: ");
                            gp.administerVaccine(record.getPatientId(), vaccineName);
                        }
                    }
                }
                case 2 -> MedicalRecordService.displayAllRecords();
                case 3 -> {
                    System.out.print("Enter patient ID: ");
                    String pid = scanner.nextLine();
                    List<entity.MedicalRecord> recordsByPatient = MedicalRecordService.displayRecordsByPatient(pid);
                    if (recordsByPatient.isEmpty()) System.out.println("No medical records found for patient " + pid);
                    else {
                        for (entity.MedicalRecord r : recordsByPatient) {
                            r.displayInfo("");
                            System.out.println("---------------------------");
                        }
                    }
                }
                case 4 -> {
                    System.out.print("Enter doctor ID: ");
                    String did = scanner.nextLine();
                    List<entity.MedicalRecord> recordsByDoctor = MedicalRecordService.displayRecordsByDoctor(did);
                    if (recordsByDoctor.isEmpty()) System.out.println("No medical records found for doctor " + did);
                    else {
                        for (entity.MedicalRecord r : recordsByDoctor) {
                            r.displayInfo("");
                            System.out.println("---------------------------");
                        }
                    }
                }
                case 5 -> {
                    System.out.print("Enter record ID to update: ");
                    String updatedRecordId = scanner.nextLine();
                    MedicalRecord updatedRecord = MedicalRecordService.createRecord();
                    MedicalRecordService.updateRecord(updatedRecordId, updatedRecord);
                }
                case 6 -> {
                    System.out.print("Enter record ID to delete: ");
                    String recordId = scanner.nextLine();
                    MedicalRecordService.deleteRecord(recordId);
                }
                case 7 -> {
                    System.out.print("Enter patient ID for history report: ");
                    String pid = scanner.nextLine();
                    MedicalRecordService.generatePatientHistoryReport(pid);
                }
                case 8 -> System.out.println("Exiting Appointment Management...");
                default -> System.out.println("Please enter a valid option (1-8).");
            }
        }
    }

    private static void departmentManagementMenu() {
        System.out.println("===== Department Management =====");
        System.out.print("""
                1- Add Department
                2- View All Departments
                3- View Department Details
                4- Assign Doctor to Department
                5- Assign Nurse to Department
                6- Update Department Information
                7- View Department Statistics
                8- Exit
                """);
        System.out.println("===============");
        option = InputHandler.getIntInput("Please enter your choice: ");
    }

    private static void showDepartmentManagementMenu() {
        while (option != 8) {
            departmentManagementMenu();
            switch (option) {
                case 1 -> {
                    Department dept = DepartmentService.addDepartment();
                    DepartmentService.saveDepartment(dept);
                }
                case 2 -> DepartmentService.displayAllDepartments();
                case 3 -> {
                    String departmentId = InputHandler.getStringInput("Enter Department ID: ");
                    Department d = DepartmentService.getDepartmentById(departmentId);
                    if (d == null) {
                        System.out.println("Department not found: " + departmentId);
                    } else {
                        System.out.println("Name: " + d.getDepartmentName());
                        System.out.println("Head Doctor ID: " + d.getHeadDoctorId());
                        System.out.println("Bed Capacity: " + d.getBedCapacity());
                        System.out.println("Available Beds: " + d.getAvailableBeds());
                    }
                }
                case 4 -> {
                    String doctorId = InputHandler.getStringInput("Enter Doctor ID: ");
                    String deptId = InputHandler.getStringInput("Enter Department ID: ");
                    DepartmentService.assignDoctorToDepartment(doctorId, deptId);
                }
                case 5 -> {
                    String nurseId = InputHandler.getStringInput("Enter Nurse ID: ");
                    String deptId = InputHandler.getStringInput("Enter Department ID: ");
                    DepartmentService.assignNurseToDepartment(nurseId, deptId);
                }
                case 6 -> {
                    String departmentId = InputHandler.getStringInput("Enter Department ID to update: ");
                    Department updatedDept = DepartmentService.addDepartment();
                    DepartmentService.updateDepartment(departmentId, updatedDept);
                }
                case 7 -> {
                    String departmentId = InputHandler.getStringInput("Enter Department ID: ");
                    Department d = DepartmentService.getDepartmentById(departmentId);
                    if (d == null) {
                        System.out.println("Department not found: " + departmentId);
                    } else {
                        System.out.println("Department Statistics for " + d.getDepartmentName() + ":");
                        System.out.println("Bed Capacity: " + d.getBedCapacity());
                        System.out.println("Available Beds: " + d.getAvailableBeds());
                    }
                }
                case 8 -> System.out.println("Exiting Department Management...");
                default -> System.out.println("Please enter a valid option (1-8).");
            }
        }
    }

    private static void reportsAndStatisticsMenu() {
        System.out.println("===== Report And Statistics =====");
        System.out.print("""
                1- Daily Appointments Report
                2- Doctor Performance Report
                3- Department Occupancy Report
                4- Patient Statistics
                5- Emergency Cases Report
                6- Exit
                """);
        System.out.println("===============");
        option = InputHandler.getIntInput("Please enter your choice: ");
    }

    private static void showReportsAndStatisticsMenu() {
        while (option != 6) {
            reportsAndStatisticsMenu();
            switch (option) {
                case 1 -> {
                    LocalDate date = InputHandler.getDateInput("Enter date for the report");
                    ReportService.generateDailyAppointmentsReport(date);
                }
                case 2 -> {
                    String doctorId = InputHandler.getStringInput("Enter Doctor ID for performance report (or leave blank for all doctors): ");
                    ReportService.generateDoctorPerformanceReport(doctorId);
                }
                case 3 -> {
                    String departmentId = InputHandler.getStringInput("Enter Department ID for occupancy report (or leave blank for all departments): ");
                    ReportService.generateDepartmentOccupancyReport(departmentId);
                }
                case 4 -> {
                    String patientId = InputHandler.getStringInput("Enter Patient ID for statistics report: ");
                    Patient patient = PatientService.getPatientById(patientId);

                    if (patient instanceof OutPatient outPatient) {
                        System.out.println("OutPatient Visit Count: " + outPatient.getVisitCount());
                        System.out.println("Last Visit Date: " + outPatient.getLastVisitDate());
                    }

                    ReportService.generatePatientStatisticsReport(patientId);
                }
                case 5 -> {
                    String patientId = InputHandler.getStringInput("Enter Patient ID for emergency cases report: ");
                    ReportService.generateEmergencyCasesReport(patientId);
                }
                case 6 -> System.out.println("Exiting Report Management...");
                default -> System.out.println("Please enter a valid option (1-6).");
            }
        }
    }
}