package main;

import Utils.InputHandler;
import entity.*;
import service.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class HospitalManagementApp {
    public static Scanner scanner = new Scanner(System.in);
    public static Integer mainMenuOption = 0;

    public static void main(String[] args) {
        System.out.println("Welcome to the Hospital Management System");
        while (mainMenuOption != 8) {
            showMainMenu();
            mainMenuOption = scanner.nextInt();
            scanner.nextLine();
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
        System.out.print("Please enter your choice: ");
    }

    private static void patientManagementMenu() {
        System.out.println("===== Main Menu =====");
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
        System.out.print("Please enter your choice: ");
    }

    private static void showPatientManagementMenu() {
        int option = 0;

        while (option != 10) {
            patientManagementMenu();
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> {
                    Patient patient = PatientService.addPatient();
                    PatientService.save(patient);


                    if(InputHandler.getConfirmation("Do you want to add allergies for this patient? ")){
                        PatientService.addAllergyToPatient(patient.getId());
                    }
                }
                case 2 -> System.out.println("Register InPatient – coming soon...");
                case 3 -> System.out.println("Register OutPatient – coming soon...");
                case 4 -> System.out.println("Register Emergency Patient – coming soon...");
                case 5 -> PatientService.displayAllPatients();
                case 6 -> {
                    System.out.print("Enter patient name to search: ");
                    String name = scanner.nextLine();
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
                    System.out.print("Enter patient ID to update: ");
                    String id = scanner.nextLine();
                    Patient updatedPatient = PatientService.addPatient(); // reuse input method
                    PatientService.editPatient(id, updatedPatient);
                }
                case 8 -> {
                    System.out.print("Enter patient ID to remove: ");
                    String id = scanner.nextLine();
                    PatientService.removePatient(id);
                }
                case 9 -> System.out.println("View Patient Medical History – coming soon...");
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
        System.out.print("Please enter your choice: ");
    }

    private static void showDoctorManagementMenu() {
        int option = 0;

        while (option != 11) {
            doctorManagementMenu();
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> {
                    Doctor doctor = DoctorService.addDoctor();
                    DoctorService.save(doctor);
                }
                case 2 -> {
                    //Surgeon surgeon = SurgeonService.addSurgeon();
                    //DoctorService.save(surgeon);
                }
                case 3 -> {
//                    Consultant consultant = ConsultantService.addConsultant();
//                    DoctorService.save(consultant);
                }
                case 4 -> System.out.println(" Add General Practitioner – coming soon...");
                case 5 -> DoctorService.displayAllDoctors();
                case 6 -> {
                    System.out.print("Enter specialization to search: ");
                    String specialization = scanner.nextLine();
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
                    System.out.println("Available Doctors:");
                    for (Doctor doc : DoctorService.getAvailableDoctors()) {
                        System.out.println("- Dr. " + doc.getFirstName() + " " + doc.getLastName() + " (ID: " + doc.getId() + ")");
                    }
                    System.out.println();
                }
                case 8 -> {
                    System.out.print("Enter Doctor ID: ");
                    String doctorId = scanner.nextLine();
                    System.out.print("Enter Patient ID to assign: ");
                    String patientId = scanner.nextLine();
                    boolean ok = DoctorService.assignPatientToDoctor(doctorId, patientId);
                    if (ok) System.out.println("Assignment succeeded.");
                    else System.out.println("Assignment failed.");
                }
                case 9 -> {
                    System.out.print("Enter doctor ID to update: ");
                    String id = scanner.nextLine();
                    Doctor updatedDoctor = DoctorService.addDoctor();
                    DoctorService.editDoctor(id, updatedDoctor);
                }
                case 10 -> {
                    System.out.print("Enter doctor ID to remove: ");
                    String id = scanner.nextLine();
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
                1- Add Nurse
                2- View All Nurses
                3- Search Nurses by Department
                4- Search Nurses by Shift
                5- Update Nurse Information
                6- Remove Nurse
                7- Exit
                """);
        System.out.println("===============");
        System.out.print("Please enter your choice: ");
    }

    private static void showNurseManagementMenu() {
        int option = 0;

        while (option != 7) {
            nurseManagementMenu();
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> {
                    Nurse nurse = NurseService.addNurse();
                    NurseService.save(nurse);
                }
                case 2 -> NurseService.displayAllNurses();
                case 3 -> {
                    System.out.print("Enter department ID to search: ");
                    String dept = scanner.nextLine();
                    List<Nurse> nursesByDepartment = NurseService.getNursesByDepartment(dept);
                    if (nursesByDepartment.isEmpty()) {
                        System.out.println("No nurses found in department \"" + dept + "\".");
                    } else {
                        System.out.println("Found " + nursesByDepartment.size() + " nurse(s):");
                        for (Nurse n : nursesByDepartment) {
                            System.out.println("- " + n.getFirstName() + " " + n.getLastName() + " (ID: " + n.getId() + ")");
                        }
                    }
                    System.out.println();
                }
                case 4 -> {
                    System.out.print("Enter shift (Morning/Evening/Night) to search: ");
                    String shift = scanner.nextLine();
                    List<Nurse> res = NurseService.getNursesByShift(shift);
                    if (res.isEmpty()) {
                        System.out.println("No nurses found with shift \"" + shift + "\".");
                    } else {
                        System.out.println("Found " + res.size() + " nurse(s):");
                        for (Nurse n : res) {
                            System.out.println("- " + n.getFirstName() + " " + n.getLastName() + " (ID: " + n.getId() + ")");
                        }
                    }
                    System.out.println();
                }
                case 5 -> {
                    System.out.print("Enter nurse ID to update: ");
                    String id = scanner.nextLine();
                    Nurse updated = NurseService.addNurse();
                    NurseService.editNurse(id, updated);
                }
                case 6 -> {
                    System.out.print("Enter nurse ID to remove: ");
                    String id = scanner.nextLine();
                    NurseService.removeNurse(id);
                }
                case 7 -> System.out.println("Exiting Nurse Management...");
                default -> System.out.println("Please enter a valid option (1–7).");
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
        System.out.print("Please enter your choice: ");
    }

    private static void showAppointmentManagementMenu() {
        int option = 0;
        while (option != 10) {
            appointmentManagementMenu();
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1 -> {
                    var appt = AppointmentService.addAppointment();
                    AppointmentService.save(appt);
                }
                case 2 -> AppointmentService.displayAllAppointments();
                case 3 -> {
                    System.out.print("Enter patient ID: ");
                    String pid = scanner.nextLine();
                    List<entity.Appointment> res = AppointmentService.getAppointmentsByPatient(pid);
                    if (res.isEmpty()) System.out.println("No appointments found for patient " + pid);
                    else {
                        for (entity.Appointment a : res) a.displayInfo("");
                    }
                }
                case 4 -> {
                    System.out.print("Enter doctor ID: ");
                    String did = scanner.nextLine();
                    List<entity.Appointment> res = AppointmentService.getAppointmentsByDoctor(did);
                    if (res.isEmpty()) System.out.println("No appointments found for doctor " + did);
                    else {
                        for (entity.Appointment a : res) a.displayInfo("");
                    }
                }
                case 5 -> {
                    LocalDate date = null;
                    while (true) {
                        System.out.print("Enter date (yyyy-MM-dd): ");
                        String d = scanner.nextLine();
                        try {
                            date = LocalDate.parse(d);
                            break;
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                        }
                    }
                    List<entity.Appointment> res = AppointmentService.getAppointmentsByDate(date);
                    if (res.isEmpty()) System.out.println("No appointments found on " + date);
                    else for (entity.Appointment a : res) a.displayInfo("");
                }
                case 6 -> {
                    System.out.print("Enter appointment ID to reschedule: ");
                    String aid = scanner.nextLine();
                    LocalDate newDate = null;
                    while (true) {
                        System.out.print("Enter new date (yyyy-MM-dd): ");
                        String d = scanner.nextLine();
                        try {
                            newDate = LocalDate.parse(d);
                            break;
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format.");
                        }
                    }
                    String newTime;
                    while (true) {
                        System.out.print("Enter new time (HH:mm): ");
                        newTime = scanner.nextLine();
                        if (newTime.matches("^([01]?\\d|2[0-3]):[0-5]\\d$")) {
                            break;
                        }
                        System.out.println("Invalid time format.");
                    }
                    AppointmentService.rescheduleAppointment(aid, newDate, newTime);
                }
                case 7 -> {
                    System.out.print("Enter appointment ID to cancel: ");
                    String aid = scanner.nextLine();
                    AppointmentService.cancelAppointment(aid);
                }
                case 8 -> {
                    System.out.print("Enter appointment ID to complete: ");
                    String aid = scanner.nextLine();
                    AppointmentService.completeAppointment(aid);
                }
                case 9 -> AppointmentService.viewUpcomingAppointments();
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
        System.out.print("Please enter your choice: ");
    }

    private static void showMedicalRecordsManagementMenu() {
        int option = 0;
        while (option != 8) {
            medicalRecordsManagementMenu();
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1 -> {
                    MedicalRecord record = MedicalRecordService.createRecord();
                    MedicalRecordService.saveRecord(record);
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
        System.out.print("Please enter your choice: ");
    }

    private static void showDepartmentManagementMenu() {
        int option = 0;
        while (option != 8) {
            departmentManagementMenu();
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1 -> {
                    Department dept = DepartmentService.addDepartment();
                    DepartmentService.saveDepartment(dept);
                }
                case 2 -> DepartmentService.displayAllDepartments();
                case 3 -> {
                    System.out.print("Enter Department ID to view details: ");
                    String departmentId = scanner.nextLine();
                    Department d = DepartmentService.getDepartmentById(departmentId);
                    if (d == null) System.out.println("Department not found: " + departmentId);
                    else {
                        System.out.println("Name: " + d.getDepartmentName());
                        System.out.println("Head Doctor ID: " + d.getHeadDoctorId());
                        System.out.println("Bed Capacity: " + d.getBedCapacity());
                        System.out.println("Available Beds: " + d.getAvailableBeds());
                    }
                }
                case 4 -> {
                    System.out.print("Enter Department ID: ");
                    String departmentId = scanner.nextLine();
                    System.out.print("Enter Doctor ID to assign: ");
                    String doctorId = scanner.nextLine();
                    boolean assignDoctorToDepartment = DepartmentService.assignDoctorToDepartment(departmentId, doctorId);
                    if (assignDoctorToDepartment) {
                        System.out.println("Doctor assigned successfully.");
                    } else {
                        System.out.println("Failed to assign doctor.");
                    }
                }
                case 5 -> {
                    System.out.print("Enter Department ID: ");
                    String departmentId = scanner.nextLine();
                    System.out.print("Enter Nurse ID to assign: ");
                    String nurseId = scanner.nextLine();
                    boolean assignNurseToDepartment = DepartmentService.assignNurseToDepartment(departmentId, nurseId);
                    if (assignNurseToDepartment) {
                        System.out.println("Nurse assigned successfully.");
                    } else {
                        System.out.println("Failed to assign nurse.");
                    }
                }
                case 6 -> {
                    System.out.print("Enter Department ID to update: ");
                    String departmentId = scanner.nextLine();
                    Department updatedDept = DepartmentService.addDepartment();
                    DepartmentService.updateDepartment(departmentId, updatedDept);
                }
                case 7 -> {
                    System.out.print("Enter Department ID to view statistics: ");
                    String departmentId = scanner.nextLine();
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
        System.out.println("===== Department Management =====");
        System.out.print("""
                1- Daily Appointments Report
                2- Doctor Performance Report
                3- Department Occupancy Report
                4- Patient Statistics
                5- Emergency Cases Report
                6- Exit
                """);
        System.out.println("===============");
        System.out.print("Please enter your choice: ");
    }

    private static void showReportsAndStatisticsMenu() {
        int option = 0;
        while (option != 6) {
            reportsAndStatisticsMenu();
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1 -> System.out.println("still");
                case 2 -> System.out.println("still");
                case 3 -> System.out.println("still");
                case 4 -> System.out.println("still");
                case 5 -> System.out.println("still");
                case 6 -> System.out.println("Exiting Report Management...");
                default -> System.out.println("Please enter a valid option (1-8).");
            }
        }
    }
}